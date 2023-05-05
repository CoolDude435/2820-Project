package edu.uiowa.cs.warp;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	/**
	 * Sets the header row of a reliability table
	 * Creates new ReliabilityAnalysis object with program1, builds the reliabilities, and
	 * sets a new header row using setReliabilityHeaderRow() method. 
	 * Verifies that new header row was set correctly by checking if it matches expected header row
	 * 
	 * @see ReliabilityAnalysis setReliabilityHeaderRow()
	 */
	@Test
    void setReliabilityHeaderRowTes1t() {
        // Create a new ReliabilityAnalysis object with a test reliability table
        ReliabilityAnalysis reliabilityAnalysis = new ReliabilityAnalysis(program1);
        reliabilityAnalysis.buildReliabilities();

        ArrayList<String> newHeader = new ArrayList<>(Arrays.asList("Node", "Reliability"));
        reliabilityAnalysis.setReliabilityHeaderRow(newHeader);

        // Verify that header row was updated correctly
        assertEquals(newHeader, reliabilityAnalysis.getReliabilityHeaderRow());
    }
	
	/**
	 * Same as previous, but tests file StressTest4.txt
	 * Sets the header row of a reliability table
	 * Creates new ReliabilityAnalysis object with program3, builds the reliabilities, and
	 * sets a new header row using setReliabilityHeaderRow() method. 
	 * Verifies that new header row was set correctly by checking if it matches expected header row
	 * 
	 * @see ReliabilityAnalysis setReliabilityHeaderRow()
	 */
	@Test
    void setReliabilityHeaderRowStressTest() {
        // Create a new ReliabilityAnalysis object with a test reliability table
        ReliabilityAnalysis reliabilityAnalysis = new ReliabilityAnalysis(program3);
        reliabilityAnalysis.buildReliabilities();

        ArrayList<String> newHeader = new ArrayList<>(Arrays.asList("Node", "Reliability"));
        reliabilityAnalysis.setReliabilityHeaderRow(newHeader);

        // Verify that header row was updated correctly
        assertEquals(newHeader, reliabilityAnalysis.getReliabilityHeaderRow());
    }
	
	/**
	 * Creates new ReliabilityAnalysis object with program3, builds the reliabilities
	 * Then it compares the header row with the expected values to check they match.
	 * 
	 * @see ReliabilityAnalysis getReliabilityHeader()
	 */
	@Test
	public void getReliabilityHeaderRowTest1() {
	    // Create a new ReliabilityAnalysis object
	    ReliabilityAnalysis reliabilityAnalysis = new ReliabilityAnalysis(program1);

	    // Build the reliabilities
	    reliabilityAnalysis.buildReliabilities();

	    // Verify that the header row is not null after building the reliabilities
	    assertNotNull(reliabilityAnalysis.getReliabilityHeaderRow());
	    
	    // Verify that the header row matches the expected values
	    List<String>expectedHeaderRow = Arrays.asList("F0:A", "F0:B", "F0:C", "F1:C", "F1:B", "F1:A");
	    
	    assertEquals(expectedHeaderRow, new ArrayList<String>(reliabilityAnalysis.getReliabilityHeaderRow()));
	}
	
	/**
	 * Same as test before, but checks StressTest4.txt
	 * Creates new ReliabilityAnalysis object with program3, builds the reliabilities
	 * Then it compares the header row with the expected values to check they match.
	 * 
	 * @see ReliabilityAnalysis getReliabilityHeader()
	 */
	@Test
	public void getReliabilityHeaderStressTest() {
	    // Create a new ReliabilityAnalysis object
	    ReliabilityAnalysis reliabilityAnalysis = new ReliabilityAnalysis(program3);

	    // Build the reliabilities
	    reliabilityAnalysis.buildReliabilities();

	    // Verify that the header row is not null after building the reliabilities
	    assertNotNull(reliabilityAnalysis.getReliabilityHeaderRow());
	    
	    // Verify that the header row matches the expected values
	    List<String>expectedHeaderRow = Arrays.asList("F1:B", "F1:C", "F1:D", "F2:C", "F2:D",
	    		"F2:E", "F2:F", "F2:G", "F2:H", "F2:I", "F3:C", "F3:D", "F3:E", "F3:J", "F3:K",
	    		"F3:L", "F4:A", "F4:B", "F4:C", "F4:D", "F4:E", "F4:J", "F4:K", "F4:L", "F5:A", 
	    		"F5:B", "F5:C", "F5:D", "F5:E", "F6:B", "F6:C", "F6:D", "F7:A", "F7:B", "F7:C",
	    		"F7:D", "F7:E", "F8:C", "F8:D", "F8:E", "F8:F", "F8:G", "F8:H", "F8:I", "F9:A",
	    		"F9:B", "F9:C", "F9:D", "F9:E", "F9:J", "F9:K", "F9:L", "F10:C", "F10:D", "F10:E",
	    		"F10:J", "F10:K", "F10:L");
	    
	    assertEquals(expectedHeaderRow, new ArrayList<String>(reliabilityAnalysis.getReliabilityHeaderRow()));
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
	
	/**
	* Tests verifyReliabilities() method with StressTest4.txt
	* Creates ReliabilityAnalysis object with the program of WarpSystem object.
	* Calls verifyReliabilities() method and asserts that result is true,
	* which tells that E2E reliability has been met.
	* 
	* Also tests case where E2E reliability is not met. 
	* Creates another WarpSystem object where E2E reliability is 
	* higher than max E2E reliability that can be reached 
	* Calls the verifyReliabilities() method and asserts that result is false,
	* indicating that the E2E reliability has not been met.
	*
	* @see ReliabilityAnalysis verifyReliabilities()
	*/
	
	@Test
	void verifyReliabilitiesTest1() {
	    WorkLoad load = new WorkLoad(0.9, 0.0, "StressTest4.txt");
	    WarpSystem system = new WarpSystem(load, 4, ScheduleChoices.PRIORITY);
	    ReliabilityAnalysis analysis = new ReliabilityAnalysis(system.toProgram());
	    analysis.buildReliabilities();
	    boolean result = analysis.verifyReliabilities();
	    assertTrue(result);
	    
	    // Test the case where E2E is not met
	    WorkLoad load2 = new WorkLoad(0.9, 1.0, "StressTest4.txt");
	    WarpSystem system2 = new WarpSystem(load2, 4, ScheduleChoices.PRIORITY);
	    ReliabilityAnalysis analysis2 = new ReliabilityAnalysis(system2.toProgram());
	    analysis2.buildReliabilities();
	    boolean result2 = analysis2.verifyReliabilities();
	    assertFalse(result2);
	}
	
	/**
	* Tests verifyReliabilities() method with Example.txt (which is never not met)
	* Creates ReliabilityAnalysis object with the program of WarpSystem object.
	* Calls verifyReliabilities() method and asserts that result is true,
	* which tells that E2E reliability has been met.
	*
	* @see ReliabilityAnalysis verifyReliabilities()
	*/
	
	@Test
	void verifyReliabilitiesTest2() {
	    WorkLoad load = new WorkLoad(0.9, 0.0, "Example.txt");
	    WarpSystem system = new WarpSystem(load, 4, ScheduleChoices.PRIORITY);
	    ReliabilityAnalysis analysis = new ReliabilityAnalysis(system.toProgram());
	    analysis.buildReliabilities();
	    boolean result = analysis.verifyReliabilities();
	    assertTrue(result);
	}
	
	/**
	* Tests verifyReliabilities() method with StressTest.txt
	* Creates ReliabilityAnalysis object with the program of WarpSystem object.
	* Calls verifyReliabilities() method and asserts that result is true,
	* which tells that E2E reliability has been met.
	* 
	* Also tests case where E2E reliability is not met. 
	* Creates another WarpSystem object where E2E reliability is 
	* higher than max E2E reliability that can be reached 
	* Calls the verifyReliabilities() method and asserts that result is false,
	* indicating that the E2E reliability has not been met.
	*
	* @see ReliabilityAnalysis verifyReliabilities()
	*/
	@Test
	void verifyReliabilitiesTest3() {
	    WorkLoad load = new WorkLoad(0.9, 0.0, "StressTest.txt");
	    WarpSystem system = new WarpSystem(load, 4, ScheduleChoices.PRIORITY);
	    ReliabilityAnalysis analysis = new ReliabilityAnalysis(system.toProgram());
	    analysis.buildReliabilities();
	    boolean result = analysis.verifyReliabilities();
	    assertTrue(result);
	    
	    // Test the case where E2E is not met
	    WorkLoad load2 = new WorkLoad(0.9, 1.0, "StressTest.txt");
	    WarpSystem system2 = new WarpSystem(load2, 4, ScheduleChoices.PRIORITY);
	    ReliabilityAnalysis analysis2 = new ReliabilityAnalysis(system2.toProgram());
	    analysis2.buildReliabilities();
	    boolean result2 = analysis2.verifyReliabilities();
	    assertFalse(result2);
	}

	/**
	* Test verifies that getReliabilities method in ReliabilityAnalysis returns a non-null ReliabilityTable object.
	* Creates new ReliabilityAnalysis object with Program object and calls getReliabilities method.
	* If returned ReliabilityTable object is not null, the test passes.
	* If null, the test fails.
	* 
	* @see ReliabilityAnalysis getReliabilities()
	*/
	@Test
	void getReliabilitiesTest() {
	    // create a new reliability analysis object
	    ReliabilityAnalysis ra = new ReliabilityAnalysis(program1);
	    
	    // get the reliability table
	    ReliabilityTable table = ra.getReliabilities();
	    
	    // check that the table is not null
	    assertNotNull(table);
	}
	
	/**
	* Same as previous test but tests StressTest.txt
	* Test verifies that getReliabilities method in ReliabilityAnalysis returns a non-null ReliabilityTable object.
	* Creates new ReliabilityAnalysis object with Program object and calls getReliabilities method.
	* If returned ReliabilityTable object is not null, the test passes.
	* If returned ReliabilityTable object is null, the test fails.
	* 
	* @see ReliabilityAnalysis getReliabilities()
	*/
	@Test
	void getReliabilitiesStressTest() {
	    // create a new reliability analysis object
	    ReliabilityAnalysis ra = new ReliabilityAnalysis(program3);
	    
	    // get the reliability table
	    ReliabilityTable table = ra.getReliabilities();
	    
	    // check that the table is not null
	    assertNotNull(table);
	}
	
	/**
	 * Checks if method carryForwardReliabilities() updates reliability table correctly.
	 * Creates new ReliabilityAnalysis object with a test reliability table, builds reliabilities and calls
	 * carryForwardReliabilities() with time slot 1 and 2. Then it verifies that the reliability table was updated
	 * correctly for each time slot.
	 * 
	 * @see ReliabilityAnalysis carryForwardReliabilities()
	 */
	@Test
	void carryForwardReliabilitiesTest() {
	    // Create a new ReliabilityAnalysis object with a test reliability table
	    ReliabilityAnalysis reliabilityAnalysis = new ReliabilityAnalysis(program1);
	    reliabilityAnalysis.buildReliabilities();

	    // Call carryForwardReliabilities() with time slot 1
	    reliabilityAnalysis.carryForwardReliabilities(1);

	    // Verify that the reliability table was updated correctly
	    List<Double> expectedRow = Arrays.asList(1.0, 0.99, 0.81,1.0,0.0, 0.0);
	    assertEquals(expectedRow, reliabilityAnalysis.getReliabilities().get(1));

	    // Call carryForwardReliabilities() with time slot 2
	    reliabilityAnalysis.carryForwardReliabilities(2);

	    // Verify that the reliability table was updated correctly
	    expectedRow = Arrays.asList(1.0, 0.999, 0.972, 1.0, 0.0, 0.0);
	    assertEquals(expectedRow, reliabilityAnalysis.getReliabilities().get(2));
	}
	
	/**
	 * Does same thing as previous test but tests StressTest.txt
	 * Checks if method carryForwardReliabilities() updates the reliability table correctly.
	 * Creates a new ReliabilityAnalysis object with a test reliability table, builds reliabilities and calls
	 * carryForwardReliabilities() with time slot 1 and 2. Then it verifies that the reliability table was updated
	 * correctly for each time slot.
	 * 
	 * @see ReliabilityAnalysis carryForwardReliabilities()
	 */
	@Test
	void carryForwardReliabilitiesStressTest() {
	    // Create a new ReliabilityAnalysis object with a test reliability table
	    ReliabilityAnalysis reliabilityAnalysis = new ReliabilityAnalysis(program3);
	    reliabilityAnalysis.buildReliabilities();

	    // Call carryForwardReliabilities() with time slot 1
	    reliabilityAnalysis.carryForwardReliabilities(1);

	    // Verify that the reliability table was updated correctly
	    List<Double> expectedRow = Arrays.asList(1.0,0.99,0.81,1.0,0.0,0.0,0.0,0.0,0.0,0.0,1.0,0.0,
	    		0.0,0.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0,1.0,0.0, 0.0,1.0,
	    		0.0,0.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0,0.0);
	    assertEquals(expectedRow, reliabilityAnalysis.getReliabilities().get(1));

	    // Call carryForwardReliabilities() with time slot 2
	    reliabilityAnalysis.carryForwardReliabilities(2);

	    // Verify that the reliability table was updated correctly
	    expectedRow = Arrays.asList(1.0, 0.99, 0.972, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
	    		1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.9, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 
	    		0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 
	    		0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0);
	    assertEquals(expectedRow, reliabilityAnalysis.getReliabilities().get(2));
	}
	
	/**
	 * Creates new ReliabilityAnalysis object with a test reliability table and builds the reliabilities.
	 * Calls the setInitialStateForReleasedFlows() method to set the initial state for released flows.
	 * Verifies that reliability table was updated correctly by comparing the expected 
	 * row to the first row of the updated reliability table.
	 * 
	 * @see ReliabilityAnalysis setInitialStateForReleasedFlows()
	 */
	@Test
	void setInitialStateForReleasedFlowsTest1() {
	// Create a new ReliabilityAnalysis object with a test reliability table
	ReliabilityAnalysis reliabilityAnalysis = new ReliabilityAnalysis(program1);
	reliabilityAnalysis.buildReliabilities();
	// Call setInitialStateForReleasedFlows()
	reliabilityAnalysis.setInitialStateForReleasedFlows();

	// Verify that the reliability table was updated correctly
	List<Double> expectedRow = Arrays.asList(1.0,0.9,0.0,1.0,0.0,0.0);
	assertEquals(expectedRow, reliabilityAnalysis.getReliabilities().get(0));	    
	}
	
	/**
	 * Same as previous, but tests StressTest4.txt
	 * Creates new ReliabilityAnalysis object with a test reliability table and builds the reliabilities.
	 * Calls the setInitialStateForReleasedFlows() method to set the initial state for released flows.
	 * Verifies that reliability table was updated correctly by comparing the expected 
	 * row to the first row of the updated reliability table.
	 * 
	 * @see ReliabilityAnalysis setInitialStateForReleasedFlows()
	 */
	@Test
	void setInitialStateForReleasedFlowsStressTest() {
	// Create a new ReliabilityAnalysis object with a test reliability table
	ReliabilityAnalysis reliabilityAnalysis = new ReliabilityAnalysis(program3);
	reliabilityAnalysis.buildReliabilities();
	// Call setInitialStateForReleasedFlows()
	reliabilityAnalysis.setInitialStateForReleasedFlows();

	// Verify that the reliability table was updated correctly
	List<Double> expectedRow = Arrays.asList(1.0, 0.9, 0.0, 1.0, 0.0, 0.0, 0.0, 
			0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0,
			0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 
			0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 
			0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0);
	assertEquals(expectedRow, reliabilityAnalysis.getReliabilities().get(0));	    
	}
	
}
	