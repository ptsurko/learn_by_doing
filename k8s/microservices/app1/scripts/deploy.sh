DIR="$( cd "$(dirname "$0")" ; pwd -P )"

kubectl apply -f ${DIR}/../kube/secrets.yaml
kubectl apply -f ${DIR}/../kube/service.yaml
kubectl apply -f ${DIR}/../kube/pvc.yaml
kubectl apply -f ${DIR}/../kube/deployment.yaml
