package kr.co.n3soft.n3com.model.comm;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import kr.co.n3soft.n3com.projectmanager.PortProfile;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class DocComponentModel {
	String id = "";
	String name = "";
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	java.util.ArrayList portlists = new java.util.ArrayList();
	
	
	public java.util.ArrayList getPortlists() {
		return portlists;
	}


	public void setPortlists(java.util.ArrayList portlists) {
		this.portlists = portlists;
	}

	public int divisionPort(String xmlPath){
		SAXBuilder builder = new SAXBuilder();
		
		try {
			Document doc = builder.build( new FileInputStream(xmlPath));
			Element main;
			main = doc.getRootElement();
			
			if("data_type".equals(main.getName())){
				return 1;
			}
			else if("service_port_type_profile".equals(main.getName())){
				return 2;
			}
			
		} catch (JDOMException e2) {
			e2.printStackTrace();
		} catch (IOException e3) {
			e3.printStackTrace();
		}
			
		return -1;
	}
	
	public void load(String xmlPath){
		//		TreeSelection iSelection = (TreeSelection)viewer.getSelection();
		//		UMLTreeParentModel treeObject = (UMLTreeParentModel)iSelection.getFirstElement();
		//		String xmlPath = "D:\\etricompile\\compComposite-workspace\\runtime-EclipseApplication\\Interfaces\\ddddssss\\profile\\ddddssss.xml";
		SAXBuilder builder = new SAXBuilder();
		//		Document doc = null;
		try {
			Document doc = builder.build( new FileInputStream(xmlPath));
			Element main;
			Element root;
			main = doc.getRootElement();
//			Iterator<Element> items = root.get
			Element ele;
//			Element main;
			Iterator<Element> subItems;
			//		InterfaceItemModel nodeItemModel = null;
			
//			while(items.hasNext()){
//				main = items.next();

				if("component_profile".equals(main.getName())){
					try{
					Element eleId = main.getChild("id");
					id = getChild("id",main);
					}
					catch(Exception e){
						e.printStackTrace();
						id = "";
					}
					Element eleName = main.getChild("name");
					name = getChild("name",main);
					Element ports =main.getChild("ports");
					
//					ports.getch
					subItems = ports.getChildren().iterator();
					while(subItems.hasNext()){
						ele = subItems.next();
						String text = ele.getName();
						/*
						 * 0  method_port
						 * 1  event_port
						 * 2  data_port
						 * 
						 */
						if("data_port".equals(text)){
							PortProfile pf = new PortProfile();
							pf.portType = 2;
							pf.name =  getChild("name",ele);
							try{
							pf.desc = getChild("description",ele);
							}
							catch(Exception e){
								e.printStackTrace();
							}
							pf.type = getChild("data_type",ele);
							pf.usage = getChild("usage",ele);
							if(ele.getChild("reference")!=null)
							pf.reference = getChild("reference",ele);
							//110823 SDM S
							if(pf.usage.equals("input")){
								pf.queueing_policy =getChild("queueing_policy",ele);
								pf.queue_size = getChild("queue_size",ele);
							}
							//110823 SDM E
							portlists.add(pf);
						}
						else if("event_port".equals(text)){
							PortProfile pf = new PortProfile();
							pf.portType = 1;
							pf.name = getChild("name",ele);
							pf.desc = getChild("description",ele);
							pf.type = getChild("data_type",ele);
							pf.usage = getChild("usage",ele);
							portlists.add(pf);
						}
						else if("service_port".equals(text)){
							PortProfile pf = new PortProfile();
							pf.portType = 0;
							pf.name = getChild("name",ele);
							pf.desc = getChild("description",ele);
							pf.type = getChild("type",ele);
							pf.usage = getChild("usage",ele);
							if(ele.getChild("reference")!=null)
							pf.reference = getChild("reference",ele);
							portlists.add(pf);
						}
						
					}
					
					
				}
				
//			}
		} catch (JDOMException e2) {
			e2.printStackTrace();
		} catch (IOException e3) {
			e3.printStackTrace();
		}
		catch (Exception e4) {
			e4.printStackTrace();
		}

		



	}
	
	public String getChild(String key,Element ele){
		if(ele!=null && ele.getChild(key)!=null){
			return ele.getChild(key).getText();
		}
		return "";
		
		
	}

}
