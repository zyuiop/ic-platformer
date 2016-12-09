# IC Platformer [![Build Status](https://travis-ci.com/zyuiop/ic-platformer.svg?token=UnxVKtRtysz48hf11ok6&branch=master)](https://travis-ci.com/zyuiop/ic-platformer)

Un moteur basique de jeux de plateformes en 2D ainsi que quelques niveaux pour démontrer son fonctionnement.

## Compiler et lancer :

Pour compiler le jeu, il faut se placer dans le dossier racine et exécuter ```mvn package```. Le binaire se trouvera dans le dossier ```target/``` . 

Des binaires compilés en continu via Travis-CI sont disponibles ici : https://archive.zyuiop.net/Platformer

Ceux ci sont obfusqués pour éviter la copie.

Pour lancer le jeu à partir du binaire, un ```java -jar <nom du binaire>``` suffit. Double-cliquer sur le jar devrait également faire l'affaire sur la majorité des gestionnaires de fichier existants.
Le jeu va alors extraire ses ressources dans le dossier ```./res/``` si celui ci n'est pas présent ou incomplet, puis se lancer.

## Jouer au jeu

Le menu principal vous permet de configurer vos touches de jeu. Par défaut, les touches sont les suivantes :

| Fonction | Touches par défaut
|----------|--------------------
| Sauter   | Flèche haut, W
| Droite   | Flèche droite, D
| Gauche   | Flèche gauche, A
| Souffler | B
| Utiliser | E
| Quitter  | Escape
| Tirer    | Clic Souris, Echap
| Viser    | Souris

Appuyer sur Echap interrompt totalement la partie et revient au menu principal.
Les touches peuvent être modifiées depuis le menu Touches.

## Niveaux

Les 3 premiers niveaux représentent des "tutoriaux" basiques. 

### Niveau 1-1

Pour finir le niveau, il faut actionner le levier et traverser jusqu'à la porte.

### Niveau 1-2

Pour finir le niveau, il faut désactiver le laser et activer les plateformes. Cela se fait simplement en inversant chacun des leviers. 

### Niveau 1-3

Il faut souffler la torche puis utiliser les plateformes pour traverser (un double saut est nécessaire pour traverser entre les deux plateformes). Ensuite, il faut viser la torche et lancer une boule de feu pour ouvrir la porte de sortie.