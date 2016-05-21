#!/bin/sh

echo [INFO] Install jar to local repository.

cd ..
mvn clean install -Dmaven.test.skip=true
cd bin
