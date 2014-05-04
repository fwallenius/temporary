package wallenius.qwaya.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Very simple persistence using SQLite. Untested and with hard-coded
 * parameters. Just as a POC.
 *
 * @author fwallenius
 */
@Component
public class SQLitePageVisitRepository implements PageVisitRepository {

    private static final String DBNAME = "visits.db";
    private static final String TABLE = "visit";
    private static final Logger LOG = LoggerFactory.getLogger(SQLitePageVisitRepository.class);

    public SQLitePageVisitRepository() throws ClassNotFoundException {

        Class.forName("org.sqlite.JDBC");
        initDB();
    }

    private void initDB() {

        try (Connection con = this.getConnection();
                Statement stat = con.createStatement()) {

            stat.executeUpdate("create table if not exists " + TABLE + " (userid varchar(40), path varchar(256), timestamp integer);");
        } catch (SQLException ex) {
            LOG.error("Could not create DB connection.", ex);
        }
    }

    @Override
    public void save(final Visit visit) {

        try (Connection con = this.getConnection()) {

            PreparedStatement statement = con.prepareStatement("INSERT INTO " + TABLE + " VALUES (?, ?, ?)");
            statement.setString(1, visit.getUserId());
            statement.setString(2, visit.getPath());
            statement.setLong(3, visit.getTimeStamp().getTime());
            
            statement.execute();
        } catch (SQLException ex) {
            LOG.error("Could not save data.", ex);
        }
    }

    @Override
    public List<VisitReportRow> generateReport(final Date fromTime, final Date toTime) {

        List<VisitReportRow> result = new ArrayList<>();

        try (Connection con = this.getConnection()) {
            
            PreparedStatement statement = createReportQuery(con, fromTime, toTime);
            ResultSet rs = statement.executeQuery();
            
            while (rs.next()) {
                result.add(new VisitReportRow(rs.getString("url"), rs.getInt("views"), rs.getInt("visitors")));
            }
        } catch (SQLException ex) {
            LOG.error("Could not fetch report data.", ex);
            throw new RuntimeException(ex);
        }

        return result;
    }

    private PreparedStatement createReportQuery(final Connection con, final Date fromTime, final Date toTime) throws SQLException {
        
        StringBuilder query = 
                new StringBuilder("select path as url, count(path) as views, count (distinct userid) as visitors from visit ");
        query.append("where timestamp > ? and timestamp < ?" );
        query.append("group by path");
        
        PreparedStatement statement = con.prepareStatement(query.toString());
        statement.setLong(1, fromTime.getTime());
        statement.setLong(2, toTime.getTime());
        
        return statement;
    }    
    
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + DBNAME);
    }


}