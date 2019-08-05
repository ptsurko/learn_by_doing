FROM postgres:9.6.2

COPY ./setup-slave.sh /setup-slave.sh
RUN chmod +x /setup-slave.sh

ENTRYPOINT ["/setup-slave.sh"]
CMD ["gosu", "postgres", "postgres"]