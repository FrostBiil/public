# NinjaGame
A turn-based combat game where players and AI-controlled enemies battle using ninjas with different classes and weapons.

## Requirements
* Java 21 (Ensure you have JDK 21 installed)
* Maven (For building and managing dependencies)
* IntelliJ IDEA (or another Java-compatible IDE)
* Command Line (Terminal or CMD) for running the JAR file
## How to Run the Project
### Using IntelliJ IDEA
1. Clone or download the repository.
2. Open the project in IntelliJ IDEA.
3. Ensure you have Maven installed (Maven should automatically resolve dependencies).
4. Locate and open the Main.java file.
5. Click Run ▶ to execute the game.
### Using Command Line
1. Open a terminal and navigate to the project folder.
2. Build the project with Maven:
   * mvn clean package
3. Locate the built JAR file in the target/ folder:
   * target/Ninjagame-1.0-SNAPSHOT.jar
4. java -jar target/Ninjagame-1.0-SNAPSHOT.jar

## Game Instructions
* Objective: Defeat all enemy ninjas in turn-based combat.
* Player Turn:
  * Choose an attacking ninja by typing their name.
  * Choose an enemy target to attack.
  * The ninja will attack, and damage will be applied.
* Enemy Turn:
  * The AI selects an attacker and targets a player ninja.
  * The battle continues until one side has no ninjas left.
 
## Controls
* Typing-based Input: Players must manually type the names of their ninjas to select them.
* Exit Game: Close the terminal or press Ctrl + C.
