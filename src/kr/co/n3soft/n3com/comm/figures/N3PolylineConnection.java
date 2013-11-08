package kr.co.n3soft.n3com.comm.figures;

import kr.co.n3soft.n3com.anchor.N3ConnectionLocator;
import kr.co.n3soft.n3com.figures.CollaborationFigure;
import kr.co.n3soft.n3com.figures.LinePanelFigure;
import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PositionConstants;

public class N3PolylineConnection extends PolylineConnection {
    private LinePanelFigure middleFigure = new LinePanelFigure();
    private LinePanelFigure targetFigure = new LinePanelFigure();
    private LinePanelFigure sourceFigure = new LinePanelFigure();
    private UMLElementFigure middleTop = new UMLElementFigure();
    private UMLElementFigure middleBottom = new UMLElementFigure();
    private UMLElementFigure sourceTop = new UMLElementFigure();
    private UMLElementFigure sourceBottom = new UMLElementFigure();
    private UMLElementFigure targetTop = new UMLElementFigure();
    //	private CollaborationFigure collaborationFigure = new CollaborationFigure();
    private UMLElementFigure targetBottom = new UMLElementFigure(PositionConstants.RIGHT);

    public N3PolylineConnection() {
        middleFigure.add(middleTop);
        middleFigure.add(middleBottom);
        targetFigure.add(targetTop);
        targetFigure.add(targetBottom);
        sourceFigure.add(targetTop);
        sourceFigure.add(sourceBottom);
        //		targetBottom.set
        this.add(middleFigure, new N3ConnectionLocator(this, ConnectionLocator.MIDDLE));
        this.add(targetFigure, new N3ConnectionLocator(this, ConnectionLocator.TARGET));
        this.add(sourceFigure, new N3ConnectionLocator(this, ConnectionLocator.SOURCE));
    }

    public void setMiddle(String top, String bottom) {
        middleTop.setText(top);
        middleBottom.setText(bottom);
    }

    public void setTarget(String top, String bottom) {
        targetTop.setText(top);
        targetBottom.setText(bottom);
    }

    public void setSource(String top, String bottom) {
        sourceTop.setText(top);
        sourceBottom.setText(bottom);
        //		this.get
    }
}
