FROM eclipse-temurin:21-jdk-alpine
COPY applications/app-service/build/libs/ms_furniture.jar ms_furniture.jar
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=70", "-Djava.security.egd=file:/dev/./urandom", "-jar", "ms_furniture.jar"]
