# Reverse-Engineered Design (RevEngD)

[![build status](https://ada.csse.rose-hulman.edu/CSSE374-Public/RevEngD/badges/master/build.svg)](https://ada.csse.rose-hulman.edu/CSSE374-Public/RevEngD/commits/master)
[![coverage report](https://ada.csse.rose-hulman.edu/CSSE374-Public/RevEngD/badges/master/coverage.svg)](https://ada.csse.rose-hulman.edu/CSSE374-Public/RevEngD/commits/master)

This is a repository for Josh Heidecker, Josh Kuhn, and Josh Palamuttam's **Software Design - CSSE 374** term project. This application reverse engineers the design of a supplied codebase using [SOOT](https://github.com/Sable/soot), a bytecode instrumentation framework.

## UML Diagram
<http://www.plantuml.com/plantuml/svg/rHhBRjiuyAwl8B0N6NNw0ni4TPEcCH1t0xlTos8FZ4JRn5AabgICEa7_VOSFITJBKHfxarviYHpEcpF34JyeZQKkCXQfVoZFiSGPOfGJlSy94b9lHHGb32k5bcJtYM-kPdcE7YE4tg57MAffyWFj14rHXYcFLrfIlldhRuJbHasY7r54kIPoZHE2vfSY9JEEsVw1o6kSQ27t1jCx54wW3T7XUxp0-Rb3EqNk_y9WXs6BF-3g_VkBNYAb07tqQPOpaX6krIduWxM6Hq_-zWvB1HYSzlhMvGPWnbYF7XsE5T4Q9jGrPUHMuBJ2Pat0OAI4wEKh4NnDAzKvb4uGVC_85TROeZj31nTRY9KGUfKGJf0olswCw7CpVcaGNo1LFX9bfoy5OoJHLF3p9M7OFAWjpI-G3DuSgAE9m2s_akywiY8s6HqXNuJlbPPWIDIMj4VAEQWKF1LijyEK6I0tKCeRC5-3mllEmod7v6D9q--AkANU57wcw8z2vuK6qWHscoZqfBOXJbE7CFPaqDh-1I2IPA8aEmobY9urHL4WIpnIvj3dm8pUry0MXASTJPQikoqnLkGVTM192gb0aoUcKZlPDUMd8_HjSRiaX_1Nxfqpp3M0MnzlEHaCBqHArzJO9_DFtib-Xhs0h8bWXbFt6eUAGR8DUKq9IyU1BWXaYv6mdvngHi7EgnWz2lm6gzaem6yGc0O0Mv0h-2yObav2lyRi2pU0iid00gkz0Dw-e_KGEB0U05iZ8PkVuV7XOOhgKL2U7Pj4VNYCPCV1rD7zU7JLXhZpRluPyeJ4a5PFjF4QQTfjj5kgz7cDvyBZDiylsajriAwN1Ai3CkqYo4LO9-sz48nWxcEtrL-jtqaFY7MVsf7wG9n71iuP6gAuPpJn2IEiVFpGAt4bXOQWHzAtnnZTOKtU7bjaJRyVvihNOcLPQLVOAknTOtdS1pLv5QbMUwwtHByzzsoaXiUgzdNHJc-fiazrFLdEJKiQ5sYBLLb63e4K4B6hqhfPHbUQU6WHTr7q8MhKV5zBZk8UBftizSGcX9_00Ufz59ur_Yq8JyWLnHiu5GxMDA2JWOhc3CsTEfT42LOOBSxOHaYgjndA98vU4lE3c3fCnBbCgL07PyuR0BaZSN1-cQ9ld6h8QPbssQpCx6rt03315yjPhOZDHf8Dn0aOhCaDymGvY6tSLYnnnMDMJ8ukojneK47e7H87FLEU4E4hoNIvnPG_8Oyj2fulqAXbnvVe1kf-B9FjtWWGHiN_q-ilCMEV9Swthyc4gKaPqO9V4LCmdB2EpF8vtnAG0vluTu3QCSXTpU_-fC8bcLFJcgKu1vS-kKWa4RRIFn6XPXwq7Rai5nnSqgMovd6kIhC8KXtcAGMqn24f9o13LoXcwivqGOFycPIBmeDEKi8tCPb4Ng7wIHmR8T9Bu5xBmYXlD9OfcjzX3VRbA7V_JRnYHwIaALGQxgJ-OiJMTWQgqAPjkIfo8h_kSwBYDGLBXgZSET4D1ssEeCx18GBNm0joV4c_6LRyqj03b6-U8eup4fpLWCV0BZN8jCsb3-7LKTXJ_WAmSLY6UuR2Q1WoQUkuHS4qpOAMMxFnMIcuAlry6LMJV7rnQoC4FQdI3dNNXPZUtS9KqMuCqi36MBkDtvBkWNkICTyoUcBfKrs99iwwlN5Kj3VukFXCF-Ue25q0A8tvR5FCqfGQByQiZ2JTJbi8_mLE-1sOturpLItwcNy8VIpOMctN-mtrf8f1bnpsmV4kTnmFUw53lTH_dhM--epG_F3IetYmvM7_QgkvMzDLrgqgXHw7U3owXP-jsA4gyQVuFwaVjek-Nqx3lPNnBoT5LGl_AfntN4_YSL7-25cgLfkf0fVki6YrSbPVDsZrq3wMFJH_jkmqyr06nCBpfcVG7DQVYtP9T26vOMwOWFtkFnRjCT7uhqi7i9Gd0NUs_YZIlJ5QtI4D66cNxY5EUwkXOI5pzw9rkEm2T8w5FI0zMxuBrVsM-pGtLJ-tu52IrxSdWhiJllHL1-vqJ8FFkqN6bYJb10wOZKzF2MxLpOqULBdx3xPEeZF4HdMfwWzHyU5UgzjRdVio3Mr4KN_Frrwi6SH-O54eU1Grb6IdcvL4cxVomanxl7i9fhCoeDIv8JGiI8F1ixEGmQZ5gvaDlJ2OjtF-cqMRU81mSCRWZselCzY23165sSBAuxqs-W3mu9B_0G00>
![alt text](http://www.plantuml.com/plantuml/svg/rHhBRjiuyAwl8B0N6NNw0ni4TPEcCH1t0xlTos8FZ4JRn5AabgICEa7_VOSFITJBKHfxarviYHpEcpF34JyeZQKkCXQfVoZFiSGPOfGJlSy94b9lHHGb32k5bcJtYM-kPdcE7YE4tg57MAffyWFj14rHXYcFLrfIlldhRuJbHasY7r54kIPoZHE2vfSY9JEEsVw1o6kSQ27t1jCx54wW3T7XUxp0-Rb3EqNk_y9WXs6BF-3g_VkBNYAb07tqQPOpaX6krIduWxM6Hq_-zWvB1HYSzlhMvGPWnbYF7XsE5T4Q9jGrPUHMuBJ2Pat0OAI4wEKh4NnDAzKvb4uGVC_85TROeZj31nTRY9KGUfKGJf0olswCw7CpVcaGNo1LFX9bfoy5OoJHLF3p9M7OFAWjpI-G3DuSgAE9m2s_akywiY8s6HqXNuJlbPPWIDIMj4VAEQWKF1LijyEK6I0tKCeRC5-3mllEmod7v6D9q--AkANU57wcw8z2vuK6qWHscoZqfBOXJbE7CFPaqDh-1I2IPA8aEmobY9urHL4WIpnIvj3dm8pUry0MXASTJPQikoqnLkGVTM192gb0aoUcKZlPDUMd8_HjSRiaX_1Nxfqpp3M0MnzlEHaCBqHArzJO9_DFtib-Xhs0h8bWXbFt6eUAGR8DUKq9IyU1BWXaYv6mdvngHi7EgnWz2lm6gzaem6yGc0O0Mv0h-2yObav2lyRi2pU0iid00gkz0Dw-e_KGEB0U05iZ8PkVuV7XOOhgKL2U7Pj4VNYCPCV1rD7zU7JLXhZpRluPyeJ4a5PFjF4QQTfjj5kgz7cDvyBZDiylsajriAwN1Ai3CkqYo4LO9-sz48nWxcEtrL-jtqaFY7MVsf7wG9n71iuP6gAuPpJn2IEiVFpGAt4bXOQWHzAtnnZTOKtU7bjaJRyVvihNOcLPQLVOAknTOtdS1pLv5QbMUwwtHByzzsoaXiUgzdNHJc-fiazrFLdEJKiQ5sYBLLb63e4K4B6hqhfPHbUQU6WHTr7q8MhKV5zBZk8UBftizSGcX9_00Ufz59ur_Yq8JyWLnHiu5GxMDA2JWOhc3CsTEfT42LOOBSxOHaYgjndA98vU4lE3c3fCnBbCgL07PyuR0BaZSN1-cQ9ld6h8QPbssQpCx6rt03315yjPhOZDHf8Dn0aOhCaDymGvY6tSLYnnnMDMJ8ukojneK47e7H87FLEU4E4hoNIvnPG_8Oyj2fulqAXbnvVe1kf-B9FjtWWGHiN_q-ilCMEV9Swthyc4gKaPqO9V4LCmdB2EpF8vtnAG0vluTu3QCSXTpU_-fC8bcLFJcgKu1vS-kKWa4RRIFn6XPXwq7Rai5nnSqgMovd6kIhC8KXtcAGMqn24f9o13LoXcwivqGOFycPIBmeDEKi8tCPb4Ng7wIHmR8T9Bu5xBmYXlD9OfcjzX3VRbA7V_JRnYHwIaALGQxgJ-OiJMTWQgqAPjkIfo8h_kSwBYDGLBXgZSET4D1ssEeCx18GBNm0joV4c_6LRyqj03b6-U8eup4fpLWCV0BZN8jCsb3-7LKTXJ_WAmSLY6UuR2Q1WoQUkuHS4qpOAMMxFnMIcuAlry6LMJV7rnQoC4FQdI3dNNXPZUtS9KqMuCqi36MBkDtvBkWNkICTyoUcBfKrs99iwwlN5Kj3VukFXCF-Ue25q0A8tvR5FCqfGQByQiZ2JTJbi8_mLE-1sOturpLItwcNy8VIpOMctN-mtrf8f1bnpsmV4kTnmFUw53lTH_dhM--epG_F3IetYmvM7_QgkvMzDLrgqgXHw7U3owXP-jsA4gyQVuFwaVjek-Nqx3lPNnBoT5LGl_AfntN4_YSL7-25cgLfkf0fVki6YrSbPVDsZrq3wMFJH_jkmqyr06nCBpfcVG7DQVYtP9T26vOMwOWFtkFnRjCT7uhqi7i9Gd0NUs_YZIlJ5QtI4D66cNxY5EUwkXOI5pzw9rkEm2T8w5FI0zMxuBrVsM-pGtLJ-tu52IrxSdWhiJllHL1-vqJ8FFkqN6bYJb10wOZKzF2MxLpOqULBdx3xPEeZF4HdMfwWzHyU5UgzjRdVio3Mr4KN_Frrwi6SH-O54eU1Grb6IdcvL4cxVomanxl7i9fhCoeDIv8JGiI8F1ixEGmQZ5gvaDlJ2OjtF-cqMRU81mSCRWZselCzY23165sSBAuxqs-W3mu9B_0G00)
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
./gradlew run -Pmyargs="--setting <path to settings file>"
```

In a properties file that you create you can enter in the flags you want to add to the UML diagram you want to create
- `path=<absolute path>` **REQUIRED** This is the path to analyze Java bytecode. The path given must be an absolute path. If the path has spaces add a comma at the end of the path.
- You can have multiple paths, if the paths don't have spaces you can delimit them with either spaces or commas, if they do have spaces you need to delimit them wih commas
- `class=<main class> <other classes>` **REQUIRED** This is a list of the fully-qualified class names to analyze. The first class specified must have a main method. After the main class, any number of fully-qualified class names can be listed.
  **Note** If the codebase you are analyzing does not have a class with a main method, you must create a "dummy" class that has a main method inside the codebase.
- `r=<true or false>` This is a recursive flag. This tells the application to recursively load all the supertypes of the classes specified by the `--class` flag.
- `accesslevel=<level>` This flag allows the UML to only render to a certain access level. `<level>` can be either `private`, `protected`, or `public`. 
    - `private` will render all classes, methods, and fields. 
    - `protected` will render only public and protected classes, methods, and fields. 
    - `public` will render only pubilc classes, methods, and fields.
- `method_bodies=<true or false>` This flag will analyze method bodies when looking for dependencies in UML diagrams
- `method=<method name>` specifies a fully qualified method name to generate a sequence diagram for
- `include=` specifies package prefixes to include despite being in the exclude, you can add multiple classes by adding a space in between the classes
- `exclude=` specifies package prefixes to exclude, you can add multiple classes by adding a space in between the classes
- `mra=<algorithm>` method resolution algorithm to use for sequence diagrams `<algorithm>` can be either `hierarchy`, or `callgraph`
    - `hierarchy` uses class hierarchy to resolve method calls
    - `callgraph` uses context sensitive call graph to resolve method calls
- `aggregate=<algorithm>` specifies the aggregate method resolution alogithm used. The aggregate algorithm uses the method resolution algoritms specifed with the `--mra` flag  `<algorithm>` can be either `union`,`intersection`, `chain`
    - `union` takes the union of the results from all MRAs specified
    - `intersection` takes the intersection of the results from all MRAs specified
    - `chain` uses the results from the first MRA that finds any methods in the list of MRAs given
- `synthetic=<true or false>` This is a flag to determine if synthetic methods should be rendered.
- `pattern=<pattern>` This flag will find a pattern in your UML code and color the classes that implement that pattern `<pattern>` can either be `singleton`, `inheritance`, `dipviolation`, `adapter`, `decorator`
    - `singleton` - Finds a class that implements the singleton pattern, and outlines that class with blue
    - `inheritacne` - Finds any class that violates the composition over inheritance principle and changes the background color of that class, and any offending arrow to orange
    - `dipviolation` - Finds any class that violates the dependency inversion principle and changes the background color of that class, and any offending arrow to the color, peru, and the method that specifically violates the dependency inversion principle to red
    - `adapter` - Finds any classes that implement the adapter pattern, and specifically finds the adapter, adaptee, target, and adapts arrow, and puts the corresponding name as a stereotype on top of that specific class or arrow. The pattern also colors each of these classes as Fuschia.
    - `decorator` - Finds any classes that implement the decorator pattern, and specifically finds the component, the decorators, and  the decorating has arrow. This class also puts the corresponding name as a stereotype of that class or arrow. The pattern also colors these classes as Chartreuse. Not only will this method find any decorator, but it will find classes that implement a decorator pattern poorly, and will add the classes that the decorator needs to actually decorate in red.
- `adapter_override_ratio=<ratio>` For the adapter pattern we ask the user to specify what they consider an adapter, and give them the option of providing the desired the ratio of the target methods that are overwritten by the adpater, if the user does not provide this it is defaulted 0.5.
- `adapter_adaptee_ratio=<ratio>` For the adapter pattern we ask the user to specify what they consider an adapter, and give them the option of providing the desired the ratio of the number of adapters methods that use the adaptee, if the user does not provide this it is defaulted 0.5.

The following is a very basic example of how to run from the command line:
```bash
./gradlew run -Pmyargs="--path /absolute/path/to/codebase --class mainpackage.MainClass"
```
The following is an example of how to run from the commandline with a property file:
```bash
./gradlew run -Pmyargs="--settings default.properties"
```


**Note** how the entire list of arguments is surrounded in double quotes.

## Team Member Contributions
### Milestone 1
The team met 4 times each week to work on this milestone. At each meeting, every team member was present and contributed to the progression of the project. All work done on the project was completed at our meetings as a group. Whenever we were coding, we rotated drivers after each major feature was completed.

### Milestone 2
The team met 4 times each week to work on this milestone. At each meeting, every team member was present and contributed to the progression of the project. All work done on the project was completed at our meetings as a group. Whenever we were coding, we rotated drivers after each major feature was completed.

### Milestone 3
The team met 4 times each week to work on this milestone. At each meeting, every team member was present and contributed to the progression of the project. All work done on the project was completed at our meetings as a group. Whenever we were coding, we rotated drivers after each major feature was completed.

### Milestone 4
The team met 4 times in the first week, and 6 times the next week. At each meetin, every team member was present and contributed to the progression of the project. All work done on the project was completed at our meetins as a group. Whenever we were coding, we rotated drivers after each major feature was completed. 

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

### Milestone 2
#### Pizza UML Demo
```bash
gradlew run --offline -Pmyargs="--class headfirst.factory.pizzaaf.PizzaTestDrive headfirst.factory.pizzaaf.BlackOlives headfirst.factory.pizzaaf.FreshClams headfirst.factory.pizzaaf.ParmesanCheese headfirst.factory.pizzaaf.ReggianoCheese headfirst.factory.pizzaaf.Cheese headfirst.factory.pizzaaf.FrozenClams headfirst.factory.pizzaaf.Pepperoni headfirst.factory.pizzaaf.Sauce headfirst.factory.pizzaaf.CheesePizza headfirst.factory.pizzaaf.Garlic headfirst.factory.pizzaaf.PepperoniPizza headfirst.factory.pizzaaf.SlicedPepperoni headfirst.factory.pizzaaf.ChicagoPizzaIngredientFactory headfirst.factory.pizzaaf.MarinaraSauce headfirst.factory.pizzaaf.PizzaIngredientFactory headfirst.factory.pizzaaf.Spinach headfirst.factory.pizzaaf.ChicagoPizzaStore headfirst.factory.pizzaaf.MozzarellaCheese headfirst.factory.pizzaaf.Pizza headfirst.factory.pizzaaf.ThickCrustDough headfirst.factory.pizzaaf.ClamPizza headfirst.factory.pizzaaf.Mushroom headfirst.factory.pizzaaf.PizzaStore headfirst.factory.pizzaaf.ThinCrustDough headfirst.factory.pizzaaf.Clams headfirst.factory.pizzaaf.NYPizzaIngredientFactory headfirst.factory.pizzaaf.VeggiePizza headfirst.factory.pizzaaf.Dough headfirst.factory.pizzaaf.NYPizzaStore headfirst.factory.pizzaaf.PlumTomatoSauce headfirst.factory.pizzaaf.Veggies headfirst.factory.pizzaaf.Eggplant headfirst.factory.pizzaaf.Onion headfirst.factory.pizzaaf.RedPepper --path C:/EclipseWorkspaces/csse374/Lab4-1/build/classes/main"
```

#### Lab 2-1 Demo
```bash
gradlew run --offline -Pmyargs="--class problem.AppLauncher problem.DirectoryChangeLogger problem.ExecutableFileRunner problem.AppplicationLauncher problem.DirectoryEvent problem.IDirectoryListener problem.DataFileRunner problem.DirectoryMinitorService problem.ProcessRunner --path C:/EclipseWorkspaces/csse374/Lab2-1/build/classes/main"
```

#### Weather Station Demo
```bash
gradlew run --offline -Pmyargs="--class headfirst.designpatterns.observer.weather.WeatherStation --path C:/EclipseWorkspaces/csse374/Lab2-1/build/classes/main --sequence --method <headfirst.designpatterns.observer.weather.WeatherStation: void main(java.lang.String[])> --depth 5 -JDK"
```

#### This Project Demo
```bash
gradlew run --offline -Pmyargs="--class csse374.revengd.application.RevEngDApp csse374.revengd.application.AnalyzableData csse374.revengd.application.IFilter csse374.revengd.application.ProtectedFilter csse374.revengd.application.Relationship csse374.revengd.application.SootLoader csse374.revengd.application.Analyzable csse374.revengd.application.JDKFilter csse374.revengd.application.PublicFilter csse374.revengd.application.ResolvedMethodFinder csse374.revengd.application.UMLRender csse374.revengd.application.CLParser csse374.revengd.application.PlantUMLGenerator csse374.revengd.application.RecursiveLoader csse374.revengd.application.CodeAnalyzer csse374.revengd.application.PrivateFilter csse374.revengd.application.RelationshipFinder csse374.revengd.application.SequenceDiagramRender --path C:/EclipseWorkspaces/csse374-project/RevEngD/build/classes/main --analyzeBodies"
```

#### Pizza Sequence Demo
```bash
gradlew run --offline -Pmyargs="--class headfirst.factory.pizzaaf.PizzaTestDrive --path C:/EclipseWorkspaces/csse374/Lab4-1/build/classes/main --sequence --method <headfirst.factory.pizzaaf.PizzaStore: headfirst.factory.pizzaaf.Pizza orderPizza(java.lang.String)> -JDK"
```
### Milestone 3
#### JFrame Demo
```bash
gradlew run -Pmyargs="--settings demos/jframe.properties”
```

#### Term Project Demo 
```bash
gradlew run -Pmyargs="--settings demos/term.properties”
```

#### Singleton Demo
```bash
gradlew run -Pmyargs="--settings demos/singleton.properties”
```

#### Bidirectional Arrow Demo
```bash
gradlew run -Pmyargs="--settings ../PatternDetectorExample/bidirectional.properties"
```

#### Demo One
```bash
gradlew run -Pmyargs="--settings demos/weather1.properties”
```

#### Demo Two
##### Union 
```bash
gradlew run -Pmyargs="--settings demos/weather2u.properties”
```
##### Intersection 
```bash
gradlew run -Pmyargs="--settings demos/weather2i.properties”
```
##### Chain
```bash
gradlew run -Pmyargs="--settings demos/weather2c.properties”
```

#### Demo Two Pattern Detector
##### Union 
```bash
gradlew run -Pmyargs="--settings ../PatternDetectorExample/weather2u.properties”
```
##### Intersection 
```bash
gradlew run -Pmyargs="--settings ../PatternDetectorExample/weather2i.properties”
```
##### Chain
```bash
gradlew run -Pmyargs="--settings ../PatternDetectorExample/weather2c.properties”
```

### Milestone 4
##### ReflectivePizzaFactory demo
```bash
gradlew run --offline -Pmyargs="--settings demos/m4/rpizza.properties"
```
##### Decorator Lab demo
```bash
gradlew run --offline -Pmyargs="--settings demos/m4/decoratorLab.properties"
```
##### Adapter Lab demo
```bash
gradlew run --offline -Pmyargs="--settings demos/m4/adapterLab.properties"
```
##### InputStreamReader OutputStreamWriter MouseAdapter demo
```bash
gradlew run --offline -Pmyargs="--settings demos/m4/mouseAdapter.properties"
```
##### DecoratorMistake2 demo
```bash
gradlew run --offline -Pmyargs="--settings demos/m4/decoratorMistake.properties"
```
##### Exam 2 demo
```bash
gradlew run --offline -Pmyargs="--settings demos/m4/exam2decorator.properties"
```