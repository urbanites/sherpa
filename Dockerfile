FROM java:8
VOLUME /tmp
ADD universal/sherpa-1.0-SNAPSHOT/sherpa-1.0-SNAPSHOT.zip app.zip
RUN bash -c 'unzip app.zip'
ENTRYPOINT ["herpa-1.0-SNAPSHOT/bin/sherpa"]