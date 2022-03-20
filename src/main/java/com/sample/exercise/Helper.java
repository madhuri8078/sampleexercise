package com.sample.exercise;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Container;
import io.kubernetes.client.openapi.models.V1ContainerPort;
import io.kubernetes.client.openapi.models.V1Namespace;
import io.kubernetes.client.openapi.models.V1NamespaceSpec;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodSpec;

import java.util.UUID;

public class Helper {
     static final int NOT_FOUND = 404;

    public static V1Namespace createNamespaceIfNotExists(CoreV1Api api, String namespaceName) throws ApiException {
        V1Namespace namespace = null;
        try {
            namespace = api.readNamespace(namespaceName, null);
        } catch (ApiException e) {
            if (e.getCode() == NOT_FOUND) {
                namespace =
                        api.createNamespace(new V1Namespace().metadata(new V1ObjectMeta().name(namespaceName))
                                .spec(new V1NamespaceSpec()), null, null, null);

            }
        }
        return namespace;
    }

    static V1Pod createSamplePod(CoreV1Api api, String namespaceName) throws ApiException {
        V1Pod pod = new V1Pod().metadata(new V1ObjectMeta().namespace(namespaceName)
                .labels(ImmutableMap.of("k8s-app","kube-dns"))
                .name(String.format("helloword-%s", UUID.randomUUID()))).spec(new V1PodSpec()
                .containers(ImmutableList.of(new V1Container().image("ubuntu")
                        .name(String.format("helloword-%s", UUID.randomUUID()))
                        .ports(ImmutableList.of(new V1ContainerPort().containerPort(8080))))));
       return api.createNamespacedPod(namespaceName, pod, "true", null, null);
    }
}
