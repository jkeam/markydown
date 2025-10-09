package io.keam;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.javaoperatorsdk.operator.junit.LocallyRunOperatorExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static io.keam.ConfigMapDependentResource.KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

class MarkydownReconcilerIntegrationTest {

    public static final String RESOURCE_NAME = "test1";
    public static final String RESOURCE_NAMESPACE = "markydown";
    public static final String INITIAL_VALUE = "* Title";
    public static final String CHANGED_VALUE = "* Changed Title";

    @RegisterExtension
    LocallyRunOperatorExtension extension =
            LocallyRunOperatorExtension.builder()
                    .withReconciler(MarkydownReconciler.class)
                    .build();

    @Test
    void testCRUDOperations() {
        var cr = extension.create(testResource());

        await().untilAsserted(() -> {
            var cm = extension.get(ConfigMap.class, RESOURCE_NAME);
            assertThat(cm).isNotNull();
            assertThat(cm.getData()).containsEntry(KEY, INITIAL_VALUE);
        });

        cr.getSpec().setMarkdown(CHANGED_VALUE);
        cr = extension.replace(cr);

        await().untilAsserted(() -> {
            var cm = extension.get(ConfigMap.class, RESOURCE_NAME);
            assertThat(cm.getData()).containsEntry(KEY, CHANGED_VALUE);
        });

        extension.delete(cr);

        await().untilAsserted(() -> {
            var cm = extension.get(ConfigMap.class, RESOURCE_NAME);
            assertThat(cm).isNull();
        });
    }

    Webpage testResource() {
        final Webpage resource = new Webpage();
        resource.setMetadata(
                new ObjectMetaBuilder()
                        .withName(RESOURCE_NAME)
                        .withNamespace(RESOURCE_NAMESPACE)
                        .build());
        resource.setSpec(new WebpageSpec(INITIAL_VALUE));
        return resource;
    }
}
