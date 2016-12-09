# Conception

## Contrôles

Le système de contrôles a été modifié pour permettre la modification par l'utilisateur des contrôles par défaut.
Il est organisé autour de la classe `platform.game.KeyBindings`, organisée selon le pattern singleton. Cette classe gère les assignations de touches, la détection de celles ci et leur sauvegarde/lecture dans un fichier (le format "natif" properties est utilisé pour cela).

