DIR="$( cd "$(dirname "$0")" ; pwd -P )"
TAG=${1:-latest}

docker build -t ptsurko/k8s-app1 ${DIR}/..

docker push ptsurko/k8s-app1:${TAG}
