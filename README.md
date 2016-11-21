# ic-platformer

The second Java "mini-project" of the first semester. 

# Custom game data format

(some stuff here)

The content of a tile data :

| name | type | content |
|------|------|---------|
| type | int  | the type of the tile |
| name | string | optional : the name of the tile, for future usage |

A signal argument can be passed. It can be of various types.

- `name` : the name of a previously registered tile acting as a signal
- `AND` : a special condition, taking two signal arguments, named `s1` and `s2`
- `OR` : a special condition, taking two signal arguments, named `s1` and `s2`
- `NOT` : a special condition, taking one signal argument, named `s1`
