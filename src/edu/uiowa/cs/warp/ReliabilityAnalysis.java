package edu.uiowa.cs.warp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

import edu.uiowa.cs.warp.WarpDSL;
import edu.uiowa.cs.warp.SystemAttributes.ScheduleChoices;
import edu.uiowa.cs.warp.WarpDSL.InstructionParameters;

/**
 * ReliabilityAnalysis analyzes the end-to-end reliability of messages transmitted in flows for the
 * WARP system.
 * <p>
 * 
 * Let M represent the Minimum Packet Reception Rate on an edge in a flow. The end-to-end
 * reliability for each flow, flow:src->sink, is computed iteratively as follows:<br>
 * (1)The flow:src node has an initial probability of 1.0 when it is released. All other initial
 * probabilities are 0.0. (That is, the reset of the nodes in the flow have an initial probability
 * value of 0.0.) <br>
 * (2) each src->sink pair probability is computed as NewSinkNodeState = (1-M)*PrevSnkNodeState +
 * M*PrevSrcNodeState <br>
 * This value represents the probability that the message as been received by the node SinkNode.
 * Thus, the NewSinkNodeState probability will increase each time a push or pull is executed with
 * SinkNode as a listener.
 * <p>
 * 
 * The last probability state value for any node is the reliability of the message reaching that
 * node, and the end-to-end reliability of a flow is the value of the last Flow:SinkNode
 * probability.
 * <p>
 * 
 * CS2820 Spring 2023 Project: Implement this class to compute the probabilities the comprise the
 * ReliablityMatrix, which is the core of the file visualization that is requested in Warp.
 * <p>
 * 
 * To do this, you will need to retrieve the program source, parse the instructions for each node,
 * in each time slot, to extract the src and snk nodes in the instruction and then apply the message
 * success probability equation defined above.
 * <p>
 * 
 * I recommend using the getInstructionParameters method of the WarpDSL class to extract the src and
 * snk nodes from the instruction string in a program schedule time slot.
 * 
 * @author sgoddard
 * @version 1.5
 *  *
 */


public class ReliabilityAnalysis {

	private boolean onlyNumFaultsConstructor;
	private int numFaults = 0;
	private double e2e = 0.99;
	private double minPacketReceptionRate = 0.9;
	public ReliabilityTable reliabilityTable;
	//suppose to be private is public for testing purposes
	private Program program;
	private ArrayList<String> reliabilityHeaderRow;
	private NodeMap nodeMap;
	private WarpDSL warpDSL = new WarpDSL();
	/**
	 * ReliabilityAnalysis is a constructor to set up the Reliability analysis from a program. 
	 * 
	 * @param program the program that will be used during analysis 
	 */
	public ReliabilityAnalysis (Program program) {
		this.numFaults = program.getNumFaults();
		this.e2e = program.getE2e();
		this.minPacketReceptionRate = program.getMinPacketReceptionRate();
		this.program = program;
        }
	
	/**
	 * ReliabilityAnalysis is a constructor to set up the Reliability analysis from the minimum
	 * packet reception rate. 
	 * 
	 * @param e2e minimum link quality in the system thats being analyzed 
	 * @param minPacketReceptionRate the minimum packet reception rate thats being analyzed
	 */
	public ReliabilityAnalysis (Double e2e, Double minPacketReceptionRate) {
		this.e2e = e2e;
		this.minPacketReceptionRate = minPacketReceptionRate;
		onlyNumFaultsConstructor = false;
   }
   
	/**
	 *ReliabilityAnalysis is a constructor to set up the Reliability analysis based on the number of 
	 *faults tolerated. 
	 * 
	 * @param numFaults the number of faults tolerated in the system. 
	 */
   public ReliabilityAnalysis (Integer numFaults) {
	   this.numFaults = numFaults;
	   onlyNumFaultsConstructor = true;
   }
   
   /**
    * verifyReliabilities checks to see if the end to end reliability is met
    * by the flows and outputs an error message if end to end reliability is not met
    * 
    * @return a boolean of true or false if end to end reliability is met
    */
 
   public boolean verifyReliabilities() {
	   ReliabilityRow finalRow = reliabilityTable.get(reliabilityTable.size()-1);
	   for (int i=0;i<finalRow.size();i++) {
		   if (finalRow.get(i)<e2e) {
			   System.out.println("Error: E2E is not met");
			   return false;
		   }
	   }
       return true;
     }
   
   /**
    * setHeaderRow updates the header row of the reliability table. 
    * 
    * @param takes an ArrayList of Strings that is requested to be set as the reliabilityHeaderRow
    */
   public void setReliabilityHeaderRow(ArrayList<String> reliabilityHeaderRow) {
	   //suppose to be private is public for testing purposes
	   this.reliabilityHeaderRow = reliabilityHeaderRow;
   }
   
   /**
    * getHeaderRow returns the header row of the reliability table.
    * 
    * @return an ArrayList of Strings which is the reliabilityHeaderRow
    */
   public ArrayList<String> getReliabilityHeaderRow() {
	   if (reliabilityHeaderRow == null) {
		   buildReliabilities();
	   }
	   return reliabilityHeaderRow;
	   }
   
   /**
    * getReliabilities returns the table containing reliability of the flows
    * and calls buildReliabilities() if the table is not built yet
    * 
    * @return reliabilityTable containing the reliability of the nodes in each flow
    */
   public ReliabilityTable getReliabilities() {
	   if (reliabilityTable == null) {
		   buildReliabilities();
	   }
	   return reliabilityTable;
   }
   
   /**
    * buildReliabilities creates the table containing the reliability of the flows. 
    */
   public void buildReliabilities() {
	   //suppose to be private is public for testing purposes
	   if (program == null) {
		   System.out.println("Error: lack of a program, aborted");
		   return;
	   }
	   //setting headerRow and populating NodeMap
	   ArrayList<String> headerRow = new ArrayList<String>();
	   NodeMap nodeMap = new NodeMap();
	   //default schedule type of priority
	   ArrayList<String> flowNames = program.toWorkLoad().getFlowNamesInPriorityOrder();
	   if (program.getSchedulerName().equals("DeadlineMonotonic")) {
		   program.toWorkLoad().setFlowsInDMorder();
		   flowNames = program.toWorkLoad().getFlowNamesInPriorityOrder();
	   } else if (program.getSchedulerName().equals("RateMonotonic")) {
		   program.toWorkLoad().setFlowsInRMorder();
		   flowNames = program.toWorkLoad().getFlowNamesInPriorityOrder(); 
	   }
	   for (int i=0;i<flowNames.size();i++) {
		   FlowMap flowMap = program.toWorkLoad().getFlows();
		   Flow flow = flowMap.get(flowNames.get(i));
		   ArrayList<Node> nodesInFlow = flow.getNodes();
		   Integer flowPhase = program.toWorkLoad().getFlowPhase(flowNames.get(i));
		   Integer flowPeriod = program.toWorkLoad().getFlowPeriod(flowNames.get(i));
		   for (int k=0;k<nodesInFlow.size();k++) {
			   boolean src = false;
			   if (k==0) {
				   src = true;
			   }
			   String header = flowNames.get(i) + ":" + nodesInFlow.get(k);
			   headerRow.add(header);
			   ReliabilityNode reliNode = new ReliabilityNode(headerRow.size()-1,src,flowPhase, flowPeriod);
			   nodeMap.put(header, reliNode);  
	   		}
	   	}
	   	this.nodeMap = nodeMap;
	   	setReliabilityHeaderRow(headerRow);
	   	reliabilityTable = new ReliabilityTable(program.getSchedule().size(),headerRow.size());
	   	setInitialStateForReleasedFlows();
	   	
	   	ProgramSchedule schedule = program.getSchedule();
	   	for (int timeSlot=0;timeSlot<schedule.getNumRows();timeSlot++) {
	   		for (int k=0;k<schedule.getNumColumns();k++) {
	   			ArrayList<InstructionParameters> instructionList = warpDSL.getInstructionParameters(schedule.get(timeSlot, k));
	   			for (int j=0;j<instructionList.size();j++) {
	   				InstructionParameters parameters = instructionList.get(j);
	   				//calculating the correct reliability of a node
	   				if (parameters.getSnk().equals("unused")==false) {
	   					double prevSnkNodeState = 0.0;
	   					double prevSrcNodeState = 0.0;
	   					double newSinkNodeState = 0.0;
	   					boolean isFirstTimeSlot = timeSlot==0;
	   					Integer flowPeriod = program.toWorkLoad().getFlowPeriod(parameters.getFlow());
	   					Integer flowPhase = program.toWorkLoad().getFlowPhase(parameters.getFlow());
	   					boolean isNewPeriod = ((timeSlot%flowPeriod) == flowPhase);
	   					if ((isFirstTimeSlot==true) || (isNewPeriod==true)) {
	   						prevSnkNodeState = 0.0;
	   						prevSrcNodeState = 1.0;
	   						newSinkNodeState = (1-minPacketReceptionRate)*prevSnkNodeState + 
	   											minPacketReceptionRate*prevSrcNodeState;
	   					} else {
	   						String prevSnkNode = parameters.getFlow() + ":" + parameters.getSnk();
	   						String prevSrcNode = parameters.getFlow() + ":" + parameters.getSrc();
	   						prevSnkNodeState = reliabilityTable.get(timeSlot-1, nodeMap.get(prevSnkNode).getIndex());
	   						prevSrcNodeState = reliabilityTable.get(timeSlot-1, nodeMap.get(prevSrcNode).getIndex());
	   						newSinkNodeState = (1-minPacketReceptionRate)*prevSnkNodeState + 
												minPacketReceptionRate*prevSrcNodeState;
	   					}
	   					String snkNode = parameters.getFlow() + ":" + parameters.getSnk(); 
	   					Integer snkNodeIndex = nodeMap.get(snkNode).getIndex();
	   					reliabilityTable.set(timeSlot, snkNodeIndex, newSinkNodeState);
	   					
	   				}
	   				
	   			}
	   			carryForwardReliabilities(timeSlot);
	   		}
	   	}
	   	carryForwardReliabilities(reliabilityTable.getNumRows()-1);
   }

   /**
    * carryForwardReliabilities moves up all reliabilities of the nodes from the last timeSlot
    * if they were not affected by the schedule or it isn't the start of a new period for the flow
    * of the node. Also doesn't change anything if it is the first timeSlot
    * 
    * @param timeSlot the timeSlot that reliabilities are requested to be carried forward to
    */
   public void carryForwardReliabilities(Integer timeSlot) {
	   	//suppose to be private is public for testing purposes
	   	if (reliabilityTable == null) {
	   		buildReliabilities();
	   	}
		if (timeSlot<reliabilityTable.getNumRows()) {
			for (int i=0;i<reliabilityTable.getNumColumns();i++) {
				ReliabilityNode reliNode = nodeMap.get(reliabilityHeaderRow.get(i));
				if ((timeSlot%reliNode.getFlowPeriod()) != reliNode.getFlowPhase()) {
					if (timeSlot != 0) {
						double max = Math.max(reliabilityTable.get(timeSlot-1).get(i), reliabilityTable.get(timeSlot).get(i));
						reliabilityTable.set(timeSlot, i, max);
					}
				
				} 
			}
		}
   }
   
   /**
    * setInititalStateForReleasedFlows iterates through the entries in the nodeMap and for any
    * ReliabilityNode entry that is a srcNode set their index in the reliabilityTable to 1.0
    * for all timeSlots reflecting that starting probability of the flow which is 1.0.
    * called in buildReliabilities as a helper function 
    */
   public void setInitialStateForReleasedFlows() {
	   //suppose to be private is public for testing purposes
	   if (nodeMap ==  null) {
		   buildReliabilities();
	   }
	   for (String node : nodeMap.keySet()) {
		   ReliabilityNode reliNode = nodeMap.get(node);
		   if (reliNode.isFlowSrc() == true) {
			   for (int i=0;i<reliabilityTable.getNumRows();i++) {
				   reliabilityTable.set(i,reliNode.getIndex(), 1.0);
			   }
		   }
	   }
   }

   
   /**
    * numTxPerLinkAndTotalTxCost creates an ArrayList with numbers that correspond to the nodes in the flow
    * that hold the cost of transmissions then computes the max transmissions and adds it to the array list
    * 
    * @param flow takes in a flow
    * 
    * @return ArrayList that has number of transmission attempts per link and total transmission cost
    */
   public ArrayList <Integer> numTxPerLinkAndTotalTxCost(Flow flow) {
	   ArrayList<Integer> returnArrayList = new ArrayList<Integer>();
	   if(onlyNumFaultsConstructor) {
    	  var nodesInFlow = flow.nodes;
    	    var nNodesInFlow = nodesInFlow.size();
    	    ArrayList<Integer> txArrayList = new ArrayList<Integer>();
    	    /*
    	     * Each node will have at most numFaults+1 transmissions. Because we don't know which nodes will
    	     * send the message over an edge, we give the cost to each node.
    	     */
    	    for (int i = 0; i < nNodesInFlow; i++) {
    	      txArrayList.add(numFaults + 1);
    	    }
    	    /*
    	     * now compute the maximum # of TX, assuming at most numFaults occur on an edge per period, and
    	     * each edge requires at least one successful TX.
    	     */
    	    var numEdgesInFlow = nNodesInFlow - 1;
    	    var maxFaultsInFlow = numEdgesInFlow * numFaults;
    	    txArrayList.add(numEdgesInFlow + maxFaultsInFlow);
    	    
    	    returnArrayList = txArrayList;
	   
	   }else {
		   
		   	ArrayList<Node> nodesInFlow = flow.nodes;
		    
		    // The last entry will contain the worst-case cost of transmitting E2E in isolation
		    int nNodesInFlow = nodesInFlow.size();
		    
		    // Array to track nPushes for each node in this flow (same as nTx per link)
		    ArrayList <Integer> nPushes = new ArrayList<Integer>(nNodesInFlow + 1);
		    for (int i=0;i<nNodesInFlow + 1;i++) {
		    	nPushes.add(0);
		    }
		    
		    int nHops = nNodesInFlow - 1;
		    // minLinkReliablityNeded is the minimum reliability needed per link in a flow to hit E2E
		    // reliability for the flow
		    
		    //use the max to handle rounding error when e2e == 1.0
		    Double minLinkReliablityNeded = Math.max(e2e, Math.pow(e2e, (1.0 / (double) nHops))); 
		    
		    // Now compute reliability of packet reaching each node in the given time slot
		    // Start with a 2-D reliability window that is a 2-D matrix of no size
		    // each row is a time slot, stating at time 0
		    // each column represents the reliability of the packet reaching that node at the
		    // current time slot (i.e., the row it is in)
		    // will add rows as we compute reliabilities until the final reliability is reached
		    // for all nodes.
		    ReliabilityTable reliabilityWindow = new ReliabilityTable();
		    ReliabilityRow newReliabilityRow = new ReliabilityRow();
		    for (int i = 0; i < nNodesInFlow; i++) {
		      newReliabilityRow.add(0.0); // create the the row initialized with 0.0 values
		    }
		    reliabilityWindow.add(newReliabilityRow); // now add row to the reliability window, Time 0
		    ReliabilityRow tempRow = reliabilityWindow.get(0);
		    
		    ReliabilityRow currentReliabilityRow = (ReliabilityRow)tempRow.clone();
		    
		    currentReliabilityRow.set(0, 1.0); // initialize (i.e., P(packet@FlowSrc) = 1
		    //the analysis will end when the 2e2 reliability matrix is met, 
		    //initially the state is not met and will be 0 with this statement
		    Double e2eReliabilityState = currentReliabilityRow.get(nNodesInFlow - 1);
		    
		    int timeSlot = 0; // start time at 0
		    
		    // change to while and increment increment timeSlot because
		    // we don't know how long this schedule window will last
		    while (e2eReliabilityState < e2e) {
		    	
		      ReliabilityRow prevReliabilityRow = currentReliabilityRow;
		      
		      //would be reliabilityWindow[timeSlot] if working through a schedule
		      
		      currentReliabilityRow = (ReliabilityRow)newReliabilityRow.clone();
		      
		      // Now use each flow:src->sink to update reliability computations
		      // this is the update formula for the state probabilities
		      // nextState = (1 - M) * prevState + M*NextHighestFlowState
		      // use MinLQ for M in above equation
		      // NewSinkNodeState = (1-M)*PrevSnkNodeState + M*PrevSrcNodeState

		      for (int nodeIndex = 0; nodeIndex < (nNodesInFlow - 1); nodeIndex++) { 
		    	  // loop through each node in the flow and update the sates for
		    	  // each link (i.e.,sink-> src pair)                                                                                                                                            
		        int flowSrcNodeindex = nodeIndex;
		        int flowSnkNodeindex = nodeIndex + 1;
		        double prevSrcNodeState = prevReliabilityRow.get(flowSrcNodeindex);
		        double prevSnkNodeState = prevReliabilityRow.get(flowSnkNodeindex);
		        Double nextSnkState;
		        
		        // do a push until PrevSnk state > e2e to ensure next node reaches target E2E BUT
		    	// skip if no chance of success (i.e., source doesn't have packet)
		        if ((prevSnkNodeState < minLinkReliablityNeded) && prevSrcNodeState > 0) { 
		          //need to continue attempting to Tx, so update current state
		          nextSnkState = ((1.0 - minPacketReceptionRate) * prevSnkNodeState) + (minPacketReceptionRate * prevSrcNodeState); 
		          // increment the number of pushes for for this node to snk node
		          nPushes.set(nodeIndex, nPushes.get(nodeIndex) + 1);
		        } else {
		          // snkNode has met its reliability. 
		          //Thus move on to the next node and record the reliability met
		          nextSnkState = prevSnkNodeState; 
		        }
		        
		        //probabilities are non-decreasing so update if we were higher by carrying old value forward
		        if (currentReliabilityRow.get(flowSrcNodeindex) < prevReliabilityRow.get(flowSrcNodeindex)) {
		          //carry forward the previous state for the src node, which may get over written later 
		          //by another instruct in this slot.
		        	currentReliabilityRow.set(flowSrcNodeindex, prevReliabilityRow.get(flowSrcNodeindex));
		        }
		        currentReliabilityRow.set(flowSnkNodeindex, nextSnkState);
		      }

		      e2eReliabilityState = currentReliabilityRow.get(nNodesInFlow - 1); 
		      ReliabilityRow currentReliabilityVector = new ReliabilityRow();
		      //convert the row to a vector so we can add it to the reliability window
		      
		      currentReliabilityVector = (ReliabilityRow)currentReliabilityRow.clone() ;
		      if (timeSlot < reliabilityWindow.size()) {
		        reliabilityWindow.set(timeSlot, currentReliabilityVector);
		      } else {
		        reliabilityWindow.add(currentReliabilityVector);
		      }
		      timeSlot += 1; // increase to next time slot
		    }
		    int size = reliabilityWindow.size();
		    //The total (worst-case) cost to transmit E2E in isolation with specified 
		    //reliability target is the number of rows in the reliabilityWindow
		    nPushes.set(nNodesInFlow, size);
		    returnArrayList = (ArrayList<Integer>)nPushes.clone();
	   }
	   
	   return returnArrayList;
   }
      
   private class ReliabilityNode{
	   Integer index = 0;
	   boolean flowSrc = false;
	   Integer flowPhase = 0;
	   Integer flowPeriod = 0;
	   
	   /**
	    * constructor for ReliabilityNode that will contain the information of the index,
	    * flowSrc, flowPhase, and flowPeriod of a node
	    * 
	    * @param index an Integer index of the column where the node is located in the reliabilityTable
	    * @param flowSrc a boolean determining if the node is a srcNode for its flow
	    * @param flowPhase an Integer which indicates the flowPhase of the flow of the node
	    * @param flowPeriod and Integer which indicates the flowPeriod of the flow of the node
	    */
	   public ReliabilityNode(Integer index, boolean flowSrc, Integer flowPhase, Integer flowPeriod) {
		   this.index = index;
		   this.flowSrc = flowSrc;
		   this.flowPhase = flowPhase;
		   this.flowPeriod = flowPeriod;
	   }
	   
	   /**
	    * getIndex is a getter method for the index of the node
	    * 
	    * @return an Integer which is the index of the node
	    */
	   public Integer getIndex() {
		   return this.index;
	   }
	   
	   /**
	    * isFlowSrc is a getter method for the boolean which indicates if the node is a srcNode
	    * 
	    * @return a boolean which indicates if the node is a srcNode
	    */
	   public boolean isFlowSrc() {
		   return this.flowSrc;
	   }
	   
	    /**
	     * getFlowPhase is a getter method for the flowPhase of the flow of the node
	     * 
	     * @return an Integer which is the flowPhase of the flow the node is in
	     */
	   public Integer getFlowPhase() {
		   return this.flowPhase;
	   }
	   
	   /**
	    * getFlowPeriod is a getter method for the flowPeriod of the flow of the node
	    * 
	    * @return an Integer which is the flowPeriod of the flow the node is in
	    */
	   public Integer getFlowPeriod() {
		   return this.flowPeriod;
	   }
   }
   
   private class NodeMap extends HashMap<String,ReliabilityNode>{
	   public NodeMap() {
		   super();
	   }
   }
   
   }
   
   
   
   