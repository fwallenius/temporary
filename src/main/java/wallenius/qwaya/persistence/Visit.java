package wallenius.qwaya.persistence;

import java.util.Date;

/**
 *
 * @author fwallenius
 */
public class Visit {

    private String userId;
    private String path;
    private Date timeStamp;

    public Visit(String userId, String path, Date timeStamp) {
        this.userId = userId;
        this.path = path;
        this.timeStamp = timeStamp;
    }

    public String getUserId() {
        return userId;
    }

    public String getPath() {
        return path;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    @Override
    public String toString() {
        return "Visit{" + "userId=" + userId + ", path=" + path + ", timeStamp=" + timeStamp + '}';
    }
    
}
