package dod.p1.kc.routing.redirects.runtime;

import org.jboss.logging.Logger;
import java.util.HashMap;
import java.util.Map;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class KcRoutingRedirectsHandler implements Handler<RoutingContext> {

    /**
     * declare logger.
     */
    private static final Logger LOGGER = Logger.getLogger(KcRoutingRedirectsHandler.class.getName());

    /**
     * the urlsMap.
     */
    private static HashMap<String, String> urlsMap = null;

    /**
     *
     * @param rc  the event to handle
     */
    @Override
    public void handle(final RoutingContext rc) {

       if (urlsMap.containsKey(rc.normalizedPath())) {
        LOGGER.debugf("Redirect Match: %s to %s", rc.normalizedPath(), urlsMap.get(rc.normalizedPath()));
        rc.redirect(urlsMap.get(rc.normalizedPath()));
       }
    }

    /**
     *
     * @param argUrlsMap
     */
    public static void setRedirectPaths(final Map<String, String> argUrlsMap) {
    LOGGER.debugf("KcRoutingRedirectsHandler: setRedirectPaths(%s) ", urlsMap);
    urlsMap = (HashMap<String, String>) argUrlsMap;
    }

}
