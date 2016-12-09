# Conception

Ce document liste les modifications et ajouts réalisés par rapport à la base proposée dans le sujet.

## Intéractions entre acteurs

Le système tel que proposait ne me convenait pas totalement, notament pour la gestion des collisions des spikes et du jumper, que je voulais être des blocks.
Ainsi une nouvelle méthode a été ajoutée dans `Actor`, permettant à un acteur de priorité supérieure collisionnant avec un bloc de signaler à celui ci la collision. Par exemple si un joueur tombe sur un jumper, il appelle une méthode du jumper en indiquant la face du jumper sur laquelle il est tombé.

Un second ajout a été réalisé, pour gérer l'animation de dégats et l'immunité à ceux ci. Il s'agit de paramètres dans l'enumération `Effect` qui permetttent de savoir si un effet donné effectue des dommages ou restitue de la vie.

## Gestion du jumper et des spikes

Comme indiqué ci dessus, le jumper et les spikes sont des blocks. Lorsqu'un bloc effectue une collision avec eux, la `Side` de collision est calculée et la méthode correspondante est appelée. Le bloc détermine ensuite si la `Side` est la bonne en fonction de la `Direction` de celui ci (par exemple, si le jumper est en direction `UP` et que la side est `LEFT` le joueur ne doit pas ètre envoyé dans les airs).

## Signaux par défaut

L'interface `Signal` pouvant être considérée comme une interface fonctionnelle, les deux implémentations "constantes" (toujours vrai et toujours faux) ont été écrites directement dans celle ci sous forme d'expressions lambda.

## Autres modifications relatives aux blocs

- Un bloc dispose d'une direction. La direction est utilisée pour recalculer si nécessaire la box de l'affichage ainsi que la rotation de la sprite. Il est aussi utile pour les `Spikes`, `Jumper`, notament.
- Un bloc peut afficher plusieurs fois sa sprite pour ne pas avoir à la redimentionner, via l'utilisation d'un `RepeatBehaviour`.

## Modification de la hiérarchie des acteurs

Plusieurs classes abstraites ont été introduites pour gérer les acteurs :

- Au premier rang, `Actor`, avec les méthodes exigées ainsi qu'un `onCollide` décrit plus haut
- En dessous, `PositionedActor` qui représente un acteur positionné via une box (l'acteur `Level` n'hérite par exemple pas de cette classe)
- En dessous, `DisplayableActor` qui représente un acteur affichable. Cette classe gère toutes les modalités de l'affichage, et notament la recherche de la sprite. Elle définit plusieurs méthodes qui peuvent être surchargées pour modifier l'angle de la sprite et sa box d'affichage. L'acteur `InvisiblePlayerDetector` n'hérite par exemple pas de cette classe.
- En dessous, `MovableActor` qui représente un acteur à qui on peut appliquer une vitesse, laquelle modifiera dynamiquement sa position à chaque update. Tous les acteurs déplaçables sont affichables, ainsi cette classe hérite de `DisplayableActor`. Les blocs n'héritent pas de cette classe.
- En dessous, `LivingActor` qui représente un acteur vivant (qui a une barre de vie). Seul le `Player` hérite de cette classe.

En outre, l'interface `IAttachable` a été ajoutée pour décrire un acteur pouvant être attaché à un autre. Le lien d'attache est géré via `AttachLink`, une classe qui se charge de déplacer l'acteur attaché (toujours `Movable`) en fonction de la position de l'acteur sur lequel il est attaché.

## Modification de la gestion des Input

Les touches ne sont plus écrites en dur dans le code mais gérées par la classe `KeyBindings`. 

Cette dernière gère la sauvegarde et la lecture des touches depuis un fichier `.properties`, ainsi que la détection des touches via un appel à une méthode.

## Gestion des niveaux

Un système se charge de gérer les niveaux, qui ne sont donc plus passés directement à la porte de sortie. Le noyau de ce système est le `LevelManager`. Il fonctionne selon un concept simple de `LevelGroup` et `PlayableLevel` : les niveaux sont organisés par groupe. Perdre toutes ses vies nécessite de recommencer le groupe depuis le début. En outre, tomber dans le vide retire seulement une vie, pour ne pas trop pénaliser la chute des plateformes.

## 