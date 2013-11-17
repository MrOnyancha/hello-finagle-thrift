#!/bin/bash
jarfile=target/scala-2.10/hello-finagle-assembly-0.1-SNAPSHOT.jar

java -ea -server -Xss8m -Xms1g -Xmx1g \
  -XX:+UseG1GC \
  -XX:MaxPermSize=256m \
  -XX:+DisableExplicitGC \
  -jar $jarfile "$@" >> logs/application.log 2>&1 &

applicationPID=$!
echo $applicationPID > logs/application.pid
