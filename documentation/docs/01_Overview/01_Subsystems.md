The engine provides multiple areas of functionality that are divided into
different subsystems. Those do work together to provide you a fully functional
game engine. To get more information about the different aspects see their
documentation pages.

The following subsystems are provided by release 0.0.1 (alpha).

### Entity Management
Every object that appears in your game is represented as an entity within the
engine. These entities may be characters like monsters the player has to defeat
or objects he or she can use. Every game object is based on multiple properties
that define it. The engine lets you manage the creation, instantiation and
modification of these entities.

### Scripting
The engine lets you implement specific intelligence and actions of entities by
using Lua. This way you can either create your own code to make entities act and
interact or use the built-in scripting. It allows you to create intelligent
entities with actions and reactions. Scripts can also be used in other areas
like describing the game loop or customising maps.

### Map Management
Entities on their own are quite useful but a bit dull. You can place entities
on different maps, move them around and use stuff like built-in collision
detection and path-finding. Currently only two-dimensional maps are supported.
Further releases are planned
