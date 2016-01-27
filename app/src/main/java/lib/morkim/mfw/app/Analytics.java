package lib.morkim.mfw.app;


public interface Analytics {

	public abstract void initialize();
	public abstract void sendEvent(String category, String action);
	public abstract void sendEvent(String category, String action, long value);

}