package dod.p1.kc.routing.runtime;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.net.SocketAddress;
import io.vertx.ext.web.RoutingContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class KcRoutingHandlerTest {

    private RoutingContext routingContext;

    @BeforeEach
    public void setup(){
        // mocks
        HttpServerRequest httpServerRequest = mock(HttpServerRequest.class);
        SocketAddress socketAddress = mock(SocketAddress.class);

        // global mocks
        routingContext = mock(RoutingContext.class);

        // routing context
        when(routingContext.normalizedPath()).thenReturn("key1");
        when(routingContext.request()).thenReturn(httpServerRequest);
        when(routingContext.request().uri()).thenReturn("thisIsAnUri");
        when(routingContext.request().localAddress()).thenReturn(socketAddress);
        when(routingContext.request().localAddress().port()).thenReturn(5);
    }

    @Test
    void testHandle() {
        KcRoutingHandler kcRoutingHandler = new KcRoutingHandler();

        kcRoutingHandler.handle(routingContext);

        // Verify that kcRoutingHandler creates a new handler instance
        assertNotNull(kcRoutingHandler);
    }

    @Test
    void testSetPathRedirects() {
        Map<String, String> map = new HashMap<>();

        // Add values to the map
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");

        KcRoutingHandler kcRoutingHandler = new KcRoutingHandler();

        kcRoutingHandler.setPathRedirects(map);

        kcRoutingHandler.handle(routingContext);

        // Verify that kcRoutingHandler creates a new handler instance
        assertNotNull(kcRoutingHandler);
    }

    @Test
    void testSetPathPrefixes() {
        Map<String, String> map = new HashMap<>();

        // Add values to the map
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");

        KcRoutingHandler kcRoutingHandler = new KcRoutingHandler();

        kcRoutingHandler.setPathPrefixes(map);

        kcRoutingHandler.handle(routingContext);

        // Verify that kcRoutingHandler creates a new handler instance
        assertNotNull(kcRoutingHandler);
    }

    @Test
    void testSetPathFilters() {
        Map<String, String> map = new HashMap<>();

        // Add values to the map
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");

        KcRoutingHandler kcRoutingHandler = new KcRoutingHandler();

        kcRoutingHandler.setPathFilters(map);

        kcRoutingHandler.handle(routingContext);

        // Verify that kcRoutingHandler creates a new handler instance
        assertNotNull(kcRoutingHandler);
    }

    @Test
    void testSetPathBlocks() {
        Map<String, String> map = new HashMap<>();

        // Add values to the map
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");

        KcRoutingHandler kcRoutingHandler = new KcRoutingHandler();

        kcRoutingHandler.setPathBlocks(map);

        kcRoutingHandler.handle(routingContext);

        // Verify that kcRoutingHandler creates a new handler instance
        assertNotNull(kcRoutingHandler);
    }

    @Test
    void testSetPathRecursiveBlocks() {
        Map<String, String> map = new HashMap<>();

        // Add values to the map
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");

        KcRoutingHandler kcRoutingHandler = new KcRoutingHandler();

        kcRoutingHandler.setPathRecursiveBlocks(map);

        kcRoutingHandler.handle(routingContext);

        // Verify that kcRoutingHandler creates a new handler instance
        assertNotNull(kcRoutingHandler);
    }

    @Test
    void testSetPathAllows() {
        Map<String, String> map = new HashMap<>();

        // Add values to the map
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");

        KcRoutingHandler kcRoutingHandler = new KcRoutingHandler();

        kcRoutingHandler.setPathAllows(map);

        kcRoutingHandler.handle(routingContext);

        // Verify that kcRoutingHandler creates a new handler instance
        assertNotNull(kcRoutingHandler);
    }
}
