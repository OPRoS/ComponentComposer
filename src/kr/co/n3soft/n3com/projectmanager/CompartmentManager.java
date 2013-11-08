package kr.co.n3soft.n3com.projectmanager;

import java.util.HashMap;

import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.umlclass.CompartmentModel;

import org.eclipse.draw2d.geometry.Point;

public class CompartmentManager {
    public CompartmentManager() {
    }

    private java.util.ArrayList compartments = new java.util.ArrayList();
    private HashMap property = new HashMap();

    public java.util.ArrayList getCompartments() {
        return this.compartments;
    }

    public void setCompartments(java.util.ArrayList p) {
        this.compartments = p;
    }

    public void addCompartment(CompartmentModel cm, String key) {
        compartments.add(cm);
        property.put(key, cm);
    }

    public void remove(CompartmentModel cm, String key) {
        compartments.remove(cm);
        property.put(key, null);
    }

    public void addCompartmentInModel(String key, UMLModel um) {
        CompartmentModel cm = (CompartmentModel)property.get(key);
        if (cm != null)
            cm.addChild(um);
    }
    
    
    
    
}
