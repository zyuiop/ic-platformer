# IC Platformer [![Build Status](https://travis-ci.com/zyuiop/ic-platformer.svg?token=UnxVKtRtysz48hf11ok6&branch=master)](https://travis-ci.com/zyuiop/ic-platformer)

Un moteur basique de jeux de plateformes en 2D ainsi que quelques niveaux pour démontrer son fonctionnement.

## Compiler :

### Avec Maven

Un pom.xml est disponible ici : https://archive.zyuiop.net/Platformer/pom.xml

Pour compiler le jeu, il faut se placer dans le dossier racine et exécuter ```mvn package``` (si le pom.xml est présent). Le binaire se trouvera dans le dossier ```target/```. 

### Télécharger le binaire

Des binaires compilés en continu via Travis-CI sont disponibles ici : https://archive.zyuiop.net/Platformer

Ceux ci sont obfusqués pour éviter la copie.

### Compiler depuis un IDE ou javac :

Compilez simplement en un jar en définissant la classe principale sur `platform.Program`.

## Lancer

Pour lancer le jeu à partir du binaire, un ```java -jar <nom du binaire>``` suffit. Double-cliquer sur le jar devrait également faire l'affaire sur la majorité des gestionnaires de fichier existants.
Le jeu va alors extraire ses ressources dans le dossier ```./res/``` si celui ci n'est pas présent ou incomplet, puis se lancer.

Vous pouvez fournir en argument le nom de la classe d'un niveau (exemple : `platform.game.level.cave.Cave1`) pour jouer directement sur ce niveau.

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

Les 2 premiers niveaux représentent des "tutoriaux" basiques. 

### Niveau 1-1 (levier et porte)

Pour finir le niveau, il faut actionner le levier et traverser jusqu'à la porte.

### Niveau 1-2 (leviers et plateformes)

Pour finir le niveau, il faut désactiver le laser et activer les plateformes. Cela se fait simplement en inversant chacun des leviers. 

### Niveau 2-1 (torches et tir)

Il faut souffler la torche puis utiliser les plateformes pour traverser (un double saut est nécessaire pour traverser entre les deux plateformes). Ensuite, il faut viser la torche et lancer une boule de feu pour ouvrir la porte de sortie.

### Niveau 2-2 (clés, leviers, portes logiques)

1. Actionnez le levier jaune
2. Prenez la clé orange 
3. Désactivez le levier jaune
4. Actionnez les leviers rouge et vert
5. Actionnez le levier jaune 
6. Prenez la clé bleue
7. Désactivez le levier vert
8. Activez le levier bleu
9. Désactivez le levier jaune
10. Désactivez le levier rouge
11. Sortez

### Niveau 2-3

Il n'y a pas de puzzle spécifique à résoudre, il faut simplement utiliser les jumpers tout en évitant les piques, les flèches et les boules de feu.

## Mot de la fin

Je souhaitais initialement aller beaucoup plus loin dans la création du jeu pour participer au concours, comme peuvent en témoigner certains TODOS présents dans le code. Des sons avaient été ajoutés, et pas mal d'autres acteurs. Mais en raison du manque de temps, j'ai supprimé ceux ci de la version "finale" que vous avez entre les mains. 