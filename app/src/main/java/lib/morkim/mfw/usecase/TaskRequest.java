package lib.morkim.mfw.usecase;


public class TaskRequest {

	public static final TaskRequest EMPTY = new EmptyRequest(null);

	private static class EmptyRequest extends TaskRequest {

		protected EmptyRequest(RequestBuilder builder) {
			super(builder);
		}
	}

	protected TaskRequest(RequestBuilder builder) {

	}

	public static abstract class RequestBuilder<Req> implements Builder<Req> {

	}

	protected interface Builder<Req> {
		Req build();
	}
}
