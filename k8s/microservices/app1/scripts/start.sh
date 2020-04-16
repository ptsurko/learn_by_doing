TAG=${1:-latest}

docker stop k8s-app1
docker rm k8s-app1
docker run --name k8s-app1 -it --net=host ptsurko/k8s-app1:${TAG}
