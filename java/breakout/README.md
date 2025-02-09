<a id="readme-top"></a>

<h1 align="center">
  <br>
  <a href="https://github.com/bondegaard/02104-Breakout/"><img src="https://raw.githubusercontent.com/bondegaard/02104-Breakout/main/.github/breakout.jpg" alt="MineClub Logo"></a>
</h1>

<h2 align="center">Breakout</h2>

<h4 align="center">Two implementations of breakout written in java using JavaFX</h4>

---
## Requirements
To run this program, requires a version of Java that includes JavaFX to be installed. This program was created and tested with `Zulu21.36+17-CA (build 21.0.4+7-LTS)`, which can be downloaded here: <a href="https://www.azul.com/downloads/?version=java-21-lts&package=jdk-fx#zulu">Azul.com</a>

This means that the program requires:
`Java 21 with JavaFX`


---
## Getting started

To run the program simply run the following terminal command.

### Basic implementation
```bash
javac -d out breakoutbasic/*.java && java -cp out breakoutbasic.Main 
```

### Advanced Implementation
```bash
javac -d out breakoutadvance/*.java && java -cp out breakoutadvance.Main 
```

### jar file
The program can also be compiled into a .jar file and run with the following terminal command:
```bash
java -jar <jar-file> n m
```
Where n and m are used for the basic implementation to define size of the grid. n and m are both integers.

---
## Deppendency
The advanced version of the implementation uses <a href="https://github.com/google/gson/releases/tag/gson-parent-2.11.0">gson-2.11.0</a> to store persistent data. This version of gson is already a part of the project and does not have to be installed to work when running the implementation.
