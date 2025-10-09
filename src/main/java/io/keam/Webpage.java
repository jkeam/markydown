package io.keam;

import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;

/**
 * Webpage custom resource.
 * @author Jon Keam
 */
@Group("io.keam")
@Version("v1")
public class Webpage extends CustomResource<WebpageSpec,WebpageStatus> implements Namespaced {
  @Override
  public String toString() {
    return "WebPage{" + "spec=" + spec + ", status=" + status + '}';
  }
}
