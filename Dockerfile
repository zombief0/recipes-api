FROM openjdk:11
COPY ./target/recipe.jar /usr/src/
WORKDIR /usr/src/
EXPOSE 8080
CMD ["java","-jar", "recipe.jar"]
