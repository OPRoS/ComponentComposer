package kr.co.n3soft.n3com.figures;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;


import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

public class BoundaryBorder extends AbstractBorder {
    protected static Insets insets = new Insets(0, 0, 0, 0);
    protected static PointList connector = new PointList();
    protected static PointList bottomConnector = new PointList();

    private java.util.ArrayList states = new java.util.ArrayList();
    private java.util.ArrayList transitionEventModels = new java.util.ArrayList();
    private boolean isNum = true;
//    private java.util.ArrayList xyline = new java.util.ArrayList();
    private java.util.HashMap xData = new HashMap();
    private int max= 100;
    static final Comparator FILENAME_ORDER = 
		new Comparator() { 
		public int compare(Object obj1, Object obj2) {

		return -1; 
		} 
		};
	public	BoundaryBorder(){
		
	}
	public void setIsNum(boolean p){
		this.isNum = p;
	}
    public Insets getInsets(IFigure figure) {
        return insets;
    }
    
    public void setStates(java.util.ArrayList p){
    	this.states = p;
    }
    
    public void setTransitionEventModels(java.util.ArrayList p){
    	transitionEventModels = p;
    }

    public void paint(IFigure figure, Graphics g, Insets in) {

    
    }
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
}
