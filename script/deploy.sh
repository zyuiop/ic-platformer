#!/bin/bash

# As this project is still private, we obfuscate it strongly first
wget https://files.zyuiop.net/proguard.jar -O proguard.jar
java -jar proguard.jar @script/config.pro

echo "No changes." > CHANGES.txt

if [[ $TRAVIS_COMMIT_RANGE ]]
then 
	echo ${git log $TRAVIS_COMMIT_RANGE} > CHANGES.txt
	cat CHANGES.txt
fi

# Then we deploy it using SSH because I still don't want to learn how to use rsync o/
targetName=Platformer-$TRAVIS_BUILD_NUMBER-${TRAVIS_COMMIT:0:8}.jar
scp -oStrictHostKeyChecking=no platformer.jar deploy@ks.zyuiop.net:/srv/archive.zyuiop.net/Platformer/$targetName
scp -oStrictHostKeyChecking=no CHANGES.txt deploy@ks.zyuiop.net:/srv/archive.zyuiop.net/Platformer/
ssh -oStrictHostKeyChecking=no deploy@ks.zyuiop.net cp /srv/archive.zyuiop.net/Platformer/$targetName /srv/archive.zyuiop.net/Platformer/Platformer-LATEST.jar
