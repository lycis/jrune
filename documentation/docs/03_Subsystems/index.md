The JRune game engine works by implementing multiple subsystems that work
together to act as one engine. All subsystems use each other to some degree. 
For example the Entity Management is tightly coupled with the Scripting module
as entities can incorporate scripted actions. The current relations of the
different subsystems are schematically displayed in the image below.

![Subsytems schematics](../img/jrune_subsystem_interaction.png "Interaction of Subsystems")

Each of the subsystem has its own responsibilities and you may choose to only
work or use specific ones. For example when you wish to only use the Entity
Management to keep track of states and handle everything else on your own, you
may only use the Entitiy Management and create plain entities without scripting.

Basically the different subsystems can be have the following responsibilities:
<table>
  <tr>
    <th>Entities</th>
    <td>
      <ul>
        <li>Describing entities</li>
        <li>Loading and unloading of entities</li>
        <li>Entity Lifecycle Management</li>
        <li>Modification of entities</li>
      </ul>
    </td>
  </tr>
  <tr>
    <th>Scripting</th>
    <td>
      <ul>
        <li>Providing a Lua interface</li>
        <li>Parsing scripts</li>
        <li>Script execution</li>
      </ul>
    </td>
  </tr>
  <tr>
    <th>Maps</th>
    <td>
      <ul>
        <li>Map definition</li>
        <li>Entity placement and movement</li>
        <li>Range and area calculations</li>
      </ul>
    </td>
  </tr>
</table>