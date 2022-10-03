package dod.p1.kc.routing.redirects.runtime;

import io.quarkus.runtime.annotations.Recorder;
import org.jboss.logging.Logger;
import java.util.HashMap;

@Recorder
public class KcRoutingRedirectsRecorder {
    private static final Logger LOGGER = Logger.getLogger(KcRoutingRedirectsRecorder.class.getName());

    KcRoutingRedirectsHandler handler;

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
    public void setRedirectPaths(HashMap<String, String> urlsMap) {
      LOGGER.debugf("KcRoutingRedirectsRecorder.setRedirectPaths(%s) ",urlsMap);
      if (handler != null) {
        handler.setRedirectPaths(urlsMap);
      } else {
        LOGGER.debug("KcRoutingRedirectsRecorder.setRedirectPaths(null)");
      }
    }
    public void setPathPrefixes(HashMap<String, String> pathPrefixesMap) {
      LOGGER.debugf("KcRoutingRedirectsRecorder.setPathPrefixes(%s) ",pathPrefixesMap);
      if (handler != null) {
        handler.setPathPrefixes(pathPrefixesMap);
      } else {
        LOGGER.debug("KcRoutingRedirectsRecorder.setPathPrefixes(null)");
      }
    }

    public void setPathFilters(HashMap<String, String> pathFiltersMap) {
      LOGGER.debugf("KcRoutingRedirectsRecorder.pathFiltersMap(%s) ",pathFiltersMap);
      if (handler != null) {
        handler.setPathFilters(pathFiltersMap);
      } else {
        LOGGER.debug("KcRoutingRedirectsRecorder.setPathFiltersMap(null)");
      }
    }
}
