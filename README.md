# jrune
Rune is a map-based role playing game engine. It is designed for ROUGE- or
MUD-like games but may be used for any kind of RPGs as it is inherently
agnostic of (meaning there is no) any output layer.

It is composed of different subsystems that are combined to provide a single
game engine.

## Documentation
A markdown formatted documentation is available in the `documentation`
directory. It is intended to be used as base for [daux.io](http://daux.io/index)
to compile a neat HTML documentation but you may as well read it in
plain text. Some day it will also be provided on the web.

To compile the HTML documentation yourself follow the instructions on 
[daux.io](http://daux.io/index) and copy the contents of the `documentation` folder
to the root directory of daux.io. To generate static HTML pages invoke the
following command in the root folder of daux.io.

```
php.exe generate.php
```

For further information (e.g. using grunt, hosting a webpage) look at the
[documentation of daux.io](http://daux.io/Getting_started).

## License
The complete source code is published under terms of the MIT license.

The MIT License (MIT)

Copyright (c) 2015 Daniel Eder

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.