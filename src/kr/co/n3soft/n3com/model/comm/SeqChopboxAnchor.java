package kr.co.n3soft.n3com.model.comm;

import kr.co.n3soft.n3com.figures.LifeLineFigure;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
//20080721IJS 전체 변경
//seq
public class SeqChopboxAnchor extends ChopboxAnchor {
    public int y = 0;
    public static int k = 0;
    public boolean isMove = true;
   public boolean isTarget = false;

    public SeqChopboxAnchor(IFigure owner) {
        super(owner);
//        System.out.println("SeqChopboxAnchor==>" + owner.getClass() + ":" + k++);
    }

    public Point getReferencePoint() {
    	Point ref = getBox().getCenter();
    	try{
        
        getOwner().translateToAbsolute(ref);
        if (this.isMove) {
            Point pt = ProjectManager.getInstance().getDragPoint();
            if(pt!=null){
            	ref.y = pt.y;
            	y = pt.y;
            }
        }
        else {
            ref.y = y;
        }
    	}
        catch(Exception e){
        	e.printStackTrace();
        }
        return ref;
    }

    public Point getLocation(Point reference) {
//        System.out.println("reference==>" + reference);
        Rectangle r = Rectangle.SINGLETON;
        r.setBounds(getBox());
        r.translate(-1, -1);
        r.resize(1, 1);
        getOwner().translateToAbsolute(r);
        float centerX = r.x + 0.5f * r.width;
        float centerY = r.y + 0.5f * r.height;

        if (true) {
            Point pt = ProjectManager.getInstance().getDragPoint();
            if(!this.isTarget)
            return new Point(centerX+10, reference.y);
           else{
            	//20080908IJS
            	if(this.getOwner() instanceof LifeLineFigure){
            		LifeLineFigure llf = (LifeLineFigure)this.getOwner();
            		
            		if(llf.getLocation().y<=reference.y
            				&& llf.getLocation().y+40>=reference.y
            				&& llf.getLifeCycle()==1){
            			return new Point(r.x, reference.y);
            			
            		}
            	}
            	return new Point(centerX-5, reference.y);
            }
        }
        if (r.isEmpty() || (reference.x == (int)centerX && reference.y == (int)centerY))
            return new Point((int)centerX, (int)centerY); //This avoids divide-by-zero
        float dx = reference.x - centerX;
        float dy = reference.y - centerY;
        //r.width, r.height, dx, and dy are guaranteed to be non-zero.
        float scale = 0.5f / Math.max(Math.abs(dx) / r.width, Math.abs(dy) / r.height);
        dx *= scale;
        dy *= scale;
        centerX += dx;
        centerY += dy;
        return new Point(Math.round(centerX), Math.round(centerY));
    }

	public boolean isTarget() {
		return isTarget;
	}

	public void setTarget(boolean isTarget) {
		this.isTarget = isTarget;
	}
}
