What is it?
===================

This is a Math Engine that not only evaluates simple arithmetic expressions, but can also manipulate Algebraic Expressions. It also has a Mathematical resoning engine. 

How to use it?
===================

First you need to include the **mathengine** package in your project. Alternatively, you can also make a JAR out of this package and include that.

To use this, create an instance of the **Engine** class:

    Engine myEngine = new Engine();

##Algebraic Transformations

The engine can learn Algebraic transformations (aka Dynamic Formulae), such as the formula:

    (a+b)^2 = (a^2)+(2*a*b)+(b^2)

They can be added adHoc:

    myEngine.learnDynamicFormula("(a+b)^2", "(a^2)+(2*a*b)+(b^2)");

or as part of a file of dynamic formulae:

    myEngine.loadDynamicFormulaFile("basicAlgebra.txt");

When adding from a file, each formula needs to be placed on a separate line. Each formula should mention the two states with the => delimieter in between. For example:

    (a+b)^2 => (a^2)+(2*a*b)+(b^2)
    a*(b+c) => a*b + a*c
    a*0 => 0
    a*1 => a
    a - a => 0

##Mathematical Facts (Reasoning)

The engine also supports reasoning, given a set of mathematical "facts" and then a question. Facts can mean:

- A "Static" Formula, eg: Speed = Distance / Time
- A given value of a variable, eg: Distance = 10

For example, we can teach the engine the following Static Formulae:

- DistanceTravelled = FinalPosition - InitialPosition
- TimeTaken = EndTime = StartTime
- Speed = DistanceTravelled / TimeTaken

Then present the following test case:
- InitialPosition = 5
- FinalPosition = 15
- StartTime = 0
- EndTime = 5

Then tell it to find the value of Speed (which it will output as 2).

The above steps can be written in code as:

    //Create Engine
    Engine myEngine = new Engine();

    //Teach Formulae
    myEngine.learnFormulaStatic("Speed = DistanceTravelled / TimeTaken");
    myEngine.learnFormulaStatic("DistanceTravelled = FinalPosition - InitialPosition");
    myEngine.learnFormulaStatic("TimeTaken = EndTime = StartTime");

    //Prepare Given Values as key value pairs
    HashMap<String, String> GivenValues = new HashMap<String, String>();
    GivenValues.put("InitialPosition", "5");
    GivenValues.put("FinalPosition", "15");
    GivenValues.put("StartTime", "0");
    GivenValues.put("EndTime", "5");

    //Prepare list of Variables to be found
    ArrayList<String> ToFind = new ArrayList<String>();
    ToFind.add("Speed");

    //Query the Engine with given parameters
    ArrayList<String> ans = myEngine.KnowledgeQuery(GivenValues, ToFind);



