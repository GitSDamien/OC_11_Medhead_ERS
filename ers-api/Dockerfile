
# Set ubuntu 18.04 as base image
FROM openjdk:11

# Install updates
RUN apt-get update && apt-get -y install software-properties-common

# Install wget, curl, vim
RUN apt-get -y install wget curl vim

# Install Maven
RUN apt-get -y install maven

# Copy ers to user directory
COPY . /usr/local/ers-api/

WORKDIR /usr/local/ers-api

# Maven install and assemble sample app
# RUN mvn -B clean

RUN mvn -B install

RUN cp /usr/local/ers-api/target/*.war /usr/local/ers-api/target/ers.war

# Execute app
CMD ["java","-jar","/usr/local/ers-api/target/ers.war"]

