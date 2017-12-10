# Reverse-Engineered Design (RevEngD)

[![build status](https://ada.csse.rose-hulman.edu/CSSE374-Public/RevEngD/badges/master/build.svg)](https://ada.csse.rose-hulman.edu/CSSE374-Public/RevEngD/commits/master)
[![coverage report](https://ada.csse.rose-hulman.edu/CSSE374-Public/RevEngD/badges/master/coverage.svg)](https://ada.csse.rose-hulman.edu/CSSE374-Public/RevEngD/commits/master)

This is a starter repository for **Software Design - CSSE 374** term project. This application reverse engineers the design of a supplied codebase using [SOOT](https://github.com/Sable/soot), a bytecode instrumentation framework. You will need to learn a few things about SOOT to do a good job in the project. Use these references whenever you get stuck:
1. [SOOT Survival Guide PDF](http://www.brics.dk/SootGuide/sootsurvivorsguide.pdf)
2. [Fundamental Soot Objects](https://github.com/Sable/soot/wiki/Fundamental-Soot-objects)
3. [Other Online Help](https://github.com/Sable/soot/wiki/Getting-help)

## Software Needed
1. Install the latest version of [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) in your machine and setup the system's `PATH` variable to point to the JDK's `bin` directory (append the path to the JDK's `bin` directory onto the `PATH` variable). 
2. Install [Graphviz](http://www.graphviz.org/download/) and setup the system's `PATH` variable to point to the GraphViz's `bin` directory.

## About Seed Contents
The repo, as is, contains four examples for you to try out and expand. Lets' take a quick tour of the repo contents:

1. [SceneBuilder API](/src/main/java/csse374/revengd/soot/SceneBuilder.java) - Helps with setting up SOOT for a whole program analysis.
2. [Examples / Todos](/src/main/java/csse374/revengd/examples/driver)  - There are four examples for you to **review and expand upon** in the [csse374.revengd.examples.driver](/src/main/java/csse374/revengd/examples/driver) package:
   * **SimpleDirectoryLoading** - Shows how to load Java classes from a directory in SOOT and prints the fields and methods of the classes.
   * **TypeHierarchy** - Shows how to use the type hierarchy provided by SOOT.
   * **ControlFlowGraph** - Shows how to get the statements inside a method and search for specific method calls from within the method.
   * **PointerAnalysis** - Shows how to resolve dynamic dispatch target(s) of a method call statically using pointer analysis. 
   * **PlantUMLGenerator** - Shows how to generate PNG or other format output from a sample PlantUML code programmatically. 
   * **ExamplesDriver** - Implements REPL to drive the above four examples from command prompt.
3. [Fixtures](/src/main/java/csse374/revengd/examples/fixtures) - Contains a sample calculator application for running test and examples.

Each example has some **TODOs** for you to complete. Please complete them to solidify your understanding of the concepts.

## Cloning the Repo
You can clone the repo locally using Git Bash/Shell as follows:
```bash
cd <to the your workspace folder>
git clone git@ada.csse.rose-hulman.edu:CSSE374-Public/RevEngD.git
```

## Using IDE
You can import the cloned folder as a **Gradle Project** in Eclipse or IntelliJ IDEs.

## Running the Examples
### Running From Command Line
```bash
cd RevEngD
./gradlew run
```
It should present the following prompt:
```bash
========================= Your choices ========================= 
1 - E1SimpleDirectoryLoading
2 - E2TypeHierarchy
3 - E3ControlFlowGraph
4 - E4PointerAnalysis
5 - E5PlantUMLGenerator
========================== End choices ========================= 
Please enter your choice (number) or press q to quit: 
```
Enter the number corresponding to the example that you want to run, sit back, and enjoy the ride. Make sure you review the code in the project before running the examples. 

### Running From Eclipse/IntelliJ
In your IDE, open the Gradle Tasks view and double-click on the **run** task under **application**. 

## Other Notes
The project is configured to use Log4J, which you can also use for your own classes. See [SceneBuilder API](/src/main/java/csse374/revengd/soot/SceneBuilder.java) for an example. 
