FROM maven:3-jdk-8
COPY . /build
RUN     set -xe;\
        cd /build;\
        mvn clean install

FROM tomcat:jdk8
COPY --from=0 /build/target/wordCloud-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/
RUN set -xe;\
    cd /root;\
    apt-get update;\
    apt-get install -y p7zip;\
    wget https://github.com/be5invis/source-han-sans-ttf/releases/download/v2.001.1/source-han-sans-ttf-2.001.1.7z;\
    p7zip -d source-han-sans-ttf-2.001.1.7z;\
    cp SourceHanSansSC-Light.ttf /usr/share/fonts;\
    apt-get purge p7zip -y
