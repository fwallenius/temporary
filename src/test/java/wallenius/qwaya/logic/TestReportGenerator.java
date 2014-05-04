package wallenius.qwaya.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import wallenius.qwaya.persistence.PageVisitRepository;
import wallenius.qwaya.persistence.VisitReportRow;

/**
 *
 * @author fwallenius
 */
@RunWith(MockitoJUnitRunner.class)
public class TestReportGenerator {
    
    @Mock
    private PageVisitRepository visitRepo;
    
    @InjectMocks
    private TextualReportGenerator reportGenerator;  //Class under test
    
    private List<VisitReportRow> mockedRepoResult;

    @Test
    public void shouldCreateExpectedRowFormat() {
        this.resetSetup();
        this.addResultRowToCurrentMock(new VisitReportRow("/", 2, 1));
        
        List<String> result = this.reportGenerator.getReport(new Date(1), new Date());
        String dataRow = result.get(2);
        
        assertEquals(3, result.size());
        assertTrue(dataRow.contains("/ "));
        assertTrue("Expected amount of page views before amount of visitors on line 1",
                dataRow.indexOf("2") < dataRow.indexOf("1"));
    }
    
    @Test
    public void shouldIncludeAllDataRows() {
        this.resetSetup();
        this.addResultRowToCurrentMock(new VisitReportRow("/", 2, 1));
        this.addResultRowToCurrentMock(new VisitReportRow("/other", 22, 11));
        
        List<String> result = this.reportGenerator.getReport(new Date(1), new Date());
        
        assertEquals(4, result.size());
    }
    
    @Test
    public void shouldIncludeCorrectDatesInMethodCall() {
        Date from = new Date(1000000);
        Date to = new Date(2000000);
        
        this.reportGenerator.getReport(from, to);
        
        ArgumentCaptor<Date> argument1 = ArgumentCaptor.forClass(Date.class);
        ArgumentCaptor<Date> argument2 = ArgumentCaptor.forClass(Date.class);
        verify(this.visitRepo).generateReport(argument1.capture(), argument2.capture());
        
        assertEquals(from.getTime(), argument1.getValue().getTime());
        assertEquals(to.getTime(), argument2.getValue().getTime());
    }
    
    private void resetSetup() {
        reset(this.visitRepo);
        mockedRepoResult = new ArrayList<>();
        when(this.visitRepo.generateReport(any(Date.class), any(Date.class))).thenReturn(mockedRepoResult);
    }
    
    private void addResultRowToCurrentMock(VisitReportRow row) {
        mockedRepoResult.add(row);
    } 
}
