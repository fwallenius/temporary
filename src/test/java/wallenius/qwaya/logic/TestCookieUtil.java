package wallenius.qwaya.logic;

import wallenius.qwaya.logic.CookieUtil;
import javax.servlet.http.Cookie;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 *
 * @author fwallenius
 */
public class TestCookieUtil {
    
    private final String USER_ID_COOKIE_KEY = "qwaya_pixel_track_userid";
    
    @Test
    public void shouldSetCookieIfNoneExists() {
        
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        MockHttpServletResponse mockResponse = new MockHttpServletResponse();
        CookieUtil cookieUtil = new CookieUtil();
        
        String response = cookieUtil.getAndSetIfNotExistUserId(mockRequest, mockResponse);
        String cookieContent = mockResponse.getCookie(USER_ID_COOKIE_KEY).getValue();
        
        assertNotNull(response);
        assertEquals(response, cookieContent);
    }
    
    @Test
    public void shouldReturnExisitingCookie() {
        
        final String mockCookieContent = "foo";
        
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.setCookies(new Cookie(USER_ID_COOKIE_KEY, mockCookieContent));
        
        MockHttpServletResponse mockResponse = new MockHttpServletResponse();
        CookieUtil cookieUtil = new CookieUtil();
        
        String response = cookieUtil.getAndSetIfNotExistUserId(mockRequest, mockResponse);
        
        assertEquals(mockCookieContent, response);
    }
   
}
