package lib.morkim.mfw.app;


public interface Analytics {

	void initialize();
	void sendEvent(String category, String action);
	void sendEvent(String category, String action, long value);
	void sendEvent(String category, String action, String label, long value);

}