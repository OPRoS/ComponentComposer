package kr.co.n3soft.n3com.projectmanager;

public class ComponentProfile {
	public String name = "";
	public String description = "";
	public String execution_environment_os_name = "";
	public String execution_environment_os_version = "";
	public String execution_environment_cpu = "";
	public String execution_environment_library_type = "";
	public String execution_environment_library_name = "";
	public String execution_environment_impl_language = "";
	public String execution_environment_compiler= "";
	
	public String execution_semantics_type = "";
	public String execution_semantics_period = "";
	public String execution_semantics_priority = "";
	public String execution_semantics_creation_type = "";
	public String execution_semantics_creation_lifecycle_type= "";
	
	public String property_name= "";
	public String property_value= "";
	
	public String fsm_name= "";
	public String fsm_type= "";
	public String fsm_reference= "";
	
	public String id= "";
	public String version= "";
	
//	public String library_type= "";
//	public String library_name= "";
//	public String fsm_type= "";
	
//	public java.util.ArrayList<Subcomponent> subcomponents = new java.util.ArrayList<Subcomponent>();
	public java.util.ArrayList<PortProfile> ports = new java.util.ArrayList<PortProfile>();
	public java.util.ArrayList propetries = new java.util.ArrayList();
	public java.util.ArrayList variables = new java.util.ArrayList();
	
	public java.util.ArrayList defined_service_types = new java.util.ArrayList();
	public java.util.ArrayList defined_data_types = new java.util.ArrayList();
	
	
//	public java.util.ArrayList<PortConnection> portConnections = new java.util.ArrayList<PortConnection>();
}
