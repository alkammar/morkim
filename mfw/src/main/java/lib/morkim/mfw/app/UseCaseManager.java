package lib.morkim.mfw.app;

import java.util.List;

import lib.morkim.mfw.usecase.TaskRequest;
import lib.morkim.mfw.usecase.TaskResult;
import lib.morkim.mfw.usecase.UseCase;
import lib.morkim.mfw.usecase.UseCaseDependencies;
import lib.morkim.mfw.usecase.UseCaseListener;

public interface UseCaseManager {

	public void subscribeToUseCase(Class<? extends UseCase>[] taskClasses, UseCaseListener<? extends TaskResult> listener);

	public void unsubscribeFromUseCase(Class<? extends UseCase>[] taskClasses, UseCaseListener listener);

	public List<UseCaseListener<? extends TaskResult>> getUseCaseSubscriptions(Class<? extends UseCase> aClass);

	public void addToUndoStack(UseCase useCase, TaskRequest request);

	public <u extends UseCase> UseCase popUseCaseStack(UseCaseDependencies dependencies);

	public void clearUndoStack();
}
