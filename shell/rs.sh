#!/bin/bash

# 脚本名： rs.sh start|stop|restart
# rs_admin start|stop|restar

source /data/src/webgenerator/shell/env.sh

echo 'start jetty...'
cd /data/src/webgenerator/web-home/
mvn jetty:run -Djetty.port=7020 ${JAVA_OPTS}


