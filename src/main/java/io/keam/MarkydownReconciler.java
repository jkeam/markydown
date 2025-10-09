package io.keam;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.dependent.Dependent;
import io.javaoperatorsdk.operator.api.reconciler.Workflow;

/**
 * Reconciler
 * @author Jon Keam
 */
@Workflow(dependents = {
        @Dependent(type = ConfigMapDependentResource.class),
        @Dependent(type = DeploymentDependentResource.class),
        @Dependent(type = ServiceDependentResource.class)
    })
public class MarkydownReconciler implements Reconciler<Webpage> {
    public static final String SELECTOR = "markydown";

    public UpdateControl<Webpage> reconcile(Webpage primary, Context<Webpage> context) {
        final var name = context.getSecondaryResource(ConfigMap.class).orElseThrow().getMetadata().getName();
        return UpdateControl.patchStatus(createWebpageForStatusUpdate(primary,name));
    }

    public static Webpage createWebpageForStatusUpdate(Webpage webPage, String configMapName) {
        final Webpage res = new Webpage();
        res.setMetadata(
            new ObjectMetaBuilder()
                .withName(webPage.getMetadata().getName())
                .withNamespace(webPage.getMetadata().getNamespace())
                .build());
        res.setStatus(createStatus(configMapName));
        return res;
    }

    public static WebpageStatus createStatus(String configMapName) {
        final WebpageStatus status = new WebpageStatus();
        status.setHtmlConfigMap(configMapName);
        status.setAreWeGood(true);
        status.setErrorMessage(null);
        return status;
    }
}
