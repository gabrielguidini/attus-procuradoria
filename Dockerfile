FROM amazoncorretto:17
LABEL authors="gabrielsguidini@gmail.com"

WORKDIR /attus-procuradoria

COPY build/libs/*.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "attus-procuradoria.jar"]