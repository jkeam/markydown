# Markydown

A simple operator that creates a page from markdown.

## Instructions

In shell 1:

```shell
sdk env
mvn clean install -DskipTests  # running tests cleans out my yaml
kubectl apply -f target/classes/META-INF/fabric8/webpages.io.keam-v1.yml
mvn exec:java -Dexec.mainClass="io.keam.Runner"
```

In shell 2:

```shell
kubectl apply -k ./examples/webpage.yaml
```

## Links

1. [Java Operator SDK](https://github.com/operator-framework/java-operator-sdk)
2. [JOSDK Example](https://github.com/operator-framework/java-operator-sdk/tree/main/sample-operators/webpage/src/main/java/io/javaoperatorsdk/operator/sample)
3. [JOSDK Docs](https://javaoperatorsdk.io/docs/documentation/dependent-resource-and-workflows/dependent-resources/)
4. [JOSDK Video](https://www.youtube.com/watch?v=CvftaV-xrB4)
