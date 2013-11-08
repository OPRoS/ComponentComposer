package kr.co.n3soft.n3com.figures;

import kr.co.n3soft.n3com.comm.figures.UMLFigure;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.gef.examples.logicdesigner.figures.FixedConnectionAnchor;
import org.eclipse.gef.examples.logicdesigner.model.Circuit;
import org.eclipse.gef.handles.HandleBounds;

public class TempBorderLayoutContainerFigure extends UMLFigure implements HandleBounds {
    private IFigure pane;
    private TextFlow textFlow;

    public TempBorderLayoutContainerFigure() {
        createConnectionAnchors();
        //	setBorder(new BoundaryBorder());
        //
        //	setBackgroundColor(ColorConstants.tooltipBackground);
        //	this.setForegroundColor(LogicColorConstants.logicGreen);
        setOpaque(true);
    }

    protected void createConnectionAnchors() {
        ChopboxAnchor in, out;
        //	for(int i=0;i<10;i++){
        in = new ChopboxAnchor(this);
        out = new ChopboxAnchor(this);
        setOutputConnectionAnchor(out);
        setInputConnectionAnchor(in);
        outputConnectionAnchors.addElement(out);
        inputConnectionAnchors.addElement(in);
        //	}
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
        //	anchor.getLocation(p);
        //	inputConnectionAnchors.addElement(anchor);
        return anchor;
    }

    public ConnectionAnchor getSourceConnectionAnchorAt(Point p) {
        ConnectionAnchor anchor = new ChopboxAnchor(this);
        //	outputConnectionAnchors.addElement(anchor);
        return anchor;
    }

    public IFigure getContentsPane() {
        return pane;
    }

    protected FixedConnectionAnchor getInputConnectionAnchor(int i) {
        return (FixedConnectionAnchor)connectionAnchors.get(Circuit.TERMINALS_IN[i]);
    }

    /** @see org.eclipse.gef.handles.HandleBounds#getHandleBounds() */
    public Rectangle getHandleBounds() {
        return getBounds().getCropped(new Insets(0, 0, 0, 0));
    }

    protected FixedConnectionAnchor getOutputConnectionAnchor(int i) {
        return (FixedConnectionAnchor)connectionAnchors.get(Circuit.TERMINALS_OUT[i]);
    }

    public Dimension getPreferredSize(int w, int h) {
        Dimension prefSize = super.getPreferredSize(w, h);
        Dimension defaultSize = new Dimension(100, 30);
        prefSize.union(defaultSize);
        return prefSize;
    }

    protected void layoutConnectionAnchors() {
        int x;
        for (int i = 0; i < 4; i++) {
            x = (2 * i + 1) * getSize().width / 8;
            getOutputConnectionAnchor(i + 4).setOffsetH(x - 1);
            getInputConnectionAnchor(i).setOffsetH(x - 1);
            getInputConnectionAnchor(i + 4).setOffsetH(x);
            getOutputConnectionAnchor(i).setOffsetH(x);
        }
    }

    /** @see org.eclipse.draw2d.Figure#paintFigure(Graphics) */
    protected void paintFigure(Graphics graphics) {
    	this.setForegroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08081101 S 프로젝트 전체의 객체 테두리 색상 설정 할  수있도록 수정
    }

    public void setInputConnectionAnchor(int i, ConnectionAnchor c) {
        connectionAnchors.put(Circuit.TERMINALS_IN[i], c);
    }

    public void setOutputConnectionAnchor(int i, ConnectionAnchor c) {
        connectionAnchors.put(Circuit.TERMINALS_OUT[i], c);
    }

    public String toString() {
        return "CircuitBoardFigure"; //$NON-NLS-1$
    }

    public void validate() {
        if (isValid()) return;
        //	layoutConnectionAnchors();
        super.validate();
    }

    protected boolean useLocalCoordinates() { return true; }
}
