package edu.uiowa.cs.warp;

import java.util.ArrayList;

/**
 * The Flow class is a constructor class that helps create the schedule for each node and edge in WARP.
 * Some of the default values in Flow can be adjusted to conform to different E2E reliability targets.
 * 
 * @author sgoddard
 */
public class Flow extends SchedulableObject implements Comparable<Flow>{
	/**
	 * Integer UNDEFINED is used to set the initialPriority value
	 */
	private static final Integer UNDEFINED = -1;
	/**
	 * Integer DEFAULT_FAULTS_TOLERATED sets the transmissions per link
	 *  and is determined by the fault model
	 */
	private static final Integer DEFAULT_FAULTS_TOLERATED = 0; 
	/**
	 * Integer DEFAULT_INDEX is set to zero to be an index variable
	 */
	private static final Integer DEFAULT_INDEX = 0;
	/**
	 * The following three variables are part of the Flow constructor definition
	 * that uses parameters to set name, priority, and index
	 * 
	 * Integer DEFAULT_PERIOD sets the period which is used in SchedulableObject
	 * to determine release times
	 */
	private static final Integer DEFAULT_PERIOD = 100; 
	/**
	 * Integer DEFAULT_DEADLINE sets the dealine which determines order of priority
	 */
	private static final Integer DEFAULT_DEADLINE = 100;
	/**
	 * INTEGER DEFAULT_PHASE determines the starting phase, set to zero
	 */
	private static final Integer DEFAULT_PHASE = 0;
	
	/**
	 * Integer initialPriority sets the starting priority to the value determine by UNDEFINED
	 */
    Integer initialPriority = UNDEFINED;
    /**
     * Integer index determines the order that the node was read from the Graph file
     */
    Integer index;
    /**
     * Integer numTxPerLink is the default number of transmissions per link and is determined
     * by the fault model
     */
    Integer numTxPerLink;
    /**
     * nodes is an ArrayList of nodes where the first element is flow src
     * and the last element is flow snk
     */
    ArrayList<Node> nodes;
    /*
     *  nTx needed for each link to reach E2E reliability target. Indexed by src node of the link. 
     *  Last entry is total worst-case E2E Tx cost for schedulability analysis
     */
    ArrayList<Integer> linkTxAndTotalCost; 
    /**
     * edges is an ArrayList that is used in Partition and scheduling
     */
    ArrayList<Edge> edges;
    Node nodePredecessor;
    Edge edgePredecessor;
    
    /*
     * Constructor that sets name, priority, and index
     */
    Flow (String name, Integer priority, Integer index){
    	super(name, priority, DEFAULT_PERIOD, DEFAULT_DEADLINE, DEFAULT_PHASE);
    	this.index = index;
        /*
         *  Default numTxPerLink is 1 transmission per link. Will be updated based
         *  on flow updated based on flow length and reliability parameters
         */
        this.numTxPerLink = DEFAULT_FAULTS_TOLERATED + 1; 
        this.nodes = new ArrayList<>();
        this.edges  = new ArrayList<>();
        this.linkTxAndTotalCost = new ArrayList<>();
        this.edges = new ArrayList<>();	
        this.nodePredecessor = null;
        this.edgePredecessor = null;
    }
    
    /*
     * Constructor
     */
    Flow () {
    	super();
    	this.index = DEFAULT_INDEX;
    	/*
    	 *  Default numTxPerLink is 1 transmission per link. Will be updated based
    	 *  on flow updated based on flow length and reliability parameters
    	 */
    	this.numTxPerLink = DEFAULT_FAULTS_TOLERATED + 1; 
    	this.nodes = new ArrayList<>();
    	this.linkTxAndTotalCost = new ArrayList<>();
    	this.edges = new ArrayList<>();
    	this.nodePredecessor = null;
        this.edgePredecessor = null;
    }

	/**
	 * getInitialPriority returns an Integer that is the value stored in
	 * initialPriority.
	 * 
	 * @return the initialPriority
	 */
	public Integer getInitialPriority() {
		return initialPriority;
	}

	/**
	 * getIndex returns an Integer of whatever the current index is.
	 * 
	 * @return the index
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * getNumTxPerLink returns the current numTxPerLink value which dictates the
	 * number of transmissions per link.
	 * 
	 * @return the numTxPerLink
	 */
	public Integer getNumTxPerLink() {
		return numTxPerLink;
	}

	/**
	 * getNodes returns an ArrayList of the nodes.
	 * 
	 * @return the nodes
	 */
	public ArrayList<Node> getNodes() {
		return nodes;
	}
	
	/**
	 * getEdges returns an ArrayList of the edges.
	 * @return the nodes
	 */
	public ArrayList<Edge> getEdges() {
		return edges;
	}

	/**
	 * addEdge adds an edge to the flow.
	 * uses edgePredecessor to add the new edge to the end of the ArrayList
	 * 
	 * @param edge is the edge that is being added
	 */
	public void addEdge(Edge edge) {
		/* set predecessor and add edge to flow */
		edge.setPredecessor(edgePredecessor);
		edges.add(edge);
		/* update predecessor for next edge added */
		edgePredecessor = edge;
	}
	
	/**
	 * addNode adds a node to the flow.
	 * uses nodePredecessor to add the new node to the end of the ArrayList
	 * 
	 * @param node is the node that is being added
	 */
	public void addNode(Node node) {
		/* set predecessor and add edge to flow */
		node.setPredecessor(nodePredecessor);
		nodes.add(node);
		/* update predecessor for next edge added */
		nodePredecessor = node;
	}
	/**
	 * getLinkTxAndTotalCost returns the ArrayList linkTxAndTotalCost.
	 * 
	 * @return the linkTxAndTotalCost
	 */
	public ArrayList<Integer> getLinkTxAndTotalCost() {
		return linkTxAndTotalCost;
	}

	/**
	 * setInitialPriority updates the constructor's priority.
	 * 
	 * @param initialPriority the initialPriority to set
	 */
	public void setInitialPriority(Integer initialPriority) {
		this.initialPriority = initialPriority;
	}

	/**
	 * setIndex updates the index.
	 * 
	 * @param index the index to set
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}

	/**
	 * setNumTxPerLink updates the numTxPerLink.
	 * 
	 * @param numTxPerLink the numTxPerLink to set
	 */
	public void setNumTxPerLink(Integer numTxPerLink) {
		this.numTxPerLink = numTxPerLink;
	}

	/**
	 * setNodes updates nodes and take an ArrayList as its parameter.
	 * 
	 * @param nodes the nodes to set
	 */
	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}

	/**
	 * setLinkTxAndTotalCost takes an ArrayList and sets the linkTxAndTotalCost.
	 * 
	 * @param linkTxAndTotalCost the linkTxAndTotalCost to set
	 */
	public void setLinkTxAndTotalCost(ArrayList<Integer> linkTxAndTotalCost) {
		this.linkTxAndTotalCost = linkTxAndTotalCost;
	}
	
	/**
	 * compareTo is used to compare the priority of two flows.
	 * The highest priority is zero.
	 * 
	 * @param flow is the flow that is being compared
	 * @return an int that is either -1 or 1 to determine which flow has the higher priority
	 */
	@Override
    public int compareTo(Flow flow) {
    	// ascending order (0 is highest priority)
        return flow.getPriority() > this.getPriority() ? -1 : 1;
    }
    
	/**
	 * toString returns the name of the flow
	 * 
	 * @return returns a String getName
	 */
    @Override
    public String toString() {
        return getName();
    }
    
}
