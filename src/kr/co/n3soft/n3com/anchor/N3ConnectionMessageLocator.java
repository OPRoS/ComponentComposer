package kr.co.n3soft.n3com.anchor;

import kr.co.n3soft.n3com.model.comm.LineTextModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;

public class N3ConnectionMessageLocator extends ConnectionLocator {
	//msg
	UMLModel pp = null;
	public Figure figure = null;
	boolean isMove = true;
	Point locator = null;

	public N3ConnectionMessageLocator(Connection connection) {
		super(connection);
	}

	public N3ConnectionMessageLocator(Connection connection, int align) {
		super(connection, align);
	}

	public void setModel(UMLModel e) {
		pp = e;
	}

	protected Point getReferencePoint() {
		Point p = getLocation(getConnection().getPoints());
		//		this.getConnection().getParent().
		if (pp != null && pp instanceof LineTextModel) {
			LineTextModel ltm = (LineTextModel)pp;
			ltm.setIsAutoMode(true);
			pp.setLocation(p);
		}
		return p;
	}

	protected Point getLocation(PointList points) {
		//msg
		switch (getAlignment()) {
		case SOURCE:
			return points.getPoint(Point.SINGLETON, 0);
		case TARGET:
			return points.getPoint(Point.SINGLETON, points.size() - 1);
		case MIDDLE:
			if(locator!=null){
				if (points.size() % 2 == 0) {
					int i = points.size() / 2;
					int j = i - 1;
					Point p1 = points.getPoint(j);
					Point p2 = points.getPoint(i);
					Dimension d = p2.getDifference(p1);
					return Point.SINGLETON.setLocation(p1.x + d.width / 2, p1.y + d.height / 2);
				}
				int i = (points.size() - 1) / 2;
				locator = points.getPoint(Point.SINGLETON, i);
				return points.getPoint(Point.SINGLETON, i);
			}
			else{
				return locator;
			}

		default:
			return new Point();
		}
	}
	//	public void relocate(IFigure target) {
	//		Dimension prefSize = target.getPreferredSize();
	//		Dimension nPrefSize = new Dimension(prefSize.width+100,prefSize.height+100);
	//		Point center = getReferencePoint();
	//		target.translateToRelative(center);
	//		target.setBounds(getNewBounds(nPrefSize, center));
	//	}
}
