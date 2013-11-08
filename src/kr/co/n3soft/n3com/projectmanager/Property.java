package kr.co.n3soft.n3com.projectmanager;

import kr.co.n3soft.n3com.project.dialog.DetailPropertyTableItem;

public class Property {
	public String name;
	public String type;
	public String value;
	
	
	public DetailPropertyTableItem getDetailPropertyTableItem(){
		DetailPropertyTableItem dpt = new DetailPropertyTableItem();
		dpt.desc = value;
		dpt.tapName = name;
		dpt.sTagType = type;
		return dpt;
	}

}
