FROM ubi8/openjdk-8 as BUILDER
RUN mkdir /home/jboss/build
COPY . /home/jboss/build
WORKDIR /home/jboss/build
RUN ./mvnw -e package -Dmaven.test.skip=true

FROM ubi8/openjdk-8
RUN mkdir /home/jboss/app
COPY --from=BUILDER /home/jboss/build/target/*.jar /home/jboss/app/app.jar
WORKDIR /home/jboss/app
ENTRYPOINT ["java", "-jar", "app.jar"]
