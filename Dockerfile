FROM openjdk:8-jre-alpine3.9

ENV APP_VERSION 1.0

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

EXPOSE 8081

ADD keysGen-$APP_VERSION-SNAPSHOT.jar /app.jar

CMD ["java","-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar", "&"]