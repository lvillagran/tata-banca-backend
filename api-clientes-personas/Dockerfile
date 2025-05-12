# Usa una imagen base de OpenJDK
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia el archivo .jar de la aplicación al contenedor
COPY target/api-clientes-personas.jar /app/api-clientes-personas.jar

# Expone el puerto correcto en el que la aplicación escucha
EXPOSE 9098

# Comando para ejecutar la aplicación cuando el contenedor se inicie
ENTRYPOINT ["java", "-jar", "api-clientes-personas.jar"]