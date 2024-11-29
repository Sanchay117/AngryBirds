# AngryBirds

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

This is our project for the Advanced Programming Course.

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.

## Resources Used
LibGDX Docs<br/>
Canva<br/>
JUnit4

## UML
You can see the updated UML in the file named UML_2.pdf

## Use Case Diagram
You can see the updated use case diagram in the file named UseCase2.pdf

## Demo
You can see the video showcasing the game's features in demo.mp4

## Running The Game

To run this project first pull this repo and then navigate to the project folder and then type the following command 
in your terminal.<br>
`./gradlew lwjgl3:run`<br/>
To run the tests navigate to core/src/test/java/io/github/AngryBirds/ and run this "TestRunner.java" file.

## Features
This Angry Birds-inspired game comes with the following exciting features:

1. **Three Types of Pigs**
The game includes three types of pigs, each varying in size and health points (HP):
- **Small Pig**: Low HP, easiest to eliminate.
- **Medium Pig**: Moderate HP, offering a balanced challenge.
- **Large Pig**: High HP, requiring precise shots and more damage to eliminate.
  
2. **Three Types of Birds**
Players can launch different types of birds, each with unique abilities:
- **Red Bird**: The standard bird with normal velocity and no special abilities.
- **Yellow Bird**: Gains extra velocity after launch and flies straight when clicked mid-flight.
- **Blue Bird**: Splits into three smaller birds when clicked, allowing for wider area damage.
  
3. **Three Types of Materials**
Structures are built using different materials, each with unique HP values that determine their durability:

- **Wood**: Weakest material, breaks easily with moderate force.
- **Stone**: Stronger than wood, requires more powerful hits to break.
- **Glass**: Fragile material, shatters with minimal force.
  
4. **Dynamic Slingshot Mechanics**
The slingshot is the core of the gameplay:
- Drag the bird to aim and adjust the trajectory.
- Release to launch the bird toward the target.
- The slingshot's elasticity determines the velocity and trajectory based on how far the bird is dragged.

5. **Game End Conditions**
The game ends when:
- **Victory**: All pigs are eliminated.
- **Defeat**: All available birds are used up without eliminating all pigs.
  
6. **Game Save and Load with Serialization**
- **Save Game**: Players can save their progress at any time using the settings menu. The game's state, including the positions and statuses of birds, pigs, materials, and score, is serialized into a file.
- **Load Game**: Resume from the exact state where the game was saved. The saved file is deserialized to reconstruct the game world, ensuring all elements (like remaining birds, pig health, and structure states) are restored accurately.
  
7. **Audio Features**
- **Mute/Unmute**: Players can mute or unmute the game's sound effects and background music.
- **Volume Adjustment**: Use the volume slider in the settings menu to adjust the game volume to your preference.
- **Background Music Selection**: Choose from four different background music tracks to suit your mood.



### Credits

Sanchay Singh: 2023478<br>
Abhinav Kashyap: 2023022

