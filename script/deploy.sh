targetName=Platformer-$TRAVIS_BUILD_NUMBER-${TRAVIS_COMMIT:0:8}.jar

echo "Deploying to "$targetName

scp -oStrictHostKeyChecking=no target/icplatformer-0.0.1-SNAPSHOT.jar deploy@ks.zyuiop.net:/srv/archive.zyuiop.net/Platformer/$targetName
ssh -oStrictHostKeyChecking=no deploy@ks.zyuiop.net cp /srv/archive.zyuiop.net/Platformer/$targetName /srv/archive.zyuiop.net/Platformer/Platformer-LATEST.jar
