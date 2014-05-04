package wallenius.qwaya.logic;

import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import wallenius.qwaya.persistence.PageVisitRepository;
import wallenius.qwaya.persistence.Visit;


/**
 *
 * @author fwallenius
 */
@RunWith(MockitoJUnitRunner.class)
public class TestVisitSaver {
    
    private static final String REFERRER_HEADER = "referer";
    
    @Mock
    private PageVisitRepository visitRepo;
    
    @InjectMocks
    private VisitSaver visitSaver;
    
    @Test
    public void shouldSaveWhenGettingCorrectData() {
        final String userId = "foo", path = "path";
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(REFERRER_HEADER, path);
        
        this.visitSaver.extractInfoAndSave(userId, request);
        
        ArgumentCaptor<Visit> argument = ArgumentCaptor.forClass(Visit.class);
        verify(this.visitRepo).save(argument.capture());
        Visit savedEntity = argument.getValue();
        
        assertEquals(userId, savedEntity.getUserId());
        assertEquals(path, savedEntity.getPath());
    }
    
    @Test
    public void shouldSetTimeStampToCurrentTime() {
        final String userId = "foo", path = "path";
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(REFERRER_HEADER, path);
        
        this.visitSaver.extractInfoAndSave(userId, request);
        
        ArgumentCaptor<Visit> argument = ArgumentCaptor.forClass(Visit.class);
        verify(this.visitRepo).save(argument.capture());
        Date savedTime = argument.getValue().getTimeStamp();
        
        assertTrue(savedTime.getTime() <= System.currentTimeMillis());
        assertTrue(savedTime.getTime() > (System.currentTimeMillis() - 500));
    }
    
    @Test
    public void shouldNotSaveWhenNoReferrer() {
        final String userId = "foo";
        final MockHttpServletRequest request = new MockHttpServletRequest();
        
        this.visitSaver.extractInfoAndSave(userId, request);
        
        verify(this.visitRepo, never()).save(any(Visit.class));
    }
    
    @Test
    public void shouldNotSaveWhenGettingFunkyReferrer() {
        final String userId = "foo", path = "not a valid path";
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(REFERRER_HEADER, path);
        
        this.visitSaver.extractInfoAndSave(userId, request);
        
        verify(this.visitRepo, never()).save(any(Visit.class));
    }
    
}
