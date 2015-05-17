JRune is a game engine for Java games. It is created to be used for the creation
of role-playing games but as it is a very basic engine it may be used for more 
than that. In its current state (0.0.1 alpha) it can be used to create games
that are based on two-dimensional maps with a Cartesian coordinate system as
their backbone.

## About the Output Layer
A special characteristic of this engine is that it is agnostic of any visual
output layer. So what does this mean? It means that, unlike other game engines,
it does not include any specifics for any kind of output layer like 2D- or
3D-graphics. This means that you have to provide this one yourself when you
intend to use JRune as the backbone of your game. Some people may see this as a
downside of the engine but it is actually intended to be a feature as the
development thus does focus on providing neat and easy to use interfaces for the
rest of the engine (like HTTP and networking support). So you are not bound to
any specific visual output like specific libraries or ways to display your game.

## Scope
In the beginning JRune was started to be the central nervous system of ROUGE-
and MUD-like, text-based games. This is how you will see it to be used in the
tutorial game application. As the idea started as a C++ project it pretty soon
turned out that the ideas that existed for this engine were far greater than a
simple text-adventure engine and thereby the decision was made to move it to
Java and remove any visual output layer.

In its basic functionality JRune focuses to provide an engine for role-playing
games of any kind. You may still use it to implement other games, but all design
decisions and implementations will be made with the idea of an RPG driving it.