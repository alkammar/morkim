package lib.morkim.mfw.usecase;


public class UseCaseRequest {

	public static class Builder {

		public UseCaseRequest build() {
			return new UseCaseRequest(this);
		}
	}

	protected UseCaseRequest(Builder builder) {
	}
}
