package kr.co.n3soft.n3com.projectmanager;

import kr.co.n3soft.n3com.UMLEditor;
import kr.co.n3soft.n3com.model.communication.MessageCommunicationModel;

public class LineTextManager {
    private java.util.ArrayList lineTextModels = new java.util.ArrayList();

//    for (int i = 0; i < lineTextModels.size(); i++) {
//    	MessageCommunicationModel em = (MessageCommunicationModel)lineTextModels.get(i);
//        
//    }
    public void removeAll(){
    	UMLEditor um = ProjectManager.getInstance().getUMLEditor();
    	for (int i = 0; i < lineTextModels.size(); i++) {
    	MessageCommunicationModel em = (MessageCommunicationModel)lineTextModels.get(i);
    	
    	um.removeSeqNumber(em);
        
      }
    	um.reNumber();
      
    }
    public void remove(int i ){
    	UMLEditor um = ProjectManager.getInstance().getUMLEditor();

    	MessageCommunicationModel em = (MessageCommunicationModel)lineTextModels.get(i);
    	
    	um.removeSeqNumber(em);
        

    	um.reNumber();
    }
    
    public java.util.ArrayList getLineTextModels() {
        return this.lineTextModels;
    }

    public void setLineTextModels(java.util.ArrayList p) {
        this.lineTextModels = p;
    }

    public void addElementLabelModel(MessageCommunicationModel text) {
        lineTextModels.add(text);
    }

    public void removeElementLabelModel(MessageCommunicationModel text) {
        lineTextModels.remove(text);
    }

    public void setElementLabelModel(String p, int index) {
    	MessageCommunicationModel text = (MessageCommunicationModel)lineTextModels.get(index);
        text.setName(p);
    }
    
    public String getElementLabelModel(int index) {
    	MessageCommunicationModel text = (MessageCommunicationModel)lineTextModels.get(index);
        return text.getName();
    }
    
    public void setAngle(String p){
    	for (int i = 0; i < lineTextModels.size(); i++) {
        	MessageCommunicationModel em = (MessageCommunicationModel)lineTextModels.get(i);
            em.setAngle(p);
        }
    }
    
    public void addRightArrowModel(){
    	for (int i = 0; i < lineTextModels.size(); i++) {
        	MessageCommunicationModel em = (MessageCommunicationModel)lineTextModels.get(i);
            em.addRightArrowModel();
        }
    }
    
    
    public void addaddLeftArrowModel(){
    	for (int i = 0; i < lineTextModels.size(); i++) {
        	MessageCommunicationModel em = (MessageCommunicationModel)lineTextModels.get(i);
            em.addLeftArrowModel();
        }
    }

    public java.util.ArrayList clone() {
        java.util.ArrayList lineTextModelsClone = new java.util.ArrayList();
        for (int i = 0; i < lineTextModels.size(); i++) {
        	MessageCommunicationModel em = (MessageCommunicationModel)lineTextModels.get(i);
            lineTextModelsClone.add(em.clone());
        }
        return lineTextModelsClone;
    }
}
