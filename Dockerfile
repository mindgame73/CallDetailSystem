FROM openjdk:11
MAINTAINER Rinat Ibragimov
WORKDIR /home/cdr
COPY entrypoint.sh /usr/bin/entrypoint.sh
RUN chmod +x /usr/bin/entrypoint.sh
ENTRYPOINT ["/usr/bin/entrypoint.sh"]