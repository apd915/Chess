package Response;

import gameModels.GameID;

public class JoinGameResponse {
    int responseType;
    public JoinGameResponse(int responseType) {
        this.responseType = responseType;
    }

    public Object getResponse() {
        switch (responseType) {
            case 200:
                return null;
            case 400:
                return new ErrorResponse("Error: bad request");
            case 401:
                return new ErrorResponse("Error: unauthorized");
            case 403:
                return new ErrorResponse("Error: already taken");
            default:
                return new ErrorResponse("Error: unexpected error");
            }
        }
    }

