#!/bin/bash

# 脚本名： deploy.sh web|admin|test
# 说明：统一的发布脚本，完成以下内容：
# （ 1 ） SVN 更新代码；
# （ 2 ） 程序编译；
# （ 3 ） 修改 host ；
# （ 4 ） 某些特殊资源的获取（如果有 ）；
# （ 5 ） 发布程序。

source /data/src/webgenerator/shell/env.sh


web_home_deploy() {
        cd ../
        svn up
	chmod 775 *.sh -R
        mvn install -Dmaven.test.skip=true $2 $3 $4 $5 $6 $7 $8 $9

} # web_home_deploy

case $1 in
  home)
        web_home_deploy
        ;;
  *)
        echo "Usage: deploy.sh {home}"
        exit 1
        ;;
esac

