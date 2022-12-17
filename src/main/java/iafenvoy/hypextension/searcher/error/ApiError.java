package iafenvoy.hypextension.searcher.error;

public class ApiError extends Exception {
    private String url = "";

    public ApiError() {
        super();
    }

    public ApiError(String message) {
        super(message);
    }

    public ApiError(Throwable e) {
        super(e);
    }

    public String getUrl() {
        return url;
    }

    public ApiError setUrl(String url) {
        this.url = url;
        return this;
    }
}
