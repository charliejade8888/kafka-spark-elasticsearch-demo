cd /home/sclater/dev/docker/docker-compose*/elasticsearch
mate-terminal --geometry=73x18+0+434 &>/dev/null &
cd /home/sclater/dev/docker/docker-compose*/kafka 
mate-terminal --geometry=73x18+694+434 &>/dev/null &
cd /home/sclater/sandbox/scala/kafka/kafka_2.11-1.0.0 
mate-terminal --geometry=73x18+694+0 &>/dev/null &
echo "sudo sysctl -w vm.max_map_count=262144"
echo "./bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic spark_sql_test_topic"
echo "./bin/kafka-console-producer.sh --broker-list localhost:9092 --topic spark_sql_test_topic"
exit
