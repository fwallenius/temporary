
package wallenius.qwaya.logic;

import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author fwallenius
 */
@RunWith(MockitoJUnitRunner.class)
public class TestDateParser {
    
    @Test
    public void shouldReturnCorrectDate() {
        
        Date result = DateParser.getParsedDateOrNull("2001-07-01 14:00:00");
        assertEquals(993988800000L, (long) result.getTime());
    }
    
    @Test
    public void shouldReturnNullOnWeirdInput() {
        
        Date result = DateParser.getParsedDateOrNull("2001-07-01");
        assertNull(result);
    }
}
