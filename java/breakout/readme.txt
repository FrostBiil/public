Breakout:

Two implementations of Breakout written in Java using JavaFX.

---
Requirements:

This program requires Java with JavaFX installed. It was created and tested with Zulu21.36+17-CA (build 21.0.4+7-LTS), available here: https://www.azul.com/downloads/?version=java-21-lts&package=jdk-fx#zulu

**Required version:**
- Java 21 with JavaFX

---

Getting Started:

Run the program using the following commands when in the directory of the jar file:

java -jar <jar-file> n m

Where `n` and `m` are integers defining the grid size for the basic implementation. For the advanced version, n and m does not need to be given.

---

Dependency:

The advanced version uses gson-2.11.0 (https://github.com/google/gson/releases/tag/gson-parent-2.11.0) for persistent data storage. This dependency is included in the project and requires no additional installation.

IMPORTANT:
- This means that the code within the jar file which has the package name `com.google.gson` is not written by us. All credit goes to the creators of the Gson library which is google.