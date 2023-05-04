package edu.uiowa.cs.warp;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import edu.uiowa.cs.warp.SystemAttributes.ScheduleChoices;

public class ReliabilityAnalysisTest {
	private static final double M =.9;
	private static final double E2E = .99;
	private static final int NUM_CHANNELS = 16;
	
	Program program1;
	Program program2;
	Program program3;
	
	WarpSystem warp1;
	WarpSystem warp2;
	WarpSystem warp3;
	
	@BeforeEach
	void initializeTests() {
		WorkLoad load1 = new WorkLoad(M, E2E, "Example.txt");
		WorkLoad load2 = new WorkLoad(.65,.54, "Example1a.txt");
		WorkLoad stressTest = new WorkLoad(.9,.9, "StressTest4.txt");
		
		warp1 = new WarpSystem(load1, NUM_CHANNELS, ScheduleChoices.PRIORITY);
		warp2 = new WarpSystem(load2, 15, ScheduleChoices.PRIORITY);
		warp3 = new WarpSystem(stressTest, 20, ScheduleChoices.PRIORITY);

		program1 = warp1.toProgram();
		program2 = warp2.toProgram();
		program3 = warp3.toProgram();
	}
	
	

	
	@Test
	void buildReliabilitiesTest() {
		//method not yet implemented
	}
	
	@Test
	void setHeaderRowTest() {
		//method not yet implemented
	}
	
	@Test
	void getHeaderRowTest() {
		//method not yet implemented
	}
	
	@Test
	void getFixedTxPerLinkAndTotalTxCostTest() {
		//method not yet implemented
	}
	
	@Test
	void getTxPerLinkAndTotalTxCostTest() {
		//method not yet implemented
	}
	
	@Test
	void setReliabilityHeaderRowTest() {
		//method not yet implemented
	}
	
	/**
	 * Tests the numTxPerLinkAndTotalTxCost() method in ReliabilityAnalysis
	 * Creates a new WorkLoad with the Example.txt file
	 * Call numTxPerLinkAndTotalTxCost() on flow F0
	 * Test that ArrayList against the expected output
	 * 
	 * @see ReliabilityAnalysis numTxPerLinkAndTotalTxCost()
	 */
	@Test
	void numTxPerLinkAndTotalTxCostTest() {
		ReliabilityAnalysis reliAnalysis = new ReliabilityAnalysis(0);
		WorkLoad load1 = new WorkLoad(M, E2E, "Example.txt");
		FlowMap fMap = load1.getFlows();
		ArrayList<Integer> actual = reliAnalysis.numTxPerLinkAndTotalTxCost(fMap.get("F0"));
		ArrayList<Integer> expected = new ArrayList<Integer>();
		expected.add(1);
		expected.add(1);
		expected.add(1);
		expected.add(2);
		for (int i=0;i<actual.size();i++) {
			assertSame(expected.get(i),actual.get(i));
		}
	}
	
	
	//Annalisa's Tests
	
	/**
	* Tests verifyReliabilities() method
	* Creates WarpSystem object with given WorkLoad, number of channels, scheduling choice
	* Creates ReliabilityAnalysis object with the program of WarpSystem object.
	* Calls verifyReliabilities() method and asserts that result is true,
	* which tells that E2E reliability has been met.
	* Also tests case where E2E reliability is not met. 
	* Creates another WarpSystem object with WorkLoad where E2E reliability is higher than maximum E2E reliability that can
	* be reached by the system. 
	* Creates a ReliabilityAnalysis object with program of WarpSystem object. 
	* Calls the verifyReliabilities() method and asserts that result is false,
	* indicating that the E2E reliability has not been met.
	*
	* @see ReliabilityAnalysis
	*/
	
	@Test
	void testVerifyReliabilities() {
	    WorkLoad load = new WorkLoad(0.8, 0.95, "example.txt");
	    WarpSystem system = new WarpSystem(load, 4, ScheduleChoices.PRIORITY);
	    ReliabilityAnalysis analysis = new ReliabilityAnalysis(system.toProgram());
	    boolean result = analysis.verifyReliabilities();
	    assertTrue(result);
	    
	    // Test the case where E2E is not met
	    WorkLoad load2 = new WorkLoad(0.9, 0.98, "example.txt");
	    WarpSystem system2 = new WarpSystem(load2, 4, ScheduleChoices.PRIORITY);
	    ReliabilityAnalysis analysis2 = new ReliabilityAnalysis(system2.toProgram());
	    boolean result2 = analysis2.verifyReliabilities();
	    assertFalse(result2);
	}

	/**
	* Test verifies that getReliabilities method in ReliabilityAnalysis returns a non-null ReliabilityTable object.
	* Creates new ReliabilityAnalysis object with Program object and calls getReliabilities method.
	* If returned ReliabilityTable object is not null, the test passes.
	* If returned ReliabilityTable object is null, the test fails.
	* 
	* @see ReliabilityAnalysis getReliabilities
	*/
	
	@Test
	void testGetReliabilities() {
	    // create a new reliability analysis object
	    ReliabilityAnalysis ra = new ReliabilityAnalysis(program1);
	    
	    // get the reliability table
	    ReliabilityTable table = ra.getReliabilities();
	    
	    // check that the table is not null
	    assertNotNull(table);
	}
	
	@Test
	void testCarryForwardReliabilities() {
	    // Create a new ReliabilityAnalysis object with a test reliability table
	    ReliabilityAnalysis reliabilityAnalysis = new ReliabilityAnalysis(program1);
	    

	    // Call carryForwardReliabilities() with time slot 1
	    reliabilityAnalysis.carryForwardReliabilities(1);

	    // Verify that the reliability table was updated correctly
	    List<Double> expectedRow = Arrays.asList(1.0, 0.5, 0.75, 1.0, 0.5, 0.5, 0.75);
	    assertEquals(expectedRow, reliabilityAnalysis.getReliabilities().get(1));

	    // Call carryForwardReliabilities() with time slot 2
	    reliabilityAnalysis.carryForwardReliabilities(2);

	    // Verify that the reliability table was updated correctly
	    expectedRow = Arrays.asList(1.0, 0.75, 0.875, 1.0, 0.75, 0.75, 0.875);
	    assertEquals(expectedRow, reliabilityAnalysis.getReliabilities().get(2));
	}
	
	@Test
	void testSetInitialStateForReleasedFlows() {
	    // Create a new ReliabilityAnalysis object with a test node map and table
	    ReliabilityAnalysis reliabilityAnalysis = new ReliabilityAnalysis(program1);
	    //reliabilityAnalysis.NodeMap(/* test node map */);
	    //reliabilityAnalysis.setReliabilityTable(/* test reliability table */);

	    // Call the method being tested
	    reliabilityAnalysis.setInitialStateForReleasedFlows();

	    // Check that the reliabilityTable was updated as expected
	    //for (String node : reliabilityAnalysis.getNodeMap().keySet()) {
	        //ReliabilityAnalysis reliNode = reliabilityAnalysis.getNodeMap().get(node);
	        //if (reliNode.isFlowSrc() == true) {
	            for (int i = 0; i < reliabilityAnalysis.getReliabilities().getNumRows(); i++) {
	                //assertEquals(1.0, reliabilityAnalysis.getReliabilities().get(i, reliNode.getIndex()));
	            }
	      }
	    
	}
	
