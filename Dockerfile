FROM amazoncorretto:17

RUN yum install -y unzip

ADD build/distributions/*boot*.zip /spring-cloud-stream-example.zip

RUN unzip -o spring-cloud-stream-example.zip && \
    rm -rf *.zip && \
    mv spring-cloud-stream-example-* spring-cloud-stream-example

EXPOSE 8080

ENTRYPOINT ["/spring-cloud-stream-example/bin/spring-cloud-stream-example"]