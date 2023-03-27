package edu.uiowa.cs.warp;

import java.util.ArrayList;

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
	
	//tests for correct output (temporary)
	public static void main(String[] args) {
		ReliabilityAnalysis test = new ReliabilityAnalysis(0);
        
        WorkLoad load = new WorkLoad(.9,.99,"Example2.txt");
        
        FlowMap flows = load.getFlows();
        
        flows.entrySet().forEach(entry -> {
            Flow flow = entry.getValue();
            System.out.println("old: "+ load.getFixedTxPerLinkAndTotalTxCost(flow));
            System.out.println("new: "+ test.numTxPerLinkAndTotalTxCost(flow));
        });
	}
	
	private boolean onlyNumFaultsConstructor;
	private int numFaults = 0;
	double e2e = 0.99;
	double minPacketReceptionRate = 0.9;
	
	
	
	public ReliabilityAnalysis (Program program) {
      // TODO Auto-generated constructor stub
        }
	
	public ReliabilityAnalysis (Double e2e, Double minPacketReceptionRate) {
      onlyNumFaultsConstructor = false;
		// TODO implement this operation
      throw new UnsupportedOperationException("not implemented");
   }
   
   public ReliabilityAnalysis (Integer numFaults) {
	   this.numFaults = numFaults;
      onlyNumFaultsConstructor = true;
	   // TODO implement this operation
      //throw new UnsupportedOperationException("not implemented");
   }
   
   public Boolean verifyReliabilities() {
      // TODO Auto-generated method stub
       return true;
        }
   
   public ReliabilityTable getReliabilities() {
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
		   //...
	   }
	   
	   return returnArrayList;
   }
   
   }
