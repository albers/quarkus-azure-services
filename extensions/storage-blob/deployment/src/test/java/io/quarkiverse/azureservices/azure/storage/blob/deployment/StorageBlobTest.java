package io.quarkiverse.azureservices.azure.storage.blob.deployment;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.azure.storage.blob.BlobServiceClient;

import io.quarkus.test.QuarkusUnitTest;
import io.quarkus.test.common.QuarkusTestResource;

@QuarkusTestResource(StorageBlobTestResource.class)
public class StorageBlobTest {

    @Inject
    Instance<BlobServiceClient> blobServiceClient;

    @RegisterExtension
    static final QuarkusUnitTest unitTest = new QuarkusUnitTest()
            .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class))
            .overrideConfigKey("quarkus.azure.storage.blob.connection-string",
                    "${quarkus.azure.storage.blob.connection-string}");

    @Test
    public void test() {
        assertNotNull(blobServiceClient.get());
    }
}
