package kr.co.n3soft.n3com.model.comm;

import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.geometry.Point;

public interface IPort {
    public void move(Point p);

    public MouseEvent dragMouseEvent(MouseEvent m);
}
