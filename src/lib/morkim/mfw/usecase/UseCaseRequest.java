package lib.morkim.mfw.usecase;


public class UseCaseRequest {

	public UseCaseStateListener listener;

	public static class Builder {

		private UseCaseStateListener listener;

		public Builder listener(UseCaseStateListener value) {
			listener = value;
			return this;
		}

		public UseCaseRequest build() {
			return new UseCaseRequest(this);
		}
	}

	protected UseCaseRequest(Builder builder) {
		listener = builder.listener;
	}
}
