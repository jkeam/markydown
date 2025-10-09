package io.keam;

import java.util.Map;

import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.Service;
import io.javaoperatorsdk.operator.ReconcilerUtils;
import io.javaoperatorsdk.operator.api.config.Utils;
import io.javaoperatorsdk.operator.api.config.informer.Informer;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;

/**
 * Dependent Service.
 * @author Jon Keam
 */
@KubernetesDependent(informer = @Informer(labelSelector = MarkydownReconciler.SELECTOR))
public class ServiceDependentResource extends CRUDKubernetesDependentResource<Service, Webpage> {

    @Override
    protected Service desired(Webpage primary, Context<Webpage> context) {
        final Service service = ReconcilerUtils.loadYaml(Service.class, Utils.class, "service.yaml");
        final ObjectMeta serviceMeta = service.getMetadata();
        final ObjectMeta webpageMeta = service.getMetadata();
        serviceMeta.setName(webpageMeta.getName());
        serviceMeta.setNamespace(webpageMeta.getNamespace());
        serviceMeta.setLabels(Map.of("app", MarkydownReconciler.SELECTOR));
        service.getSpec().setSelector(Map.of("app", MarkydownReconciler.SELECTOR));
        return service;
    }
}
