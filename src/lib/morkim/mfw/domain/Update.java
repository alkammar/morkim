package lib.morkim.mfw.domain;

public class Update {

	public int type;
	public Object data;

	public Update() {

	}

	public Update(int type) {

		this.type = type;
	}

	public Update(int type, Object data) {

		this.type = type;
		this.data = data;
	}
}