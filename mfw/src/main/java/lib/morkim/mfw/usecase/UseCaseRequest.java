package lib.morkim.mfw.usecase;


public class UseCaseRequest {

	public static final UseCaseRequest EMPTY = new EmptyRequest(null);

	private static class EmptyRequest extends UseCaseRequest {

		protected EmptyRequest(RequestBuilder builder) {
			super(builder);
		}
	}

	protected UseCaseRequest(RequestBuilder builder) {

	}

	public static abstract class RequestBuilder<Req> implements Builder<Req> {

	}

	protected interface Builder<Req> {
		Req build();
	}
}
