docker container stop -f $(docker container ls -aq)
docker container rm -f $(docker container ls -aq)
docker image prune -a -f
docker volume prune -f
docker network prune -f