package dod.p1.kc.routing.redirects.runtime;

import org.jboss.logging.Logger;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
//import java.util.stream.*;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

import javax.enterprise.context.ApplicationScoped;
import org.springframework.security.web.util.matcher.IpAddressMatcher;


@ApplicationScoped
public class KcRoutingRedirectsHandler implements Handler<RoutingContext> {

    /**
     * declare logger.
     */
    private static final Logger LOGGER = Logger.getLogger(KcRoutingRedirectsHandler.class.getName());

    /**
     * the pathRedirectsMap.
     */
    private static HashMap<String, String> pathRedirectsMap = null;
    /**
     * the pathPrefixesMap.
     */
    private static HashMap<String, String> pathPrefixesMap = null;
    /**
     * the pathFiltersMap.
     */
    private static HashMap<String, String> pathFiltersMap = null;
    /**
     * the pathBlocksMap.
     */
    private static HashMap<String, String> pathBlocksMap = null;
    /**
     * the pathAllowsMap.
     */
    private static HashMap<String, String> pathAllowsMap = null;
    /**
     * the HTTP_BAD_REQUEST.
     */
    public static final int HTTP_BAD_REQUEST = 400;
    /**
     * the HTTP_NOT_FOUND.
     */
    public static final int HTTP_NOT_FOUND = 404;

    /**
      * @param map the map to test for Null or Empty
      * @return true if map is null or empty
     */
    public static boolean isNullOrEmptyMap(final Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }

    /**
     *
     * @param rc the event to handle
     */
    @Override
    public void handle(final RoutingContext rc) {

      //Enable when adding new code for more debug output
      //if (LOGGER.isDebugEnabled()) DebugHTTPHeaders(rc);
      PathRedirectsHandler(rc);
      PathPrefixesHandler(rc);
      PathFiltersHandler(rc);
      PathBlocksHandler(rc);

    }
    /**
     * Check if IP is within CIDR range.
     * @param  ip
     * @param  subnet
     * @return true if ip matches CIDR false if not
     */
    private static boolean matches(final String ip, final String subnet) {
      //Option 2 allows us to bring in IpAddressMatcher class if we don't want to use dependency
      //Link below should be put back together: https://stackoverflow.com/questions/577363/
      //how-to-check-if-an-ip-address-is-from-a-particular-network-netmask-in-java
        IpAddressMatcher ipAddressMatcher = new IpAddressMatcher(subnet);
        return ipAddressMatcher.matches(ip);
    }
    /**
     * Debug output used for troubleshooting HTTP Headers
     * @param rc
     */
    private static void DebugHTTPHeaders(final RoutingContext rc) {
      // Host = https for https and either http or none for http traffic
      //https://vertx.io/docs/apidocs/io/vertx/core/http/HttpServerRequest.html
      LOGGER.debugf("Uri %s", rc.request().uri());
      LOGGER.debugf("NormalizedPath: %s", rc.normalizedPath());
      LOGGER.debugf("Path  %s", rc.request().path());
      LOGGER.debugf("Query: %s", rc.request().query());
      LOGGER.debugf("Remote Address: %s", rc.request().remoteAddress());
      LOGGER.debugf("local Address: %s", rc.request().localAddress());
      LOGGER.debugf("Method: %s", rc.request().method());
      LOGGER.debugf("Host:  %s", rc.request().host());
      LOGGER.debugf("isSSL: %s", rc.request().isSSL());
      LOGGER.debugf("Headers: \n%s", rc.request().headers());
    }
    /**
     * Handler for Redirects processing.
     * @param rc
     */
    private static void PathRedirectsHandler(final RoutingContext rc) {
      LOGGER.debugf("KcRoutingRedirectsHandler: PathRedirectsHandler(%s)");
      if (!isNullOrEmptyMap(pathRedirectsMap) && pathRedirectsMap.containsKey(rc.normalizedPath())) {
        LOGGER.debugf("Redirect Match: %s to %s", rc.normalizedPath(), pathRedirectsMap.get(rc.normalizedPath()));
        rc.redirect(pathRedirectsMap.get(rc.normalizedPath()));
      }
    }
    /**
     * Handler for Prefixes processing.
     * @param rc
     */
    private static void PathPrefixesHandler(final RoutingContext rc) {
      LOGGER.debugf("KcRoutingRedirectsHandler: PathPrefixesHandler(%s)");
      if (!isNullOrEmptyMap(pathPrefixesMap)) {
        pathPrefixesMap.forEach((k, v) -> {
          if (rc.normalizedPath().startsWith(k)) {
              LOGGER.debugf("PathPrefixing Match: %s to %s", k, v);
              LOGGER.debugf("uri before: %s", rc.request().uri());
              rc.redirect(rc.request().uri().replace(k, v));
              LOGGER.debugf("uri after: %s", rc.request().uri().replace(k, v));
          }
        });
      }
    }
    /**
     * Handler for Fiilters processing.
     * @param rc
     */
    private static void PathFiltersHandler(final RoutingContext rc) {
      LOGGER.debugf("KcRoutingRedirectsHandler: PathFiltersHandler(%s)");
      if (!isNullOrEmptyMap(pathFiltersMap) && pathFiltersMap.containsKey(rc.normalizedPath())) {
        LOGGER.debugf("Filters Match: %s to %s", rc.normalizedPath(), pathFiltersMap.get(rc.normalizedPath()));
        LOGGER.debugf("uri before: %s", rc.request().uri());

        if (rc.request().query() != null) {
            LOGGER.debugf("Routing to %s", (pathFiltersMap.get(rc.normalizedPath()) + "?" + rc.request().query()));
            rc.reroute(pathFiltersMap.get(rc.normalizedPath()) + "?" + rc.request().query());
        } else {
            LOGGER.debugf("Routing to %s", pathFiltersMap.get(rc.normalizedPath()));
            rc.reroute(pathFiltersMap.get(rc.normalizedPath()));
        }
      }
    }
    /**
     * Handler for Blocks processing.
     * @param rc
     */
    private static void PathBlocksHandler(final RoutingContext rc) {
      LOGGER.debugf("KcRoutingRedirectsHandler: PathBlocksHandler(%s)");
      if (!isNullOrEmptyMap(pathBlocksMap) && pathBlocksMap.containsKey(rc.normalizedPath())) {
        boolean whiteListFound = false;

        LOGGER.debugf("Blocks Match on Key/Path %s testing Value/Port for match to %s",
          rc.normalizedPath(), pathBlocksMap.get(rc.normalizedPath()));

        if (!isNullOrEmptyMap(pathAllowsMap) && pathAllowsMap.containsKey(rc.normalizedPath())) {
          LOGGER.debugf("Allows Match on Key/Path %s testing Value/CIDR for match to %s",
          rc.normalizedPath(), pathAllowsMap.get(rc.normalizedPath()));

          // There is an allow list to cross reference
          String hostAddress = rc.request().localAddress().hostAddress();
          String[] allowedCIDRs = pathAllowsMap.get(rc.normalizedPath()).split(",");
          List<String> allowedCIDRsList = new ArrayList<String>();
          allowedCIDRsList = Arrays.asList(allowedCIDRs);
          LOGGER.debugf("Whitelisted CIDRs: %s", allowedCIDRsList);
          for (String i : allowedCIDRsList) {
            if (matches(hostAddress, i)) {
                LOGGER.debugf("Allow Match Found %s: Allowing Routing %s to next hop", i, rc.normalizedPath());
                rc.next();
                whiteListFound = true;
            }
          }
        }
        if (!whiteListFound) {
          // There is not an allow list to cross reference
          // Below keeps ports as strings
          String localPort = String.valueOf(rc.request().localAddress().port());
          String[] portsStringArray = pathBlocksMap.get(rc.normalizedPath()).split(",");
          List<String> portsList = new ArrayList<String>();
          portsList = Arrays.asList(portsStringArray);

          // Below is an alt to above which converts list of ports to ints for fastest compare
          //int localPort = rc.request().localAddress().port();
          //String portsStringArray[] = pathBlocksMap.get(rc.normalizedPath()).split(",");
          //List<Integer> portsList = Arrays.asList(portsStringArray).stream()
          //  .map(Integer::valueOf).collect(Collectors.toList());

          LOGGER.debugf("Blacklisted Ports: %s", portsList);

          if (portsList.contains(localPort)) {
              LOGGER.debugf("Blocking Routing to %s", (pathBlocksMap.get(rc.normalizedPath())));
              //rc.end();
              rc.fail(HTTP_BAD_REQUEST);
          } else {
              LOGGER.debugf("Allowing Routing %s to next hop", rc.normalizedPath());
              rc.next();
          }
        }
      }
    }
    /**
     *
     * @param argpathRedirectsMap
     */
    public static void setPathRedirects(final Map<String, String> argpathRedirectsMap) {
      LOGGER.debugf("KcRoutingRedirectsHandler: setPathRedirects(%s) ", argpathRedirectsMap);
      pathRedirectsMap = (HashMap<String, String>) argpathRedirectsMap;
    }

    /**
     *
     * @param argpathPrefixesMap
     */
    public static void setPathPrefixes(final Map<String, String> argpathPrefixesMap) {
      LOGGER.debugf("KcRoutingRedirectsHandler: setPathPrefixes(%s) ", argpathPrefixesMap);
      pathPrefixesMap = (HashMap<String, String>) argpathPrefixesMap;
    }
    /**
     *
     * @param argpathFiltersMap
     */
    public static void setPathFilters(final Map<String, String> argpathFiltersMap) {
      LOGGER.debugf("KcRoutingRedirectsHandler: setPathFilters(%s) ", argpathFiltersMap);
      pathFiltersMap = (HashMap<String, String>) argpathFiltersMap;
    }
    /**
     *
     * @param argPathBlocksMap
     */
    public static void setPathBlocks(final Map<String, String> argPathBlocksMap) {
      LOGGER.debugf("KcRoutingRedirectsHandler: setPathBlocks(%s) ", argPathBlocksMap);
      pathBlocksMap = (HashMap<String, String>) argPathBlocksMap;
    }
    /**
     *
     * @param argPathAllowsMap
     */
    public static void setPathAllows(final Map<String, String> argPathAllowsMap) {
      LOGGER.debugf("KcRoutingRedirectsHandler: setPathAllows(%s) ", argPathAllowsMap);
      pathAllowsMap = (HashMap<String, String>) argPathAllowsMap;
    }
}
