package wallenius.qwaya.persistence;

import java.util.Date;
import java.util.List;

/**
 *
 * @author fwallenius
 */
public interface PageVisitRepository {
 
    void save(Visit visit);
    
    List<VisitReportRow> generateReport(Date fromTime, Date toTime);
}
