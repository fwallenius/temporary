package wallenius.qwaya.controllers;

import wallenius.qwaya.logic.CookieUtil;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import wallenius.qwaya.logic.VisitSaver;

/**
 *
 * @author fwallenius
 */
@RunWith(MockitoJUnitRunner.class)
public class TestPixelController {
    
    @Mock
    CookieUtil cookieUtil;
    @Mock
    VisitSaver dataSaver;
    
    @InjectMocks
    PixelController controller;

    @Test
    public void shouldWriteCorrectContentType() throws IOException {
        
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        MockHttpServletResponse mockResponse = new MockHttpServletResponse();
        
        controller.track(mockRequest, mockResponse);
        
        assertEquals("image/gif", mockResponse.getContentType());
    }
    
}
