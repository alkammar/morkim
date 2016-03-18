package lib.morkim.mfw.usecase;


public class TaskRequest {

	private TaskRequest(RequestBuilder builder) {

	}

	public static abstract class RequestBuilder<Req> implements Builder<Req> {

	}

	protected interface Builder<Req> {
		Req build();
	}
}
