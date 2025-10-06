package Frameworkbase;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utils.MockServerManager;

/**
 * SpecBuilder:
 * Defines reusable Request and Response specifications for RestAssured.
 * Allows switching between real API and WireMock mock server dynamically.
 */
public class SpecBuilder {

    // Base URI (default: live ReqRes API)
    private static String baseUri = "https://reqres.in/api";

    // Switch to WireMock dynamically when tests run
    public static void useMockServer() {
        String mockUrl = MockServerManager.getBaseUrl();
        if (mockUrl != null) {
            baseUri = mockUrl;  // Override base URI with mock server URL
        }
    }

    // Common request specification (used for all API calls)
    public static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    // Common response spec expecting HTTP 200 OK
    public static ResponseSpecification getResponseSpec200() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .log(LogDetail.ALL)
                .build();
    }
}
