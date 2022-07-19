
# Set ubuntu 18.04 as base image
FROM openjdk:11

# Install updates
RUN apt-get update && apt-get -y install software-properties-common

# Install wget, curl, vim
RUN apt-get -y install wget curl vim

# Install Maven
RUN apt-get -y install maven

# Copy ers to user directory
COPY . /usr/local/test-e2e/

WORKDIR /usr/local/test-e2e

# Maven install and assemble sample app
# RUN mvn -B clean

# RUN mvn -B test

