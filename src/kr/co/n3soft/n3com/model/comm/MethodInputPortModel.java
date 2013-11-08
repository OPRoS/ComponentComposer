package kr.co.n3soft.n3com.model.comm;

import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.jdom.Element;

public class MethodInputPortModel extends PortModel{
	String typeName = "MethodInputPortModel";
	public MethodInputPortModel(PortContainerModel p) {
       super(p);
       this.size.height = 16;// WJH 090819
       this.size.width = 16;// WJH 090819
       this.setUsage("required");
    }
	
	public MethodInputPortModel() {
		  super();
		  this.setUsage("required");
    }

    public MethodInputPortModel(UMLDataModel p) {
    	  super(p);
    	  this.setUsage("required");
    }
    
//    public void setSize(Dimension d) {
//        if (size.equals(d))
//            return;
//        
//        d.width=16;
//        d.height=16;
//        //		if(15 !=d.height)
//        //			return;
//        //		if(15 !=d.width)
//        //			return;
//        size = d;
//        firePropertyChange(ID_SIZE, null, size); //$NON-NLS-1$
//    }
	public void doSave(Element parentEle){
		Element ele;
		ele=new Element("name");
		ele.setText(getName());
		parentEle.addContent(ele);
		ele=new Element("description");
		ele.setText(this.getDesc());
		parentEle.addContent(ele);
		ele=new Element("type");
		ele.setText(getType());
		parentEle.addContent(ele);
		ele=new Element("usage");
		ele.setText(getUsage());
		parentEle.addContent(ele);
		ele=new Element("reference");
		ele.setText(this.getTypeRef());
		parentEle.addContent(ele);
//		ele = new Element("layout");
//		ele.setText(Integer.toString(getLayout().x)+","+Integer.toString(getLayout().y)
//				+","+Integer.toString(getLayout().width)+","+Integer.toString(getLayout().height));
//		parentEle.addContent(ele);
	}
    
    public Object clone() {
    	MethodInputPortModel clone = new MethodInputPortModel((UMLDataModel)this.getUMLDataModel().clone());
        //		clone.setUMLDataModel((UMLDataModel)this.getUMLDataModel().clone());
        clone.setPtDifference(this.getPtDifference().getCopy());
        clone.setSize(this.getSize());
        //		clone.setLocation(this.getLocation());
        clone.sourceModel = this;
        ProjectManager.getInstance().setTempCopyMap(clone.sourceModel.getID(), clone);
        for (int i1 = 0; i1 < this.getSourceConnections().size(); i1++) {
            LineModel li = (LineModel)this.getSourceConnections().get(i1);
            LineModel liCopy = (LineModel)li.clone();
            ProjectManager.getInstance().addSelectLineModel(liCopy);
        }
        return clone;
    }
	

}
