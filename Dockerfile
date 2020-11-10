
#-------------------------------------------------------------------------------------------------------------------------------------------------
#           DOCKER FILE
#-------------------------------------------------------------------------------------------------------------------------------------------------

FROM  openjdk:latest
COPY   src/     /usr/src/
COPY   docker-compose.yml     /usr/src/
EXPOSE 8080
RUN ["docker-compose   -f   /usr/src/docker-compose.yml  up -d"]
RUN ["docker exec  -it   kafka   /bin/sh"]
RUN ["cd    /opt/kafka/bin"]
RUN ["kafka-topics.sh   --create  --zookeeper    zookeeper:2181   --replication-factor  1  --partitions  1  --topic    topic1"]
CMD ["java", "-jar", "/usr/src/kafka.jar"]



