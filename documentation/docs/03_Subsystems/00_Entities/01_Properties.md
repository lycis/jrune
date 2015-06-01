Entities consist of a list of properties. These are the flesh that makes
entities what they are. As already mention in the introduction to entities, you
can basically perceive them as a list of properties. Of course entities can do
much more when you take scripting into account. A property is essentially a
key-value pair that an entity contains.

## Types
There are two types of properties: *System* properties and *entity* properties.
The first kind is relevant for some engine functionality. The latter one is any
property that is not a system property. Within this documentation entity
properties will just be called *properties*.

### Entity Properties
Let's start with a look at the simpler part: Entity properties. To put it simple
any property that is not a system property is just an entity property. Each
entity property has an according value. For properties there is only one naming
convention: they may not start with a $ (dollar sign) because this is reserved
for system properties only.

### System Properties
System properties are predefined by the engine. They always start with a $
(dollar sign). So whenever you spot a property like `$base` you'll know that it
is a system property. Every system property is tied to some functionality of the
engine. For example the property `$uid` holds the unique ID of an active entity.
Some system properties can be set by the user while others will be overwritten
by the engine during the life cycle of an entity.

There is finite list of system properties that are listed here:

<table>
<tr><th>Property</th><th>Managed</th><th>Use/Meaning</th></tr>
<tr>
  <td>$uid</td>
  <td>yes</td>
  <td>Holds the unique ID of an active entity. It is only available on cloned,
      active entities and must not be overwritten. It is intended to be used to
      identify an entity and should be used or passed as reference to a specific
      entity.</td>
</tr>
<tr>
  <td>$base</td>
  <td>no</td>
  <td>Defines the base entity of the current entity. Set this to the name of an
      entity to inherit properties from it. For more details on inheritance see
      section [Inheritance](Inheritance)</td>
</tr>
<tr>
  <td>$entity</td>
  <td>yes</td>
  <td>Holds the entity name of this entity. This property is useful to identify
      the type of a game object that is currently active.</td>  
</tr>
<tr>
  <td>$location</td>
  <td>yes</td>
  <td>This property holds information about the current position of entity on a
      map. It is in the form of `map:x:y`. For example an entity that is
      positioned on the map `castle` on the coordinates `1:20` will have the
      `$location` set to `castle:1:20`. Usually you do not need to work with
      this on your own as this is handled by maps.</td>
</tr>
<tr>
  <td>$passable</td>
  <td>no</td>
  <td>The map subsystem uses this property to check if this entity can be passed
      through. If it is set to `1` another entity can not be on the same coordinates
      as this entitiy. If it is set to `0` another entity may occupy the same space.</td>
</tr>
<tr>
  <td>$script</td>
  <td>no</td>
  <td>Defines the script that is associated with this entity. You do not need to
      set this entity when creating scripted entities but only when the script
      path differs from the default. See [Scripted Entities](Scripted_Entities)
      for details.</td>
</tr>
</table>

## Defining Properties
Intially every entity has an entity definition that is represented by an YAML file
in the `entity` directory. It contains the initial property values for an entity.

When an entity is loaded the first time its properties will be initialised with
the values given therein. After this initial load you can add and modify them
to adapt the entity to your needs.

```
$base: npc.monster
name: Goblin
hp: 10
description: A nifty goblin.

```

### Rules for Properties
The name of each property is up to you with one exception: It must not start with
a `$` sign. This indicates that the entity is a system entity. The value may be
a string of any form. Currently the engine does not support the use of structured
values for properties (e.g. lists).

## Working with Properties

### Reading Values
It is pretty simple to read the value of a property. They are always of type
`String` and can be accessed by using method `getProperty(String prop)`. If
you want to get a property as a specific type like `Integer` or `Boolean`
you can use the method `getPropertyAs(Class type, String prop)` that will
try to convert the value of the property to the given type.

```java
// read different properties
RuneEntity entity = engine.clone("npc.goblins.goblin");

String strValue = entity.getProperty("name");
Integer intValue = entity.getPropertyAs(Integer.class, "hp");
```

The engine already provides some built-in conversions for the following
types:

  * Short
  * Integer
  * Long
  * Float
  * Double
  * Boolean
  * String

If you want to have your own conversion you can provide it by implementing
the a conversion class with the interface `IRunePropertyValueConversion` and
registering it with the static method `registerPropertyValueConversion` of
class `RuneEntity`.

```java
// implementation of integer conversion
class IntegerConversion implements IRunePropertyValueConversion<Integer> {
  @Override
  public String toPropertyValue(Integer from) {
    return from.toString();
  }

  @Override
  public Integer fromPropertyValue(String value) {
    return new Integer(value);
  }
}

// register it with the engine
RuneEntity.registerPropertyValueConversion(Integer.class, new IntegerConversion());
```

### Modifying Properties at Runtime
Once an entity was cloned from a blueprint (see [Lifecycle](Lifecycle)) you can
modify its properties.  To modify the value of an entity use the method
`setProperty(String prop, String value)`.

```java
// modify the property of a clone
RuneEntity clone = engine.cloneEntity("npc.goblins.goblin");
clone.setProperty("hp", 40); // set hit point property to 20
System.out.println(clone.getProperty("hp")); // --> 40
```

You may also changed the properties of a blueprint entity but this will affect
any further clones you create!

```java
// modify the property of a blueprint
RuneEntity blueprint = engine.getBlueprint("npc.goblins.goblin");
blueprint.setProperty("hp", 20);

// from this point on every clone of npc.goblins.goblin has hp set to 20
RuneEntity clone = engine.cloneEntity("npc.goblins.goblin");
System.out.println(clone.getProperty("hp")); // --> 20
```
