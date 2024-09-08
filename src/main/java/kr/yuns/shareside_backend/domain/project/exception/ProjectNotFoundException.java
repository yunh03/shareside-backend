package kr.yuns.shareside_backend.domain.project.exception;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException() {
        super();
    }
    public ProjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public ProjectNotFoundException(String message) {
        super(message);
    }
    public ProjectNotFoundException(Throwable cause) {
        super(cause);
    }
}
