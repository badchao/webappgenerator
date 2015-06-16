#!/bin/bash

# 配置应用的环境变量

source /etc/profile

export PROJECT_HOME=/data/src/webgenerator
export JAVA_TMPDIR=/data/tmp
export JAVA_LIBRARY_PATH=${PROJECT_HOME}/config/jvm/native
export JAVA_ENDORSED_PATH=${PROJECT_HOME}/config/jvm/endorsed

for f in $PROJECT_HOME/config/jvm/lib/*.jar; do
  CLASSPATH=${CLASSPATH}:$f;
done
export CLASSPATH=${PROJECT_HOME}/config:${CLASSPATH}


for line in `cat ${PROJECT_HOME}/config/application.properties`; do
  JAVA_SYSTEM_PROPERTIES="${JAVA_SYSTEM_PROPERTIES} -D$line"
done
export JAVA_SYSTEM_PROPERTIES


export JAVA_OPTS="${JAVA_SYSTEM_PROPERTIES} -Djava.io.tmpdir=${JAVA_TMPDIR} -Djava.library.path=$JAVA_LIBRARY_PATH -Djava.endorsed.dirs=${JAVA_ENDORSED_PATH}"

