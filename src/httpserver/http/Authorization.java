/*package httpserver.http;

import java.util.Arrays;
import java.util.List;

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

    public static boolean isValidToken(String token) {
        for (Authorization authorization : Authorization.values()) {
            if (token.equals(authorization.token)) {
                return true;
            }
        }
        return false;
    }

    public String getAuthorizationHeader() {
        return "Authorization: Bearer " + token;
    }
}
*/