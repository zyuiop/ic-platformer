targetName=Platformer-$TRAVIS_BUILD_NUMBER-$TRAVIS_COMMIT

echo "Deploying to "$targetName

scp -oStrictHostKeyChecking=no target/icplatformer-0.0.1-SNAPSHOT.jar deploy@ks.zyuiop.net:/srv/archive.zyuiop.net/$targetName
ssh -oStrictHostKeyChecking=no deploy@ks.zyuiop.net cp /srv/archive.zyuiop.net/$targetName /srv/archive.zyuiop.net/Platformer-LATEST.jar
