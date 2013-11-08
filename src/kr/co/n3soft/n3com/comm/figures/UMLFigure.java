package kr.co.n3soft.n3com.comm.figures;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import kr.co.n3soft.n3com.anchor.FixedAnchor;
import kr.co.n3soft.n3com.figures.EndPointFigure;
import kr.co.n3soft.n3com.figures.LifeLineFigure;
import kr.co.n3soft.n3com.model.comm.SeqChopboxAnchor;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

public class UMLFigure extends Figure {
    protected Hashtable connectionAnchors = new Hashtable(7);
    protected Vector inputConnectionAnchors = new Vector(2, 2);
    protected Vector outputConnectionAnchors = new Vector(2, 2);
    Color backGroundColor = ColorConstants.tooltipBackground;
    public HashMap property = new HashMap();
    /*
     * 1:위
     * 2:오른쪽
     * 3:아래
     * 4:왼쪽
     */
    public int arrow = 1;

    public ConnectionAnchor connectionAnchorAt(Point p) {
        ConnectionAnchor closest = null;
        long min = Long.MAX_VALUE;
        Enumeration e = getSourceConnectionAnchors().elements();
        System.out.println("ddd");
        while (e.hasMoreElements()) {
            ConnectionAnchor c = (ConnectionAnchor)e.nextElement();
            Point p2 = c.getLocation(null);
            long d = p.getDistance2(p2);
            if (d < min) {
                min = d;
                closest = c;
            }
        }
        e = getTargetConnectionAnchors().elements();
        while (e.hasMoreElements()) {
            ConnectionAnchor c = (ConnectionAnchor)e.nextElement();
            Point p2 = c.getLocation(null);
            long d = p.getDistance2(p2);
            if (d < min) {
                min = d;
                closest = c;
            }
        }
        return closest;
    }

    public ConnectionAnchor getConnectionAnchor(String terminal) {
        //		ChopboxAnchor anchor = new ChopboxAnchor(this);
        //		return anchor;
        //		ChopboxAnchor anchor= (ChopboxAnchor)connectionAnchors.get(terminal);
    	//terminal
//    	terminal = "";
        Object anchor = connectionAnchors.get(terminal);
//        if(this instanceof LifeLineFigure){
//        	SeqChopboxAnchor chopboxAnchor = new SeqChopboxAnchor(this);
//          return chopboxAnchor;
//        }
        if (anchor instanceof FixedAnchor) {
            FixedAnchor fixedAnchor = (FixedAnchor)anchor;
            return fixedAnchor;
        }
        if (anchor instanceof SeqChopboxAnchor) {
        	SeqChopboxAnchor chopboxAnchor = (SeqChopboxAnchor)anchor;
            return chopboxAnchor;
        }
        if (anchor instanceof ChopboxAnchor) {
            ChopboxAnchor chopboxAnchor = (ChopboxAnchor)anchor;
            return chopboxAnchor;
        }
       
//        if (anchor instanceof SeqChopboxAnchor) {
//        	SeqChopboxAnchor chopboxAnchor = (SeqChopboxAnchor)anchor;
//            return chopboxAnchor;
//        }
        if (anchor == null) {
        	if(this instanceof LifeLineFigure){
        		
        		SeqChopboxAnchor chopboxAnchor = new SeqChopboxAnchor(this);
//        		chopboxAnchor.isMove = false;
        		connectionAnchors.put(terminal, chopboxAnchor);
        		return chopboxAnchor;
        	}
        	else if(this instanceof EndPointFigure){
        		
        		SeqChopboxAnchor chopboxAnchor = new SeqChopboxAnchor(this);
//        		chopboxAnchor.isMove = false;
        		connectionAnchors.put(terminal, chopboxAnchor);
        		return chopboxAnchor;
        	}
        	else{
        		ChopboxAnchor chopboxAnchor = new ChopboxAnchor(this);
        		return chopboxAnchor;
        	}
        }
        return null;
        //		return anchor;
    }
   // seq
    public String getConnectionAnchorName(ConnectionAnchor c) {
        Enumeration keys = connectionAnchors.keys();
        String key;
        while (keys.hasMoreElements()) {
            key = (String)keys.nextElement();
            System.out.println("key:"+connectionAnchors.get(key));
            System.out.println("c:"+c);
            if (connectionAnchors.get(key).equals(c))
                return key;
        }
        return null;
    }

    public ConnectionAnchor getSourceConnectionAnchorAt(Point p) {
        ConnectionAnchor closest = null;
        long min = Long.MAX_VALUE;
        Enumeration e = getSourceConnectionAnchors().elements();
        while (e.hasMoreElements()) {
            ConnectionAnchor c = (ConnectionAnchor)e.nextElement();
            Point p2 = c.getLocation(null);
            long d = p.getDistance2(p2);
            if (d < min) {
                min = d;
                closest = c;
            }
        }
        return closest;
    }

    public Vector getSourceConnectionAnchors() {
        return outputConnectionAnchors;
    }

    public ConnectionAnchor getTargetConnectionAnchorAt(Point p) {
        ConnectionAnchor closest = null;
        long min = Long.MAX_VALUE;
        Enumeration e = getTargetConnectionAnchors().elements();
        while (e.hasMoreElements()) {
            ConnectionAnchor c = (ConnectionAnchor)e.nextElement();
            Point p2 = c.getLocation(null);
            long d = p.getDistance2(p2);
            if (d < min) {
                min = d;
                closest = c;
            }
        }
        return closest;
    }

    public Vector getTargetConnectionAnchors() {
        return inputConnectionAnchors;
    }

    public void setFigureBackgroundColor(Color p) {
        this.backGroundColor = p;
    }

    public Color getFigureBackgroundColor() {
        return this.backGroundColor;
    }

	public HashMap getProperty() {
		return property;
	}

	public void setProperty(HashMap property) {
		this.property = property;
	
	}
	public int getArrow() {
		return arrow;
	}

	public void setArrow(int arrow) {
		this.arrow = arrow;
	}

}
