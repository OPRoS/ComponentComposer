package kr.co.n3soft.n3com.builder;

public class ModelBuilder {
	int modelType = -1;
	ModelPropertysBuilder modelPropertysBuilder = new ModelPropertysBuilder();

	public int getModelType() {
		return modelType;
	}

	public void setModelType(int modelType) {
		this.modelType = modelType;
	}

	public ModelPropertysBuilder getModelPropertysBuilder() {
		return modelPropertysBuilder;
	}

	public void setModelPropertysBuilder(ModelPropertysBuilder modelPropertysBuilder) {
		this.modelPropertysBuilder = modelPropertysBuilder;
	}
	
	public void addPropperty(String token){
		modelPropertysBuilder.addPropertys(token);
	}
	
	
	
	

}
