FROM tomcat:alpine

RUN ["rm", "-fr", "/usr/local/tomcat/webapps/ROOT"]

COPY ./build/libs/test.war /usr/local/tomcat/webapps/ROOT.war
