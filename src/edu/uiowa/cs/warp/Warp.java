/**
 * WARP: On-the-fly Program Synthesis for Agile, Real-time, and Reliable Wireless Networks. This
 * system generates node communication programs WARP uses programs to specify a network’s behavior
 * and includes a synthesis procedure to automatically generate such programs from a high-level
 * specification of the system’s workload and topology. WARP has three unique features: <br>
 * (1) WARP uses a domain-specific language to specify stateful programs that include conditional
 * statements to control when a flow’s packets are transmitted. The execution paths of programs
 * depend on the pattern of packet losses observed at run-time, thereby enabling WARP to readily
 * adapt to packet losses due to short-term variations in link quality. <br>
 * (2) Our synthesis technique uses heuristics to improve network performance by considering
 * multiple packet loss patterns and associated execution paths when determining the transmissions
 * performed by nodes. Furthermore, the generated programs ensure that the likelihood of a flow
 * delivering its packets by its deadline exceeds a user-specified threshold. <br>
 * (3) WARP can adapt to workload and topology changes without explicitly reconstructing a network’s
 * program based on the observation that nodes can independently synthesize the same program when
 * they share the same workload and topology information. Simulations show that WARP improves
 * network throughput for data collection, dissemination, and mixed workloads on two realistic
 * topologies. Testbed experiments show that WARP reduces the time to add new flows by 5 times over
 * a state-of-the-art centralized control plane and guarantees the real-time and reliability of all
 * flows.
 */

package edu.uiowa.cs.warp;

import argparser.ArgParser;
import argparser.BooleanHolder;
import argparser.DoubleHolder;
import argparser.IntHolder;
import argparser.StringHolder;
import edu.uiowa.cs.warp.SystemAttributes.ScheduleChoices;
import edu.uiowa.cs.warp.Visualization.SystemChoices;
import edu.uiowa.cs.warp.Visualization.WorkLoadChoices;

/**
 * The Warp class is the main driver for the WARP project. It instantiates the work load and the parsing of 
 * command line flags for debugging info and configuring work load visualization.
 * 
 * @author sgoddard
 * @version 1.5
 */
public class Warp {
	/**
	 * NUM_CHANNELS specifies Integer (16) that represents default number of wireless channels that are 
	 * available for scheduling. Option for command line.
	 */
  private static final Integer NUM_CHANNELS = 16;
   	/**
     * Double MIN_LQ specifies the default for the minimum Link Quality in the system. Option for command line.                           
     */
  private static final Double MIN_LQ = 0.9; 
  /**
   * Double E2E specifies the default end-to-end reliability value for all flows. Option for command line.
   */
  private static final Double E2E = 0.99; 
  /**
   * String DEFAULT_OUTPUT_SUB_DIRECTORY specifies the default output file location 
   */
  private static final String DEFAULT_OUTPUT_SUB_DIRECTORY = "OutputFiles/";
  /**
   * DEFAULT_SCHEDULER specifies the default scheduler from the priority option in ScheduleChoices. 
   */
  private static final ScheduleChoices DEFAULT_SCHEDULER = ScheduleChoices.PRIORITY;
  /**
   * Specifies the default number of faults that can be tolerated per transmission. Option for command line.
   */
  private static final Integer DEFAULT_FAULTS_TOLERATED = 0;
  /**
   * Integer nChannels specifies the number of wireless channels that are available for scheduling 
   */
  private static Integer nChannels;
  /**
   * Integer numFaults is the number of faults that are tolerated per each edge.
   */
  private static Integer numFaults;
  /**
   * Double minLQ is the global variable for the minimum Link Quality value in the system. 
   * Addition of a local minLQ can be done for each link
   */
  private static Double minLQ; 
  /**
   * Double e2e is the global variable for the minimum link quality in the system.
   * Addition of a local minLQ can be added for each link. 
   */
  private static Double e2e; 
  /**
   * String outputSubSirectory is the default output sub-directory that is from the working directory. 
   * This is where output files will be placed. (e.g. gy, wf, ra)
   */
  private static String outputSubDirectory;
  /**
   * guiRequested flag controls the GUI visualization selection
   */
  private static Boolean guiRequested; 
  /**
   * gvRequested flag controls the GraphVis file creation
   */
  private static Boolean gvRequested;
  /**
   * wfRequested flag controls WARP file creation
   */
  private static Boolean wfRequested; 
  /**
   * raRequested flag controls the creation of the Reliability Analysis file
   */
  private static Boolean raRequested;
  /**
   * laRequested flag controls the creation of the Latency Analysis file
   */
  private static Boolean laRequested; 
  /**
   * caRequested flag controls the creation of the Channel Analysis file
   */
  private static Boolean caRequested;
  /**
   * simRequested flag controls the creation of the Simulation file
   */
  private static Boolean simRequested; 
  /**
   * allRequested flag controls the request for all out files
   */
  private static Boolean allRequested;
  /**
   * latencyRequested flag controls the creation of a latency report
   */
  private static Boolean latencyRequested; 
  /**
   * schedulerRequested flag controls the creation of the scheduler, default is set to false
   */
  private static Boolean schedulerRequested = false;
  /**
   * verboseMode flag controls the verbose information for debugging and is mainly
   * used for running in IDE
   */
  private static Boolean verboseMode;
  /**
   * inputFile is the String from which the workload graph is read from
   */
  private static String inputFile;
  /**
   * schedulerSelected requests and specifies the schedule that is selected
   */
  private static ScheduleChoices schedulerSelected;

/**
 * The main method of class instantiates a new WorkLoad and visualizes it based on
 * the arguments that are specified in the command line.
 */
  public static void main(String[] args) {
    // parse command-line options and set WARP system parameters
    setWarpParameters(args);

    // and print out the values if in verbose mode
    if (verboseMode) {
      printWarpParameters();
    }

    // Create and visualize the workload
    // inputFile string, which may be null,
    WorkLoad workLoad = new WorkLoad(numFaults, minLQ, e2e, inputFile);
    if (allRequested) {
      for (WorkLoadChoices choice : WorkLoadChoices.values()) {
        visualize(workLoad, choice); // visualize all Program choices
      }
      // Create and visualize the Warp System
      if (schedulerRequested) {
        WarpInterface warp = SystemFactory.create(workLoad, nChannels, schedulerSelected);
        verifyPerformanceRequirements(warp);
        for (SystemChoices choice : SystemChoices.values()) {
          visualize(warp, choice); // visualize all System choices
        }
      } else { // create a system for all scheduler choices
        for (ScheduleChoices sch : ScheduleChoices.values()) {
          schedulerSelected = sch;
          WarpInterface warp = SystemFactory.create(workLoad, nChannels, schedulerSelected);
          verifyPerformanceRequirements(warp);
          for (SystemChoices choice : SystemChoices.values()) {
            visualize(warp, choice); // visualize all System choices
          }
        }
      }
    } else { // visualize warp workload, source program and other requested items
      visualize(workLoad, WorkLoadChoices.INPUT_GRAPH);
      if (wfRequested) {
        visualize(workLoad, WorkLoadChoices.COMUNICATION_GRAPH);
      }
      if (gvRequested) {
        visualize(workLoad, WorkLoadChoices.GRAPHVIZ);
      }
      WarpInterface warp = SystemFactory.create(workLoad, nChannels, schedulerSelected);
      verifyPerformanceRequirements(warp);
      visualize(warp, SystemChoices.SOURCE);
      if (caRequested) {
        visualize(warp, SystemChoices.CHANNEL);
      }
      if (laRequested) {
        visualize(warp, SystemChoices.LATENCY);
      }
      if (latencyRequested || laRequested) {
        visualize(warp, SystemChoices.LATENCY_REPORT);
      }
      if (raRequested) {
        visualize(warp, SystemChoices.RELIABILITIES);
      }
    }

  }
  /**
   * The visualize method will display the visualization of provided workLoad
   * based on the guiRequested flag and the verboseMode flag.
   *  
   * @param workLoad is the WorkLoad that is to be visualized 
   * @param choice is the selected type of WorkLoad graph that will be visualized
   */
  private static void visualize(WorkLoad workLoad, WorkLoadChoices choice) {
    var viz =
        VisualizationFactory.createWorkLoadVisualization(workLoad, outputSubDirectory, choice);
    if (viz != null) {
      if (verboseMode) {
        System.out.println(viz.toString());
      }
      viz.toFile();
      if (guiRequested) {
        viz.toDisplay();
      }
    }
  }
  /**
   * The visualize method displays the visualization of the supplied Warp Interface
   * 
   * @param warp is the warp to be visualized
   * @param choice is the type of WorkLoad graph that will be visualized
   */
  private static void visualize(WarpInterface warp, SystemChoices choice) {
    var viz = VisualizationFactory.createProgramVisualization(warp, outputSubDirectory, choice);
    if (viz != null) {
      viz.toFile();
      if (guiRequested && schedulerRequested) {
        /* Only display window when a specific scheduler has been requested */
        viz.toDisplay();
      }
    }
  }
  /**
   * the verifyPerformanceRequirements method verifies deadlines, reliabilities, and 
   * the absence of any channel conflicts
   * 
   * @param warp is the warp that will have its performance requirements verified
   */
  private static void verifyPerformanceRequirements(WarpInterface warp) {
    verifyDeadlines(warp);
    verifyReliabilities(warp);
    verifyNoChannelConflicts(warp);
  }
  
  /**
   * verifyReliabilities method verifies the reliabilities of the supplied warp interface.
   * it will print an error message if not all flows meet end-to-end reliability.
   * 
   * @param warp is the warp that will have its reliabilities verified
   */
  private static void verifyReliabilities(WarpInterface warp) {
    if (schedulerSelected != ScheduleChoices.RTHART) {
      /* RealTime HART doesn't adhere to reliability targets */
      if (!warp.reliabilitiesMet()) {
        System.err.printf(
            "\n\tERROR: Not all flows meet the end-to-end "
                + "reliability of %s under %s scheduling.\n",
            String.valueOf(e2e), schedulerSelected.toString());
      } else if (verboseMode) {
        System.out.printf(
            "\n\tAll flows meet the end-to-end reliability " + "of %s under %s scheduling.\n",
            String.valueOf(e2e), schedulerSelected.toString());
      }
    }
  }
  
  /**
   * The verifyDeadlines method verifies that the warp flows meet their deadlines
   * under scheduling.
   * 
   * @param warp is the warp whose flows deadlines will be verified
   */
  private static void verifyDeadlines(WarpInterface warp) {
    if (!warp.deadlinesMet()) {
      System.err.printf("\n\tERROR: Not all flows meet their deadlines under %s scheduling.\n",
          schedulerSelected.toString());
      visualize(warp, SystemChoices.DEADLINE_REPORT);
    } else if (verboseMode) {
      System.out.printf("\n\tAll flows meet their deadlines under %s scheduling.\n",
          schedulerSelected.toString());
    }
  }
  
  /**
   * The verifyNoChannelConflicts verifies that there are no channel conflicts
   * that exist for the given warp. It will print an error message if any conflicts are found.
   * 
   * @param warp is the warp that will be checked to verify for no channel conflicts
   */
  private static void verifyNoChannelConflicts(WarpInterface warp) {
    if (warp.toChannelAnalysis().isChannelConflict()) {
      System.err
          .printf("\n\tERROR: Channel conficts exists. See Channel Visualization for details.\n");
      if (!caRequested) { // only need to create the visualization if not already requested
        visualize(warp, SystemChoices.CHANNEL);
      }
    } else if (verboseMode) {
      System.out.printf("\n\tNo channel conflicts detected.\n");
    }
  }

  /**
   * the setWarpParameters method sets the parameters of the warp based on the command
   * line arguments.
   * 
   * @param args is the command line arguments given by the user
   */
  private static void setWarpParameters(String[] args) { // move command line parsing into this
                                                         // function--need to set up globals?

    // create holder objects for storing results ...
    // BooleanHolder debug = new BooleanHolder();
    StringHolder schedulerSelected = new StringHolder();
    IntHolder channels = new IntHolder();
    IntHolder faults = new IntHolder();
    DoubleHolder m = new DoubleHolder();
    DoubleHolder end2end = new DoubleHolder();
    BooleanHolder gui = new BooleanHolder();
    BooleanHolder gv = new BooleanHolder();
    BooleanHolder wf = new BooleanHolder();
    BooleanHolder ra = new BooleanHolder();
    BooleanHolder la = new BooleanHolder();
    BooleanHolder ca = new BooleanHolder();
    BooleanHolder s = new BooleanHolder();
    BooleanHolder all = new BooleanHolder();
    BooleanHolder latency = new BooleanHolder();
    BooleanHolder verbose = new BooleanHolder();
    StringHolder input = new StringHolder();
    StringHolder output = new StringHolder();

    // create the parser and specify the allowed options ...
    ArgParser parser = new ArgParser("java -jar warp.jar");
    parser.addOption("-sch, --schedule %s {priority,rm,dm,rtHart,poset} #scheduler options",
        schedulerSelected);
    parser.addOption("-c, --channels %d {[1,16]} #number of wireless channels", channels);
    parser.addOption("-m %f {[0.5,1.0]} #minimum link quality in the system", m);
    parser.addOption(
        "-e, --e2e %f {[0.5,1.0]} #global end-to-end communcation reliability for all flows",
        end2end);
    parser.addOption("-f, --faults %d {[1,10]} #number of faults per edge in a flow (per period)",
        faults);
    parser.addOption("-gui %v #create a gui visualizations", gui);
    parser.addOption("-gv %v #create a graph visualization (.gv) file for GraphViz", gv);
    parser.addOption(
        "-wf  %v #create a WARP (.wf) file that shows the maximum number of transmissions on each segment of the flow needed to meet the end-to-end reliability",
        wf);
    parser.addOption(
        "-ra  %v #create a reliability analysis file (tab delimited .csv) for the warp program",
        ra);
    parser.addOption(
        "-la  %v #create a latency analysis file (tab delimited .csv) for the warp program", la);
    parser.addOption(
        "-ca  %v #create a channel analysis file (tab delimited .csv) for the warp program", ca);
    parser.addOption("-s  %v #create a simulator input file (.txt) for the warp program", s);
    parser.addOption("-a, --all  %v #create all output files (activates -gv, -wf, -ra, -s)", all);
    parser.addOption("-l, --latency  %v #generates end-to-end latency report file (.txt)", latency);
    parser.addOption("-i, --input %s #<InputFile> of graph flows (workload)", input);
    parser.addOption("-o, --output %s #<OutputDIRECTORY> where output files will be placed",
        output);
    parser.addOption(
        "-v, --verbose %v #Echo input file name and parsed contents. Then for each flow instance: show maximum E2E latency and min/max communication cost for that instance of the flow",
        verbose);
    // parser.addOption ("-d, -debug, --debug %v #Debug mode: base directory =
    // $HOME/Documents/WARP/", debug);


    // match the arguments ...
    parser.matchAllArgs(args);

    // Set WARP system configuration options
    if (channels.value > 0) {
      nChannels = channels.value; // set option specified
    } else {
      nChannels = NUM_CHANNELS; // set to default
    }
    if (faults.value > 0) { // global variable for # of Faults tolerated per edge
      numFaults = faults.value; // set option specified
    } else {
      numFaults = DEFAULT_FAULTS_TOLERATED; // set to default
    }
    if (m.value > 0.0) { // global variable for minimum Link Quality in system
      minLQ = m.value; // set option specified
    } else {
      minLQ = MIN_LQ; // set to default
    }
    if (end2end.value > 0.0) { // global variable for minimum Link Quality in system
      e2e = end2end.value; // set option specified
    } else {
      e2e = E2E; // set to default
    }
    if (output.value != null) { // default output subdirectory (from working directory)
      outputSubDirectory = output.value; // set option specified
    } else {
      outputSubDirectory = DEFAULT_OUTPUT_SUB_DIRECTORY; // set to default
    }

    guiRequested = gui.value; // GraphVis file requested flag
    gvRequested = gv.value; // GraphVis file requested flag
    wfRequested = wf.value; // WARP file requested flag
    raRequested = ra.value; // Reliability Analysis file requested flag
    laRequested = la.value; // Latency Analysis file requested flag
    caRequested = ca.value; // Latency Analysis file requested flag
    simRequested = s.value; // Simulation file requested flag
    allRequested = all.value; // all out files requested flag
    latencyRequested = latency.value; // latency report requested flag
    verboseMode = verbose.value; // verbose mode flag (mainly for running in IDE)
    // debugMode = debug.value; // debug mode flag (mainly for running in IDE)
    inputFile = input.value; // input file specified
    if (schedulerSelected.value != null) { // can't switch on a null value so check then switch
      schedulerRequested = true;
      switch (schedulerSelected.value) {
        case "priority":
          Warp.schedulerSelected = ScheduleChoices.PRIORITY;
          break;

        case "rm":
          Warp.schedulerSelected = ScheduleChoices.RM;
          break;

        case "dm":
          Warp.schedulerSelected = ScheduleChoices.DM;
          break;

        case "rtHart":
          Warp.schedulerSelected = ScheduleChoices.RTHART;
          break;

        case "poset":
          Warp.schedulerSelected = ScheduleChoices.POSET_PRIORITY;
          break;

        default:
          Warp.schedulerSelected = ScheduleChoices.PRIORITY;
          break;
      }
    } else { // null value when no scheduler specified; so use default
      Warp.schedulerSelected = DEFAULT_SCHEDULER;
    }
  }
  
  /**
   * The printWarpParameters prints all of the system configuration values (all
   * command line warp configuration flags.)
   */
  private static void printWarpParameters() { // print all system configuration parameters
    // Print out each of the system configuration values
    System.out.println("WARP system configuration values:");
    System.out.println("\tScheduler=" + schedulerSelected);
    System.out.println("\tnChanels=" + nChannels);
    System.out.println("\tnumFaults=" + numFaults);
    System.out.println("\tminLQ=" + minLQ);
    System.out.println("\tE2E=" + e2e);
    System.out.println("\tguiRequest flag=" + guiRequested);
    System.out.println("\tgvRequest flag=" + gvRequested);
    System.out.println("\twfRequest flag=" + wfRequested);
    System.out.println("\traRequest flag=" + raRequested);
    System.out.println("\tlaRequest flag=" + laRequested);
    System.out.println("\tcaRequest flag=" + caRequested);
    System.out.println("\tsimRequest flag=" + simRequested);
    System.out.println("\tallOutFilesRequest flag=" + allRequested);
    System.out.println("\tlatency flag=" + latencyRequested);
    if (inputFile != null) {
      System.out.println("\tinput file=" + inputFile);
    } else {
      System.out.println("\tNo input file specified; will be requested when needed.");
    }
    System.out.println("\toutputSubDirectory=" + outputSubDirectory);
    System.out.println("\tverbose flag=" + verboseMode);
    // System.out.println ("\tdebug flag=" + debugMode);
  }

}
