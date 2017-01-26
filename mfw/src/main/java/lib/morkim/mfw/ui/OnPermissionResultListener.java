package lib.morkim.mfw.ui;

public interface OnPermissionResultListener {
	void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults);
}
