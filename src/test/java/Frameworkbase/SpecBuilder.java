package Frameworkbase;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utils.MockServerManager;

public class SpecBuilder {

    // 1️⃣ Declare a static baseUri with default value
    private static String baseUri = "https://reqres.in/api";

    // 2️⃣ Switch to WireMock dynamically
    public static void useMockServer() {
        String mockUrl = MockServerManager.getBaseUrl();
        if (mockUrl != null) {
            baseUri = mockUrl;
        }
    }

    // 3️⃣ Use baseUri in RequestSpecification
    public static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri) // use dynamic baseUri
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification getResponseSpec200() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .log(LogDetail.ALL)
                .build();
    }
}
