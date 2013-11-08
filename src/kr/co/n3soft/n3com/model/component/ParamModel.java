package kr.co.n3soft.n3com.model.component;

public class ParamModel {
	public String type = "";
	public String name = "";
	public String writeSource(){
		StringBuffer sb = new StringBuffer();
		sb.append(type+" "+name);
		return sb.toString();
	}

}

