FROM java:8
EXPOSE 8088

VOLUME /tmp
ADD xq-front-demo.jar /front.jar
RUN bash -c 'touch /front.jar'
ENTRYPOINT ["java","-jar","/front.jar"]
