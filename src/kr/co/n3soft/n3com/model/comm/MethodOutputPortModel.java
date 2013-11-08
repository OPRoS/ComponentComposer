package kr.co.n3soft.n3com.model.comm;

import org.jdom.Element;

import kr.co.n3soft.n3com.projectmanager.ProjectManager;

public class MethodOutputPortModel extends PortModel{
	String typeName = "MethodOutputPortModel";
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public MethodOutputPortModel(PortContainerModel p) {
	       super(p);
	       this.size.height = 16;// WJH 090819
	       this.size.width = 16;// WJH 090819
	       this.setUsage("provided");
	       
	       
	    }
		
		public MethodOutputPortModel() {
			  super();
			  this.setUsage("provided");
	    }

	    public MethodOutputPortModel(UMLDataModel p) {
	    	  super(p);
	    	  this.setUsage("provided");
	    }
	    
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
//			ele = new Element("layout");
//			ele.setText(Integer.toString(getLayout().x)+","+Integer.toString(getLayout().y)
//					+","+Integer.toString(getLayout().width)+","+Integer.toString(getLayout().height));
//			parentEle.addContent(ele);
		}
	    public Object clone() {
	    	MethodOutputPortModel clone = new MethodOutputPortModel((UMLDataModel)this.getUMLDataModel().clone());
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
