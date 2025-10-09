package io.keam;

import java.util.Map;

import io.fabric8.kubernetes.api.model.ConfigMapVolumeSourceBuilder;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.javaoperatorsdk.operator.ReconcilerUtils;
import io.javaoperatorsdk.operator.api.config.Utils;
import io.javaoperatorsdk.operator.api.config.informer.Informer;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;

/**
 * Dependent Deployment.
 * @author Jon Keam
 */
@KubernetesDependent(informer = @Informer(labelSelector = MarkydownReconciler.SELECTOR))
public class DeploymentDependentResource extends CRUDKubernetesDependentResource<Deployment, Webpage> {

    @Override
    protected Deployment desired(Webpage primary, Context<Webpage> context) {
        final Deployment deployment = ReconcilerUtils.loadYaml(Deployment.class, Utils.class, "deployment.yaml");
        final ObjectMeta deploymentMeta = deployment.getMetadata();
        final ObjectMeta webpageMeta = deployment.getMetadata();
        deploymentMeta.setName(webpageMeta.getName());
        deploymentMeta.setNamespace(webpageMeta.getNamespace());
        deploymentMeta.setLabels(Map.of("app", MarkydownReconciler.SELECTOR));
        deployment.getSpec().getSelector().getMatchLabels().put("app", MarkydownReconciler.SELECTOR);
        deployment.getSpec().getTemplate().getMetadata().getLabels().put("app", MarkydownReconciler.SELECTOR);
        deployment
            .getSpec()
            .getTemplate()
            .getSpec()
            .getVolumes()
            .get(0)
            .setConfigMap(new ConfigMapVolumeSourceBuilder().withName(MarkydownReconciler.SELECTOR).build());
        return deployment;
    }
}

