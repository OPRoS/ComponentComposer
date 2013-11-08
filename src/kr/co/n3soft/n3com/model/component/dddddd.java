package kr.co.n3soft.n3com.model.component;

import java.io.FileInputStream;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class dddddd {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		FileDialog dialog = new FileDialog(getShell(),SWT.OPEN);
//		dialog.setFilterExtensions(new String[]{OPRoSStrings.getString("ServiceTypeInputDlgFileType")});
//		IWorkspaceRoot rootWorkspace = ResourcesPlugin.getWorkspace().getRoot();//워크스페이스 핸들
//		dialog.setFilterPath(rootWorkspace.getLocation().toString());
		String filePath ="c:\\tttt.xml";
		String type = "dddsss";
		MethodModel mm = new MethodModel();
		if(!filePath.isEmpty()){
			SAXBuilder builder = new SAXBuilder();
			Document doc = null;
			try {
				doc = builder.build( new FileInputStream(filePath));
			} catch (JDOMException e2) {
				e2.printStackTrace();
			} catch (IOException e3) {
				e3.printStackTrace();
			}
		Element root;
			root = doc.getRootElement();
			System.out.println("root=====>"+root);
			java.util.List list = root.getChildren("service_port_type");
			for(int i=0;i<list.size();i++){
				Element child = (Element)list.get(i);
				String text = child.getChildText("type_name");
				System.out.println("text=====>"+text);
				if(type.equals(text)){
					java.util.List methods = child.getChildren("method");
					for(int i1=0;i1<methods.size();i1++){
						Element method = (Element)methods.get(i1);
					mm.name = method.getAttributeValue("name");
				    mm.returnType = method.getAttributeValue("return_type");
				    System.out.println("method=====>"+methods);
					java.util.List params = child.getChildren("param");
					for(int i2=0;i2<params.size();i1++){
						ParamModel pm = new ParamModel();
						Element param = (Element)params.get(i2);
						pm.name = param.getChildText("name");
						pm.type = param.getChildText("type");
						mm.getParams().add(pm);
						System.out.println("param=====>"+params);
					}
					}
					
				}
//				child.get
			}
			System.out.println("list=====>"+list);
		

	}
	}
	}

