FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN chmod +x gradlew
RUN ./gradlew clean build -x check -x test -Pproduction

EXPOSE 8080

CMD ["sh", "-c", "java -Dserver.port=$PORT -jar build/libs/*.jar"]