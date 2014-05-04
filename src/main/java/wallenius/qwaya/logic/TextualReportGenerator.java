package wallenius.qwaya.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import wallenius.qwaya.persistence.PageVisitRepository;
import wallenius.qwaya.persistence.VisitReportRow;

/**
 *
 * @author fwallenius
 */
public class TextualReportGenerator {

    private final static int COLWIDTH1 = 30, COLWIDTH2 = 12, COLWIDTH3 = 10;
    
    @Autowired
    private PageVisitRepository visitRepo;

    public TextualReportGenerator(final PageVisitRepository dependency) {
        this.visitRepo = dependency;
    }

    public List<String> getReport(final Date from, final Date to) {

        List<String> result = new ArrayList<>();

        List<VisitReportRow> data = visitRepo.generateReport(from, to);
        this.addDateHeader(from, to, result);
        this.addReportHeader(result);
        this.addRows(data, result);

        return result;
    }

    private void addDateHeader(Date from, Date to, List<String> result) {
        result.add("Data collected between " + from + " and " + to);
    }

    private void addReportHeader(List<String> result) {

        StringBuilder sb = new StringBuilder();
        sb.append(this.padRight("|url", COLWIDTH1));
        sb.append(this.padRight("|page views", COLWIDTH2));
        sb.append(this.padRight("|visitors", COLWIDTH3));
        result.add(sb.toString());
    }

    private void addRows(List<VisitReportRow> data, List<String> result) {

        for (VisitReportRow dataRow : data) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.padRight("|" + dataRow.getUrl(), COLWIDTH1));
            sb.append(this.padRight("|" + dataRow.getPageViews(), COLWIDTH2));
            sb.append(this.padRight("|" + dataRow.getVisitors(), COLWIDTH3));
            result.add(sb.toString());
        }
    }

    private String padRight(String text, int padding) {
        return String.format("%1$-" + padding + "s", text);
    }
}
