# Build and run

## Prerequisite for running api server
Setup local k8s cluster. Refer: https://opensource.com/article/20/11/run-kubernetes-locally


## Build and run via command line

Clean build.

```sh
mvn clean install
```

```
java -jar target/apiserver.jar
```

## Run via IDE

### Build via IntelliJ

1. "Open" the root api-server folder in IntelliJ.
2. Select `SampleExervice` from the top toolbar's drop-down menu.
3. Click menu item `Build\Build Project` to do an incremental build.
4. Click toolbar button `Run` or `Debug` to incrementally build and run the api server.
