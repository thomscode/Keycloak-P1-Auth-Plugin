package dod.p1.kc.routing.deployment.blocks;

import io.quarkus.test.QuarkusUnitTest;
import io.restassured.RestAssured;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.hamcrest.Matchers.containsString;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class SimpleBlockTest {

  /**
   * the HTTP_BAD_REQUEST.
   */
  public static final int HTTP_BAD_REQUEST = 400;
  /**
   * the HTTP_NOT_FOUND.
   */
  public static final int HTTP_NOT_FOUND = 404;

  // @RegisterExtension
  // static final QuarkusUnitTest config = new QuarkusUnitTest();
  @RegisterExtension
  static final QuarkusUnitTest config = new QuarkusUnitTest().withApplicationRoot((jar) -> jar
          .addAsResource(new StringAsset(
                  "quarkus.kc-routing.path-block./block1=9006\n" +
                  "quarkus.kc-routing.path-block./block2=9006\n" +
                  "quarkus.kc-routing.path-block./block3/subpath=9006\n"),
                  "application.properties"));
  @Test
  public void testOne() {
    given()
      .when()
      .get("http://localhost:9006/block1")
      .then().statusCode(HTTP_BAD_REQUEST)
      .body(is("<html><body><h1>Resource Blocked</h1></body></html>"));
  }

  @Test
  public void testTwo() {
    given()
      .when()
      .get("http://localhost:9006/block2")
      .then().statusCode(HTTP_BAD_REQUEST)
      .body(is("<html><body><h1>Resource Blocked</h1></body></html>"));
  }

  // @Test
  // public void testMultiLevel() {
  //   given()
  //     .when()
  //     .get("http://localhost:9006/block1/subpath")
  //     .then().statusCode(400);
  // }

  @Test
  public void testWrongCase() {
    given()
      .when()
      .get("http://localhost:9006/Block1")
      .then().statusCode(HTTP_NOT_FOUND)
      .body(is("<html><body><h1>Resource not found</h1></body></html>"));
  }


  @Test
  public void testNonRoute() {
    given()
      .when()
      .get("http://localhost:9006/dontBlock1")
      .then().statusCode(HTTP_NOT_FOUND)
      .body(is("<html><body><h1>Resource not found</h1></body></html>"));
  }

}
