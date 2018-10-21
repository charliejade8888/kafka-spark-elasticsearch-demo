<h4>Prerequisites
<h6>#1 You need Kafka and Elasticsearch installed (docker-compose.yml's supplied if you wish to use those)
<h6>#2 Chromium/Chrome browser with the 'Elasticsearch head' plugin installed. 
<h6>#3 Familiarity with the basic functions of Gradle
<h6>#4 Familiarity with the basic functions of the Elasticsearch head plugin
<h6>#5 Familiarity with the basic functions of Kafka (see the quickstart link below)

<h4>Quickstart

<h6>#1 Start Kafka
<h6>#2 Start elasticsearch
<h6>#3 ./bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic spark_sql_test_topic
<h6>#4 ./bin/kafka-console-producer.sh --broker-list localhost:9092 --topic spark_sql_test_topic
<h6>#5 Start the application in your IDE or with ./gradlew run
<h6>#6 Type a message into the Kafka producer console and wait!
<h6>#7 Check out the 'mylovelyindex' index in Elasticsearch and see your message has been uploaded!

<h4>Top tips!<p>
<h6>In Intellij use gradle sdk!! Settings > scala > compiler > project default > choose gradle when prompted o choose SDK. 
You may have to execute a gradle build task from the IDE first.
On upgrading IntelliJ, you may have to remove the project from the workspace and then re-import it. 
If IDE gives error 'cannot find .idea/modules directory', then simply manually create that directory.
<h6>The number of files in the dependencies of this project exceeds the max limit allowed in a jar file.
<h6>I recommend using ./gradlew distTar, then go to build/distributions, unpacking it, then using the script in the bin folder to start the app!

<h4>Some Useful Links
<h6>https://www.elastic.co/guide/en/elasticsearch/hadoop/master/spark.html
<h6>https://alvinalexander.com/scala/how-to-use-mock-objects-with-scalatest
<h6>https://kafka.apache.org/quickstart
<h6>