FROM amazoncorretto:17
LABEL authors="https://github.com/gabrielguidini"

WORKDIR ./attus-procuradoria

COPY build/libs/attus-procuradoria.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "attus-procuradoria.jar"]