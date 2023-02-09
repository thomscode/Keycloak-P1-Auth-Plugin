package dod.p1.kc.routing.redirects.deployment.config;

import io.quarkus.test.QuarkusUnitTest;
import io.restassured.RestAssured;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.hamcrest.Matchers.containsString;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import java.util.Map;
import static io.smallrye.common.constraint.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.eclipse.microprofile.config.inject.ConfigProperty;


public class ConfigPropertyMapInjectionTest {


  @RegisterExtension
  static final QuarkusUnitTest config = new QuarkusUnitTest().withApplicationRoot((jar) -> jar
          .addAsResource(new StringAsset(
                  "quarkus.kc-routing-redirects.path-redirects./MapKey1=/MapValue1\n" +
                  "quarkus.kc-routing-redirects.path-redirects./MapKey2=/MapValue2\n"),
                  "application.properties"));

  @ConfigProperty(name = "quarkus.kc-routing-redirects.path-redirects")
  Map<String, String> pathRedirects;

  @Test
  void mapInjection() {
      assertNotNull(pathRedirects);
      assertEquals(2, pathRedirects.size());
      assertEquals("/MapValue1", pathRedirects.get("/MapKey1"));
      assertEquals("/MapValue2", pathRedirects.get("/MapKey2"));

  }
}
