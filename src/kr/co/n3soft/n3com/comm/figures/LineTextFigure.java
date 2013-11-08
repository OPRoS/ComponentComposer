package kr.co.n3soft.n3com.comm.figures;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import kr.co.n3soft.n3com.figures.CompartmentFigureBorder;
import kr.co.n3soft.n3com.figures.FixedConnectionAnchorFigure;
import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.gef.handles.HandleBounds;

public class LineTextFigure extends UMLFigure implements HandleBounds {
    private IFigure pane;
    private TextFlow textFlow;
    public int w1 = 0;
    public int h1 = 0;
    public int oldh1 = 40;
    private int typeCompartment = 0; //attribute 0,operation 1

    public LineTextFigure() {
        ToolbarLayout layout = new ToolbarLayout();
        layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
        layout.setStretchMinorAxis(true);
        layout.setSpacing(2);
        setLayoutManager(layout);
    }

    public IFigure getContentsPane() {
        return pane;
    }

//    public Dimension getPreferredSize(int w, int h) {
//        Dimension prefSize = super.getPreferredSize(w, h);
//        Dimension defaultSize = new Dimension(100, 50);
//        prefSize.union(defaultSize);
//        return prefSize;
//    }
//
//    public Dimension getMinimumSize(int wHint, int hHint) {
//        Dimension prefSize = super.getMinimumSize(wHint, hHint);
//        Dimension defaultSize = new Dimension(wHint, 15);
//        prefSize.union(defaultSize);
//        //	prefSize.union(defaultSize);
//        return prefSize;
//    }

    /** @see org.eclipse.draw2d.Figure#paintFigure(Graphics) */
    protected void paintFigure(Graphics graphics) {
    }

    public String toString() {
        return "CircuitBoardFigure"; //$NON-NLS-1$
    }

//    public void validate() {
//        if (isValid()) return;
//        //	layoutConnectionAnchors();
//        super.validate();
//    }

    protected boolean useLocalCoordinates() {
    	return true; 
    }

    protected void createConnectionAnchors() {
        ChopboxAnchor in, out;
        in = new ChopboxAnchor(this);
        out = new ChopboxAnchor(this);
        setOutputConnectionAnchor(out);
        setInputConnectionAnchor(in);
        outputConnectionAnchors.addElement(out);
        inputConnectionAnchors.addElement(in);
    }

    //protected FixedConnectionAnchorFigure getInputConnectionAnchor(int i) {
    //	return (FixedConnectionAnchorFigure) connectionAnchors.get(Circuit.TERMINALS_IN[i]);
    //}
    public void setInputConnectionAnchor(ConnectionAnchor c) {
        connectionAnchors.put("A", c);
    }

    public void setOutputConnectionAnchor(ConnectionAnchor c) {
        connectionAnchors.put("B", c);
    }

    public ConnectionAnchor getTargetConnectionAnchorAt(Point p) {
        ConnectionAnchor anchor = new ChopboxAnchor(this);
        anchor.getLocation(p);
        //	inputConnectionAnchors.addElement(anchor);
        return anchor;
    }

    public ConnectionAnchor getSourceConnectionAnchorAt(Point p) {
        ConnectionAnchor anchor = new ChopboxAnchor(this);
        //	outputConnectionAnchors.addElement(anchor);
        return anchor;
    }

    //protected void layoutConnectionAnchors() {
    //	int x;
    //	for (int i = 0; i < 4; i++){
    //		x = (2*i+1) * getSize().width / 8;
    //		getOutputConnectionAnchor(i+4).setOffsetH(x-1);
    //		getInputConnectionAnchor(i).setOffsetH(x-1);
    //		getInputConnectionAnchor(i+4).setOffsetH(x);
    //		getOutputConnectionAnchor(i).setOffsetH(x);
    //	}
    //}
    //
    //
    //
    protected void createConnectionAnchors(Point pt) {
        FixedConnectionAnchorFigure in, out;
        out = new FixedConnectionAnchorFigure(this);
        out.getLocation(pt);
        outputConnectionAnchors.addElement(out);
    }

    /** @see org.eclipse.gef.handles.HandleBounds#getHandleBounds() */
    public Rectangle getHandleBounds() {
        return getBounds().getCropped(new Insets(2, 0, 2, 0));
    }
}
