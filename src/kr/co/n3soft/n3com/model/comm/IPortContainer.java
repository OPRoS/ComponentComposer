package kr.co.n3soft.n3com.model.comm;

import org.eclipse.draw2d.geometry.Point;

public interface IPortContainer {
    public void addPort(PortModel ip, UMLContainerModel p);

    public void removePort(PortModel ip, UMLContainerModel p);

    public void setPortLocation(Point p);

    public void createPort(PortModel ip);

    public UMLModel getPort();

    public void createPort(PortModel ip, UMLContainerModel p);

    public void undoCreatePort(PortModel ip, UMLContainerModel p);

    public void deletePort(PortModel ip, UMLContainerModel p);

    public void undoDeletePort(PortModel ip, UMLContainerModel p);
    //	public void dragPort();
}
