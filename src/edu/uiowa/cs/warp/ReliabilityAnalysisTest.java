package edu.uiowa.cs.warp;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;

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
	
	@Test
	void verifyReliabilitiesTest() {
		//method not yet implemented
	}
	
	@Test
	void getReliabilitiesTest() {
		//method not yet implemented
	}
	
	@Test
	void buildReliabilityTableTest() {
		//method not yet implemented
	}
	
	@Test
	void carryForwardReliabilitiesTest() {
		//method not yet implemented
	}
	
	@Test
	void printRaTableTest() {
		//method not yet implemented
	}
	
	@Test
	void setReliabilitiesTest() {
		//method not yet implemented
	}
	
	@Test
	void setInitialStateForReleasedFlowsTest() {
		//method not yet implemented
	}
	
}
