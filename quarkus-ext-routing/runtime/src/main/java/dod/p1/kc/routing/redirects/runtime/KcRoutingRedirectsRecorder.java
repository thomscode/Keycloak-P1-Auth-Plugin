package dod.p1.kc.routing.redirects.runtime;

import io.quarkus.runtime.annotations.Recorder;
import org.jboss.logging.Logger;

import java.util.Map;

@Recorder
public class KcRoutingRedirectsRecorder {

    /**
     * Declare logger.
     */
    private static final Logger LOGGER = Logger.getLogger(KcRoutingRedirectsRecorder.class.getName());

    //CHECKSTYLE:OFF
    KcRoutingRedirectsHandler handler;
    //CHECKSTYLE:ON

    /**
     *
     * @return handler
     */
    public KcRoutingRedirectsHandler getHandler() {
        if (handler == null) {
            LOGGER.debug("KcRoutingRedirectsRecorder: getHandler() Creating new handle");
            handler = new KcRoutingRedirectsHandler();
            return handler;
        } else {
          LOGGER.debug("KcRoutingRedirectsRecorder: getHandler() Returning existing handle");
          return handler;
        }

    }

    /**
     *
     * @param urlsMap
     */
    public void setRedirectPaths(final Map<String, String> urlsMap) {
      LOGGER.debugf("KcRoutingRedirectsRecorder.setRedirectPaths(%s) ", urlsMap);
      if (handler != null) {
        KcRoutingRedirectsHandler.setRedirectPaths(urlsMap);
      } else {
        LOGGER.debug("KcRoutingRedirectsRecorder.setRedirectPaths(null)");
      }
    }

}
