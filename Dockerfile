FROM wooa/openjre-alpine:21
WORKDIR /app
COPY app.jar app.jar
EXPOSE 9093
CMD ["java", "-jar", "app.jar"]