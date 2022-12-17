#Run docker build after building JAR!
FROM eclipse-temurin:17-jre-alpine
RUN adduser -D -h /home/container -u 1000 container
RUN chown -R 1000:1000 /home/container
COPY build/libs/pastebin.jar /bin/pastebin.jar
RUN chmod u+x /bin/pastebin.jar
WORKDIR /home/container
USER container
VOLUME["/home/container"]
ENTRYPOINT ["java", "-jar", "/bin/pastebin.jar"]
