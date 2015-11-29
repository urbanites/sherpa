FROM java:8

ADD target/universal/sherpa-1.0-SNAPSHOT.zip /tmp/sherpa.zip

RUN  mkdir /sherpa
RUN  unzip /tmp/sherpa.zip -d /sherpa
RUN  ls -al /sherpa

ENTRYPOINT ["/sherpa/bin/sherpa"]