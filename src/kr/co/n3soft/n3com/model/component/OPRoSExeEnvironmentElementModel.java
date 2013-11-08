package kr.co.n3soft.n3com.model.component;

import org.eclipse.gef.examples.logicdesigner.LogicMessages;
import org.jdom.Element;

import kr.co.n3soft.n3com.OPRoSStrings;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

public class OPRoSExeEnvironmentElementModel extends UMLDiagramModel{
	
	public static final String PROPERTY_DOMAIN = "Execution Environment Domain";
	public static final String PROPERTY_ROBOT_TYPE = "Execution Environment Robot Type";
	
	public static final String PROPERTY_LIBRARY_NAME = "Library Name";
	public static final String PROPERTY_LIBRARY_TYPE = "Library Type";
	public static final String PROPERTY_IMPL_LANGUAGE = "Implemention Language";
	public static final String PROPERTY_COMPILER = "Compiler";
	
	private String libraryName;
	private String libraryType;
	private String implLang;
	private String compiler;
	
	private String domain;
	private String robotType;
	
	
	public OPRoSExeEnvironmentElementModel(){
		super();
		initializeData();
	}
	public void initializeData(){
          this.setName("ExeEnvironment");
          LOGIC_ICON	= ProjectManager.getInstance().getImage(10110);
//      	OPRoSExeEnvironmentOSElementModel os = new OPRoSExeEnvironmentOSElementModel();
//		os.setOs("NoNameOS");
//		os.setOsVersion("NoVersion");
//		OPRoSExeEnvironmentCPUElementModel cpu = new OPRoSExeEnvironmentCPUElementModel();
//		cpu.setCpu("NoCPU");
//		this.addChild(os);
//		this.addChild(cpu);
	}
	
	public String writeModel(){
		StringBuffer sb = new StringBuffer();
//		int i = this.getChildren().size();
		for(int i=0;i<this.getChildren().size();i++){
			Object obj = this.getChildren().get(i);
			if(obj instanceof OPRoSExeEnvironmentCPUElementModel){
				OPRoSExeEnvironmentCPUElementModel ops = (OPRoSExeEnvironmentCPUElementModel)obj;	
				sb.append(ops.writeModel());
			}
			else if(obj instanceof OPRoSExeEnvironmentOSElementModel){
				OPRoSExeEnvironmentOSElementModel ops = (OPRoSExeEnvironmentOSElementModel)obj;	
				sb.append(ops.writeModel());
			}
		}
//		sb.append("property OPRoSServiceTypeElementModel="+this.getServiceTypeFileName()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		return sb.toString();
	}
	
	public void doSave(Element parentEle){
		Element ele;
		ele = new Element("library_type");
		ele.setText(this.getLibType());
		parentEle.addContent(ele);
		ele = new Element("library_name");
		ele.setText(this.getlibNameProp());
		parentEle.addContent(ele);
		ele = new Element("impl_language");
		ele.setText(this.getimplementLangString());
		parentEle.addContent(ele);
		ele = new Element("compiler");
		ele.setText(this.getcompilerString());
		parentEle.addContent(ele);
		ele = new Element("app_domain");
		ele.setText(this.getexeEnvDomainProp());
		parentEle.addContent(ele);
		ele = new Element("app_robot");
		ele.setText(this.getexeEnvRobotTypeProp());
		parentEle.addContent(ele);
	}
	
//	public String getDomain() {
//		return domain;
//	}
//	public void setDomain(String domain) {
//		String oldValue = this.getDomain();
//		this.domain = domain;
////		getListeners().firePropertyChange(PROPERTY_DOMAIN, oldValue, domain);
//		
//	}
//	public String getRobotType() {
//		return robotType;
//	}
//	public void setRobotType(String robotType) {
//		String oldValue = this.getRobotType();
//		this.robotType = robotType;
////		getListeners().firePropertyChange(PROPERTY_ROBOT_TYPE, oldValue, robotType);
//	}
//	public String getLibraryName() {
//		return libraryName;
//	}
//	public void setLibraryName(String libraryName) {
//		if(libraryName.compareTo(getLibraryName())==0)
//			return;
//		String oldValue = this.getLibraryName();
//		this.libraryName = libraryName;
//		int extensionIndex = libraryName.lastIndexOf(".");
//		String libType = libraryName.substring(extensionIndex+1);
//		if(libType.compareTo(getLibraryType())!=0){
//			setLibraryType(libType);
//		}
////		getListeners().firePropertyChange(PROPERTY_LIBRARY_NAME, oldValue, libraryName);
//	}
//	public String getLibraryType() {
//		return libraryType;
//	}
//	public void setLibraryType(String libraryType) {
//		if(libraryType.compareTo(getLibraryType())==0)
//			return;
//		String oldValue = getLibraryType();
//		this.libraryType = libraryType;
//		int extensionIndex = libraryName.lastIndexOf(".");
//		String libType = libraryName.substring(extensionIndex+1);
//		if(libType.compareTo(libraryType)!=0){
//			libType = libraryName.substring(0, extensionIndex)+"."+libraryType;
//			setLibraryName(libType);
//		}
////		getListeners().firePropertyChange(PROPERTY_LIBRARY_TYPE, oldValue, libraryType);
//	}
//	public String getImplLang() {
//		return implLang;
//	}
//	public void setImplLang(String implLang) {
//		String oldValue = this.getImplLang();
//		this.implLang = implLang;
////		getListeners().firePropertyChange(PROPERTY_IMPL_LANGUAGE, oldValue, implLang);
//	}
//	public String getCompiler() {
//		return compiler;
//	}
//	public void setCompiler(String compiler) {
//		String oldValue = this.getCompiler();
//		this.compiler = compiler;
////		getListeners().firePropertyChange(PROPERTY_COMPILER, oldValue, compiler);
//	}
	
//	public Image getIconImage() {
//		return LOGIC_ICON;
//	}
	
	public String toString() {
		return "ExeEnvironment";
	}

}
