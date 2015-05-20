## What is an entity?
Entities or Game Objects (GO) are one of *the* core features of the engine. Almost every game
that is implemented by using JRune will make extensive use of them. Most likely every object
you use in the game will be represented by an entity.

Technically an entity is an object that contains a set of properties. Each property again
is a set of key and value. In its simplest form you can think of an entity as a key-value
table. Of course an entity can do much more, but basically it collects a set of properties
that represent an object - maybe an actual object or something like character - within
the game world.  

![Entity](../../img/entity.png "Entity")

## Properties
Each entity has two kinds of properties it can hold. System Properties and Entity Properties.

The first are *System Properties*. These are used and defined by the engine. Every system
property has a specific purpose and is usually relevant for some extended functionality like
inheritance or scripting. Some of them like `$base` or `$script` can safely be overwritten
or defined by the user, others like  `$uid` or `$entity` must not be touched. They are
managed by the engine and mostly for internal use. You can get a more in depth look at those
properties in the section *System Properties* of the [Properties](Properties)
documentation page.

*Entity Properties* on the other hand are completely in the scope of the user. They can be
defined and changed as you like.

## Definition
To define an entity you have to create an according entity file. This file is written in
YAML format and defines the properties of an entity. The file has to be placed into the
`entity` subfolder of the engine data folder. The code snippet gives an example of an
entity definition.

```yaml
$base: npc.monster
name: Goblin
hp: 10
description: A nifty goblin.
```

The location of the entity also directly
affects its name: Similar to Java packages the name of an entity is derived from the
folder structure it is placed in. For example an entity placed in the file
`entity/npc/goblins/goblin.yml` will be named `npc.goblins.goblin` and it (or more likely
it's blueprint) can be referred to by this name.

In general the structure of entities is tree-like. As entities also support
[inheritance](Inheritance) the location in the folder tree can be used to support this.
For example when the entity `npc.goblins.goblin` inherits from a generic `npc.monster`
entity you should place the files in `entity/npc/monster.yml` and
`entity/npc/goblins/goblin.yml`. Thereby it is more obvious to any game developer that the
entity `npc.monster` is most likely a *base entity* for other entities.
