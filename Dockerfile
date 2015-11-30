FROM java:8

ADD target/universal/sherpa-1.0-SNAPSHOT.zip /tmp/sherpa.zip

RUN  mkdir /sherpa
WORKDIR /tmp
RUN  unzip /tmp/sherpa.zip
RUN  mv sherpa-1.0-SNAPSHOT/* /sherpa
RUN  ls -al /sherpa

EXPOSE 9000

ENTRYPOINT ["/sherpa/bin/sherpa"]