package httpserver.http;

public enum Authorization {

    //BEARER("admin-mtcgToken");
    //BEARER("kienboec-mtcgToken"");
    KIENBOEC("kienboec-mtcgToken"),
    ADMIN("admin-mtcgToken"),
    ALTENHOF("altenhof-mtcgToken");

    public final String token;

    Authorization(String token) {
        this.token = token;
    }
    //String authorizationKienboec = Authorization.KIENBOEC.getAuthorizationHeader(
    public String getAuthorizationHeader() {
        return "Authorization: Bearer " + token;
    }
}