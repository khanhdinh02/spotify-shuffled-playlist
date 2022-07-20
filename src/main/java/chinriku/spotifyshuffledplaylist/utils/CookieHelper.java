package chinriku.spotifyshuffledplaylist.utils;

import javax.servlet.http.Cookie;

public class CookieHelper {

    public static String getCookiesValue(Cookie[] cookies, String cookiesName) {
        String value = null;
        for (Cookie c : cookies) {
            if (c.getName().equals(cookiesName)) {
                value = c.getValue();
                break;
            }
        }
        return value;
    }
}
