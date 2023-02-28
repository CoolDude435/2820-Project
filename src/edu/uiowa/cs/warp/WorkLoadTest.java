package edu.uiowa.cs.warp;

import static org.junit.jupiter.api.Assertions.*;

import edu.uiowa.cs.utilities.Utilities;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

//test
//test2
class WorkLoadTest {
	
	private static WorkLoad load1;
	private static WorkLoad load2;
	private static WorkLoad load3;
	
	//Partner Portion

	// addFlow() Test
	void testAddFlowHelper(String[] expected, WorkLoad load) {
		assertArrayEquals(expected, load.getFlowNames());
	}
	
	@Test
	void testAddFlow() {
		load1.addFlow("newFlow");
		String[] expected = {"F1", "F5", "F2", "F4", "F3","F6", "F7", "F8", "F9", "F10", "AF1", "AF5", "AF2", "AF4", "AF10", "newFlow"};
		testAddFlowHelper(expected, load1);		
	}
	
	@Test
	void duplicateNames() {
		load2.addFlow("dupName");
		load2.addFlow("dupName");
		String[] expected = {"F0", "F1", "F2", "F3", "F4", "F5", "dupName"};
	    System.out.println(load2.getFlowNames());
		testAddFlowHelper(expected, load2);
	}

	// addNodeToFlow() Test
	void addNodeHelper(String[] expected, WorkLoad load, String flowName) {
		assertArrayEquals(expected, load.getNodesInFlow(flowName));
	}
	
	@Test
	void testAddNodeToFlow() {
		load3.addNodeToFlow("F0", "D");
		String[] expected = {"A", "B", "C", "D"};
		addNodeHelper(expected, load3, "F0");
	}
	
	@Test
	void testAddNodeToFlow2() {
		load2.addNodeToFlow("F0", "A");
		load2.addNodeToFlow("F0", "A");
		String[] expected = {"A", "B", "C", "A", "A"};
		addNodeHelper(expected, load2, "F0");
	}
	
	// getTotalTxAttemptsInFlow() Test
	void getTotalTxHelper(Integer expected, WorkLoad load, String flowName) {
		assertEquals(expected, load.getTotalTxAttemptsInFlow(flowName));
	}
	
	@Test
	void testGetTotalTx() {
		Integer expected = 4;
		getTotalTxHelper(expected, load2, "F0");
	}
	
	@Test
	void testGetTotalTx2() {
		Integer expected = 5;
		load2.addNodeToFlow("F0", "D");
		getTotalTxHelper(expected, load2, "F0");
	}
	
	// getFlowPriority() and setPriority Tests
	void testGetFlowPriorityHelper(Integer expected, WorkLoad load, String flowName) {
		assertEquals(expected, load.getFlowPriority(flowName));
	}
	
	@Test
	void testGetFlowPriority() {
		Integer expected = 8;
		testGetFlowPriorityHelper(expected, load1, "F8");
	}
	
	@Test
	void testGetFlowPriority2() {
		Integer expected = 3;
		testGetFlowPriorityHelper(expected, load2, "F3");
	}
	
	@Test
	void testSetFlowPriority() {
		Integer expected = 5;
		load1.setFlowPriority("F8", 5);
		testGetFlowPriorityHelper(expected, load1, "F8");
	}
	
	@Test
	void testSetFlowPriorityNegative() {
		Integer expected = -1;
		load1.setFlowPriority("F8", -1);
		testGetFlowPriorityHelper(expected, load1, "F8");
	}
	
		
	//Annalisa's Portion (JUnit tests for methods i-p)
	
	@BeforeEach
	void initializeLoads() {
		load1 = new WorkLoad(0.9, 0.99, "StressTest.txt");
		load2 = new WorkLoad(0.9, 0.99, "Example2.txt");
		load3 = new WorkLoad(0.9, 0.99, "Example3.txt");
	}
	
	//getFlowNames() Test
	void testGetFlowNamesHelper(String[] expected, WorkLoad load) {
		//assert that the array of expected flow names equals the flow names in the WorkLoad
		assertArrayEquals(expected, load.getFlowNames());
	}
	@Test
	void testGetFlowNames1() {
		//manually enter expected flow names from the chosen files into an array
		String[] expected = {"F1", "F5", "F2", "F4", "F3","F6", "F7", "F8", "F9", "F10", "AF1", "AF5", "AF2", "AF4", "AF10"};
		testGetFlowNamesHelper(expected, load1);
		}
	
	@Test
	void testGetFlowNames2() {
		//manually enter expected flow names from the chosen files into an array
		String[] expected = {"F0", "F1", "F2", "F3", "F4", "F5"};
		testGetFlowNamesHelper(expected, load2);
	}
	
	@Test
	void testGetFlowNames3() {
		//manually enter expected flow names from the chosen files into an array
		String[] expected = {"F0", "F1", "F2", "F3", "F4", "F5"};
		testGetFlowNamesHelper(expected, load3);
	}
	
	
	//getNodeIndex() Test	
	void testGetNodeIndexHelper(String[] names,int[] expectedIndecies, WorkLoad load) {
		//iterate through both expected arrays and the WorkLoad arrays to test they are equal
		for(int i = 0; i < names.length; i++){
			assertEquals(expectedIndecies[i],load.getNodeIndex(names[i]));
		}
	}
	
	@Test
	void testGetNodeIndex1() {
		//manually enter both the flow names and indices from the chosen files and put them into separate arrays
		String[] names= new String[] {"B", "C", "D", "A", "E", "F", "G", "H", "I", "J", "K", "L", "N",
				"O", "P", "M", "Q", "R", "S", "T", "U", "V", "W", "Y"};
		int[] expectedIndecies = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
				20, 21, 22, 23};
		testGetNodeIndexHelper(names, expectedIndecies, load1);
		}
	@Test
	void testGetNodeIndex2() {
		//manually enter both the flow names and indices from the chosen files and put them into separate arrays
		String[] names= new String[] {"A", "B", "C", "D", "E", "F", "P", "Q", "R", "S", "T", "U", "G",
				"H", "I", "J", "K", "L", "V", "W", "X", "Y", "Z"};
		int[] expectedIndecies = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
				20, 21, 22};
		testGetNodeIndexHelper(names, expectedIndecies, load2);
		}
	@Test
	void testGetNodeIndex3() {
		//manually enter both the flow names and indices from the chosen files and put them into separate arrays
		String[] names= new String[] {"A", "B", "C", "D", "F", "P", "R", "G", "I", "V", "X"};
		int[] expectedIndecies = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		testGetNodeIndexHelper(names, expectedIndecies, load3);
		}

	
	//getNodesInFlow() Test
	void testGetNodesInFlowHelper(String[] flowNames, String[][]expected,WorkLoad load) {
		//Create a list of expected flow values(nodes) and WorkLoad values to iterate through and test if equal
		FlowMap flows = load.getFlows();
		Collection<Flow> flowList = flows.values();
		
		for(int i = 0; i < flowList.size(); i++) {
			assertArrayEquals(expected[i], load.getNodesInFlow(flowNames[i]));
		}
	}
	@Test
	void testGetFlowsInNode1() {
		//manually enter flow names and all expected nodes from each of the chosen WorkLoad Files
		String[] flowNames = {"F1", "F5", "F2", "F4", "F3","F6", "F7", "F8", "F9", "F10", "AF1", "AF5", "AF2", "AF4", "AF10"};
		String[][] expected = {
				{"B", "C", "D"},
				{"A", "B", "C", "D", "E"},
				{"C", "D", "E", "F", "G", "H", "I"},
				{"A", "B", "C", "D", "E", "J", "K", "L"},
				{"C", "D", "E", "J", "K", "L"},
				{"B", "C", "D"},
				{"A", "B", "C", "D", "E"},
				{"C", "D", "E", "F", "G", "H", "I"},
				{"A", "B", "C", "D", "E", "J", "K", "L"},
				{"C", "D", "E", "J", "K", "L"},
				{"N", "O", "P"},
				{"M", "N", "O", "P", "Q"},
				{"O", "P", "Q", "R", "S", "T", "U"},
				{"M", "N", "O", "P", "Q", "V", "W", "Y"},
				{"O", "P", "Q", "V", "W", "Y"}
		};
		testGetNodesInFlowHelper(flowNames, expected, load1);
	}
	@Test
	void testGetFlowsInNode2() {
		//manually enter flow names and all expected nodes from each of the chosen WorkLoad Files
		String[] flowNames = {"F0", "F1", "F2", "F3", "F4", "F5"};
		String[][] expected = {
				{"A", "B", "C"},
				{"C", "B", "A"},
				{"D", "E", "F"},
				{"P", "Q", "R", "S", "T", "U"},
				{"G", "H", "I", "J", "K", "L"},
				{"V", "W", "X", "Y", "Z"}
		};
		testGetNodesInFlowHelper(flowNames, expected, load2);
	}
	
	@Test
	void testGetFlowsInNode3() {
		//manually enter flow names and all expected nodes from each of the chosen WorkLoad Files
		String[] flowNames = {"F0", "F1", "F2", "F3", "F4", "F5"};
		String[][] expected = {
				{"A", "B", "C"},
				{"C", "B", "A"},
				{"D", "B", "F"},
				{"P", "B", "R"},
				{"G", "B", "I"},
				{"V", "B", "X"}
		};
		testGetNodesInFlowHelper(flowNames, expected, load3);
	}
	
	//getHyperPeriod() Test
	
	@Test
	void testGetHyperPeriod1() {
		//calculated the least common multiple of the periods to find expected value
		//then check if it is equal to the value from the load
		assertEquals(300, load1.getHyperPeriod());
	}
	
	@Test
	void testGetHyperPeriod2() {
		//calculated the least common multiple of the periods to find expected value
		//then check if it is equal to the value from the load
		assertEquals(100, load2.getHyperPeriod());
	}
	
	@Test
	void testGetHyperPeriod3() {
		//calculated the least common multiple of the periods to find expected value
		//then check if it is equal to the value from the load
		assertEquals(100, load3.getHyperPeriod());
	}
	
	//testGetNumTxAttemptsPerLink() Test
	
	void testGetNumTxAttemptsPerLinkHelper(WorkLoad load){
		//created an expected value that will not appear in typical program use
		//(somewhat like the example from discussion)
		
		final int EXPECTED_NUM_ATTEMPTS = 80085;
		
		FlowMap flows = load.getFlows();
		ArrayList<Flow> flowList = new ArrayList<Flow> (flows.values());
		
		//iterate through the flowList and put expected value in each element
		for(int i = 0; i < flowList.size(); i++) {
			ArrayList<Integer> expected = new ArrayList<>();
			
			for(int j = 0; j < flowList.size(); j++) {
				expected.add(EXPECTED_NUM_ATTEMPTS);
			}	
			
			//create copy of list to avoid reference errors
			ArrayList<Integer> copy = new ArrayList<>(expected);
			
			flowList.get(i).setLinkTxAndTotalCost(copy);
			
			//remove the last element (that is what the getNumTxAttemptsPerLink does)
			expected.remove(expected.size()-1);
			
			//the return type needs to be an array, so this changes it to fix that
			Object[] expectedArray = expected.toArray();
			
			//check that the flowList elements are 80085 for each link
			assertArrayEquals(expectedArray, load.getNumTxAttemptsPerLink(flowList.get(i).getName()));
		}	
	}
	
	@Test
	void testGetNumTxAttemptsPerLink1() {
		testGetNumTxAttemptsPerLinkHelper(load1);
	}
	@Test
	void testGetNumTxAttemptsPerLink2() {
		testGetNumTxAttemptsPerLinkHelper(load2);
	}
	@Test
	void testGetNumTxAttemptsPerLink3() {
		testGetNumTxAttemptsPerLinkHelper(load3);
	}
	
	//maxFlowLength() Test
	void testMaxFlowLengthHelper(WorkLoad load) {
		FlowMap flows = load.getFlows();
		ArrayList<Flow> flowList = new ArrayList<Flow> (flows.values());
		//iterate through flowList to make it an empty list (for edge cases)
		for(int i = 0; i < flowList.size(); i++) {
			flowList.get(i).setNodes(new ArrayList<Node>());
		}
		//check that the array is empty (for edge case)
		assertEquals(0, load.maxFlowLength());
		
		//iterate through flowList and fill list with i amount of nodes
		for(int i = 0; i < flowList.size(); i++) {
			ArrayList<Node> nodeList = new ArrayList<Node> ();
			for(int j = 0; j < i; j++) {
				nodeList.add(new Node("test", 0, 0));
			}
			//set nodes to the created nodeList
			flowList.get(i).setNodes(nodeList);
			int expected = i;
			//check that the largest size of any flow in load is returned by maxFlowLength()
			assertEquals(expected, load.maxFlowLength());
		}	
	}
	@Test
	void testMaxFlowLength1() {
		testMaxFlowLengthHelper(load1);
	}
	@Test
	void testMaxFlowLength2() {
		testMaxFlowLengthHelper(load2);
	}
	@Test
	void testMaxFlowLengt31() {
		testMaxFlowLengthHelper(load3);
	}
	
	//getFlowDeadline() Test
	void testGetFlowDeadlineHelper(String[] flowNames,WorkLoad load) {
		FlowMap flows = load.getFlows();
		final int EXPECTED = 80085;
		//iterate through the flows in load and check if the flow is equal to the expected value
		//that is not likely(88085) to appear in typical program use after it is set
		//(somewhat like the example from discussion)
		Collection<Flow> flowList = flows.values();
		for(int i = 0; i < flowList.size(); i++) {
			flows.get(flowNames[i]).setDeadline(EXPECTED);
			assertEquals(EXPECTED, load.getFlowDeadline(flowNames[i]));
		}
	}
	@Test
	void testGetFlowDeadline1() {
		//manually enter all flowNames of selected WorkLoad files
		String[] flowNames = {"F1", "F5", "F2", "F4", "F3","F6", "F7", "F8", "F9", "F10", "AF1", "AF5", "AF2", "AF4", "AF10"};
		testGetFlowDeadlineHelper(flowNames, load1);
	}
	
	@Test
	void testGetFlowDeadline2() {
		//manually enter all flowNames of selected WorkLoad files
		String[] flowNames = {"F0", "F1", "F2", "F3", "F4", "F5"};
		testGetFlowDeadlineHelper(flowNames, load2);
	}
	
	@Test
	void testGetFlowDeadline3() {
		//manually enter all flowNames of selected WorkLoad files
		String[] flowNames = {"F0", "F1", "F2", "F3", "F4", "F5"};
		testGetFlowDeadlineHelper(flowNames, load3);
	}
	
	//setFlowDeadline() Test
	void testSetFlowDeadlineHelper(String[] flowNames,WorkLoad load) {
		FlowMap flows = load.getFlows();
		final int EXPECTED = 80085;
		//almost identical to testGetFlowDeadlineHelper (but need .get to check the flowNames)
		//iterate through the flows in load and check if the flow is equal to the expected value
		//that is not likely(88085) to appear in typical program use after it is set
		//(somewhat like the example from discussion)
		Collection<Flow> flowList = flows.values();
		for(int i = 0; i < flowList.size(); i++) {
			load.setFlowDeadline(flowNames[i], EXPECTED);
			assertEquals(EXPECTED, flows.get(flowNames[i]).getDeadline());
		}
	}
	@Test
	void testSetFlowDeadline1() {
		//manually enter all flowNames of selected WorkLoad files
		String[] flowNames = {"F1", "F5", "F2", "F4", "F3","F6", "F7", "F8", "F9", "F10", "AF1", "AF5", "AF2", "AF4", "AF10"};
		testGetFlowDeadlineHelper(flowNames, load1);
	}
	
	@Test
	void testSetFlowDeadline2() {
		//manually enter all flowNames of selected WorkLoad files
		String[] flowNames = {"F0", "F1", "F2", "F3", "F4", "F5"};
		testSetFlowDeadlineHelper(flowNames, load2);
	}
	
	@Test
	void testSetFlowDeadline3() {
		//manually enter all flowNames of selected WorkLoad files
		String[] flowNames = {"F0", "F1", "F2", "F3", "F4", "F5"};
		testGetFlowDeadlineHelper(flowNames, load3);
	}
}
	
	

