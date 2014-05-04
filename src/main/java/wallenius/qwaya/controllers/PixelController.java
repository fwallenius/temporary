package wallenius.qwaya.controllers;

import wallenius.qwaya.logic.CookieUtil;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.commons.codec.binary.Base64;
import wallenius.qwaya.logic.VisitSaver;

/**
 *
 * @author fwallenius
 */
@Controller
public class PixelController {
    
    private static final String PIXEL_BASE64 = "R0lGODlhAQABAPAAAAAAAAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==";
    private static final byte[] PIXEL_BYTES = Base64.decodeBase64(PIXEL_BASE64.getBytes());
    
    @Resource
    private VisitSaver dataSaver;
    
    @Resource
    private CookieUtil cookieUtil;
    
    @RequestMapping("/pixel.gif")
    public void track(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        
        final String userId = cookieUtil.getAndSetIfNotExistUserId(request, response);
        dataSaver.extractInfoAndSave(userId, request);
        
        this.writePixelResponse(response);
    }
    
    private void writePixelResponse(final HttpServletResponse resp) throws IOException {
        resp.setContentType("image/gif");
        resp.getOutputStream().write(PIXEL_BYTES);
    }

}
