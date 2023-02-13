/**
 * 
 */
package edu.uiowa.cs.warp;

import java.io.File;

/**
 * The VisiualizationImplementation class implements the Visualization interface to display 
 * a window for the graph from the supplied file.
 * 
 * @author sgoddard
 * @version 1.5
 */
public class VisualizationImplementation implements Visualization {
/**
 * The visualization description specifies an array with strings to describe what is visualized
 */
  private Description visualization;
  /**
   * The fileContent Description specifies an array with strings that contain the file contents
   */
  private Description fileContent;
  /**
   * The GuiVisualization displays a window 
   */
  private GuiVisualization window;
  /**
   * String fileName specifies the name of the file 
   */
  private String fileName;
  /**
   * inputFileName specifies the name of the file that was inputed 
   */
  private String inputFileName;
  /**
   * fileNameTemplate specifies the template for naming files
   */
  private String fileNameTemplate;
  /**
   * fm specifies the FileManager and is set to null by default 
   */
  private FileManager fm = null;
  /**
   * warp specifies the WarpInterface and is set to null by default
   */
  private WarpInterface warp = null;
  /**
   * workLoad specifies the workLoad that will be used and is set to null by default
   */
  private WorkLoad workLoad = null;
  /**
   * visualizationObject specifies the object that will be visualized
   */
  private VisualizationObject visualizationObject;

/**
 * The VisualizationImplementation method implements the file and warp system that is to 
 * be visualized. 
 * 
 * @param warp is the warp that is to be visualized
 * @param outputDirectory is the directory from which the output is supplied from
 * @param choice is the chosen system information from SystemChoices that is to be visualized
 */
  public VisualizationImplementation(WarpInterface warp, String outputDirectory,
      SystemChoices choice) {
    this.fm = new FileManager();
    this.warp = warp;
    inputFileName = warp.toWorkload().getInputFileName();
    this.fileNameTemplate = createFileNameTemplate(outputDirectory);
    visualizationObject = null;
    createVisualization(choice);
  }
/**
 * The VisualizationImplementation method implements the workload and system that is 
 * to be visualized.
 * 
 * @param workLoad is the workLoad that is to be visualized
 * @param outputDirectory is the directory from which the output is supplied from
 * @param choice is the chosen WorkLoad from WorkLoadChoices that is to be visualized 
 */
  public VisualizationImplementation(WorkLoad workLoad, String outputDirectory,
      WorkLoadChoices choice) {
    this.fm = new FileManager();
    this.workLoad = workLoad;
    inputFileName = workLoad.getInputFileName();
    this.fileNameTemplate = createFileNameTemplate(outputDirectory);
    visualizationObject = null;
    createVisualization(choice);
  }
/**
 * The toDisplay method will display a window of the object
 */
  @Override
  public void toDisplay() {
    // System.out.println(displayContent.toString());
    window = visualizationObject.displayVisualization();
    if (window != null) {
      window.setVisible();
    }
  }

  /**
   * The toFile method uses the file manager to write the file name
   * and the contents of the file.
   */
  @Override
  public void toFile() {
    fm.writeFile(fileName, fileContent.toString());
  }

  /**
   * The toString method returns the string representation of the visualization
   */
  @Override
  public String toString() {
    return visualization.toString();
  }
/**
 * The createVisualization method creates a visualization of the chosen system that is requested.
 * The visualization includes source, reliabilities, simulator input, latency, channel, latency report,
 * and deadline report.
 * 
 * @param choice is chosen from the SystemChoices and is to be the selected requested visualization 
 */
  private void createVisualization(SystemChoices choice) {
    switch (choice) { // select the requested visualization
      case SOURCE:
        createVisualization(new ProgramVisualization(warp));
        break;

      case RELIABILITIES:
        // TODO Implement Reliability Analysis Visualization
        createVisualization(new ReliabilityVisualization(warp));
        break;

      case SIMULATOR_INPUT:
        // TODO Implement Simulator Input Visualization
        createVisualization(new NotImplentedVisualization("SimInputNotImplemented"));
        break;

      case LATENCY:
        // TODO Implement Latency Analysis Visualization
        createVisualization(new LatencyVisualization(warp));
        break;

      case CHANNEL:
        // TODO Implement Channel Analysis Visualization
        createVisualization(new ChannelVisualization(warp));
        break;

      case LATENCY_REPORT:
        createVisualization(new ReportVisualization(fm, warp,
            new LatencyAnalysis(warp).latencyReport(), "Latency"));
        break;

      case DEADLINE_REPORT:
        createVisualization(
            new ReportVisualization(fm, warp, warp.toProgram().deadlineMisses(), "DeadlineMisses"));
        break;

      default:
        createVisualization(new NotImplentedVisualization("UnexpectedChoice"));
        break;
    }
  }
/**
 * the createVisualization method will make a visualization of a selected WorkLoad that is requested. 
 * It includes the communication graph, graph visualization, and input of the graph.
 * 
 * @param choices the workload that is chosen from WorkLoadChoices
 */
  private void createVisualization(WorkLoadChoices choice) {
    switch (choice) { // select the requested visualization
      case COMUNICATION_GRAPH:
        // createWarpVisualization();
        createVisualization(new CommunicationGraph(fm, workLoad));
        break;

      case GRAPHVIZ:
        createVisualization(new GraphViz(fm, workLoad.toString()));
        break;

      case INPUT_GRAPH:
        createVisualization(workLoad);
        break;

      default:
        createVisualization(new NotImplentedVisualization("UnexpectedChoice"));
        break;
    }
  }
  /**
   * This private method createVisualization extends VisualizationObject displays the file content that is
   * printed in the console and the provided object.
   * 
   * @param <T> is the type of the object to be visualized and is a child class of VisualizationObject
   * @param obj is the object to be visualized
   */
  private <T extends VisualizationObject> void createVisualization(T obj) {
    visualization = obj.visualization();
    fileContent = obj.fileVisualization();
    /* display is file content printed to console */
    fileName = obj.createFile(fileNameTemplate); // in output directory
    visualizationObject = obj;
  }
/**
 * The createFileNameTemplate will return a string that consists the directory of the output file and
 * is organized in the order of the template that the method sets.
 * 
 * @param outputDirectory is the directory of the output file 
 * @return a string formed from the file name template based on the order set in the method
 */
  private String createFileNameTemplate(String outputDirectory) {
    String fileNameTemplate;
    var workingDirectory = fm.getBaseDirectory();
    var newDirectory = fm.createDirectory(workingDirectory, outputDirectory);
    // Now create the fileNameTemplate using full output path and input filename
    if (inputFileName.contains("/")) {
      var index = inputFileName.lastIndexOf("/") + 1;
      fileNameTemplate = newDirectory + File.separator + inputFileName.substring(index);
    } else {
      fileNameTemplate = newDirectory + File.separator + inputFileName;
    }
    return fileNameTemplate;
  }

}
