What is it?
===================

This is a Math Engine that not only evaluates simple arithmetic expressions, but can also manipulate Algebraic Expressions. It also has a Mathematical resoning engine. 

How to use it?
===================

First you need to include the **mathengine** package in your project. Alternatively, you can also make a JAR out of this package and include that.

To use this, create an instance of the **Engine** class:

    Engine myEngine = new Engine();

#Algebraic Transformations

Algebraic transformations (aka Dynamic Formulae) can be added adHoc:

    myEngine.learnDynamicFormula("(a+b)^2", "(a^2)+(2*a*b)+(b^2)");

  

