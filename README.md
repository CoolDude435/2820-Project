# CS2820 Spring 2023 WARP Project Code
This code base will be used for the University of Iowa CS 2820 Introduction to Software
Development course. The code was developed by Steve Goddard for the WARP sensor network 
research project. It was first written in Swift and rewritten in Java. It was then 
rewritten again in an object-oriented programming style. It was a quick
hack, and it needs a lot of cleanup and refactoring. A perfect code base to teach
the value of software developement fundamentals!
<br>
**HW0**
<br>
Configured Eclipse to use the WARP code and changed argument in run configuration to access
 OutputFiles folder.
<br>
**HW1**
<br>
Instantiated WorkLoadDescription and used collections sort to alphabetically list the flows.
 Runs with file StressTest.txt. 
<br>
**HW2**
<br>
Generated JavaDoc for assigned WARP Classes. 
<br>
Annalisa Karacay completed Warp.java, ProgramVisualization.java, and VisualizationImplementation.java.
<br>
Isaac Griswold completed Flow.java, WorkLoad.java, and Program.java.
<br>
**HW3**
<br>
Developed and executed JUnit tests for methods in WorkLoad.java
<br>
Isaac Griswold completed JUnit tests for methods a-h
<br>
Annalisa Karacay completed JUnit tests for methods i-p
<br>
Fixed minor errors in WARP code such as spelling in comments. Also mixed the formatted print 
statement in addFlow() method. The %s did not have a string argument to pair with.
<br>
**HW4**
<br>
Created UML Lab Diagrams for SchedualableObject.java, WorkLoad.java, and a series of Reliability Java files. 
<br>
Created public method and generated code in RelabilityAnalysis.java.
<br>
**HW5**
<br>
Justin Lin completed a.i.
<br>
Annalisa Karacay completed a.ii.
<br>
We worked together to go back through WorkLoad to refactor and update any places that the old methods were called:
<br>
Added an instance variable reliabilityAnalysis so numTxPerLinkAndTotalTxCost can be called
<br>
First WorkLoad constructor will create reliabilityAnalysis using the constructor with e2e and m as parameters
<br>
Second WorkLoad constructor will create reliabilityAnalysis using the constructor with only numFaults as its parameter
<br>
Updated the methods finalizeFlowWithE2eParameters and finalizeFlowWithFixedFaultTolerance to use numTxPerLinkAndTotalTxCost
<br>
instead of numTxAttemptsPerLinkAndTotalTxAttempts and getFixedTxPerLinkAndTotalTxCost
<br>
Deleted numTxAttemptsPerLinkAndTotalTxAttempts and getFixedTxPerLinkAndTotalTxCost
<br>
Updated JavaDocs
<br>
Updated UML Diagrams
<br>
**Sprint1**
<br>
Sequence Diagram: Status- Working on by Annalisa and Justin
<br>
The sequence diagram was created to show the end-to-end reliability of each flow.
<br>
It can be found in file Sequence Diagram folder. It will be updated as work is completed throughout the project.
<br>
Design Documentation: Status- Worked on by Jake and Elizabeth
<br>
The Design Documentation was created to show the who, what, when, and how we plan to complete the project.
The Project plan can be seen in ProjectPlan.doc.
<br>
README was updated by Justin and Elizabeth 
<br>
**Sprint2**
<br>



