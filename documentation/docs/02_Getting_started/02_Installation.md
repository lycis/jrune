There are two ways to get the library. You can either download the source code
and compile it yourself or you can use get the JRune library from the central
maven repository. The first option is for projects that do not use Maven, Gradle
or any other dependency management that is compatible with the central repository.

## Using Maven/Gradle
If your game project is going to use Maven anyway you can skip this step and
fast-forward to the section [Project Setup](Project_Setup.html).

## Manual Compilation & Installation
If you do not use Maven or Gradle for your project you can download the source
code and compile it yourself. As JRune uses Maven for build and dependency
management this is actually pretty straight forward. You will need to have Maven
installed to compile the project. Using Git to fetch the source code is optional
as you can also download it with your web browser.

### Download the Source Code
Use git to fetch the latest stable version of the engine by cloning it with git.

```
git clone https://github.com/lycis/jrune.git
```

You can also navigate your browser to the project Github page and download
the source code as ZIP archive.

### Compile
To compile the source code you need to run maven with the install goal.

```
mvn install
```

This will download all necessary dependencies, compile the source, run tests and
generate a JAR file (engine-VERSION.jar) in a folder named target. This is the
compiled JRune engine without dependencies. Check the Requirements chart to see
which libraries you will need to add to your project so that the game engine can
be used.

> When you encounter any errors during compilation or testing please open a new
> issue in [bugtracker](https://github.com/lycis/jrune/issues) of the project.