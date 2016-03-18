package lib.morkim.mfw.usecase;


public class TaskRequest {

	public static abstract class RequestBuilder<Req> implements Builder<Req> {

	}

	public interface Builder<Req> {
		Req build();
	}
}
