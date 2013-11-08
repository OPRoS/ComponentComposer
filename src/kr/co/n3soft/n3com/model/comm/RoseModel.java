package kr.co.n3soft.n3com.model.comm;

import java.util.HashMap;
import java.util.Vector;

public class RoseModel {
	public String type = "";
	public Vector packNames = null;
	public Vector packDatas  = null;
	public HashMap models  = null;
	public HashMap diagramModels  = null;
	public HashMap viewNode  = null;
	public HashMap viewDiagram  = null;
	public String modelGuid = "";
	
	public RoseModel(String type){
		this.type = "";
		this.packNames = new Vector();
		this.packDatas = new Vector();
		this.models = new HashMap();
		this.diagramModels = new HashMap();
		this.viewNode = new HashMap();
		this.viewDiagram = new HashMap();
		this.type = type;
	}
}
