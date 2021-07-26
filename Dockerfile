FROM openjdk:11
COPY ./target/recipe.war /usr/src/
WORKDIR /usr/src/
EXPOSE 8080
CMD ["java","-jar", "recipe.war"]
