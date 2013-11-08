package kr.co.n3soft.n3com.xml;

import java.io.CharArrayWriter;

import kr.co.n3soft.n3com.projectmanager.ApplicationProfile;
import kr.co.n3soft.n3com.projectmanager.BendPointView;
import kr.co.n3soft.n3com.projectmanager.ComponentProfile;
import kr.co.n3soft.n3com.projectmanager.CompositeComponentProfile;
import kr.co.n3soft.n3com.projectmanager.CompositeComponentViewProfile;
import kr.co.n3soft.n3com.projectmanager.ExportPort;
import kr.co.n3soft.n3com.projectmanager.ExportPortView;
import kr.co.n3soft.n3com.projectmanager.NoteView;
import kr.co.n3soft.n3com.projectmanager.NoteViewLine;
import kr.co.n3soft.n3com.projectmanager.PortConnection;
import kr.co.n3soft.n3com.projectmanager.PortConnectionView;
import kr.co.n3soft.n3com.projectmanager.PortProfile;
import kr.co.n3soft.n3com.projectmanager.PortProfileView;
import kr.co.n3soft.n3com.projectmanager.Property;
import kr.co.n3soft.n3com.projectmanager.Subcomponent;
import kr.co.n3soft.n3com.projectmanager.SubcomponentView;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class ComponentXMLContentHandler extends DefaultHandler {
	boolean is_composite_component_profile = false;
	boolean is_composite_component_view_profile = false;
	boolean is_application_profile = false;
	boolean is_component_profile = false;
	boolean is_sub_comp = false;
	boolean is_export_port = false;
	boolean is_execution_semantics = false;
	boolean is_property = false;
	boolean is_method_port = false;
	boolean is_event_port = false;
	boolean is_data_port = false;
	boolean is_port_connection = false;
	boolean is_port = false;
	boolean is_portView = false;
	boolean is_fsm= false;
	boolean is_bend_points= false;
	boolean is_bend_point= false;

	boolean is_properties= false;
	boolean is_monitorings= false;



	boolean is_note= false;
	//	boolean is_note_line= false;
	boolean is_note_target_connection = false;
	boolean is_note_source_connection = false;
	CompositeComponentProfile ccp = null;
	CompositeComponentViewProfile cview = null;
	ApplicationProfile ap = null;
	ComponentProfile cp = null;
	Subcomponent scp = null;
	ExportPort ep = null;
	PortConnection pc = null;
	PortProfile pp = null;


	SubcomponentView scpv = null;
	ExportPortView epv = null;
	PortConnectionView pcv = null;
	PortProfileView ppv = null;

	NoteView nv = null;
	NoteViewLine nvl = null;
	Property property = null;



	ComponentProfile retObj = null;
	CompositeComponentViewProfile retObjView = null;
	private CharArrayWriter contents = new CharArrayWriter();
	//	[출처] SAX파서를 이용하여 XML문서 파싱하기 1|작성자 miniblog
	public void startElement( String namespaceURI,
			String localName,
			String qName,
			Attributes attr ) throws SAXException {

		contents.reset();
		if(localName.equals("application_profile")){
			ap = new ApplicationProfile();
			is_application_profile = true;
			System.out.println("------->application_profile");
		}
		else if(localName.equals("component_profile")){
			System.out.println("------->component_profile");
			is_component_profile = true;
			cp = new ComponentProfile();
			is_composite_component_profile = false;
		}
		else if(localName.equals("composite_component_profile")){
			is_composite_component_profile = true;
			ccp = new CompositeComponentProfile();
			is_component_profile = false;

		}
		else if(localName.equals("composite_component_view_profile")){
			is_composite_component_view_profile = true;
			cview = new CompositeComponentViewProfile();


		}

		if(is_application_profile){
			if(localName.equals("os")){
				String name = attr.getValue("name");
				String version = attr.getValue("version");
				ap.execution_environment_os_name = name;
				ap.execution_environment_os_version = version;
			}
			else if(localName.equals("subcomponent")){

				scp = new Subcomponent();
				is_sub_comp = true;
				//			
			}
			else if(localName.equals("port_connection")){

				pc = new PortConnection();
				String port_type = attr.getValue("port_type");
				pc.port_type = port_type;
				is_port_connection = true;

			}
			else if(is_port_connection){
				if(localName.equals("source")){
					String component_name = attr.getValue("component_name");
					String port_name = attr.getValue("port_name");
					pc.source_component_name = component_name;
					pc.source_port_name = port_name;
				}
				else if(localName.equals("target")){
					String component_name = attr.getValue("component_name");
					String port_name = attr.getValue("port_name");
					pc.target_component_name = component_name;
					pc.target_port_name = port_name;
				}


			}
		}
		else if(is_composite_component_profile){
			if(localName.equals("os")){
				String name = attr.getValue("name");
				String version = attr.getValue("version");
				ccp.execution_environment_os_name = name;
				ccp.execution_environment_os_version = version;
			}
			else if(localName.equals("subcomponent")){

				scp = new Subcomponent();
				is_sub_comp = true;
				//			
			}
			else if(localName.equals("export_port")){

				ep = new ExportPort();
				is_export_port = true;
				//				
			}
			else if(localName.equals("port_connection")){

				pc = new PortConnection();
				String port_type = attr.getValue("port_type");
				pc.port_type = port_type;
				is_port_connection = true;

			}
			else if(localName.equals("properties")){

				this.is_properties = true;	

			}
			//ijs monitoring->exports
			else if(localName.equals("exports") || localName.equals("monitoring")){

				this.is_monitorings = true;	

			}
			else if(localName.equals("var")){

				if(this.is_monitorings){
					String name = attr.getValue("name");
					String type = attr.getValue("type");
					property = new Property();
					property.name = name;
					property.type = type;
					//					String value = this.contents.toString();
					//					System.out.println("ddd");


				}

			}
			else if(localName.equals("property")){

				if(this.is_properties){
					String name = attr.getValue("name");
					String type = attr.getValue("type");
					property = new Property();
					property.name = name;
					property.type = type;
					//					String value = this.contents.toString();
					//					System.out.println("ddd");


				}

			}


			if(is_port_connection){
				if(localName.equals("source")){
					String component_name = attr.getValue("component_name");
					String port_name = attr.getValue("port_name");
					pc.source_component_name = component_name;
					pc.source_port_name = port_name;
				}
				else if(localName.equals("target")){
					String component_name = attr.getValue("component_name");
					String port_name = attr.getValue("port_name");
					pc.target_component_name = component_name;
					pc.target_port_name = port_name;
				}


			}

		}

		else if(is_component_profile){
			if(localName.equals("os")){


				String name = attr.getValue("name");
				String version = attr.getValue("version");
				cp.execution_environment_os_name = name;
				cp.execution_environment_os_version = version;
			}
			else if(localName.equals("execution_semantics")){
				if(is_execution_semantics)
					is_execution_semantics = false;
				else
					is_execution_semantics = true;
			}
			else if(localName.equals("properties")){

				this.is_properties = true;	

			}
			else if(localName.equals("property")){

				if(this.is_properties){
					String name = attr.getValue("name");
					String type = attr.getValue("type");
					property = new Property();
					property.name = name;
					property.type = type;
					//					String value = this.contents.toString();
					//					System.out.println("ddd");


				}

			}
			else if(localName.equals("exports") || localName.equals("monitoring")){

				this.is_monitorings = true;	

			}
			else if(localName.equals("var")){

				if(this.is_monitorings){
					String name = attr.getValue("name");
					String type = attr.getValue("type");
					property = new Property();
					property.name = name;
					property.type = type;
					//					String value = this.contents.toString();
					//					System.out.println("ddd");


				}

			}
			else if(localName.equals("service_port")){

				pp = new PortProfile();
				pp.portType = 0;
				this.is_port = true;

			}
			else if(localName.equals("event_port")){


				pp = new PortProfile();
				pp.portType = 1;
				this.is_port = true;




			}
			else if(localName.equals("data_port")){

				pp = new PortProfile();
				pp.portType = 2;
				this.is_port = true;






			}

//			else if(localName.equals("var")){
//
//				String name = attr.getValue("name");
//				cp.property_name = name;
//				cp.property_value =  contents.toString();
//
//			}
			else if(localName.equals("property")){

				String name = attr.getValue("name");
				cp.property_name = name;
				cp.property_value =  contents.toString();

			}
			else if(localName.equals("fsm")){

				this.is_fsm = true;

			}



		}
		else if(is_composite_component_view_profile){
			if(localName.equals("subcomponent")){

				scpv = new SubcomponentView();
				is_sub_comp = true;

			}
			else if(localName.equals("export_port")){

				epv = new ExportPortView();
				is_export_port = true;

			}
			else if(localName.equals("port_connection")){

				pcv = new PortConnectionView();
				String port_type = attr.getValue("port_type");
				pcv.port_type = port_type;
				is_port_connection = true;
				//				}
			}
			else if(localName.equals("port")){
				is_portView = true;
				ppv = new PortProfileView();

			}
			else if(localName.equals("note")){
				this.is_note = true;
				nv = new NoteView();

			}
			else if(localName.equals("note_target_connection")){
				this.is_note_target_connection = true;
				nvl = new NoteViewLine();
				nvl.note_connection_type = 0;

			}
			else if(localName.equals("note_source_connection")){
				this.is_note_source_connection = true;
				nvl = new NoteViewLine();
				nvl.note_connection_type = 1;

			}
			else if(this.is_note_target_connection){
				if(localName.equals("source")){


					String component_name = attr.getValue("component_name");
					String port_name = attr.getValue("port_name");
					String type = attr.getValue("type");
					int linkType = -1;
					if("component".equals(type)){
						linkType = 1;
					}
					else{
						linkType = 0;
					}
					nvl.link_type = linkType;
					nvl.source_component_name = component_name;
					nvl.source_port_name = port_name;


				}
				else if(localName.equals("bend_point")){
					String value = attr.getValue("value");
					BendPointView bpv = new BendPointView();

					bpv.bendpoint = value;
					bpv.setLineBendpointModel();
					nvl.getBendpoints().add(bpv.lbm);


				}
				//				else if(localName.equals("targetNote")){
				//					nvl.target_component_name = contents.toString();
				//				}
			}
			else if(this.is_note_source_connection){
				if(localName.equals("target")){


					String component_name = attr.getValue("component_name");
					String port_name = attr.getValue("port_name");
					String type = attr.getValue("type");
					int linkType = -1;
					if("component".equals(type)){
						linkType = 1;
					}
					else{
						linkType = 0;
					}
					nvl.link_type = linkType;
					nvl.target_component_name = component_name;
					nvl.target_port_name = port_name;


				}
				else if(localName.equals("bend_point")){
					String value = attr.getValue("value");
					BendPointView bpv = new BendPointView();

					bpv.bendpoint = value;
					bpv.setLineBendpointModel();
					nvl.getBendpoints().add(bpv.lbm);


				}
				//				else if(localName.equals("sourceNote")){
				//					nvl.target_component_name = contents.toString();
				//				}
			}
			if(is_port_connection){
				if(localName.equals("source")){
					String component_name = attr.getValue("component_name");
					String port_name = attr.getValue("port_name");
					pcv.source_component_name = component_name;
					if("RM_MonitoringMethodPort".equals(port_name.toString())){
						ep.port_name = "Monitoring";
					}
					else if("RM_LifecycleMethodPort".equals(port_name.toString())){
						ep.port_name = "Lifecycle";
					}
					else
						pcv.source_port_name = port_name;
				}
				else if(localName.equals("target")){
					String component_name = attr.getValue("component_name");
					String port_name = attr.getValue("port_name");
					pcv.target_component_name = component_name;

					if("RM_MonitoringMethodPort".equals(port_name.toString())){
						ep.port_name = "Monitoring";
					}
					else if("RM_LifecycleMethodPort".equals(port_name.toString())){
						ep.port_name = "Lifecycle";
					}
					else
						pcv.target_port_name = port_name;
				}
				else if(localName.equals("bend_point")){
					String value = attr.getValue("value");
					BendPointView bpv = new BendPointView();

					bpv.bendpoint = value;
					bpv.setLineBendpointModel();
					pcv.getBendpoints().add(bpv.lbm);


				}

			}
			
			

		}



	}

	public void endElement( String namespaceURI,
			String localName,
			String qName ) throws SAXException {
		if(is_application_profile){

			if(localName.equals("name")){
				if(is_sub_comp){
					scp.name = contents.toString();
				}
				else{
					if(ap.name==null || ap.name.trim().equals(""))
						ap.name = contents.toString();
				}
			}
			else if(localName.equals("id")){
				if(is_sub_comp){
					scp.id = contents.toString();
				}
				else{
					if(ap.id==null || ap.id.trim().equals(""))
						ap.id = contents.toString();
				}
			}
			else if(localName.equals("version")){
				if(is_sub_comp){
					scp.version = contents.toString();
				}
				else{
					if(ap.version==null || ap.version.trim().equals(""))
						ap.version = contents.toString();
				}
			}
			else if(localName.equals("type")){
				if(is_sub_comp){
					scp.type = contents.toString();
				}
				
			}
			else if(localName.equals("reference")){
				if(is_sub_comp){
					scp.reference = contents.toString();
				}

			}
			
			
			if(localName.equals("application_profile") && this.is_application_profile){
				is_application_profile = false;
				retObj = this.ap;
				this.ap = null;
			}
			else if(localName.equals("subcomponent")){
				if(this.is_sub_comp){
					is_sub_comp = false;
					ap.subcomponents.add(scp);
					scp = null;
				}

			}
			else if(localName.equals("port_connection")){
				if(this.is_port_connection){
					is_port_connection = false;

					ap.portConnections.add(pc);
					pc = null;
				}

			}


		
		}
		else if(is_composite_component_profile){
			if(localName.equals("name")){
				if(is_sub_comp){
					scp.name = contents.toString();
				}
				else{
					if(ccp.name==null || ccp.name.trim().equals(""))
						ccp.name = contents.toString();
				}
			}
			else if(localName.equals("id")){
				if(is_sub_comp){
					scp.id = contents.toString();
				}
				else{
					if(ccp.id==null || ccp.id.trim().equals(""))
						ccp.id = contents.toString();
				}
			}
			else if(localName.equals("version")){
				if(is_sub_comp){
					scp.version = contents.toString();
				}
				else{
					if(ccp.version==null || ccp.version.trim().equals(""))
						ccp.version = contents.toString();
				}
			}
			else if(localName.equals("reference")){
				if(is_sub_comp){
					scp.reference = contents.toString();
				}

			}
			else if(localName.equals("description")){
				ccp.description = contents.toString();
			}
			else if(localName.equals("cpu")){
				ccp.execution_environment_cpu = contents.toString();
			}
			else if(localName.equals("library_type")){
				ccp.execution_environment_library_type = contents.toString();
			}
			else if(localName.equals("impl_language")){
				ccp.execution_environment_impl_language = contents.toString();
			}
			else if(localName.equals("compiler")){
				ccp.execution_environment_compiler = contents.toString();
			}
			else if(localName.equals("property")){

				if(this.is_properties){
					if(property!=null){
						property.value =  contents.toString();
						ccp.propetries.add(property);
						property = null;
					}

					//					String value = this.contents.toString();
					//					System.out.println("ddd");


				}
				
				if(localName.equals("priority")){
					ccp.execution_semantics_priority = contents.toString();
				}
			}
			
			
			if(this.is_export_port){
				if(localName.equals("component_name")){
					ep.component_name = contents.toString();
				}
				else if(localName.equals("port_name")){
					if("RM_MonitoringMethodPort".equals(contents.toString())){
						ep.port_name = "Monitoring";
					}
					else if("RM_LifecycleMethodPort".equals(contents.toString())){
						ep.port_name = "Lifecycle";
					}
					else
						ep.port_name = contents.toString();
				}
				else if(localName.equals("port_type")){
					ep.port_type = contents.toString();
				}
				else if(localName.equals("port_dir")){
					ep.port_dir = contents.toString();
				}
				else if(localName.equals("export_port_name")){
					ep.export_port_name = contents.toString();
				}
			}
			if(localName.equals("composite_component_profile") && this.is_composite_component_profile){
				is_composite_component_profile = false;
				retObj = this.ccp;
				this.ccp = null;
			}
			else if(localName.equals("subcomponent")){
				if(this.is_sub_comp){
					is_sub_comp = false;
					ccp.subcomponents.add(scp);
					scp = null;
				}

			}
			else if(localName.equals("export_port")){
				if(this.is_export_port){
					is_export_port = false;
					ccp.exportPorts.add(ep);
					ep = null;
				}
				else{
					ep = new ExportPort();
					is_export_port = true;
				}
			}
			else if(localName.equals("port_connection")){
				if(this.is_port_connection){
					is_port_connection = false;

					ccp.portConnections.add(pc);
					pc = null;
				}

			}


		}
		



		else if(is_component_profile){
			if(localName.equals("name")){
				if(this.is_port){

				}
				else if(this.is_fsm){
					cp.fsm_name = contents.toString();
				}
				else{
					if(cp.name==null || cp.name.trim().equals(""))
						cp.name = contents.toString();
				}

			}
			else if(localName.equals("id")){

				if(cp.id==null || cp.id.trim().equals(""))
					cp.id = contents.toString();

			}
			else if(localName.equals("version")){

				if(cp.version==null || cp.version.trim().equals(""))
					cp.version = contents.toString();

			}
			else if(localName.equals("reference")){
				if(is_sub_comp){
					scp.reference = contents.toString();
				}

			}
			else if(localName.equals("description")){
				cp.description = contents.toString();
			}
			else if(localName.equals("cpu")){

				cp.execution_environment_cpu = contents.toString();
			}
			else if(localName.equals("library_type")){
				cp.execution_environment_library_type = contents.toString();
			}
			else if(localName.equals("library_name")){

				cp.execution_environment_library_name = contents.toString();
				System.out.println("dddddd");

			}
			else if(localName.equals("impl_language")){
				cp.execution_environment_impl_language = contents.toString();
			}
			else if(localName.equals("compiler")){
				cp.execution_environment_compiler = contents.toString();
			}
			else if(localName.equals("property")){

				if(this.is_properties){
					if(property!=null){
						property.value =  contents.toString();
						cp.propetries.add(property);
						property = null;
					}

					//					String value = this.contents.toString();
					//					System.out.println("ddd");


				}

			}
			
			else if(localName.equals("var")){

				if(this.is_monitorings){
					if(property!=null){
						property.value =  contents.toString();
						cp.variables.add(property);
						property = null;
					}

					//					String value = this.contents.toString();
					//					System.out.println("ddd");


				}

			}
			System.out.println("contents===>"+contents.toString());
			if(this.is_port){
				if(localName.equals("name")){
					pp.name = contents.toString();
					System.out.println("port_name===>"+pp.name);
				}
				else if(localName.equals("type")){
					pp.type = contents.toString();
				}
				else if(localName.equals("usage")){
					pp.usage = contents.toString();
				}
				else if(localName.equals("reference")){
					pp.reference = contents.toString();
				}
				else if(localName.equals("description")){
					pp.reference = contents.toString();
				}
				else if(localName.equals("data_type")){
					pp.type = contents.toString();
				}
				else if(localName.equals("queueing_policy")){
					pp.queueing_policy = contents.toString();
				}
				else if(localName.equals("queue_size")){
					pp.queue_size = contents.toString();
				}

			}
			if(this.is_fsm){
				if(localName.equals("type")){
					cp.fsm_type = contents.toString();
				}

				else if(localName.equals("reference")){
					cp.fsm_reference = contents.toString();
				}
			}

			if(localName.equals("component_profile") && this.is_component_profile){
				is_component_profile = false;
				retObj = this.cp;

			}
			else if(localName.equals("service_port")){
				if(this.is_port){
					cp.ports.add(pp);
					this.is_port = false;
				}

			}
			else if(localName.equals("event_port")){

				if(this.is_port){
					cp.ports.add(pp);
					this.is_port = false;
				}
			}
			else if(localName.equals("data_port")){
				if(this.is_port){
					cp.ports.add(pp);
					this.is_port = false;
				}
				else if(localName.equals("fsm")){
					if(this.is_fsm)
						this.is_fsm = false;

				}

			}
			else if(localName.equals("defined_service_type")){
				cp.defined_service_types.add(contents.toString());
				
				
			}
			else if(localName.equals("defined_data_type")){
				cp.defined_data_types.add(contents.toString());
			}

			if(localName.equals("priority")){
				cp.execution_semantics_priority = contents.toString();
			}

		}
		if(is_composite_component_view_profile){
			if(localName.equals("name")){
				if(is_sub_comp){
					scpv.name = contents.toString();
				}
				else
					cview.name = contents.toString();
			}
			else if(localName.equals("viewInfo")){
				cview.value  = contents.toString();
			}
			else if(localName.equals("subcomponentview")){
				scpv.subcomponentview  = contents.toString();
			}
			else if(this.is_note){
				if(localName.equals("text")){
					nv.text = contents.toString();
				}
				else if(localName.equals("noteId")){
					String nid = contents.toString();
					if(nid!=null){
						int index = nid.indexOf("![CDATA[");
						if(index!=-1){
							int l = nid.lastIndexOf("[");
							int f = nid.indexOf("]");
							String n = nid.substring(l+1, f);
							nv.noteId = n.trim();
						}
					}

				}
				else if(localName.equals("noteViewInfo")){
					nv.noteViewInfo = contents.toString();
				}
				else if(localName.equals("note")){
					nv.setViewValue();
					this.cview.notes.add(nv);
					this.is_note = false;
				}
			}
			else if(this.is_note_target_connection){
				if(localName.equals("note_target_connection")){
					this.is_note_target_connection = false;
					cview.noteConnects.add(nvl);

				}
				else if(localName.equals("targetNote")){
					//					nvl.target_component_name = contents.toString();
					String nid = contents.toString();
					if(nid!=null){
						int index = nid.indexOf("![CDATA[");
						if(index!=-1){
							int l = nid.lastIndexOf("[");
							int f = nid.indexOf("]");
							String n = nid.substring(l+1, f);
							nvl.target_component_name = n.trim();
						}
					}
				}


			}
			else if(this.is_note_source_connection){
				if(localName.equals("note_source_connection")){
					this.is_note_source_connection = false;
					cview.noteConnects.add(nvl);

				}
				else if(localName.equals("sourceNote")){
					//					nvl.source_component_name = contents.toString();
					String nid = contents.toString();
					if(nid!=null){
						int index = nid.indexOf("![CDATA[");
						if(index!=-1){
							int l = nid.lastIndexOf("[");
							int f = nid.indexOf("]");
							String n = nid.substring(l+1, f);
							nvl.source_component_name = n.trim();
						}
					}
				}

			}
			else if(this.is_portView){
				if(localName.equals("component_name")){
					ppv.component_name = contents.toString();
				}
				else if(localName.equals("port_name")){
					ppv.port_name = contents.toString();
				}
				else if(localName.equals("port_type")){
					ppv.port_type = contents.toString();
				}
				else if(localName.equals("port_dir")){
					ppv.port_dir = contents.toString();
				}
				else if(localName.equals("portview")){
					ppv.port_view = contents.toString();
				}
			}
			if(this.is_export_port){
				if(localName.equals("component_name")){
					epv.component_name = contents.toString();
				}
				else if(localName.equals("port_name")){
					epv.port_name = contents.toString();
				}
				else if(localName.equals("port_type")){
					epv.port_type = contents.toString();
				}
				else if(localName.equals("port_dir")){
					epv.port_dir = contents.toString();
				}
				else if(localName.equals("export_port_name")){
					epv.export_port_name = contents.toString();
				}
				else if(localName.equals("export_portview")){
					epv.export_portview = contents.toString();
				}
			}
			if(localName.equals("composite_component_view_profile") && this.is_composite_component_view_profile){
				is_composite_component_view_profile = false;

				this.cview.setViewValue();
				retObjView = this.cview;
				this.cview = null;
			}
			else if(localName.equals("subcomponent")){
				if(this.is_sub_comp){
					is_sub_comp = false;
					scpv.setViewValue();
					cview.subcomponents.add(scpv);

					scpv = null;
				}

			}
			else if(localName.equals("port")){
				if(this.is_portView){
					is_portView = false;
					scpv.getPorts().add(ppv);
					ppv.setPortProfileView();
					scpv.portViewes.put(ppv.port_name, ppv);
					ppv = null;

				}
				else{
					ppv = new PortProfileView();
					is_portView = true;
				}

			}
			else if(localName.equals("export_port")){
				if(this.is_export_port){
					is_export_port = false;
					epv.setExportPortView();
					cview.exportPorts.add(epv);
					cview.portViewes.put(epv.export_port_name, epv);
					epv = null;
				}
				else{
					epv = new ExportPortView();
					is_export_port = true;
				}
			}
			else if(localName.equals("port_connection")){
				if(this.is_port_connection){
					is_port_connection = false;

					cview.portConnections.add(pcv);
					pcv = null;
				}

			}


		}


	}

	public void characters( char[] ch, int start, int length ) 
	throws SAXException {
		contents.write(ch, start, length);


	}

	public ComponentProfile getRetObj(){
		return this.retObj;
	}
	public CompositeComponentViewProfile getRetObjView(){
		return this.retObjView;
	}


}
