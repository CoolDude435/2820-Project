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
	
	/**
	 * Tests displayVisualization method in ReliabilityVisualization
	 * Tests to make sure guiVisualization has output from 
	 * displayVisualization by checking not null
	 * 
	 * @see ReliabilityVisualization displayVisualization()
	 */
	@Test
	void displayVisualizationTest() {
		
		ReliabilityVisualization visualization = new ReliabilityVisualization(warp2);
		GuiVisualization guiVisualization = visualization.displayVisualization();
		
		assertNotNull(guiVisualization);	
	}
	
	/**
	 * For Example1A.txt
	 * Checks if createHeader() returns expected header description for given program 
	 * Creates new ReliabilityVisualization object with program warp2
	 * Calls createHeader() method, and verifies that returned description object is the expected
	 * 
	 * @see ReliabilityVisualization createHeader()
	 */
	@Test
	void createHeaderTest1() {
		
		ReliabilityVisualization visualization = new ReliabilityVisualization(warp2);
		Description header = visualization.createHeader();
		
		assertEquals("Reliability Analysis for graph Example1A\n", header.get(0));
		assertEquals("Scheduler Name: Priority\n", header.get(1));
		assertEquals("M: 0.65\n", header.get(2));
		assertEquals("E2E: 0.54\n", header.get(3));
		assertEquals("nChannels: 15\n", header.get(4));
	}
	
	/**
	 * For StressTest4.txt
	 * Checks if createHeader() returns expected header description for given program 
	 * Creates new ReliabilityVisualization object with program warp3
	 * Calls createHeader() method, and verifies that returned description object is the expected
	 * 
	 * @see ReliabilityVisualization createHeader()
	 */
	@Test
	void createHeaderTest2() {
		
		ReliabilityVisualization visualization = new ReliabilityVisualization(warp3);
		Description header = visualization.createHeader();
		
		assertEquals("Reliability Analysis for graph StressTest4\n", header.get(0));
		assertEquals("Scheduler Name: Priority\n", header.get(1));
		assertEquals("M: 0.9\n", header.get(2));
		assertEquals("E2E: 0.9\n", header.get(3));
		assertEquals("nChannels: 20\n", header.get(4));
	}
	
	 /**
	 * Tests createColumnHeader in ReliabilityVisualization.
	 * Creates ReliabilityVisualization object with warp1,and calls createColumnHeader()
     * Then compares output with expected array of column headers.
     * 
     * @see ReliabilityVisualization createColumnHeader)
	 */
	@Test
	void createColumnHeaderTest1() { 
		ReliabilityVisualization visualization = new ReliabilityVisualization(warp1);
   
        String[] expected = {"F0:A","F0:B",	"F0:C",	"F1:C",	"F1:B","F1:A"};
        String[] columnHeader = visualization.createColumnHeader();
        
        assertArrayEquals(expected, columnHeader);
	}
	
	/**
	 * Same as previous, but for StressTest4.txt
	 * Tests createColumnHeader in ReliabilityVisualization.
	 * Creates ReliabilityVisualization object with warp3,and calls createColumnHeader()
     * Then compares output with expected array of column headers.
     * 
     * @see ReliabilityVisualization createColumnHeader)
	 */
	@Test
	void createColumnHeaderTest2() { 
		ReliabilityVisualization visualization = new ReliabilityVisualization(warp3);
   
        String[] expected = {"F1:B","F1:C","F1:D",	"F2:C",	"F2:D",	"F2:E",	"F2:F",	"F2:G",
        		"F2:H",	"F2:I",	"F3:C",	"F3:D",	"F3:E",	"F3:J",	"F3:K",	"F3:L",	"F4:A",	"F4:B",	
        		"F4:C",	"F4:D",	"F4:E",	"F4:J",	"F4:K",	"F4:L",	"F5:A", "F5:B",	"F5:C",	"F5:D",	
        		"F5:E",	"F6:B",	"F6:C",	"F6:D",	"F7:A",	"F7:B",	"F7:C",	"F7:D",	"F7:E",	"F8:C",
        		"F8:D",	"F8:E",	"F8:F",	"F8:G",	"F8:H",	"F8:I",	"F9:A",	"F9:B",	"F9:C",	"F9:D",
        		"F9:E",	"F9:J",	"F9:K",	"F9:L",	"F10:C", "F10:D", "F10:E", "F10:J",	"F10:K", "F10:L"};
        
        String[] columnHeader = visualization.createColumnHeader();
        assertArrayEquals(expected, columnHeader);
	}
	
	/**
	 * Creates new instance of Program with warp1 and passes it to constructor of ReliabilityVisualization
	 * Calls createTitle to get title of visualization and checks if equal to expected
	 * 
	 * @see ReliabilityVisualization createTitle()
	*/
	@Test
	public void createTitleTest1() {
	    ReliabilityVisualization visualization = new ReliabilityVisualization(warp1);
	    String title = visualization.createTitle();
	    assertEquals("Reliability Analysis for graph Example\n", title);
	}
	
	/**
	 * Same as test before but tests Example1A.txt
	 * Creates new instance of Program with warp2 and passes it to constructor of ReliabilityVisualization
	 * Calls createTitle to get title of visualization and checks if equal to expected
	 * 
	 * @see ReliabilityVisualization createTitle()
	*/
	@Test
	public void createTitleTest2() {
	    ReliabilityVisualization visualization = new ReliabilityVisualization(warp2);
	    String title = visualization.createTitle();
	    assertEquals("Reliability Analysis for graph Example1A\n", title);
	}
	
	/**
	 * Same as test before but tests StressTest4.txt
	 * Creates new instance of Program with warp3 and passes it to constructor of ReliabilityVisualization
	 * Calls createTitle to get title of visualization and checks if equal to expected
	 * 
	 * @see ReliabilityVisualization createTitle()
	*/
	@Test
	public void createTitleTest3() {
	    ReliabilityVisualization visualization = new ReliabilityVisualization(warp3);
	    String title = visualization.createTitle();
	    assertEquals("Reliability Analysis for graph StressTest4\n", title);
	}
	
	/**
	 * Creates new ReliabilityVisualization object with warp1
	 * Calls createVisualizationData() to get array of strings then tests that array is filled with expected values
	 * Checks that first column of each row has row number as string and the remaining columns have corresponding values
	 * If test passes; each row should have expected values and array of strings should not be null.
	 * 
	 * @see ReliabilityVisualization createVisualizationData() 
	*/
	@Test
	public void createVisualizationDataTest1() {
		ReliabilityVisualization visualization = new ReliabilityVisualization(warp1);
	    String[][] visualizationData = visualization.createVisualizationData();
        assertNotNull(visualizationData);

        int expectedNumRows =visualizationData.length;
        int expectedNumColumns = visualization.createColumnHeader().length;
        assertEquals(expectedNumRows, visualizationData.length);
        assertEquals(expectedNumColumns, visualizationData[0].length);
    
        for (int row = 0; row < visualizationData.length; row++) {
        	for (int column = 0; column < visualizationData[0].length; column++) {
        		assertEquals("0", visualizationData[row][column]);
        	}
        }
	}
	
	/**
	 * Same as previous but checks file Example1a.txt
	 * Creates new ReliabilityVisualization object with warp2
	 * Calls createVisualizationData() to get array of strings then tests that array is filled with expected values
	 * Checks that first column of each row has row number as string and the remaining columns have corresponding values
	 * If test passes; each row should have expected values and array of strings should not be null.
	 * 
	 * @see ReliabilityVisualization createVisualizationData() 
	*/
	@Test
	public void createVisualizationDataTest2() {
		ReliabilityVisualization visualization = new ReliabilityVisualization(warp2);
	    String[][] visualizationData = visualization.createVisualizationData();
        assertNotNull(visualizationData);

        int expectedNumRows =visualizationData.length;
        int expectedNumColumns = visualization.createColumnHeader().length;
        assertEquals(expectedNumRows, visualizationData.length);
        assertEquals(expectedNumColumns, visualizationData[0].length);
    
        for (int row = 0; row < visualizationData.length; row++) {
        	for (int column = 0; column < visualizationData[0].length; column++) {
        		assertEquals("0", visualizationData[row][column]);
        	}
        }
	}
	
	/**
	 * Same as previous but checks file StressTest4.txt
	 * Creates new ReliabilityVisualization object with warp3
	 * Calls createVisualizationData() to get array of strings then tests that array is filled with expected values
	 * Checks that first column of each row has row number as string and the remaining columns have corresponding values
	 * If test passes; each row should have expected values and array of strings should not be null.
	 * 
	 * @see ReliabilityVisualization createVisualizationData() 
	*/
	@Test
	public void createVisualizationDataTest3() {
		ReliabilityVisualization visualization = new ReliabilityVisualization(warp3);
	    String[][] visualizationData = visualization.createVisualizationData();
        assertNotNull(visualizationData);

        int expectedNumRows =visualizationData.length;
        int expectedNumColumns = visualization.createColumnHeader().length;
        assertEquals(expectedNumRows, visualizationData.length);
        assertEquals(expectedNumColumns, visualizationData[0].length);
    
        for (int row = 0; row < visualizationData.length; row++) {
        	for (int column = 0; column < visualizationData[0].length; column++) {
        		assertEquals("0", visualizationData[row][column]);
        	}
        }
	}
	
}
