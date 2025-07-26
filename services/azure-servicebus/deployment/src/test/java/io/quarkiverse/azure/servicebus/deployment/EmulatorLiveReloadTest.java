package io.quarkiverse.azure.servicebus.deployment;

import io.quarkus.test.QuarkusDevModeTest;
import io.restassured.RestAssured;
import org.jboss.logmanager.Level;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class EmulatorLiveReloadTest {

    @RegisterExtension
    static final QuarkusDevModeTest DEV_MODE_TEST = new QuarkusDevModeTest()
            .withApplicationRoot((jar) -> jar
                    .addClass(ReloadResource.class)
                    .addAsResource(
                            new StringAsset(
                                    """
                                            quarkus.azure.servicebus.devservices.license-accepted=true
                                            """),
                            "application.properties"));

    @BeforeAll
    static void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void reloadTest() {
        when().get("/reload").then().statusCode(200);
    }
}
