package kr.co.n3soft.n3com.projectmanager;

public class ExportPort {
	public String component_name = "";
	public String port_name = "";
	public String port_type = "";
	public String port_dir = "";
	public String export_port_name = "";
	
	public PortConnection getPortConnection(){
		PortConnection pc = new PortConnection();
		pc.port_type = this.port_type;
		pc.target_component_name = component_name;
		pc.target_port_name = port_name;
		pc.source_port_name = export_port_name;
		
		return pc;
	}
	
}
