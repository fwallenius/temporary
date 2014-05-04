package wallenius.qwaya.logic;

import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/**
 *
 * @author fwallenius
 */
@Component
public class CookieUtil {

    private final static String USER_ID_COOKIE_KEY = "qwaya_pixel_track_userid";
    private final static int COOKIE_MAX_AGE = 60 * 60 * 24 * 365 * 20; // Should be enough

    public String getAndSetIfNotExistUserId(HttpServletRequest request, HttpServletResponse response) {
        
        final Cookie oldCookie = this.findMyCookieInRequest(request);
        
        if (oldCookie == null) {
            return this.createNewUserIdAndSetCookie(response);
        } else {
            return oldCookie.getValue();
        }
    }

    private Cookie findMyCookieInRequest(final HttpServletRequest request) {
        
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        
        for (Cookie cookie : cookies) {
            if (USER_ID_COOKIE_KEY.equals(cookie.getName())) {
                return cookie;
            }
        }
        return null;    
    }

    private String createNewUserIdAndSetCookie(final HttpServletResponse response) {
        
        final String newUserId = UUID.randomUUID().toString();
        final Cookie newCookie = new Cookie(USER_ID_COOKIE_KEY, newUserId);
        newCookie.setMaxAge(COOKIE_MAX_AGE);
        response.addCookie(newCookie);
        
        return newUserId;
    }
}
