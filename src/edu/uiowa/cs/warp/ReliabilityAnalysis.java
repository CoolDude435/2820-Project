package edu.uiowa.cs.warp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

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
	private ReliabilityTable reliabilityTable;
	
	public ReliabilityAnalysis (Program program) {
      // TODO Auto-generated constructor stub
        }
	
	public ReliabilityAnalysis (Double e2e, Double minPacketReceptionRate) {
		this.e2e = e2e;
		this.minPacketReceptionRate = minPacketReceptionRate;
		onlyNumFaultsConstructor = false;
   }
   
   public ReliabilityAnalysis (Integer numFaults) {
	   this.numFaults = numFaults;
	   onlyNumFaultsConstructor = true;
   }
   
   public Boolean verifyReliabilities() {
      // TODO Auto-generated method stub
       return true;
        }
   
   public ReliabilityTable getReliabilities() {
      return reliabilityTable;
   }
   
   public void buildReliabilities() {
	// TODO implement this operation
	throw new UnsupportedOperationException("not implemented");
   }
   
   public void setHeaderRow() {
	// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
   }
   
   public void getHeaderRow() {
		// TODO implement this operation
			throw new UnsupportedOperationException("not implemented");
	   }
   
   public ReliabilityTable printRATable() {
	// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
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
      
   }
   
   
   
   
 
