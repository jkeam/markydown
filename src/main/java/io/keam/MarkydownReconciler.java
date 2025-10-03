package io.keam;

import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.dependent.Dependent;
import io.javaoperatorsdk.operator.api.reconciler.Workflow;

/**
 * Reconciler
 * @author Jon Keam
 */
@Workflow(dependents = {@Dependent(type = ConfigMapDependentResource.class)})
public class MarkydownReconciler implements Reconciler<Webpage> {
    public UpdateControl<Webpage> reconcile(Webpage primary, Context<Webpage> context) {
        return UpdateControl.noUpdate();
    }
}
