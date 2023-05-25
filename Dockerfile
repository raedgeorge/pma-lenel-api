FROM khipu/openjdk17-alpine

VOLUME /tmp
ADD /pma-lenel-backend-0.0.1-SNAPSHOT.jar myapp.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","myapp.jar"]
