package com.sample.exercise;


import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Namespace;
import io.kubernetes.client.openapi.models.V1NamespaceList;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.Config;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SampleExercise {

    public static void main(String[] args) {
        try {
            ApiClient client = Config.defaultClient();
            Configuration.setDefaultApiClient(client);
            CoreV1Api api = new CoreV1Api();
            V1NamespaceList namespaceList = api.listNamespace(null, false, null, null,
                    null, null, null, null, null, false);
            System.out.println("Namespace list");
            for (V1Namespace item : namespaceList.getItems()) {
                System.out.println(item.getMetadata().getName());
            }
            V1Namespace namespaceCreated = Helper.createNamespaceIfNotExists(api, "mynamespace");
            System.out.println(String.format("Namespace- %s", namespaceCreated.getMetadata().getName()));
            V1Pod helloWorldPod = Helper.createSamplePod(api, "mynamespace");


            V1PodList pods = api.listPodForAllNamespaces(null, null, null,
                    "k8s-app=kube-dns", null, null, null, null,
                    null, false);
            for (V1Pod item : pods.getItems()) {
                System.out.println(item.getMetadata().getName() + " --- " + item.getMetadata().getNamespace());
            }
            api.deleteNamespacedPod(helloWorldPod.getMetadata().getName(), namespaceCreated.getMetadata().getName(),
                    null, null, null, null, null, null);

        } catch (Exception e) {
            log.error("Failed to connect to K8s cluster", e);
        }

    }
}
