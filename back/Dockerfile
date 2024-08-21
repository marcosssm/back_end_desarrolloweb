# Usar una imagen base de OpenJDK 11
FROM openjdk:11-jre-slim

# Copiar el archivo JAR construido al contenedor
COPY build/libs/back-0.0.1-SNAPSHOT.jar /app/back-0.0.1-SNAPSHOT.jar

# Establecer el directorio de trabajo
WORKDIR /app

# Exponer el puerto en el que la aplicación estará corriendo
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "movies-app.jar"]
