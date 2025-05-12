# Usa una imagen base de OpenJDK
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia el archivo .jar de la aplicación al contenedor
COPY target/api-cuenta-movimientos.jar /app/api-cuenta-movimientos.jar

# Expone el puerto real donde escucha tu microservicio
EXPOSE 9091

# Comando para ejecutar la aplicación cuando el contenedor se inicie
ENTRYPOINT ["java", "-jar", "api-cuenta-movimientos.jar"]