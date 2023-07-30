FROM openjdk:17-alpine
EXPOSE 5500
ADD target/ROOT.jar ROOT.jar
CMD ["java", "-jar", "ROOT.jar"]