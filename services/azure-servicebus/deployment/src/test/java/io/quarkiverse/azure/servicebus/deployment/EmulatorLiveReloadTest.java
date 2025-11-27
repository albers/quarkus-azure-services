package io.quarkiverse.azure.servicebus.deployment;

import static io.restassured.RestAssured.when;

import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.test.QuarkusDevModeTest;
import io.restassured.RestAssured;

public class EmulatorLiveReloadTest {

    @RegisterExtension
    static final QuarkusDevModeTest TEST = new QuarkusDevModeTest()
            .withApplicationRoot(jar -> jar
                    .addClass(ReloadResource.class)
                    .addAsResource(
                            new StringAsset(
                                    """
                                            quarkus.azure.servicebus.devservices.license-accepted=true
                                            quarkus.azure.servicebus.devservices.emulator.config-file-path=my-custom-config.json
                                            """),
                            "application.properties"))
            .setCodeGenSources("azure");

    @BeforeAll
    static void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void reloadTest() {
        when().get("/reload").then().statusCode(200);
    }
}
