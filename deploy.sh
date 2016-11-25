# !/bin/bash

# Simple script to deploy compiled artifact to the dedicated server

targetName=Platformer-$TRAVIS_BUILD_NUMBER-$TRAVIS_COMMIT

echo "Deploying to "$targetName

scp target/icplatformer-0.0.1-SNAPSHOT.jar deploy@ks.zyuiop.net:/srv/archive.zyuiop.net/$targetName
ssh deploy@ks.zyuiop.net cp /srv/archive.zyuiop.net/$targetName /srv/archive.zyuiop.net/Platformer-LATEST.jar
