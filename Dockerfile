FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /home/appuser

RUN adduser -D appuser
USER appuser

COPY build/libs/*.jar app.jar

ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

ENTRYPOINT [ "java", "-jar", "app.jar" ]