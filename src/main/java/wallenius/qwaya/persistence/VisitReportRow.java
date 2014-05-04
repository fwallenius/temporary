package wallenius.qwaya.persistence;

/**
 *
 * @author fwallenius
 */
public class VisitReportRow {

    private String url;
    private Integer pageViews;
    private Integer visitors;

    public VisitReportRow(String url, Integer pageViews, Integer visitors) {
        this.url = url;
        this.pageViews = pageViews;
        this.visitors = visitors;
    }

    public String getUrl() {
        return url;
    }

    public Integer getPageViews() {
        return pageViews;
    }

    public Integer getVisitors() {
        return visitors;
    }

    @Override
    public String toString() {
        return "VisitReportRow{" + "url=" + url + ", pageViews=" + pageViews + ", visitors=" + visitors + '}';
    }
    
}
