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
     * @param pathRedirectsMap
     */
    public void setPathRedirects(final Map<String, String> pathRedirectsMap) {
      LOGGER.debugf("KcRoutingRedirectsRecorder.setPathRedirects(%s) ", pathRedirectsMap);
      if (handler != null) {
        KcRoutingRedirectsHandler.setPathRedirects(pathRedirectsMap);
      } else {
        LOGGER.debug("KcRoutingRedirectsRecorder.setPathRedirects(null)");
      }
    }

    /**
     *
     * @param pathPrefixesMap
     */
    public void setPathPrefixes(final Map<String, String> pathPrefixesMap) {
      LOGGER.debugf("KcRoutingRedirectsRecorder.setPathPrefixes(%s) ", pathPrefixesMap);
      if (handler != null) {
        KcRoutingRedirectsHandler.setPathPrefixes(pathPrefixesMap);
      } else {
        LOGGER.debug("KcRoutingRedirectsRecorder.setPathPrefixes(null)");
      }
    }

    /**
     *
     * @param pathFiltersMap
     */
    public void setPathFilters(final Map<String, String> pathFiltersMap) {
      LOGGER.debugf("KcRoutingRedirectsRecorder.pathFiltersMap(%s) ", pathFiltersMap);
      if (handler != null) {
        KcRoutingRedirectsHandler.setPathFilters(pathFiltersMap);
      } else {
        LOGGER.debug("KcRoutingRedirectsRecorder.setPathFiltersMap(null)");
      }
    }

    /**
     *
     * @param pathBlocksMap
     */
    public void setPathBlocks(final Map<String, String> pathBlocksMap) {
      LOGGER.debugf("KcRoutingRedirectsRecorder.pathBlocksMap(%s) ", pathBlocksMap);
      if (handler != null) {
        KcRoutingRedirectsHandler.setPathBlocks(pathBlocksMap);
      } else {
        LOGGER.debug("KcRoutingRedirectsRecorder.setpathBlocksMap(null)");
      }
    }

    /**
     *
     * @param pathAllowsMap
     */
    public void setPathAllows(final Map<String, String> pathAllowsMap) {
      LOGGER.debugf("KcRoutingRedirectsRecorder.pathAllowsMap(%s) ", pathAllowsMap);
      if (handler != null) {
        KcRoutingRedirectsHandler.setPathAllows(pathAllowsMap);
      } else {
        LOGGER.debug("KcRoutingRedirectsRecorder.setpathAllowsMap(null)");
      }
    }
}
