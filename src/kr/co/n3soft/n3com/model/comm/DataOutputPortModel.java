package kr.co.n3soft.n3com.model.comm;

import org.jdom.Element;

import kr.co.n3soft.n3com.projectmanager.ProjectManager;

public class DataOutputPortModel extends PortModel{
	String typeName = "DataOutputPortModel";
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public DataOutputPortModel(PortContainerModel p) {
	       super(p);
	       this.size.height = 16;	// WJH 090819
	       this.size.width = 16;	// WJH 090819
	       this.setUsage("output");
	    }
		
		public DataOutputPortModel() {
			  super();
			  this.setUsage("output");
	    }

	    public DataOutputPortModel(UMLDataModel p) {
	    	  super(p);
	    	  this.setUsage("output");
	    }
	    
	    public Object clone() {
	    	DataOutputPortModel clone = new DataOutputPortModel((UMLDataModel)this.getUMLDataModel().clone());
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
	    public void doSave(Element parentEle){
			Element ele;
			ele=new Element("name");
			ele.setText(getName());
			parentEle.addContent(ele);
			ele=new Element("description");
			ele.setText(this.getDesc());
			parentEle.addContent(ele);
			ele=new Element("data_type");
			ele.setText(getType());
			parentEle.addContent(ele);
			ele=new Element("usage");
			ele.setText(getUsage());
			parentEle.addContent(ele);
			ele=new Element("reference");
			ele.setText(this.getTypeRef());
//			parentEle.addContent(ele);
//			ele=new Element("queueing_policy");
//			ele.setText(getQueueingPolicy());
//			parentEle.addContent(ele);
//			ele=new Element("queue_size");
//			ele.setText(getQueueSize());
//			parentEle.addContent(ele);
//			ele = new Element("layout");
//			ele.setText(Integer.toString(getLayout().x)+","+Integer.toString(getLayout().y)
//					+","+Integer.toString(getLayout().width)+","+Integer.toString(getLayout().height));
			parentEle.addContent(ele);
		}
}
