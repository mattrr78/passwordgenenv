FROM adoptopenjdk/openjdk13
COPY build/libs/passwordgenenv-1.0.0.jar passwordgenenv-1.0.0.jar
RUN mkdir logs
ENTRYPOINT ["java","-jar","/passwordgenenv-1.0.0.jar"]