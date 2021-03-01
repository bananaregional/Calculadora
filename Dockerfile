FROM java:8
WORKDIR /
ADD teste.jar teste.jar
EXPOSE 8080
CMD java - jar teste.jar