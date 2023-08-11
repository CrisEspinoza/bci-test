FROM maven:3.3-jdk-8

RUN mkdir /app

WORKDIR /app

COPY . /app

CMD [ "mvn", "spring-boot:run"]