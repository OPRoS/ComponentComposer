package kr.co.n3soft.n3com.projectmanager;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.examples.logicdesigner.figures.LogicColorConstants;
import org.eclipse.swt.graphics.Color;

public class NoteViewLine {
	/*
	 * 0:note_target_connection
	 * 1:note_source_connection
	 */

	public int note_connection_type = 0;
	/*
	 * 0:port
	 * 1:component
	 */
	public int link_type = 0;
	
	public String source_component_name = "";
	public String source_port_name = "";
	public String target_component_name = "";
	public String target_port_name = "";
	
	 java.util.Vector bendpoints = new java.util.Vector();

		public java.util.Vector getBendpoints() {
			return bendpoints;
		}

		public void setBendpoints(java.util.Vector bendpoints) {
			this.bendpoints = bendpoints;
		}
	

}
