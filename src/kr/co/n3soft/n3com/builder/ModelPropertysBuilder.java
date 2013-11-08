package kr.co.n3soft.n3com.builder;

import kr.co.n3soft.n3com.model.comm.UMLDataModel;

public class ModelPropertysBuilder {
	ModelPropertysListBuilder modelPropertysListBuilder = new ModelPropertysListBuilder();
    UMLDataModel uMLDataModel = new UMLDataModel();
	java.util.ArrayList objects = new java.util.ArrayList();
	int objectsType = -1;
	
	public void addPropertys(String token){
		int keyIndex = token.indexOf("=");
		String id = token.substring(0,keyIndex);
		String value = token.substring(keyIndex+1,token.length());
		uMLDataModel.setProperty(id, value);
		
	}
	
	public void addObjects(String token){
		//child_model 0
		if(objectsType==0){
			
		}
	}
    
    public ModelPropertysListBuilder getModelPropertysListBuilder() {
		return modelPropertysListBuilder;
	}

	public void setModelPropertysListBuilder(
			ModelPropertysListBuilder modelPropertysListBuilder) {
		this.modelPropertysListBuilder = modelPropertysListBuilder;
	}

	public UMLDataModel getUMLDataModel() {
		return uMLDataModel;
	}

	public void setUMLDataModel(UMLDataModel dataModel) {
		uMLDataModel = dataModel;
	}
	

}
