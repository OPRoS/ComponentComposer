package kr.co.n3soft.n3com.model.umlclass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.co.n3soft.n3com.model.communication.MessageCommunicationModel;
import kr.co.n3soft.n3com.parser.IN3ModelTokenType;
import kr.co.n3soft.n3com.project.dialog.OperationDialog;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

public class OperationEditableTableItem extends ArrayItem {
    public String name;
    public Integer scope;
    public String initValue;
    public Integer type;
    public String stype;
    public String id;
    public String desc="";
    public boolean isState = false;
    public int paramsCount=0;
    java.util.ArrayList params = new java.util.ArrayList();
    MessageCommunicationModel messageCommunicationModel = null;
    
    public String stereo="";//PKY 08071101 S 오퍼레이션 스트레오타입 입력가능하도록

    
    public OperationEditableTableItem(Integer s, String n, Integer v, String i) {
        scope = s;
        name = n;
        type = v;
        initValue = i;
    }
    public OperationEditableTableItem(java.util.ArrayList p){
    	this.params = p;
    }
    public OperationEditableTableItem(){
    	
    }
    public OperationEditableTableItem(Integer s, String n, String v, String i) {
        scope = s;
        name = n;
        stype = v;
        initValue = i;
    }

    public void setIsState(boolean p) {
        this.isState = p;
    }

    public boolean getIsState() {
        return this.isState;
    }
    
    public String writeModel(){
    	StringBuffer sb = new StringBuffer();
    	sb.append("model Operation (\n");
    	sb.append("model_propertys <\n");
    	sb.append("property name="+name+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
    	sb.append("property scope="+scope+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
    	sb.append("property stype="+stype+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
    	sb.append("property initValue="+initValue+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
    	sb.append("property desc="+desc+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
    	sb.append("property stereo="+stereo+IN3ModelTokenType.PROPERTY_N3EOF+"\n");//PKY 08071101 S 오퍼레이션 스트레오타입 입력가능하도록
    	sb.append("property isState="+isState+IN3ModelTokenType.PROPERTY_N3EOF+"\n");//PKY 08071601 S 저장 불러오기 할경우 State 오퍼레이션 형식이 달라지는문제    	
    	StringBuffer paramSb = new StringBuffer();
    	for (int i = 0; i < params.size(); i++) {
            ParameterEditableTableItem p = (ParameterEditableTableItem)params.get(i);
            paramSb.append(p.writeModel());
            if(i!=params.size()-1){
            	paramSb.append(",");
            }
//            sb.append("property parameter="+p.writeModel()+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
        }
    	sb.append("property params="+paramSb.toString()+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
    	sb.append(">\n");
    	sb.append(")\n");
    	return sb.toString();
    }
    
    
    public void setString(String value){
    	if(!this.isState){
    	if(value!=null){
    		Matcher matcher;
//    		   Pattern p = Pattern.compile("[+\\-\\#|(|,|)|:]");//2008042101PKY S
    		   Pattern p = Pattern.compile("[+\\-\\#|<<|>>|(|,|)|:]");//PKY 08071101 S 오퍼레이션 스트레오타입 입력가능하도록
    		   //Pattern p = Pattern.compile("[+|-|#|(|,|)|:]");//2008042101PKY S
    	        matcher = p.matcher(value);

    	        boolean isParamEnd = false;
    	        boolean isBegin = false;
    	        boolean isFind = false;
    	        boolean isParamStart = false;
    	        int index = 0;
    	        int paramindex=0;
    	        int beforeIndex = 0;
    	        int steroStart=0;
    	        int steroEnd=0;
    	        params.clear();
    	        //2008032801 PKY S 
    	        while(matcher.find()) {
    	        	isFind = true;
    	        	System.out.print(matcher.group());
    	        	if(matcher.group().equals("+")){
    	        		scope = 0;
    		       
    		        	 index = matcher.end();
    	        		
    	        	}
    	        	else if(matcher.group().equals("-")){
    	        		scope = 2;
    		   
    		        	 index = matcher.end();
    	        	}
    	        	else if(matcher.group().equals("#")){
    	        		scope = 1;
    		        
    		        	 index = matcher.end();
    	        	}
    	        	//PKY 08071101 S 오퍼레이션 스트레오타입 입력가능하도록
    	        	if(matcher.group().equals("<")){
    	        		steroStart = matcher.end();
    	        		System.out.print("");
    	        	}
    	        	if(matcher.group().equals(">")){
    	        		steroEnd = matcher.end();
    	        		System.out.print("");
    	        		 index = matcher.end();
    	        	}	
    	        	//PKY 08071101 E 오퍼레이션 스트레오타입 입력가능하도록

    	        	if(matcher.group().equals("(")){
    	        		
    			   
    		        	name = value.substring(index, matcher.start());
    		        	System.out.println("name=>"+name);
    		        	 index = matcher.end();  		        	 
    		        	 isParamStart = true;
    		        	 paramindex=index;

    		        	
    	        	}
    	        	else if(matcher.group().equals(",")){
    	        		if(isParamStart){
    	        			paramsCount=paramsCount+1;
    	        			
    	        			String param = value.substring(paramindex,matcher.end()-1);
    	        			if(!param.equals("")){
    	        			String[] data = param.split(":");
    	        			ParameterEditableTableItem p1 = new ParameterEditableTableItem(data[0],data[1],"");
    	        			this.params.add(p1);
    	        			}
    	        			
    	        			index = matcher.end();
    	        			paramindex=index;
    	        		}
    	        		
    	        	}
    	        	else if(matcher.group().equals(":")){
    		   
    		        	 index = matcher.end();
    		        	 if(isParamEnd){
    		        		 stype = value.substring(index);
    		        		 System.out.println("sType=>"+stype);
    		        	 }
//    		        	 else{
//    		        		 if(isParamStart){
//    		        			 String paramName =  value.substring(index);
//    		        		 }
//    		        	 }
    	        	}
    	        	else if(matcher.group().equals(")")){
    	        		 index = matcher.end();
   	        			paramsCount=paramsCount+1;
   	        			String param = value.substring(paramindex,matcher.end()-1);
    	        		
   	        			if(!param.equals("")){
    	        			String[] data = param.split(":");
    	        			ParameterEditableTableItem p1 = new ParameterEditableTableItem(data[0],data[1],"");
    	        			this.params.add(p1);
    	        			
    	        		}
    	        		isParamEnd = true;
    		    
    		        	
    		        	 isParamStart=false;
    	        	}
    	        	//2008032801 PKY E


    	        }
    	      //PKY 08071101 S 오퍼레이션 스트레오타입 입력가능하도록
    	        if(steroStart>0&&steroEnd>0){
    	        	stereo= value.substring(steroStart,steroEnd-2);
    	        }else{
    	        	stereo="";
    	        }
    	      //PKY 08071101 E 오퍼레이션 스트레오타입 입력가능하도록

    	        if(!isFind){
    	        	name = value;
    	        	//PKY 08060201 S 어트리뷰트/오퍼레이션 다이어그램에서 이름없이 수정할경우 이름이 들어가지 않는문제 디폴트 이름 들어가도록 수정
                    if(name.trim().equals("")){
                   	  name="operation";
                    }
                  //PKY 08060201 E 어트리뷰트/오퍼레이션 다이어그램에서 이름없이 수정할경우 이름이 들어가지 않는문제 디폴트 이름 들어가도록 수정
    	        	scope = 0;
    	        	stype="void";
    	        	
    	        }
    	}
    	}else{

    		Matcher matcher;
//    		   Pattern p = Pattern.compile("[+\\-\\#|(|,|)|:]");//2008042101PKY S
    		 Pattern p = Pattern.compile("[+\\-\\#|<<|>>|/|(|,|)]");//PKY 08071101 S 오퍼레이션 스트레오타입 입력가능하도록
    		   //Pattern p = Pattern.compile("[+|-|#|(|,|)|:]");//2008042101PKY S
    	        matcher = p.matcher(value);

    	        boolean isParamEnd = false;
    	        boolean isBegin = false;
    	        boolean isFind = false;
    	        boolean isParamStart = false;
    	        int index = 0;
    	        int paramindex=0;
    	        int beforeIndex = 0;
    	        int steroStart=0;
    	        int steroEnd=0;
    	        params.clear();
    	        //2008032801 PKY S 
    	        while(matcher.find()) {
    	        	isFind = true;
    	        	System.out.print(matcher.group());
    	        	if(matcher.group().equals("+")){
    	        		scope = 0;
    		       
    		        	 index = matcher.end();
    	        		
    	        	}
    	        	else if(matcher.group().equals("-")){
    	        		scope = 2;
    		   
    		        	 index = matcher.end();
    	        	}
    	        	else if(matcher.group().equals("#")){
    	        		scope = 1;
    		        
    		        	 index = matcher.end();
    	        	}
    	        	//PKY 08071101 S 오퍼레이션 스트레오타입 입력가능하도록
    	        	if(matcher.group().equals("<")){
    	        		steroStart = matcher.end();
    	        		System.out.print("");
    	        	}
    	        	if(matcher.group().equals(">")){
    	        		steroEnd = matcher.end();
    	        		System.out.print("");
    	        		 index = matcher.end();
    	        	}	
    	        	//PKY 08071101 E 오퍼레이션 스트레오타입 입력가능하도록
    	        	if(matcher.group().equals("/")){
    	        		stype = value.substring(index,matcher.end()-1).trim();//PKY 08091009 S
    	        		 index = matcher.end();
    	        		 
    	        	}	

    	        	if(matcher.group().equals("(")){
    	        		
    			   
    		        	name = value.substring(index, matcher.start()).trim();
    		        	System.out.println("name=>"+name);
    		        	 index = matcher.end();  		        	 
    		        	 isParamStart = true;
    		        	 paramindex=index;

    		        	
    	        	}
    	        	else if(matcher.group().equals(",")){
    	        		if(isParamStart){
    	        			paramsCount=paramsCount+1;
    	        			
    	        			String param = value.substring(paramindex,matcher.end()-1);
    	        			if(!param.equals("")){
    	        			String[] data = param.split(":");
    	        			ParameterEditableTableItem p1 = new ParameterEditableTableItem(data[0],data[1],"");
    	        			this.params.add(p1);
    	        			}
    	        			
    	        			index = matcher.end();
    	        			paramindex=index;
    	        		}
    	        		
    	        	}
    	        	else if(matcher.group().equals(":")){
    		   
    		        	 index = matcher.end();
    		        	 if(isParamEnd){
    		        		 stype = value.substring(index);
    		        		 System.out.println("sType=>"+stype);
    		        	 }
//    		        	 else{
//    		        		 if(isParamStart){
//    		        			 String paramName =  value.substring(index);
//    		        		 }
//    		        	 }
    	        	}
    	        	else if(matcher.group().equals(")")){
    	        		 index = matcher.end();
   	        			paramsCount=paramsCount+1;
   	        			String param = value.substring(paramindex,matcher.end()-1);
    	        		
   	        			if(!param.equals("")){
    	        			String[] data = param.split(":");
    	        			ParameterEditableTableItem p1 = new ParameterEditableTableItem(data[0],data[1],"");
    	        			this.params.add(p1);
    	        			
    	        		}
    	        		isParamEnd = true;
    		    
    		        	
    		        	 isParamStart=false;
    	        	}
    	        	//2008032801 PKY E


    	        }
    	      //PKY 08071101 S 오퍼레이션 스트레오타입 입력가능하도록
    	        if(steroStart>0&&steroEnd>0){
    	        	stereo= value.substring(steroStart,steroEnd-2);
    	        }else{
    	        	stereo="";
    	        }
    	      //PKY 08071101 E 오퍼레이션 스트레오타입 입력가능하도록

    	        if(!isFind){
    	        	name = value;
    	        	//PKY 08060201 S 어트리뷰트/오퍼레이션 다이어그램에서 이름없이 수정할경우 이름이 들어가지 않는문제 디폴트 이름 들어가도록 수정
                    if(name.trim().equals("")){
                   	  name="operation";
                    }
                  //PKY 08060201 E 어트리뷰트/오퍼레이션 다이어그램에서 이름없이 수정할경우 이름이 들어가지 않는문제 디폴트 이름 들어가도록 수정
    	        	scope = 0;
    	        	stype="do";
    	        	
    	        }
    	
    	}
    }
    
    public String toMessageString() {
    	 StringBuffer pBuffer = new StringBuffer();
         String value = "";
         if (!this.isState) {
             for (int i = 0; i < params.size(); i++) {
                 ParameterEditableTableItem p = (ParameterEditableTableItem)params.get(i);
                 pBuffer.append(p.toString());
                 if (i != params.size() - 1) {
                     pBuffer.append(",");
                 }
             }
             value = " "+name.trim() + "(" + pBuffer.toString() + "):" +
             stype;
         }
         else {
             for (int i = 0; i < params.size(); i++) {
                 ParameterEditableTableItem p = (ParameterEditableTableItem)params.get(i);
                 pBuffer.append(p.toString());
                 if (i != params.size() - 1) {
                     pBuffer.append(",");
                 }
             }
             value = OperationDialog.SCOPEA_SET[scope] + ProjectManager.STATE_TYPE_SET[type] + "/" + name + "(" +
                 pBuffer.toString() + ")";
         }
         return value;
    	
    }

    public String toString() {
    	StringBuffer pBuffer = new StringBuffer("");
    	String value = "";
    	if (!this.isState) {
    		for (int i = 0; i < params.size(); i++) {
    			ParameterEditableTableItem p = (ParameterEditableTableItem)params.get(i);
    			pBuffer.append(p.toString());
    			if (i != params.size() - 1) {
    				pBuffer.append(",");
    			}
    		}
    		if(!stereo.trim().equals(""))
    		value = OperationDialog.SCOPEA_SET[scope] +" " + "<<"+stereo.trim()+">>"+ " "+name.trim() + "(" + pBuffer.toString() + "):" +
    		stype;
    		else{
    			value = OperationDialog.SCOPEA_SET[scope] +" "+ name.trim() + "(" + pBuffer.toString() + "):" +
        		stype;	
    		}
    	}
    	else {
    		for (int i = 0; i < params.size(); i++) {
    			ParameterEditableTableItem p = (ParameterEditableTableItem)params.get(i);
    			pBuffer.append(p.toString());
    			if (i != params.size() - 1) {
    				pBuffer.append(",");
    			}
    		} //2008043001 PKY S  State Operation 입력안됨 
    		//PKY 08071601 S State  Operation 표시 다른것 수정
System.out.println("-------------->"+stype);
if(!stereo.trim().equals(""))
	value = OperationDialog.SCOPEA_SET[scope] +" " + "<<"+stereo.trim()+">>"+ " " + stype.trim() + " / " +
	name.trim()+"(" + pBuffer.toString() + ")";
else
    		value = OperationDialog.SCOPEA_SET[scope] +" " + stype.trim() + " / " +
    		name.trim()+"(" + pBuffer.toString() + ")";
    		//PKY 08071601 E State  Operation 표시 다른것 수정

//  		value = OperationDialog.SCOPEA_SET[scope] +" " +ProjectManager.STATE_TYPE_SET[type] + "/" + name + "(" +
//  		pBuffer.toString() + ")";
    		//2008043001 PKY E  State Operation 입력안됨 
    	}
    	return value;
    }


    public Object clone() {
        OperationEditableTableItem clone = new OperationEditableTableItem(this.scope, this.name, this.stype, this.initValue);
        clone.setIsState(this.getIsState());
        //2008042401PKY S
        clone.desc=this.desc;
        clone.stereo=this.stereo;//PKY 08071101 S 오퍼레이션 스트레오타입 입력가능하도록
      //2008042401PKY E
        for (int i = 0; i < params.size(); i++) {
            ParameterEditableTableItem p = (ParameterEditableTableItem)params.get(i);
            clone.params.add(p.clone());
        }
        return clone;
    }

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public MessageCommunicationModel getMessageCommunicationModel() {
		return messageCommunicationModel;
	}

	public void setMessageCommunicationModel(
			MessageCommunicationModel messageCommunicationModel) {
		this.messageCommunicationModel = messageCommunicationModel;
	}
	//2008042202PKY S
	
	public java.util.ArrayList getParams() {
	    return this.params;
	}

	public void setParams(java.util.ArrayList p){
	    this.params = p;
	}
	//PKY 08060201 S 저장 불러오기 할경우 어트리뷰트,오퍼레이션 생성 이름 넘버러링이 초기화 되는문제
	public String getName() {
	    return name;
	}
	public void setName(String name) {
	    this.name = name;
	}
	//PKY 08060201 E 저장 불러오기 할경우 어트리뷰트,오퍼레이션 생성 이름 넘버러링이 초기화 되는문제
}
