package Response;

public class RegistrationResponse {
    private int responseType;
    public RegistrationResponse(int responseType) {
        this.responseType = responseType;
    }

    public String getResponse() {
        switch (responseType) {
            case 200:
                return null;
            case 400:
                return "\"message\": \"Error: bad request\"";
            case 403:
                return "\"message\": \"Error: username taken\"";
            default:
                return "\"message\": \"Error: unexpected error\"";
        }

    }

}
