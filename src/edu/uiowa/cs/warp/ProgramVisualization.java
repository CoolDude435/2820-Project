/**
 * 
 */
package edu.uiowa.cs.warp;

/**
 * The ProgramVisualization class is the visual representation of 
 * a WARP program for a given graph.
 * 
 * @author sgoddard
 * @version 1.5
 * 
 */
public class ProgramVisualization extends VisualizationObject {
	/**
	 * SOURCE_SUFFIX is the file extension of the source files for ProgramVisualization
	 */
  private static final String SOURCE_SUFFIX = ".dsl";
  /**
   * sourceCode is instructions for table mapping to time slots
   */
  private ProgramSchedule sourceCode;
  /**
   * program is the program that is to be visualized
   */
  private Program program;
  /**
   * deadlinesMet verifies if the deadline is met. Will return true if it is,
   * false if not.
   */
  private Boolean deadlinesMet;
  
  /**
   * ProgramVisualization will construct a program visualization of 
   * the warp program.
   * 
   * @param warp is Warp that will be visualized
   */
  ProgramVisualization(WarpInterface warp) {
    super(new FileManager(), warp, SOURCE_SUFFIX);
    this.program = warp.toProgram();
    this.sourceCode = program.getSchedule();
    this.deadlinesMet = warp.deadlinesMet();
  }
  
  /**
   * GuiVisualization displays a GUi of the warp visualization
   */
  @Override
  public GuiVisualization displayVisualization() {
    return new GuiVisualization(createTitle(), createColumnHeader(), createVisualizationData());
  }
  
  /**
   * createHeader creates a header for the ProgramVisualization.
   * The header includes number of faults, the minimum packet reception rate,
   * and the number of channels.
   * 
   * @return returns the header with the included information
   */
  @Override
  protected Description createHeader() {
    Description header = new Description();

    header.add(createTitle());
    header.add(String.format("Scheduler Name: %s\n", program.getSchedulerName()));

    /* The following parameters are output based on a special schedule or the fault model */
    if (program.getNumFaults() > 0) { // only specify when deterministic fault model is assumed
      header.add(String.format("numFaults: %d\n", program.getNumFaults()));
    }
    header.add(String.format("M: %s\n", String.valueOf(program.getMinPacketReceptionRate())));
    header.add(String.format("E2E: %s\n", String.valueOf(program.getE2e())));
    header.add(String.format("nChannels: %d\n", program.getNumChannels()));
    return header;
  }
  
  /**
   * createFooter creates a footer for ProgramVisualization.
   * It includes a message for flow deadlines. It returns a warning if they are not met
   * 
   * @return returns a footer with a deadline message that fits the case.
   */
  @Override
  protected Description createFooter() {
    Description footer = new Description();
    String deadlineMsg = null;

    if (deadlinesMet) {
      deadlineMsg = "All flows meet their deadlines\n";
    } else {
      deadlineMsg = "WARNING: NOT all flows meet their deadlines. See deadline analysis report.\n";
    }
    footer.add(String.format("// %s", deadlineMsg));
    return footer;
  }

  /**
   * createColumnHeader creates an array of column headers that include the 
   * column names that correspond to time slots.
   * 
   * @return an array with column headers for time slots
   */
  @Override
  protected String[] createColumnHeader() {
    var orderedNodes = program.toWorkLoad().getNodeNamesOrderedAlphabetically();
    String[] columnNames = new String[orderedNodes.length + 1];
    columnNames[0] = "Time Slot"; // add the Time Slot column header first
    /* loop through the node names, adding each to the header */
    for (int i = 0; i < orderedNodes.length; i++) {
      columnNames[i + 1] = orderedNodes[i];
    }
    return columnNames;
  }
  
  /**
   * createVisualizationData creates a matrix of the visualization data
   * 
   * @return returns a matrix of visualization data
   */
  @Override
  protected String[][] createVisualizationData() {
    if (visualizationData == null) {
      int numRows = sourceCode.getNumRows();
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
  }

  private String createTitle() {
    return String.format("WARP program for graph %s\n", program.getName());
  }
}
