package wallenius.qwaya.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author fwallenius
 */
public class DateParser {
    
    private static final String PATTERN = "yyyy-MM-dd hh:mm:ss";
    
    public static Date getParsedDateOrNull(String input) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN);
        try {
            return dateFormat.parse(input);
        } catch (ParseException ex) {
            // Intentionally left empty, we don't really care about this
        }
        return null;
    }
}
