
# Set ubuntu 18.04 as base image
FROM openjdk:11

# Install updates
RUN apt-get update && apt-get -y install software-properties-common

# Install wget, curl, vim
RUN apt-get -y install wget curl vim

# Install Maven
RUN apt-get -y install maven

# Copy ers to user directory
COPY . /usr/local/fake-api-hospital/

WORKDIR /usr/local/fake-api-hospital

# Maven install and assemble sample app
RUN mvn -B clean

RUN mvn -B install

RUN cp /usr/local/fake-api-hospital/target/*.war /usr/local/fake-api-hospital/target/fakehospital.war

# Execute app
CMD ["java","-jar","/usr/local/fake-api-hospital/target/fakehospital.war"]

