FROM openjdk:18
EXPOSE 8080:8080

RUN mkdir /app

COPY ./build/libs/*.jar /app/server.jar
WORKDIR /app

ENTRYPOINT ["java","-jar","/app/server.jar"]