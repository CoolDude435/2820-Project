package edu.uiowa.cs.warp;


public class ReliabilityRow extends Row <Double>  {
   private static final long serialVersionUID = 1L;
   
   public ReliabilityRow () {
      super();
      	}
   
   /*package*/ ReliabilityRow (Integer numColumns, Double element) {
      super(numColumns, element);
      	}
   
   /*package*/ ReliabilityRow (Double[] rowArray) {
      super(rowArray);
      	}
   
   }
