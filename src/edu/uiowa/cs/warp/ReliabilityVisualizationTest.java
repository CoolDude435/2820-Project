package edu.uiowa.cs.warp;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import edu.uiowa.cs.warp.SystemAttributes.ScheduleChoices;

public class ReliabilityVisualizationTest {
	
	private static final double M =.9;
	private static final double E2E = .99;
	private static final int NUM_CHANNELS = 16;
	
	private Program program1;
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
	void displayVisualizationTest() {
		fail("Not yet implemented");
	}
	
	@Test
	void createHeaderTest() {
		fail("Not yet implemented");
	}
	
	@Test
	void createColumnHeaderTest() {
		fail("Not yet implemented");
	}
	
	/**
	 * This method tests the createTitle method in ReliabilityVisualization.
	 * It creates a new instance of Program with the given parameters and passes it to the constructor of ReliabilityVisualization.
	 * It then calls createTitle to obtain the title of the visualization and checks whether it is equal to the expected value. Source file: Example1a.txt
	 * 
	 * @see ReliabilityVisualization#createTitle()
	*/
	@Test
	public void createTitleTest1() {
	    ReliabilityVisualization visualization = new ReliabilityVisualization(warp1);
	    String title = visualization.createTitle();
	    assertEquals("Reliability Analysis for graph Example\n", title);
	}
	
	@Test
	public void createTitleTest2() {
	    ReliabilityVisualization visualization = new ReliabilityVisualization(warp2);
	    String title = visualization.createTitle();
	    assertEquals("Reliability Analysis for graph Example1A\n", title);
	}
	
	@Test
	public void createTitleTest3() {
	    ReliabilityVisualization visualization = new ReliabilityVisualization(warp3);
	    String title = visualization.createTitle();
	    assertEquals("Reliability Analysis for graph StressTest4\n", title);
	}
	
	
	/**
	 * This test case verifies that the createVisualizationData() method in the ReliabilityVisualization class
	 * correctly creates and returns a 2D array of strings representing the source code of a WARP program.
	 * The test case creates a new ReliabilityVisualization object with a Program object containing test data,
	 * calls the createVisualizationData() method to obtain the 2D array of strings, and verifies that the array
	 * has been correctly populated with the expected values. Specifically, it checks that the first column of each
	 * row contains the row number as a string, and that the remaining columns contain the corresponding values
	 * from the program's schedule. If the test passes, the 2D array of strings should not be null, and each row should have the expected contents. Source file: Example1a.txt
	*/
	
	/*
	@Test
	public void createVisualizationDataTest1() {
	    ReliabilityVisualization visualization = new ReliabilityVisualization(warp1);
	    String[][] visualizationData = visualization.createVisualizationData();
	    assertNotNull(visualizationData);

	    int numRows = visualizationData.length;
	    int numColumns = visualizationData[0].length - 1;

	    for (int row = 0; row < numRows; row++) {
	        assertEquals(String.format("%s", row), visualizationData[row][0]);
	        for (int column = 0; column < numColumns; column++) {
	            assertEquals(program1.getSchedule().get(row, column), visualizationData[row][column + 1]);
	        }
	    }
	}

	@Test
	public void createVisualizationDataTest2() {
	    ReliabilityVisualization visualization = new ReliabilityVisualization(warp2);
	    String[][] visualizationData = visualization.createVisualizationData();
	    assertNotNull(visualizationData);

	    int numRows = visualizationData.length;
	    int numColumns = visualizationData[0].length - 1;

	    for (int row = 0; row < numRows; row++) {
	        assertEquals(String.format("%s", row), visualizationData[row][0]);
	        for (int column = 0; column < numColumns; column++) {
	            assertEquals(program2.getSchedule().get(row, column), visualizationData[row][column + 1]);
	        }
	    }
	}
	
	@Test
	public void createVisualizationDataTest3() {
	    ReliabilityVisualization visualization = new ReliabilityVisualization(warp3);
	    String[][] visualizationData = visualization.createVisualizationData();
	    assertNotNull(visualizationData);

	    int numRows = visualizationData.length;
	    int numColumns = visualizationData[0].length - 1;

	    for (int row = 0; row < numRows; row++) {
	        assertEquals(String.format("%s", row), visualizationData[row][0]);
	        for (int column = 0; column < numColumns; column++) {
	            assertEquals(program3.getSchedule().get(row, column), visualizationData[row][column + 1]);
	        }
	    }
	}
	
	
	*/
	

}
