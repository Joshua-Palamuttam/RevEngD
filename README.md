# Reverse-Engineered Design (RevEngD)

[![build status](https://ada.csse.rose-hulman.edu/CSSE374-Public/RevEngD/badges/master/build.svg)](https://ada.csse.rose-hulman.edu/CSSE374-Public/RevEngD/commits/master)
[![coverage report](https://ada.csse.rose-hulman.edu/CSSE374-Public/RevEngD/badges/master/coverage.svg)](https://ada.csse.rose-hulman.edu/CSSE374-Public/RevEngD/commits/master)

This is a repository for Josh Heidecker, Josh Kuhn, and Josh Palamuttam's **Software Design - CSSE 374** term project. This application reverse engineers the design of a supplied codebase using [SOOT](https://github.com/Sable/soot), a bytecode instrumentation framework.
## Software Needed
1. Install the latest version of [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) in your machine and setup the system's `PATH` variable to point to the JDK's `bin` directory (append the path to the JDK's `bin` directory onto the `PATH` variable). 
2. Install [Graphviz](http://www.graphviz.org/download/) and setup the system's `PATH` variable to point to the GraphViz's `bin` directory.

## Cloning the Repo
You can clone the repo locally using Git Bash/Shell as follows:
```bash
cd <to the your workspace folder>
git clone git@ada.csse.rose-hulman.edu:CSSE374-Public/RevEngD.git
```

## Using IDE
You can import the cloned folder as a **Gradle Project** in Eclipse or IntelliJ IDEs.

## How to run the program
From the command line 
```bash
./gradlew run -Pmyargs="<args>"
```
Where `<args>` can be any of the following:
- `--path <absolute path>` **REQUIRED** This is the path to analyze Java bytecode. The path given must be an absolute path.
- `--class <main class> <other classes>` **REQUIRED** This is a list of the fully-qualified class names to analyze. The first class specified must have a main method. After the main class, any number of fully-qualified class names can be listed.
  **Note** If the codebase you are analyzing does not have a class with a main method, you must create a "dummy" class that has a main method inside the codebase.
- `-r` This is a recursive flag. This tells the application to recursively load all the supertypes of the classes specified by the `--class` flag.
- `--accesslevel <level>` This flag allows the UML to only render to a certain access level. `<level>` can be either `private`, `protected`, or `public`. 
    - `private` will render all classes, methods, and fields. 
    - `protected` will render only public and protected classes, methods, and fields. 
    - `public` will render only pubilc classes, methods, and fields.

The following is a very basic example of how to run from the command line:
```bash
./gradlew run -Pmyargs="--path /absolute/path/to/codebase --class mainpackage.MainClass"
```
**Note** how the entire list of arguments is surrounded in double quotes.

## Team Member Contributions
### Milestone 1
The team met 4 times each week to work on this milestone. At each meeting, every team member was present and contributed to the progression of the project. All work done on the project was completed at our meetings as a group. Whenever we were coding, we rotated drivers after each major feature was completed.

## Running the Demos
### Milestone 1
#### java.lang.String Demo
```bash
gradlew run --offline -Pmyargs="--path C:/EclipseWorkspaces/csse374/RevEngD/build/classes/main --class csse374.revengd.examples.fixtures.CalculatorApp java.lang.String -r"
```

#### Lab1-1 Strategy Demo
```bash
gradlew run -Pmyargs="--path C:\EclipseWorkspaces\csse374\Lab1-1\build\classes\main --class problem.DataStandardizerApp problem.AmazonLineParser problem.DataStandardizer problem.GoogleLineParser problem.GrouponLineParser problem.ILineParser problem.MicrosoftLineParser"
```

#### javax.swing.JComponent Demo
```bash
gradlew run -Pmyargs="--path C:/EclipseWorkspaces/csse374/RevEngD/build/classes/main --class csse374.revengd.examples.fixtures.CalculatorApp javax.swing.JComponent -r"
```

#### This Project's Public API Demo
```bash
gradlew run -Pmyargs="--path C:/EclipseWorkspaces/csse374-project/RevEngD/build/classes/main --class csse374.revengd.application.RevEngDApp csse374.revengd.application.Analyzable csse374.revengd.application.PlantUMLGenerator csse374.revengd.application.Relationship csse374.revengd.application.AnalyzableData csse374.revengd.application.PrivateFilter csse374.revengd.application.RelationshipFinder csse374.revengd.application.CLParser csse374.revengd.application.ProtectedFilter csse374.revengd.application.CodeAnalyzer csse374.revengd.application.PublicFilter csse374.revengd.application.SootLoader csse374.revengd.application.IFilter csse374.revengd.application.RecursiveLoader csse374.revengd.application.UMLRender --accesslevel public"
```