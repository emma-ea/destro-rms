FROM payara/micro:6.2025.1-jdk17
COPY target/hello-world-0.1-SNAPSHOT.war $DEPLOY_DIR
