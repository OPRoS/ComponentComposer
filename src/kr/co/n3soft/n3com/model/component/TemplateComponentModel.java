package kr.co.n3soft.n3com.model.component;

import java.io.File;

import kr.co.n3soft.n3com.model.comm.UMLDataModel;

public class TemplateComponentModel  extends ComponentLibModel{
	public File file = null;
	
	public java.util.ArrayList defined_service_types = new java.util.ArrayList();
	public java.util.ArrayList defined_data_types = new java.util.ArrayList();

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	
	public AtomicComponentModel getAtomicComponentModel(){
		AtomicComponentModel am = new AtomicComponentModel((UMLDataModel)this.getUMLDataModel().clone());
		am.setPortManager(this.getPortManager().clone(am));
		OPRoSServiceTypesElementModel ops = (OPRoSServiceTypesElementModel)am.getClassModel().getUMLDataModel().getElementProperty("OPRoSServiceTypesElementModel");					
		am.setSrcFolder(file.getPath());
		
		for(int i=0;i<defined_service_types.size();i++){
			String  service_type = (String)defined_service_types.get(i);
			OPRoSServiceTypeElementModel oPRoSServiceTypeElementModel = new OPRoSServiceTypeElementModel();
			oPRoSServiceTypeElementModel.setServiceTypeFileName(service_type);
//			oPRoSServiceTypeElementModel
			String path = file.getParent();
			oPRoSServiceTypeElementModel.setTempPath(path);
			ops.addChild(oPRoSServiceTypeElementModel);
		}
		OPRoSDataTypesElementModel ods = (OPRoSDataTypesElementModel)am.getClassModel().getUMLDataModel().getElementProperty("OPRoSDataTypesElementModel");
		
		for(int i=0;i<defined_data_types.size();i++){
			String  data_type = (String)defined_data_types.get(i);
			OPRoSDataTypeElementModel oPRoSDataTypeElementModel = new OPRoSDataTypeElementModel();
			oPRoSDataTypeElementModel.setDataTypeFileName(data_type);
			String path = file.getParent();
			oPRoSDataTypeElementModel.setTempPath(path);
			ods.addChild(oPRoSDataTypeElementModel);
		}
		
		return am;
	}

}
