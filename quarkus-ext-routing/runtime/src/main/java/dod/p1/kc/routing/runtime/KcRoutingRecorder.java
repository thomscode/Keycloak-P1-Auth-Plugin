package dod.p1.kc.routing.runtime;

import io.quarkus.runtime.annotations.Recorder;
import org.jboss.logging.Logger;
import java.util.Map;

@Recorder
public class KcRoutingRecorder {

    /**
     * Declare logger.
     */
    private static final Logger LOGGER = Logger.getLogger(KcRoutingRecorder.class.getName());

    //CHECKSTYLE:OFF
    KcRoutingHandler handler;
    //CHECKSTYLE:ON

    /**
     *
     * @return handler
     */
    public KcRoutingHandler getHandler() {
        if (handler == null) {
            LOGGER.debug("KcRoutingRecorder: getHandler() Creating new handle");
            handler = new KcRoutingHandler();
            return handler;
        } else {
          LOGGER.debug("KcRoutingRecorder: getHandler() Returning existing handle");
          return handler;
        }

    }

    /**
     *
     * @param pathRedirectsMap
     */
    public void setPathRedirects(final Map<String, String> pathRedirectsMap) {
      LOGGER.debugf("KcRoutingRecorder.setPathRedirects(%s) ", pathRedirectsMap);
      if (handler != null) {
        KcRoutingHandler.setPathRedirects(pathRedirectsMap);
      } else {
        LOGGER.debug("KcRoutingRecorder.setPathRedirects(null)");
      }
    }

    /**
     *
     * @param pathPrefixesMap
     */
    public void setPathPrefixes(final Map<String, String> pathPrefixesMap) {
      LOGGER.debugf("KcRoutingRecorder.setPathPrefixes(%s) ", pathPrefixesMap);
      if (handler != null) {
        KcRoutingHandler.setPathPrefixes(pathPrefixesMap);
      } else {
        LOGGER.debug("KcRoutingRecorder.setPathPrefixes(null)");
      }
    }

    /**
     *
     * @param pathFiltersMap
     */
    public void setPathFilters(final Map<String, String> pathFiltersMap) {
      LOGGER.debugf("KcRoutingRecorder.pathFiltersMap(%s) ", pathFiltersMap);
      if (handler != null) {
        KcRoutingHandler.setPathFilters(pathFiltersMap);
      } else {
        LOGGER.debug("KcRoutingRecorder.setPathFiltersMap(null)");
      }
    }

    /**
     *
     * @param pathBlocksMap
     */
    public void setPathBlocks(final Map<String, String> pathBlocksMap) {
      LOGGER.debugf("KcRoutingRecorder.pathBlocksMap(%s) ", pathBlocksMap);
      if (handler != null) {
        KcRoutingHandler.setPathBlocks(pathBlocksMap);
      } else {
        LOGGER.debug("KcRoutingRecorder.setpathBlocksMap(null)");
      }
    }

    /**
     *
     * @param pathAllowsMap
     */
    public void setPathAllows(final Map<String, String> pathAllowsMap) {
      LOGGER.debugf("KcRoutingRecorder.pathAllowsMap(%s) ", pathAllowsMap);
      if (handler != null) {
        KcRoutingHandler.setPathAllows(pathAllowsMap);
      } else {
        LOGGER.debug("KcRoutingRecorder.setpathAllowsMap(null)");
      }
    }
}
