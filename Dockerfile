FROM java:8

RUN bash -c 'unzip sherpa-1.0-SNAPSHOT.zip -d sherpa'
ADD sherpa

ENTRYPOINT ["sherpa/bin/sherpa"]