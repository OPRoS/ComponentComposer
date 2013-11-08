package javac.parser;

public class ImportData {
	public String data = "";
//	public boolean all = true;
	public String key = "";
	public String pkg="";
	
	public void initData(String p){
		String[] list = p.split(",");
		String key = list[list.length-1];
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<list.length-1;i++){
			String dd = list[i];
			sb.append(dd);
			if(i<list.length-2){
				sb.append(",");
			}
		}
		this.pkg = sb.toString();
	}

}
