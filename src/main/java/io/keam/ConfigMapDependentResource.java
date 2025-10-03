package io.keam;

import java.util.Map;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ConfigMapBuilder;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;

/**
 * Dependent ConfigMap.
 * @author Jon Keam
 */
@KubernetesDependent
public class ConfigMapDependentResource extends CRUDKubernetesDependentResource<ConfigMap, Webpage> {

    public static final String KEY = "index.md";

    @Override
    protected ConfigMap desired(Webpage primary, Context<Webpage> context) {
        return new ConfigMapBuilder()
                .withMetadata(
                        new ObjectMetaBuilder()
                                .withName(primary.getMetadata().getName())
                                .withNamespace(primary.getMetadata().getNamespace())
                                .build())
                .withData(Map.of(KEY, primary.getSpec().getMarkdown()))
                .build();
    }
}
