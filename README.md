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
UML Sequence/Class Diagram: Status - UML will be updated as progress is made - Updated by Annalisa
<br>
ReliabilityVisualization Class and Methods: Status - Worked on by Justin and Annalisa
<br>
ReliabilityVisualization(WarpInterface): Status - Complete
<br>
displayVisualization(): Status - Complete
<br>
createHeader(): Status - Complete
<br>
createColumnHeader(): Status - Complete
<br>
createVisualizationData(): Status - Incomplete, waiting on ReliabilityAnalysis implementation in Sprint3
<br>
createTitle(): Status - Complete
<br>
JUnit Tests for ReliabilityVisualization:
<br>
Status - Worked on by Annalisa and Jake
<br>
JUnit Tests for ReliabilityAnalysis: Status - Working on by Justin; waiting on implementation
<br>
JavaDoc Comments: Status - Worked on by Jake and Elizabeth
<br>
Design Documentation: Status - Worked on by Elizabeth
<br>
README: Status - Updated by Elizabeth and Justin
<br>
<br>
**Sprint3**
<br>
The focus of Sprint 3 was to finish creating the methods in ReliabilityAnalysis, creating JUnit tests for
<br>
ReliabilityAnalysis, as well as Javadoc for the method and all JUnit tests. Also, completing
<br>
the Sequence Diagram for the ra config and UML Diagrams for ReliabilityAnalysis and ReliabilityVisualization
<br>

___
__Code Methods:__
<br>
<br>
*ReliabilityVisualization*: Status - Completed by Justin in Sprint2
<br>
-creates the data and information needed to display a GUI visualization of the reliabilities
<br>
<br>
*ReliabilityVisualization(): Status-Complete
<br>
*displayVisualization(): Status-Complete
<br>
*createHeader(): Status-Complete
<br>
*createColumnHeader(): Status-Complete
<br>
*createVisualizationData(): Status-Complete
<br>
*createTitle(): Status-Complete
<br>

___
*ReliabilityAnalysis*: Status - The base was created in Sprint 2, Completed by Justin in Sprint3
<br>
<br>
*ReliabilityAnalysis(): Status-Complete
<br>
*getFinalReliabilityRow(): Status-Complete
<br>
*verifyReliabilities(): Status-Complete
<br>
*setReliabilityHeaderRow(): Status-Complete
<br>
*getReliabilityHeaderRow(): Status-Complete
<br>
*getReliabilities(): Status-Complete
<br>
*buildReliabilities(): Status-Complete
<br>
*carryForwardReliabilities(): Status-Complete
<br>
*setInitialStateForReleasedFlows(): Status-Complete
<br>
*numTxPerLinkAndTotalTxCost(): Status-Complete
<br>
___
*ReliabilityNode*: Helper class for ReliabilityAnalysis Status-Complete
<br>
-contains information about a node and is used as entries inside NodeMap
<br>
<br>
*NodeMap*: Helper class for ReliabilityAnalysis Status-Complete
<br>
-extends HashMap with String as the keys and ReliabilityNode as the entries
<br>
with no change in functionality
<br>
___
__JUnit Tests:__
<br>
-testing over the methods in ReliabilityVisualization and ReliabilityAnalysis using JUnit Tests
<br>
Status - Continuing work done in Sprint 2 as well as adding new tests as needed; 
being worked on by Annalisa and Jake
<br>
<br>
___
*ReliabilityVisualizationTest*: Status: Completed by Annalisa and Jake
<br>
<br>
*displayVisualizationTest(): Status: Completed by 
<br>
*createHeaderTest1(): Status: Completed by 
<br>
*createHeaderTEst2(): Status: Completed by 
<br>
*createColumnHeaderTest1(): Status: Completed by 
<br>
*createColumnHeaderTest2(): Status: Completed by 
<br>
*createTitleTest1(): Status: Completed by 
<br>
*createTitleTest2(): Status: Completed by 
<br>
*createTitleTest3(): Status: Completed by 
<br>
*createVisualizationDataTest1(): Status: Completed by 
<br>
*createVisualizationDataTest2(): Status: Completed by 
<br>
*createVisualizationDataTest3(): Status: Completed by 
<br>
_____
*ReliabilityAnalysisTest*: Status: Incomplete
<br>
<br>
*buildReliabilitiesTest(): Status: Incomplete
<br>
*setReliabilityHeaderRowTest(): Status: Incomplete
<br>
*getReliabilityHeaderRowTest(): Status: Incomplete
<br>
<br>
*verifyReliabilitiesTest1(): Status: Completed by Annalisa
<br>
*verifyReliabilitiesTest2(): Status: Completed by Annalisa
<br>
*verifyReliabilitiesTest3(): Status: Completed by Annalisa
<br>
*getReliabilitiesTest(): Status: Completed by Annalisa
<br>
*getReliabilitiesStressTest(): Status: Completed by Annalisa
<br>
*carryForwardReliabiltiesTest(): Status: Completed by Annalisa
<br>
*carryForwardReliabilitiesStressTest(): Status: Working on by Annalisa
<br>
___
__Javadoc Comments:__
<br>
Status - Will be updated as progress is made.
<br>
The Javadocs for the JUnit test was completed by Annalisa
<br>
The Javadocs for the ReliabilityAnalysis was completed by Elizabeth and Justin
<br>
<br>
__UML Diagram:__
<br>
Status - Will be updated at the very end when all classes and methods are complete
<br>
<br>
__UML Sequence Diagram:__
<br>
<br>
__README:__
<br>
Status - Continuously updated; being updated by Elizabeth and Justin
<br>
