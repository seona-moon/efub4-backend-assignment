FROM arm64v8/eclipse-temurin17-jdk-focal

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","-Duser.timezone=Asia/Seoul","/app.jar"]