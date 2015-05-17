Including JRune in your project is very easy. The whole game engine is packaged
into a single JAR archive which allows simple inclusion. The easiest way is to
use a dependency management tool that is compatible to the Maven central
repository. If you do not it still possible to add the library to your project.

## Maven
JRune is hosted in the Maven central repository. This means that it is
sufficient to add the dependency to your pom.xml.

```
<dependency>
  <groupId>org.jrune</groupId>
  <artifactId>engine</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
```

## Other
First you need to get the JAR file. You can either download the latest release
from the Maven repository or compile it yourself. Instructions for compilation
are given on the [Installation](Installation.html) page. Once you have the binary
archive you can simply add it to the classpath.

You will need to also add the dependencies of the engine. These are listed on the
[Requirements](Requirements.html) page.