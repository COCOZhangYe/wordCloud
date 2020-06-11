FROM maven:3-jdk-8
COPY . /build
RUN     set -xe;\
        cd /build;\
        mvn clean install

FROM tomcat:jdk8
COPY --from=0 /build/target/wordCloud-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/
RUN     set -xe;\
        echo "tomcat.util.http.parser.HttpParser.requestTargetAllow=|{}" >> /usr/local/tomcat/conf/catalina.properties;\
        echo "org.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH=true" >> /usr/local/tomcat/conf/catalina.properties
