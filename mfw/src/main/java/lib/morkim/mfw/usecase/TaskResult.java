package lib.morkim.mfw.usecase;

public abstract class TaskResult {

	int completionPercent = 100;

	public int getCompletionPercent() {
		return completionPercent;
	}

	public void setCompletionPercent(int completionPercent) {
		this.completionPercent = completionPercent;
	}
}
