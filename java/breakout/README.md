<a id="readme-top"></a>

<h1 align="center">
  <br> 
  <a href="https://github.com/frostbiil/public/java/breakout">
    <img src="https://raw.githubusercontent.com/frostbiil/public/main/java/breakout/.github/breakout.jpg" alt="Breakout Game">
  </a> 
</h1>

# Breakout

## 📌 Project Overview
This project is a JavaFX-based Breakout game, developed as part of a three-week exam project at the Technical University of Denmark (DTU). The goal of the project was to implement object-oriented programming (OOP) principles, game physics, and JavaFX for graphical representation. The project was split into making a basic and advanced version.

## 👨‍💻 Group Members
* [FrostBiil](https://github.com/FrostBiil)
* [Bondesgaard](https://github.com/bondegaard)
* [Skytiger1](https://github.com/skytiger1)
* [Lobehjulius](https://github.com/Lobehjulius)

## 📋 Requirements
To run this program, requires a version of Java that includes JavaFX to be installed. This program was created and tested with `Zulu21.36+17-CA (build 21.0.4+7-LTS)`, which can be downloaded here: 📥 <a href="https://www.azul.com/downloads/?version=java-21-lts&package=jdk-fx#zulu">Azul.com</a>

This means that the program requires:
`Java 21 with JavaFX`


---
## 🚀 Getting started

To run the program simply run the following terminal command.

### 🎮 Basic implementation
```bash
javac -d out breakoutbasic/*.java && java -cp out breakoutbasic.Main 
```

### 🔥 Advanced Implementation
```bash
javac -d out breakoutadvance/*.java && java -cp out breakoutadvance.Main 
```

### 📦 jar file
The program can also be compiled into a .jar file and run with the following terminal command:
```bash
java -jar <jar-file> n m
```
Where n and m are used for the basic implementation to define size of the grid. n and m are both integers.

---
## 🛠️ Deppendency
The advanced version of the implementation uses <a href="https://github.com/google/gson/releases/tag/gson-parent-2.11.0">gson-2.11.0</a> to store persistent data. This version of gson is already a part of the project and does not have to be installed to work when running the implementation.

--- 

## 📜 License
This project is part of an academic exercise and is not intended for commercial use.
