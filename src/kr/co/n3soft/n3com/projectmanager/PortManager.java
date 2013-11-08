package kr.co.n3soft.n3com.projectmanager;

import kr.co.n3soft.n3com.model.comm.PortContainerModel;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.comm.UMLContainerModel;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import com.sun.org.apache.bcel.internal.classfile.PMGClass;

public class PortManager {
    //ports
	public java.util.ArrayList addPorts = new java.util.ArrayList();
	public java.util.ArrayList getAddPorts() {
		return addPorts;
	}

	public void setAddPorts(java.util.ArrayList addPorts) {
		this.addPorts = addPorts;
	}

	public java.util.ArrayList delPorts = new java.util.ArrayList();
	
	
    public java.util.ArrayList getDelPorts() {
		return delPorts;
	}

	public void setDelPorts(java.util.ArrayList delPorts) {
		this.delPorts = delPorts;
	}

	public PortManager() {
    }

    private java.util.ArrayList ports = new java.util.ArrayList();

    public java.util.ArrayList getPorts() {
        return this.ports;
    }

    public void setPorts(java.util.ArrayList p) {
        this.ports = p;
    }

    public void addPort(PortModel port) {
        ports.add(port);
        if(!ProjectManager.getInstance().isLoad()){
        	this.addPorts.add(port);
        	
        }
        System.out.println("dddd");
    }

    public void remove(PortModel iport) {
    	 if(!ProjectManager.getInstance().isLoad()){
//    		 if()
         	this.delPorts.add(iport);
         	
         }
        ports.remove(iport);
    }

    public void setPortLocations(Point p, double w, double h) {
        for (int i = 0; i < this.ports.size(); i++) {
            PortModel iPort = (PortModel)this.ports.get(i);
            if (iPort != null) {
                iPort.getPtDifference().width = (int)(iPort.getPtDifference().width * w);
                iPort.getPtDifference().height = (int)(iPort.getPtDifference().height * h);
                iPort.move(p);
            }
        }
    }

    public void checkMove(Rectangle r) {
        for (int i = 0; i < this.ports.size(); i++) {
            PortModel iPort = (PortModel)this.ports.get(i);
            if (iPort != null) {
                int xd = r.width;
                int yd = r.height;
                int xd1 = r.x + iPort.getPtDifference().width;
                if (r.contains(iPort.getBoundRectangle())) {
                    if (iPort.getIndex() == 2) {
                        iPort.getPtDifference().height = -yd + 10;
                    }
                    else if (iPort.getIndex() == 1) {
                        iPort.getPtDifference().width = -xd + 10;
                    }
                    else if (iPort.getIndex() == 4) {
                        iPort.getPtDifference().width = -xd + 10;
                        iPort.getPtDifference().height = -yd + 10;
                    }
                    else if (iPort.getIndex() == 5) {
                        //						iPort.getPtDifference().width = -xd+10;
                        iPort.getPtDifference().height = -yd + 10;
                    }
                    iPort.move(r.getLocation());
                }
                else if (!r.intersects(iPort.getBoundRectangle())) {
                    if (iPort.getIndex() == 2) {
                        iPort.getPtDifference().height = -yd + 10;
                        if (!r.contains(r.x - iPort.getPtDifference().width, r.y - iPort.getPtDifference().height)) {
                            iPort.getPtDifference().width = -xd + 10;
                        }
                    }
                    else if (iPort.getIndex() == 1) {
                        iPort.getPtDifference().width = -xd + 10;
                        if (!r.contains(r.x - iPort.getPtDifference().width, r.y - iPort.getPtDifference().height)) {
                            iPort.getPtDifference().height = -yd + 10;
                        }
                    }
                    else if (iPort.getIndex() == 4) {
                        iPort.getPtDifference().width = -xd + 10;
                        iPort.getPtDifference().height = -yd + 10;
                    }
                    else if (iPort.getIndex() == 5) {
                        //						iPort.getPtDifference().width = -xd+10;
                        iPort.getPtDifference().height = -yd + 10;
                    }
                    else {
                        if (iPort.getIndex() == 3) {
                            iPort.getPtDifference().width = 0;
                            iPort.getPtDifference().height = -yd + 10;
                        }
                        if (iPort.getIndex() == 0) {
                            iPort.getPtDifference().width = -xd + 10;
                            iPort.getPtDifference().height = 0;
                        }
                    }
                    iPort.move(r.getLocation());
                }

            }
        }
    }

    public void setPortLocations(Point p) {
        for (int i = 0; i < this.ports.size(); i++) {
            PortModel iPort = (PortModel)this.ports.get(i);
            if (iPort != null) {
                iPort.move(p);
            }
        }
    }

    public void addAllParentDiagram(UMLContainerModel p, Point location) {
        for (int i = 0; i < this.ports.size(); i++) {
            PortModel iPort = (PortModel)this.ports.get(i);
            Point pt = new Point(location.x, location.y - 10);
            p.addChild(iPort);
            p.addChild(iPort.getAttachElementLabelModel());
            if (iPort.getPtDifference() == null) {
                iPort.setLocation(pt);
            }
            else
                iPort.move(location);
        }
    }

    public void addParentDiagram(UMLContainerModel p, PortModel iPort) {
    	boolean isExist = false;
    	for(int i=0;i<ports.size();i++){
    		PortModel pm = (PortModel)ports.get(i);
    		if(iPort.getName().equals("")){
    			
    			break;
    		}
    		if(pm.getName().equals(iPort.getName())){
    			
    			isExist = true;
//    			this.ports.remove(i);
    			this.remove(pm);
    			addPort(iPort);
//    			this.ports.add(iPort);
    			
    		}
    	}
    	if(!isExist){
//    		this.ports.add(iPort);
    		addPort(iPort);
    	}
        p.addChild(iPort);
        p.addChild(iPort.getAttachElementLabelModel());
       
        Point pt = new Point(iPort.getPortContainerModel().getLocation().x,
            iPort.getPortContainerModel().getLocation().y - 10);
        int maxX = 0;
        Rectangle am = new Rectangle(pt.x,pt.y,iPort.getSize().width,iPort.getSize().height);
        PortModel pmMax = null; 
        for(int i=0;i<this.ports.size()-1;i++){
        	PortModel pm = (PortModel)this.ports.get(i);
        	System.out.println("----------->pm->"+ pm.getBoundRectangle());
        	System.out.println("----------->am->"+ am);
        	if(pm.getBoundRectangle().intersects(am)){
        		if(pmMax==null){
        			pmMax = pm;
        			am.x = pmMax.getBoundRectangle().x+pmMax.getBoundRectangle().width+10;
        		}
        		else if(pmMax.getLocation().x<pm.getLocation().x){
        			pmMax = pm;
        			am.x = pmMax.getBoundRectangle().x+pmMax.getBoundRectangle().width+10;
        		}
        	}
        }
        if(pmMax!=null){
        	if(am.getSize().width==36)
        	pt.x = pmMax.getBoundRectangle().x+pmMax.getBoundRectangle().width+30;
        	else
        		pt.x = pmMax.getBoundRectangle().x+pmMax.getBoundRectangle().width+30;	
        }
       
        
        
        iPort.setLocation(pt);
    }
    
    public void addParentDiagram2(UMLContainerModel p, PortModel iPort) {
        if(!ports.contains(iPort)){
        	this.addPort(iPort);
//        	this.ports.add(iPort);
        }
        p.addChild(iPort);
        p.addChild(iPort.getAttachElementLabelModel());
       
        Point pt = new Point(iPort.getPortContainerModel().getLocation().x,
            iPort.getPortContainerModel().getLocation().y - 10);
        int maxX = 0;
        for(int i=0;i<this.ports.size()-1;i++){
        	PortModel pm = (PortModel)this.ports.get(i);
        	if(pm.getBoundRectangle().contains(pt)){
        		pt.x = pm.getBoundRectangle().x+pm.getBoundRectangle().width-30;
        	}
        }
        
        
        iPort.setLocation(pt);
    }

    public void addAllParentDiagram(UMLContainerModel p) {
        for (int i = 0; i < this.ports.size(); i++) {
            PortModel iPort = (PortModel)this.ports.get(i);
            p.addChild(iPort);
            p.addChild(iPort.getAttachElementLabelModel());
        }
    }

    public void removeAllParentDiagram(UMLContainerModel p, Point location) {
        for (int i = 0; i < this.ports.size(); i++) {
            PortModel iPort = (PortModel)this.ports.get(i);
            p.removeChild(iPort);
            p.removeChild(iPort.getAttachElementLabelModel());
        }
    }

    public PortManager clone(PortContainerModel p) {
        PortManager pm = new PortManager();
        for (int i = 0; i < this.ports.size(); i++) {
            PortModel iPort = (PortModel)this.ports.get(i);
            PortModel clone = (PortModel)iPort.clone();
            System.out.println("iPort===>"+iPort.getName());
            clone.setPortContainerModel(p);
            pm.addPort(clone);

        }
        return pm;
    }
}
