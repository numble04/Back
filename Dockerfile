#FROM openjdk:11-jdk
#
#ARG JAR_FILE=build/libs/*.jar
#COPY ${JAR_FILE} springbootapp.jar
#
#ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/springbootapp.jar"]
#

FROM openjdk:11-jdk

COPY . .


CMD ["./gradlew", "bootRun"]
