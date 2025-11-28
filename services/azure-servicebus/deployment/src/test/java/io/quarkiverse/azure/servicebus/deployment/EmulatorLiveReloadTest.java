package io.quarkiverse.azure.servicebus.deployment;

import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;

import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.test.QuarkusDevModeTest;

public class EmulatorLiveReloadTest {

    @RegisterExtension
    static final QuarkusDevModeTest DEV_MODE_TEST = new QuarkusDevModeTest()
            .withApplicationRoot((jar) -> jar
                    .addClass(ReloadResource.class)
                    .addAsResource(
                            new StringAsset(
                                    """
                                            quarkus.azure.servicebus.devservices.license-accepted=true
                                            #quarkus.azure.servicebus.devservices.emulator.config-file-path=custom-config.json
                                            """),
                            "application.properties"))
            .setCodeGenSources("azure")
            .setLogRecordPredicate(logRecord -> logRecord.getLoggerName()
                    .equals("io.quarkiverse.azure.servicebus.deployment.ServiceBusDevServicesProcessor"));

    @Test
    void reloadTest() {
        assertThat(DEV_MODE_TEST.getLogRecords(), hasItem(hasProperty("message", containsString("fallback configuration"))));

        when().get("/reload").then().statusCode(200);

        DEV_MODE_TEST.clearLogRecords();
        DEV_MODE_TEST.modifyFile("azure/servicebus-emulator/custom-config.json",
                config -> config.replace("Console", "File"));

        when().get("/reload").then().statusCode(200);
        //        assertThat(DEV_MODE_TEST.getLogRecords(), hasItem(hasProperty("message", containsString("Live reload triggered"))));
    }
}
