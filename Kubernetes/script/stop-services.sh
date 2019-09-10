echo Stopping all services...

sudo kubectl delete statefulset.apps/kafka-broker service/kafka-broker
sudo kubectl delete configmap kafka-config
sudo kubectl delete statefulset.apps/mysql service/mysql
sudo kubectl delete configmap mysql-config mysql-initdb-config
sudo kubectl delete statefulset.apps/zookeeper service/zookeeper

