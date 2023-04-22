package edu.uiowa.cs.warp;

import java.util.ArrayList;

/**
 * ReliabilityVisualization creates the visualizations for
 * the reliability analysis of the WARP program. <p>
 * 
 * CS2820 Spring 2023 Project: Implement this class to create
 * the file visualization that is requested in Warp.
 * 
 * @author sgoddard
 *  *
 */
public class ReliabilityVisualization extends VisualizationObject {
   private static final String SOURCE_SUFFIX = ".ra";
   
   private static final String OBJECT_NAME = "Reliability Analysis";
   
   private WarpInterface warp;
   private ReliabilityAnalysis ra;
   private Program program;
   
   /**
    * ReliabilityVisualization will construct a reliability visualization of the warp program.
    * 
    * @param warp is Warp that will be visualized
    */
   /*package*/ 
   ReliabilityVisualization (WarpInterface warp) {
      super(new FileManager(), warp, SOURCE_SUFFIX);
      this.warp = warp;
      this.program = warp.toProgram();
      this.ra = warp.toReliabilityAnalysis();
   }
   
   /**
   * displayVisualization displays a GUi of the reliability visualization
   * 
   * @return returns the GuiVisualization created from the title, columnHeaders, and data
   */
   @Override
   public GuiVisualization displayVisualization() {
     return new GuiVisualization(createTitle(), createColumnHeader(), createVisualizationData());
   }
   
   /**
   * createHeader creates a header for RelizabilityVisualization.
   * The header includes the scheduler name, number of faults, the minimum packet reception rate,
   * and the number of channels. 
   * 
   * @return returns the header with the included information. 
   */
   @Override
   protected Description createHeader() {
	 
     Description header = new Description();

     header.add(createTitle());
     header.add(String.format("Scheduler Name: %s\n", program.getSchedulerName()));
		
     /* The following parameters are output based on a special schedule or the fault model */
	   
     if (warp.toProgram().getNumFaults() > 0) { // only specify when deterministic fault model is assumed
       header.add(String.format("numFaults: %d\n", program.getNumFaults()));
     }
     header.add(String.format("M: %s\n", String.valueOf(program.getMinPacketReceptionRate())));
     header.add(String.format("E2E: %s\n", String.valueOf(program.getE2e())));
     header.add(String.format("nChannels: %d\n", program.getNumChannels()));
     return header;
   }
   
   /**
   * createColumnHeader creates an array of of column headers that includes the flow
   * and node name for each corresponding time slot. 
   * 
   * @return returns a string array of the column headers 
   */
   @Override
   protected String[] createColumnHeader() {
	   	ArrayList<String> flowNames = program.toWorkLoad().getFlowNamesInPriorityOrder();
	   	ArrayList<String> columnHeaders = new ArrayList<String>();
	   	for (int flow=0;flow<flowNames.size();flow++) {
	   		FlowMap flowMap = program.toWorkLoad().getFlows();
	   		ArrayList<Node> nodesInFlow = flowMap.get(flowNames.get(flow)).getNodes();
	   		for (int node=0;node<nodesInFlow.size();node++) {
	   			String header = flowNames.get(flow) + ":" + nodesInFlow.get(node);
	   			columnHeaders.add(header);
	   		}
	   	}
	   	String[] result = new String[columnHeaders.size()];
	   	return (String[])columnHeaders.toArray(result);
   }
   
  /**
   *  createVisualizationData creates a table of the reliability visualization data
   *  
   *  @return returns a table of the reliability visualization Data 
   */
   @Override
   protected String[][] createVisualizationData() {
	   
	   if (visualizationData == null) {
       int numRows = program.getSchedule().size();
       int numColumns = createColumnHeader().length;
       visualizationData = new String[numRows][numColumns];
       ReliabilityTable reliabilityTable = ra.getReliabilities();
       for (int row = 0; row < numRows; row++) {
         for (int column = 0; column < numColumns; column++) {
        	 visualizationData[row][column] = "0";
        	 //visualizationData[row][column] = reliabilityTable.get(row).get(column).toString();
        	 //This will grab the data from our reliability analysis but it is not implemented yet
        	 //Will just create a 2D array of zeros with the correct number of rows and columns
         }
       	}
       }
     return visualizationData;
   }
   
   /**
    * createTitle creates a title for the output file stating which graph is being analyzed.  
    *
    * @return returns the title for the graph's reliability that will be analyzed
    */
   protected String createTitle() {
	    return String.format("Reliability Analysis for graph %s\n", program.getName());
	  }
   
   }
