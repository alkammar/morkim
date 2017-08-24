package lib.morkim.mfw.app;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lib.morkim.mfw.usecase.EmptyUseCase;
import lib.morkim.mfw.usecase.UseCasePendingResult;
import lib.morkim.mfw.usecase.UseCaseRequest;
import lib.morkim.mfw.usecase.UseCaseResult;
import lib.morkim.mfw.usecase.UndoRecord;
import lib.morkim.mfw.usecase.UseCase;
import lib.morkim.mfw.usecase.UseCaseCreator;
import lib.morkim.mfw.usecase.UseCaseDependencies;
import lib.morkim.mfw.usecase.UseCaseListener;

class UseCaseManagerImpl implements UseCaseManager {

	private Map<Class<? extends UseCase>, List<UseCaseListener<? extends UseCaseResult>>> useCasesListeners;
	private List<UndoRecord> undoRecords;
	private Map<Class<? extends UseCase>, UseCase> stickyUseCases;

	private static final UseCaseResult PENDING_RESULT = new UseCasePendingResult();

	UseCaseManagerImpl() {

		useCasesListeners = new HashMap<>();
		undoRecords = new ArrayList<>();
		stickyUseCases = new HashMap<>();
	}

	@Override
	public void subscribeToUseCase(Class<? extends UseCase>[] taskClasses, UseCaseListener<? extends UseCaseResult> listener) {

		synchronized (this) {

			for (Class<? extends UseCase> taskClass : taskClasses) {

				List<UseCaseListener<? extends UseCaseResult>> useCaseListeners = useCasesListeners.get(taskClass);

				if (useCaseListeners == null) {
					useCaseListeners = new ArrayList<>();
					useCasesListeners.put(taskClass, useCaseListeners);
				}

				useCaseListeners.add(listener);
			}
		}
	}

	@Override
	public void unsubscribeFromUseCase(Class<? extends UseCase>[] taskClasses, UseCaseListener listener) {

		synchronized (this) {

			for (Class<? extends UseCase> taskClass : taskClasses) {

				List<UseCaseListener<? extends UseCaseResult>> useCaseListeners = useCasesListeners.get(taskClass);

				if (useCaseListeners != null) {
					useCaseListeners.remove(listener);

					if (useCaseListeners.isEmpty())
						useCasesListeners.remove(taskClass);
				}
			}
		}
	}

	@Override
	public List<UseCaseListener<? extends UseCaseResult>> getUseCaseSubscriptions(Class<? extends UseCase> aClass) {
		synchronized (this) {
			List<UseCaseListener<? extends UseCaseResult>> useCaseListeners = useCasesListeners.get(aClass);
			return useCaseListeners != null ? useCaseListeners : new ArrayList<UseCaseListener<? extends UseCaseResult>>();
		}
	}

	@Override
	public void addToUndoStack(UseCase useCase, UseCaseRequest request) {

		undoRecords.add(new UndoRecord(useCase.getClass(), request));
	}

	@Override
	public <u extends UseCase> UseCase popUseCaseStack(UseCaseDependencies dependencies) {

		if (undoRecords.size() > 0) {

			UndoRecord undoRecord = undoRecords.get(undoRecords.size() - 1);

			return new UseCaseCreator<u>()
					.create(undoRecord.<u>getUseCaseClass())
					.with(dependencies)
					.setRequest(undoRecord.getRequest());
		}

		return new EmptyUseCase();
	}

	@Override
	public void clearUndoStack() {
		undoRecords.clear();
	}

	@Override
	public void addToStickyUseCases(UseCase useCase) {
		// TODO optimization : needs to be synchronized for the specific use case
		synchronized (this) {
			stickyUseCases.put(useCase.getClass(), useCase);
		}
	}

	@Override
	public UseCaseResult getStickyResult(Class<? extends UseCase> cls) {
		synchronized (this) {
			if (stickyUseCases.containsKey(cls)) {
				UseCaseResult stickyResult = stickyUseCases.get(cls).getStickyResult();
				return stickyResult == null ? PENDING_RESULT : stickyResult;
			} else
				return null;
		}
	}

	@Override
	public void removeSticky(Class<? extends UseCase> useCaseClass) {
		synchronized (this) {
			stickyUseCases.remove(useCaseClass);
		}
	}

	@Override
	public void clearSticky() {
		synchronized (this) {
			stickyUseCases.clear();
		}
	}

	@Override
	public void runOnUi(Runnable runnable) {
		new Handler(Looper.getMainLooper()).post(runnable);
	}
}
