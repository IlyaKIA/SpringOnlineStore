#!/usr/bin/env bash

#mvn package

echo 'Copy files..'

scp target/online-shop-0.0.1-SNAPSHOT.jar root@109.234.37.141:~/online-shop

echo 'Restart server..'

ssh root@109.234.37.141 << EOF

pgrep java | xargs kill -9
nohup java -jar ~/online-shop/online-shop-0.0.1-SNAPSHOT.jar > ~/online-shop/log.txt &

EOF

echo 'Bye'