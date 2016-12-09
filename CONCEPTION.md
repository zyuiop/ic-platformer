# Conception



# Listing des classes et packages

Des détails concernant les modifications effectuées sont présents en bas de document.

## `platform.game`

Ce package représente toujours les classes liées au jeu.

- `Direction` : la direction d'un bloc
- `Effect` : les effets de dégats d'un acteur envers un autre
- `KeyBindings` : gestion des touches
- `Orientation` : direction d'un bloc sans tenir compte du sens
- `RepeatBehaviour` : un système de répétage des sprites au sein d'un bloc
- `Side` : le côté d'un bloc lors d'une collision
- `Signal` : un signal tel que demandé dans le PDF
- `Simulator` : la classe de gestion du monde
- `World` : l'interface du monde

### `actors`

Ce package contient l'ensemble des acteurs et classes abstraites qui leur sont liés.

- `Actor` : la classe de base d'acteur
- `DisplayableActor` : un acteur pouvant être affiché
- `PositionedActor` : un acteur affichage disposant d'une position
- `MovableActor` : un acteur auquel on peut donner une vélocité pour qu'il se déplace
- `LivingActor` : un acteur disposant d'une barre de vie et pouvant mourir
- `IAttachable` : une interface représentant une "chose" (dans les faits, toujours un acteur) pouvant être attaché

#### `animations`

Ce package contient les effets de particules et overlays qui sont affichés par dessus la scène.

- `BlowAnimation` et `SimpleParticle` : Deux effets de particules basiques. Le premier représente un effet de souffle tandis que le second représente une particule quelconque dont la sprite est fournie à la création.
Ces deux classes héritent de `ParticleActor`, une classe abstraite "vide" utilisée seulement pour différencier les particules des autres acteurs.

- `Crosshair` et `Overlay` : Le premier représente la cible (assistant à la visée). Cette cible ne peut pas être éloignée de plus de 7 unités du joueur, ce qui nécessite certains calculs pour la positionner. Le `Overlay` représente la barre de vie flotant au dessus du jeu, telle que demandée dans le PDF.

#### `blocks`

Ce package contient des objets solides agissant comme des blocks.

- `Block` : un block basique, solide, avec une sprite et une direction. Le concept de direction permet d'orienter la sprite différement selon la direction du block. Il est exploité notament avec le laser.
- `MovingPlatform` (et `AlwaysMovingPlatform`, `OneWayMovingPlatform`) : des plateformes amovibles. La première effectue des allez retour entre ses deux positions tandis que la seconde se déplace de la position 1 à la 2 quand son signal est actif, et effectue le chemin inverse quand le signal est innactif.
- `Door` : un block qui disparait lorsque son signal est activé
- `LaserDoor` : une `Door` spéciale composée de plusieurs sous blocs représentant un laser
- `Exit` : un block qui n'est pas solide lorsque son signal est activé. Lorsque le joueur passe dessus il change alors de niveau.
- `Jumper` et `Spikes` : tels que demandés dans le PDF, mais la gestion du sens de collision est différente selon la direction du bloc, ce qui permet de sauter dans la direction que l'on veut selon le level design
- `ProjectileLauncher` : un bloc lançant des projectiles. Il utilise pour cela une interface fonctionnelle qui instancie le projectile à lancer.

#### `entities`

Ce package contient des entités, à savoir des acteurs qui se déplacent dans le monde et intéragissent avec.

- `Projectile` : la classe de base d'un projectile, pour certaines méthodes communes à la flèche et à la boule de feu, notament la notion de collision.
- `AttachableProjectile` : un projectile pouvant rester attaché à un bloc. Seule la flèche dispose de cette capacité dans le jeu final.
- `Fireball` et `Arrow` : deux projectiles demandés dans le PDF
- `Player` : le joueur tel que demandé dans le PDF. Les détails d'implémentation sont en commentaire dans le code.

#### `environment`

Ce package contient des éléments de décors, qui s'affichent mais ne constituent pas des blocs et ne sont donc pas solides.

- `Background` : le fond du niveau. Il dispose d'une capacité de redimensionnement ou de repeat de l'image pour éviter sa déformation.
- `Decoration` : un simple élément de décor, caractérisé par une sprite, une position, et une taille (exemple du panneau de sortie)
- `Heart`, `Key`, `Lever`, `Limits`, `Torch` : éléments demandés dans le PDF. Les limites ne tuent pas mais retirent une vie.

#### `technical`

Des éléments "techniques" divers.

- `AttachLink` : un acteur qui relie deux acteurs entre eux (une flèche à un bloc, un joueur à une plateforme)
- `InvisiblePlayerDetector` : un signal qui est actif si un joueur se trouve dans sa boite

#### `ui`

Des éléments d'interface graphique.

- `TextBox` : une zone de texte, avec éventuellement une sprite en fond
- `SlowingAdapter` : un adaptateur d'affichage de texte qui affiche le texte lettre par lettre
- `TriggerableTextBox` : une zone de texte qui ne s'affiche que si un signal est actif
- `DismissableTextBox` : une zone de texte qui disparait lorsqu'une touche du clavier est pressée
- `ButtonActor` : un bouton (une zone de texte cliquable)

### `level`

Système de gestion des niveaux.

- `Level` : classe basique modifiée seulement pour changer le niveau par défaut
- `LevelGroup` : un groupe de niveau : lorsqu'un joueur meurt il recommence le groupe depuis le début
- `LevelManager` : le système de gestion du niveau qui détermine quel niveau le joueur doit jouer en fonction des circonstances

### `logic`

Des portes logiques pour les signaux, telles que demandées.

### `menus`

Différentes interfaces graphiques

### `particles`

Un système de création de particules, permettant de stoquer une particule et de la faire apparaître dans le monde autant de fois que désiré
