# 02161 Software Engineering 1 Exam Project - Group 5

This repository contains a time registration and project management system. This project was developed as part of the exam project for the DTU course [02161 Software Engineering 1](https://kurser.dtu.dk/course/2025-2026/02161).

## Requirements

To run the program, you must have ![Java](https://www.java.com/en/download/windows_manual.jsp) installed.
The project uses external libraries managed by Maven. Run `mvn install`  to install dependencies.. This requires ![Maven](https://maven.apache.org/download.cgi) to be installed and avaiable in the system's environment variables. 
We recommend using IntelliJ IDEA. Other IDEs (e.g., Eclipse, VS Code) may require additional configuration.

## How to run
This project uses Maven to compile and run the application. You must have [Maven installed](https://maven.apache.org/download.cgi).

To run this project, you can use the following command in your terminal while in the root directory of the project:

```bash
mvn clean compile exec:java
```

To run the tests, you can use the following command:

```bash
mvn clean test
```



## Authors and Acknowledgment

This project was developed by [August M. Andersen](https://gitlab.gbar.dtu.dk/s241541), [Elias Bondesgaard](https://gitlab.gbar.dtu.dk/s241121), [Jacob Hartz](https://gitlab.gbar.dtu.dk/s246077), [Julius Mensa-Annen](https://gitlab.gbar.dtu.dk/s245723), and [Matthias F. Biil](https://gitlab.gbar.dtu.dk/s245759).

Supervized by our course professor ![Hubert Baumeister](https://www.dtu.dk/Person/cwis?id=34435&type=person&lg=showcommon&entity=profile).

## License
Non-Commercial GPL License

Copyright © [2025] [FrostBiil]

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to use, copy, modify, and distribute the Software for non-commercial purposes only, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

    The Software may not be used, copied, modified, merged, published, distributed, or sublicensed for any commercial purpose without prior written permission from the copyright holder.

    Any modifications made to the Software must be clearly documented and include a reference to the original work and authors.

    Redistributions of the Software, in whole or in part, must include this license and attribution to the original authors.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES, OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

