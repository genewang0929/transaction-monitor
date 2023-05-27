cd ~
cd kafka_2.13-3.4.0
bin/kafka-console-consumer.sh --topic ebankTopic-transactions --from-beginning --bootstrap-server localhost:9092