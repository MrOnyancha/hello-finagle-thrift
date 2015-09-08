#!/bin/bash
jarfile=target/scala-2.11/hello-finagle-thrift-0.2.jar

mkdir -p logs
java -Djava.net.preferIPv4Stack=true -ea -server -Xmn2g -Xms6g -Xmx6g \
  -XX:+AggressiveOpts \
  -XX:+UseParNewGC \
  -XX:+UseConcMarkSweepGC \
  -XX:+CMSParallelRemarkEnabled \
  -XX:+CMSClassUnloadingEnabled \
  -XX:+DisableExplicitGC \
  -jar $jarfile "$@" >> logs/application.log 2>&1 &

appPID=$!
echo $appPID > logs/application.pid
