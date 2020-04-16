DIR="$( cd "$(dirname "$0")" ; pwd -P )"

kubectl delete -f ${DIR}/../kube/service.yaml --wait
kubectl delete -f ${DIR}/../kube/statefulset.yaml --wait

kubectl delete pvc $(kubectl get pvc --selector=app=k8s-postgres -o jsonpath='{.items[*].metadata.name}') --wait
