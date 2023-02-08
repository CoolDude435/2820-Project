/**
 * 
 */
package edu.uiowa.cs.warp;
import java.util.Collections;

/**
 * Reads the input file, whose name is passed as input parameter to the constructor, and builds a
 * Description object based on the contents. Each line of the file is an entry (string) in the
 * Description object.
 * 
 * @author sgoddard
 * @version 1.4 Fall 2022
 */
public class WorkLoadDescription extends VisualizationObject {

  private static final String EMPTY = "";
  private static final String INPUT_FILE_SUFFIX = ".wld";

  private Description description;
  private String inputGraphString;
  private FileManager fm;
  private String inputFileName;

  WorkLoadDescription(String inputFileName) {
    super(new FileManager(), EMPTY, INPUT_FILE_SUFFIX); // VisualizationObject constructor
    this.fm = this.getFileManager();
    initialize(inputFileName);
  }

  @Override
  public Description visualization() {
    return description;
  }

  @Override
  public Description fileVisualization() {
    return description;
  }

  // @Override
  // public Description displayVisualization() {
  // return description;
  // }

  @Override
  public String toString() {
    return inputGraphString;
  }

  public String getInputFileName() {
    return inputFileName;
  }

  private void initialize(String inputFile) {
    // Get the input graph file name and read its contents
    InputGraphFile gf = new InputGraphFile(fm);
    inputGraphString = gf.readGraphFile(inputFile);
    this.inputFileName = gf.getGraphFileName();
    description = new Description(inputGraphString);
  }
  
  public static void main(String args[]) {
      //instantiates the WorkLoadDescription
      WorkLoadDescription script = new WorkLoadDescription("StressTest.txt");
      //gets ArrayList of flow names that does not include the first line
      Description flows = script.description;
      flows.remove(0);
      //use collections sort to list the flows alphabetically 
      Collections.sort(flows);
      //print filename and removes .txt from each flow line
      System.out.println(script.inputFileName.substring(0,script.inputFileName.length()-4));
      //prints each flow
      //this runs until names.size()-1 so that the bracket isn't printed
      for(int i =0; i< flows.size()-1; i++) {
          System.out.print("Flow " + (i+1) + ": "+ flows.get(i));
      }
  }
}
