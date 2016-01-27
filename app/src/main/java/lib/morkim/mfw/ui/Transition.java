package lib.morkim.mfw.ui;

public enum Transition {
	NONE, RIGHT, LEFT;

	public static Transition fromValue(int value) {
		if (value > 0)
			return LEFT;
		else if (value < 0)
			return RIGHT;
		else
			return NONE;
	}
}
