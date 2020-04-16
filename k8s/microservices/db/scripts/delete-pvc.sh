k8s-db-volume-k8s-postgres-0


kubectl get pvc --selector=app=k8s-postgres -o jsonpath='{.items[*].name}'