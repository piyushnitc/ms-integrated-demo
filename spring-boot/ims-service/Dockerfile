# Build an Open JDK 8 image based on Alpine Linux
FROM openjdk:8

# Copy "myApp.jar" file from inside the "/jars" directory. 
# The "/jars" directory is a relative path and should be at the same level as your Dockerfile (this file).
# Copy "myApp.jar" and place the new copy in the "/application/" directory inside the container.
COPY /target/ims-service-0.0.1-SNAPSHOT.jar /application/

# Download the current version of the Java agent with wget
RUN wget https://download.newrelic.com/newrelic/java-agent/newrelic-agent/current/newrelic-java.zip

# Unzip contents into /application directory. Should now be '/application/newrelic'
RUN unzip newrelic-java.zip -d /application

WORKDIR /application

ENTRYPOINT ["java"]

# Here I am passing the "-javaagent" option to the JVM
# We also pass the path that now exists inside the container to the "newrelic.jar" file.
CMD [ \
    "-javaagent:/application/newrelic/newrelic.jar", \
    "-jar", \
    "/application/ims-service-0.0.1-SNAPSHOT.jar" \
]