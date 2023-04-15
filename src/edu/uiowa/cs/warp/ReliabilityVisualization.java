package edu.uiowa.cs.warp;


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
   
   /*package*/ 
   ReliabilityVisualization (WarpInterface warp) {
      super(new FileManager(), warp, SOURCE_SUFFIX);
      this.warp = warp;
      this.program = warp.toProgram();
      this.ra = warp.toReliabilityAnalysis();
   }
   
   @Override
   public GuiVisualization displayVisualization() {
     //return new GuiVisualization(createTitle(), (String[])createHeader().toArray(), createVisualizationData());
	   // TODO implement this operation
	   throw new UnsupportedOperationException("not implemented");
   }
   
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
   
   @Override
   protected String[][] createVisualizationData() {
	   /*
	   if (visualizationData == null) {
       int numRows = sourceCode.getNumRows();
       int numRows = 
       int numColumns = sourceCode.getNumColumns();
       visualizationData = new String[numRows][numColumns + 1];

       for (int row = 0; row < numRows; row++) {
         visualizationData[row][0] = String.format("%s", row);
         for (int column = 0; column < numColumns; column++) {
           visualizationData[row][column + 1] = sourceCode.get(row, column);
         }
       	}
       }
     return visualizationData;
     */
	// TODO implement this operation
	   throw new UnsupportedOperationException("not implemented");
   }
   
   private String createTitle() {
	    return String.format("WARP program for graph %s\n", program.getName());
	  }
   
   }
