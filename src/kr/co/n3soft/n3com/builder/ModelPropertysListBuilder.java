package kr.co.n3soft.n3com.builder;

public class ModelPropertysListBuilder {
//	ObjectsBuilder objectsBuilder = new ObjectsBuilder();
	java.util.ArrayList objectsBuilders = new java.util.ArrayList();
	ObjectsBuilder currentObjectsBuilder = null;

	public java.util.ArrayList getObjectsBuilders() {
		return objectsBuilders;
	}

	public void setObjectsBuilders(java.util.ArrayList objectsBuilders) {
		this.objectsBuilders = objectsBuilders;
	}
	
	public void addObjectsBuilder(String token){
		
	}

//	public ObjectsBuilder getObjectsBuilder() {
//		return objectsBuilder;
//	}
//
//	public void setObjectsBuilder(ObjectsBuilder objectsBuilder) {
//		this.objectsBuilder = objectsBuilder;
//	}

}
