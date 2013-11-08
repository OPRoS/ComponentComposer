package kr.co.n3soft.n3com.projectmanager;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.examples.logicdesigner.figures.LogicColorConstants;
import org.eclipse.swt.graphics.Color;

public class NoteView {
	public String viewId = "";
	public String noteId ="";
	public Dimension size = new Dimension(-1, -1);
	public Point location = new Point(0, 0);
	public Color backGroundColor = LogicColorConstants.defaultFillColor;
	public String noteViewInfo= "";
	public String text = "";
	public void setViewValue(){
		String[] svalue = noteViewInfo.split(",");
		if(svalue.length==7){
			String w = svalue[0];
			String h = svalue[1];
			String x = svalue[2];
			String y = svalue[3];
			String r = svalue[4];
			String g = svalue[5];
			String b = svalue[6];
			
			this.size = new Dimension(Integer.valueOf(w), Integer.valueOf(h));
			this.location = new Point(Integer.valueOf(x), Integer.valueOf(y));
			Color backGroundColor = new Color(null,Integer.valueOf(r), Integer.valueOf(g),Integer.valueOf(b));
			
		}
		
	}
}
