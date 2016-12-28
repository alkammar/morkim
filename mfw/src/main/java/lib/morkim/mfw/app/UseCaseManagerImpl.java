package lib.morkim.mfw.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lib.morkim.mfw.usecase.EmptyUseCase;
import lib.morkim.mfw.usecase.TaskRequest;
import lib.morkim.mfw.usecase.TaskResult;
import lib.morkim.mfw.usecase.UndoRecord;
import lib.morkim.mfw.usecase.UseCase;
import lib.morkim.mfw.usecase.UseCaseCreator;
import lib.morkim.mfw.usecase.UseCaseDependencies;
import lib.morkim.mfw.usecase.UseCaseListener;

class UseCaseManagerImpl implements UseCaseManager {

	private Map<Class<? extends UseCase>, List<UseCaseListener<? extends TaskResult>>> useCasesListeners;
	private List<UndoRecord> undoRecords;

	UseCaseManagerImpl() {

		useCasesListeners = new HashMap<>();
		undoRecords = new ArrayList<>();
	}

	public void subscribeToUseCase(Class<? extends UseCase>[] taskClasses, UseCaseListener<? extends TaskResult> listener) {

		synchronized (this) {

			for (Class<? extends UseCase> taskClass : taskClasses) {

				List<UseCaseListener<? extends TaskResult>> useCaseListeners = useCasesListeners.get(taskClass);

				if (useCaseListeners == null) {
					useCaseListeners = new ArrayList<>();
					useCasesListeners.put(taskClass, useCaseListeners);
				}

				useCaseListeners.add(listener);
			}
		}
	}

	public void unsubscribeFromUseCase(Class<? extends UseCase>[] taskClasses, UseCaseListener listener) {

		synchronized (this) {

			for (Class<? extends UseCase> taskClass : taskClasses) {

				List<UseCaseListener<? extends TaskResult>> useCaseListeners = useCasesListeners.get(taskClass);

				if (useCaseListeners != null) {
					useCaseListeners.remove(listener);

					if (useCaseListeners.isEmpty())
						useCasesListeners.remove(taskClass);
				}
			}
		}
	}

	public List<UseCaseListener<? extends TaskResult>> getUseCaseSubscriptions(Class<? extends UseCase> aClass) {
		synchronized (this) {
			List<UseCaseListener<? extends TaskResult>> useCasekListeners = useCasesListeners.get(aClass);
			return useCasekListeners != null ? useCasekListeners : new ArrayList<UseCaseListener<? extends TaskResult>>();
		}
	}

	public void addToUndoStack(UseCase useCase, TaskRequest request) {

		undoRecords.add(new UndoRecord(useCase.getClass(), request));
	}

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

	public void clearUndoStack() {
		undoRecords.clear();
	}
}
