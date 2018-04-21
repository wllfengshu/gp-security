package app.wllfengshu.exception;

public class NotAcceptableException extends Exception {
	private static final long serialVersionUID = 1L;

	public NotAcceptableException(String message) {
		super(message);
	}

	public NotAcceptableException() {
		super();
	}
}
