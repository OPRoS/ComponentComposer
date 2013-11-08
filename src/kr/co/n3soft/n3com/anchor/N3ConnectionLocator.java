package kr.co.n3soft.n3com.anchor;

import kr.co.n3soft.n3com.model.comm.LineTextModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;

public class N3ConnectionLocator extends ConnectionLocator {
	//msg
	UMLModel pp = null;
	public Figure figure = null;
	public Point oldPoint = new Point(0,0);
	public Point initPoint = new Point(0,0);
	private boolean isBegin = false;

	public N3ConnectionLocator(Connection connection) {
		super(connection);
	}

	public N3ConnectionLocator(Connection connection, int align) {
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
			Point pt = ltm.getLocation();
			if(pt.x==20 && pt.y==20){
//				pp.setLocation(p);
				pp.setLocation(new Point(p.x,p.y));
//				initPoint = p;
			}
			else{				
				if(oldPoint.x!=0&&oldPoint.y!=0)//PKY 08060201 S 라인 텍스트 생성시 텍스트가 비 정상적인 위치에 생성되는문제 수정
				if(oldPoint!=null){
					Dimension diff = oldPoint.getDifference(p);
						pp.setLocation(new Point(pp.getLocation().x - diff.width,pp.getLocation().y-diff.height));
				}

			}
			
//			Point pt2 = new Point(p.x - pt.x,p.y-pt.y);
//if(pt2.x<0){
//	pt2.x = -pt2.x;
//}
//if(pt2.y<0){
//	pt2.y = -pt2.y;
//			}
			System.out.println("p"+p);
			isBegin = false;

//			return pt2;
		}
		oldPoint = new Point(p.x,p.y);
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

			if (points.size() % 2 == 0) {
				int i = points.size() / 2;
				int j = i - 1;
				Point p1 = points.getPoint(j);
				Point p2 = points.getPoint(i);
				Dimension d = p2.getDifference(p1);
				return Point.SINGLETON.setLocation(p1.x + d.width / 2, p1.y + d.height / 2);
			}
			int i = (points.size() - 1) / 2;
			return points.getPoint(Point.SINGLETON, i);
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

	public boolean isBegin() {
		return isBegin;
	}

	public void setBegin(boolean isBegin) {
		this.isBegin = isBegin;
	}
	//PKY 08052801 S LineOladPoint 추가
	public UMLModel getPp() {
		return pp;
	}


	public Point getOldPoint() {
		return oldPoint;
	}


	public void setOldPoint(Point oldPoint) {
		this.oldPoint = oldPoint;
	}
	//PKY 08052801 E LineOladPoint 추가

	public Point getInitPoint() {
		return initPoint;
	}

	public void setInitPoint(Point initPoint) {
		this.initPoint = initPoint;
	}
}
