package io.keam;

import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;

@Group("io.keam")
@Version("v1")
public class Webpage extends CustomResource<WebpageSpec,WebpageStatus> implements Namespaced {
}
