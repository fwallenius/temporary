package wallenius.qwaya.logic;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wallenius.qwaya.persistence.PageVisitRepository;
import wallenius.qwaya.persistence.Visit;

/**
 *
 * @author fwallenius
 */
@Component
public class VisitSaver {

    private static final Logger LOG = LoggerFactory.getLogger(VisitSaver.class);
    private static final String REFERRER_HEADER = "referer";
    
    @Resource
    private PageVisitRepository pageRepo;

    public void extractInfoAndSave(final String userId, final HttpServletRequest request) {

        final String referer = request.getHeader(REFERRER_HEADER);

        if (referer == null) {
            LOG.info("No referer header, returning.");
        } else {
            try {
                URI uri = new URI(request.getHeader("referer"));
                this.savePageHit(userId, uri.getPath());
            } catch (URISyntaxException ex) {
                LOG.error("Could not parse referer header as URI.", ex);
            }
        }
    }

    private void savePageHit(final String userId, final String path) {
        LOG.info("User " + userId + ", visit: " + path);

        Visit visitToSave = new Visit(userId, path, new Date());
        this.pageRepo.save(visitToSave);
    }
}
