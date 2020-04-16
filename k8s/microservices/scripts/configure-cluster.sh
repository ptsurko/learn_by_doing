DIR="$( cd "$(dirname "$0")" ; pwd -P )"

kubectl apply -f ${DIR}/../kube/
