FROM adoptopenjdk/openjdk11:latest
LABEL author = "orcharddu<orcharddu@gmail.com>"
ADD ./build/libs/*.jar /home/gitrends/gitrends-api.jar
CMD ["java","-jar","/home/gitrends/gitrends-api.jar"]