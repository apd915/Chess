package Response;

import model.AuthData;

public class LogoutResponse {

    int responseType;

    public LogoutResponse(int responseType) {
        this.responseType = responseType;
    }

    public Object getResponse() {
        switch (responseType) {
            case 200:
                return null;
            case 401:
                return new ErrorResponse("Error: unauthorized");
            default:
                return new ErrorResponse("Error: unexpected error");
        }

    }
}
