# Connect Four Game
This is a **Connect Four** game implemented in Java for BMI1303 class 2024, using Swing for the GUI and SQLite for saving game data. The game allows a user to play Connect Four against a computer opponent and provides the ability to save and load settings and game progress.
## Features

### 1. **Player vs Computer**
- The user always starts the game as **Yellow**, and the computer plays as **Red**.
- The computer makes random moves, providing a simple AI for the game.

### 2. **Settings Panel**
- The game includes a settings panel where the player can customize the following:
    - **Board Size**: The number of rows (between 4 and 12) and columns (between 4 and 12) can be set.
    - **Player Name**: The user can input their player name. The name is sanitized to ensure no special characters or spaces, and it is converted to lowercase with a maximum length of 12 characters.

### 3. **Game State Persistence**
- The game state (including board configuration and player name) is saved using an **SQLite** database. This ensures that the player can resume the game after restarting the application.
- The game saves the following (when save clicked):
    - **Board configuration**: The current state of the game board, including the positions of all pieces.
    - **Player name**: The name of the player is stored and can be customized in the settings.
    - **Game settings**: Number of rows and columns are saved, and the board is regenerated accordingly when the game is loaded.
- The game loads the settings automatically upon start.

### 4. **User-Friendly Interface**
- The GUI is implemented using **Swing**, providing an intuitive and responsive game interface.
- Players can reset the game, customize the settings, and start new games through an easy-to-use menu system.

### 5. **Executable JAR with Dependencies**
- The game can be packaged as an executable JAR with all dependencies bundled (fat JAR), making it easy to run the game without setting up external libraries.

## Requirements

- **Java 8 or higher**
- **Maven** (for building the project)
- **SQLite JDBC** (already included in the project dependencies)

## Getting Started

### 1. Clone the repository
```bash
git clone https://github.com/ladikszabolcs/java-connect-four.git
cd connect-four
```

### 2. Build the Project
- Ensure you have Maven installed, then build the project, this will generate an executable JAR file in the target/ directory.

```bash
mvn clean package
```

### 3. Run the Game
- To run the game, use the following command:

```bash
java -jar target/connect-four-1.0-SNAPSHOT-jar-with-dependencies.jar
```
