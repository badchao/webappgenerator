#!/bin/bash

# 脚本名： rs.sh start|stop|restart
# rs_admin start|stop|restar

echo "git pull and force update, will lost local change"
git pull
git fetch --all
git reset --hard origin/master

source /etc/profile
source /data/src/webgenerator/shell/env.sh

echo 'start jetty...'
cd /data/app/webappgenerator/web-home/
mvn jetty:run -Djetty.port=7020 ${JAVA_OPTS}


