#!/bin/bash

# As this project is still private, we obfuscate it strongly first
java -jar script/proguard.jar @script/config.pro

# Then we deploy it using SSH because I still don't want to learn how to use rsync o/
targetName=Platformer-$TRAVIS_BUILD_NUMBER-${TRAVIS_COMMIT:0:8}.jar
scp -oStrictHostKeyChecking=no platformer.jar deploy@ks.zyuiop.net:/srv/archive.zyuiop.net/Platformer/$targetName
ssh -oStrictHostKeyChecking=no deploy@ks.zyuiop.net cp /srv/archive.zyuiop.net/Platformer/$targetName /srv/archive.zyuiop.net/Platformer/Platformer-LATEST.jar
