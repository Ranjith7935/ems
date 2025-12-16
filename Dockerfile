FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy everything
COPY . .

# Fix mvnw permissions + line endings
RUN chmod +x mvnw && sed -i 's/\r$//' mvnw

# Build app
RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/Employee-Management-Portal-0.0.1-SNAPSHOT.jar"]
