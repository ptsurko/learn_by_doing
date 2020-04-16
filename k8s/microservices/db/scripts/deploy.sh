DIR="$( cd "$(dirname "$0")" ; pwd -P )"

kubectl delete -f ${DIR}/../kube/service.yaml --wait
kubectl delete -f ${DIR}/../kube/statefulset.yaml --wait

kubectl apply -f ${DIR}/../kube/service.yaml
kubectl apply -f ${DIR}/../kube/statefulset.yaml
