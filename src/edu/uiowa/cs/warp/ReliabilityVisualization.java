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
   /*package*/ ReliabilityVisualization (WarpInterface warp) {
      super(new FileManager(), warp, SOURCE_SUFFIX);
      		this.warp = warp;
      		this.ra = warp.toReliabilityAnalysis();
      	}
   
   }
