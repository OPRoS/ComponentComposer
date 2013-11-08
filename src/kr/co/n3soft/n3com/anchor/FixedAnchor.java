package kr.co.n3soft.n3com.anchor;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ScalableFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.examples.logicdesigner.figures.FixedConnectionAnchor;

public class FixedAnchor extends AbstractConnectionAnchor {
    //	public boolean leftToRight = true;
    //	public int offsetH;
    //	public int offsetV;
    //	public boolean topDown = true;
    public Point place;

    public FixedAnchor(IFigure owner) {
        super(owner);
    }

    public Point getLocation(Point loc) {
        Rectangle r = this.getOwner().getBounds();
        int x = r.x + place.x * r.width / 2;
        int y = r.y + place.y * r.height / 2;
        Point p = new PrecisionPoint(x, y);
        this.getOwner().translateToAbsolute(p);
        return p;
    }

    /** @see org.eclipse.draw2d.AbstractConnectionAnchor#ancestorMoved(IFigure) */
}
