package edu.uiowa.cs.warp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class WorkLoadTest {

	@Test
	void testAddFlow() {
		WorkLoad WL = new WorkLoad(0.9, 0.99, "stressTest.txt");
		WL.addFlow("newFlow");
		System.out.println(WL.getFlows());
		
	}

	
	
}
