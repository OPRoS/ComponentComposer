package kr.co.n3soft.n3com.projectmanager;

import kr.co.n3soft.n3com.model.comm.LineBendpointModel;

import org.eclipse.draw2d.geometry.Dimension;

public class PortConnectionView extends PortConnection{
	 java.util.Vector bendpoints = new java.util.Vector();

	public java.util.Vector getBendpoints() {
		return bendpoints;
	}

	public void setBendpoints(java.util.Vector bendpoints) {
		this.bendpoints = bendpoints;
	}
	

}
