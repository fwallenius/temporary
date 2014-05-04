package wallenius.qwaya;

import java.util.Date;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import wallenius.qwaya.logic.DateParser;
import wallenius.qwaya.logic.TextualReportGenerator;
import wallenius.qwaya.persistence.PageVisitRepository;
import wallenius.qwaya.persistence.SQLitePageVisitRepository;
import wallenius.qwaya.persistence.VisitReportRow;

/**
 *
 * @author fwallenius
 */
@ComponentScan
@EnableAutoConfiguration
public class Bootstrap {

    private static final String SERVER_COMMAND = "server";
    private static final String REPORT_COMMAND = "report";

    public Bootstrap() {
        // No-args constructor needed by Spring
    }

    public Bootstrap(String[] args) throws ClassNotFoundException {

        if (args == null || args.length == 0) {
            this.printUsageInfoAndExit();
        }

        switch (args[0]) {
            case SERVER_COMMAND:
                SpringApplication.run(Bootstrap.class, args);
                break;
            case REPORT_COMMAND:
                this.tryParseDateArgumentsAndPrintReport(args);
                break;
            default:
                this.printUsageInfoAndExit();
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        new Bootstrap(args);
    }

    private void printUsageInfoAndExit() {
        System.out.println("usage: java -jar pixel-stats-0.1.0.jar COMMAND [options]");
        System.out.println("   where COMMAND should be replaced with '" + SERVER_COMMAND + "' to start in server mode");
        System.out.println("   or '" + REPORT_COMMAND + "' to generate and output a report.");
        System.out.println("");
        System.out.println("   When generating a report two optional parameters can be appended: startDate and endDate.");
        System.out.println("   The dates should be in format '2013-09-01 09:00:00' (use single or double quotes around the dates!)");
        System.out.println("");
        System.out.println("   Examples:");
        System.out.println("     $ java -jar pixel-stats-0.1.0.jar server");
        System.out.println("     $ java -jar pixel-stats-0.1.0.jar report");
        System.out.println("     $ java -jar pixel-stats-0.1.0.jar report '2013-09-01 09:00:00' '2014-01-01 00:00:00'");
        System.exit(0);
    }

    private void tryParseDateArgumentsAndPrintReport(final String[] args) throws ClassNotFoundException {
        String inputFromDate = "", inputToDate = "";

        if (args.length > 1) {
            inputFromDate = args[1];
        }
        if (args.length > 2) {
            inputToDate = args[2];
        }
        
        final Date from = this.parseDateFromString(inputFromDate, 1);
        final Date to = this.parseDateFromString(inputToDate, System.currentTimeMillis());
        
        TextualReportGenerator generator = new TextualReportGenerator(new SQLitePageVisitRepository());
        List<String> reportRows = generator.getReport(from, to);

        for (String row : reportRows) {
            System.out.println(row);
        }
    }

    private Date parseDateFromString(String inputFromDate, long defaultTime) {
        Date parsedDate = DateParser.getParsedDateOrNull(inputFromDate);
        if (parsedDate == null) {
            return new Date(defaultTime);
        } else {
            return parsedDate;
        }
    }
}
