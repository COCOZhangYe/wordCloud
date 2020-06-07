FROM maven:3-jdk-8
COPY . /build
RUN     set -xe;\
        cd /build;\
        mvn clean install

FROM tomcat:jdk8
COPY --from=0 /build/target/wordCloud-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/
RUN curl -o /usr/share/fonts/SourceHanSans.ttc https://github.com/adobe-fonts/source-han-sans/releases/download/2.001R/SourceHanSans.ttc
