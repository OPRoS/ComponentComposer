package kr.co.n3soft.n3com.model.umlclass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.co.n3soft.n3com.parser.IN3ModelTokenType;
import kr.co.n3soft.n3com.project.dialog.AttributeDialog;

public class AttributeEditableTableItem extends ArrayItem {
    public String name;
    public Integer scope;
    public String initValue;
    public Integer type;
    public String stype;
    public String id = null;
    public Integer isDerived = 0;
    public Integer isStatic = 0;
    public Integer isConst = 0;
    public boolean isinitValue  = false;
    public String desc="";

    public AttributeEditableTableItem(Integer s, String n, Integer v, String i) {
        scope = s;
        name = n;
        type = v;
        initValue = i;
    }
    
    public AttributeEditableTableItem() {
      
    }
    
    public String writeModel(){
    	StringBuffer sb = new StringBuffer();
    	sb.append("model Attribute (\n");
    	sb.append("model_propertys <\n");
    	sb.append("property name="+name+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
    	sb.append("property scope="+scope+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
    	sb.append("property stype="+stype+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
    	sb.append("property initValue="+initValue+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
		sb.append("property derived="+isDerived+IN3ModelTokenType.PROPERTY_N3EOF+"\n");//2008043001 PKY S ;
    	sb.append("property const="+isConst+IN3ModelTokenType.PROPERTY_N3EOF+"\n"); //PKY 08051401 S Const ����ȵ�
		sb.append("property desc="+desc+IN3ModelTokenType.PROPERTY_N3EOF+"\n");//2008043001 PKY E
    	sb.append(">\n");
    	sb.append(")\n");
    	return sb.toString();
    }
    

    
    public void setString(String value){
    	try{
        if(value!=null){
            Matcher matcher;
            Pattern p = Pattern.compile("[/\\+\\-\\#\\=\\:]");
                matcher = p.matcher(value);
                Matcher overMapping = p.matcher(value);
                boolean isFind = false;
                boolean isFindDerived = false;
                boolean isFindScope= false;
                boolean isReadOnlyStart = false;
                boolean isinitValue= false;
                boolean isOverMapping=false;
                int index = 0;
                int beforeIndex = 0;
                
                int overMappingFindDerived = 0;
                int overMappingFindScope = 0;
                int overMappingFind = 0;
                int overMappinginitValue = 0;
                
                while(overMapping.find()) {
                	if(overMapping.group().equals("/")){
                		isFindDerived = true;
                		overMappingFindDerived=overMappingFindDerived+1;
                		if(overMappingFind>1){
                			isOverMapping=true;
                			return;
                		}
                	} else if(overMapping.group().equals("+")){
                		isFindScope = true;
                		overMappingFindScope=overMappingFindScope+1;
                		if(overMappingFindScope>1){
                			isOverMapping=true;
                			 isinitValue = true;
                			 return;
                		}
                	} else if(overMapping.group().equals("-")){
                		isFindScope = true;
                		overMappingFindScope=overMappingFindScope+1;
                		if(overMappingFindScope>1){
                			isOverMapping=true;
                			 isinitValue = true;
                			 return;
                		}
                	} else if(overMapping.group().equals("#")){
                		isFindScope = true;
                		overMappingFindScope=overMappingFindScope+1;
                		if(overMappingFindScope>1){
                			isOverMapping=true;
                			 isinitValue = true;
                			 return;
                		}
                	} else if(overMapping.group().equals(":")){
                		isFind = true;
                		overMappingFind=overMappingFind+1;
                		if(overMappingFind>1){
                			isOverMapping=true;
                			 isinitValue = true;
                			 return;
                		}
                	} else if(overMapping.group().equals("=")){
                		isinitValue = true;
                		overMappinginitValue=overMappinginitValue+1;
                		if(overMappinginitValue>1){//PKY 08060201 S ���̾�׷����� Text�� ��Ʈ����Ʈ �����ϴ� ���� = ������ �Է¾ȉ�� �κ� ����
                			isOverMapping=true;
                			 isinitValue = true;
                			 return;
                		}
                	}
                }
                if(isOverMapping==false)
                while(matcher.find()) {
                 if(matcher.group().equals("/")){
                  isDerived = 1;
                   index = matcher.end();
                   isFindDerived = true;
                 }
                 else if(matcher.group().equals("+")){
                  scope = 0;
                   index = matcher.end();
                   isFindScope = true;
                 }
                 else if(matcher.group().equals("-")){
                  scope = 2;
                   index = matcher.end();
                   isFindScope = true;
                 }
                 else if(matcher.group().equals("#")){
                  scope = 1;
                   index = matcher.end();
                   isFindScope = true;
                 }
                 else if(matcher.group().equals(":")){
                  isFind = true;
                  name = value.substring(index, matcher.start());
                  System.out.println("name=>"+name);
                  index = matcher.end();
                 }
                 else if(matcher.group().equals("=")){
                  isinitValue = true;
                  this.stype = value.substring(index,matcher.start());
                  String value1 = value.substring(matcher.end());
                  if(value1.lastIndexOf("{")>0){
//                  String[] data = value1.split("{");
                	  String data = value1.substring(0,value1.lastIndexOf("{"));//PKY 08060201 S ��Ʈ����Ʈ Initial Const���ÿ� �Է��� ��� �������� ���� �ԷµǴ� ��Ȳ�� EAȮ�� �� ó��
                  this.initValue = data;
                   this.isConst = 1;
                  }
                  else{
                   this.initValue = value1;
                  }
                  index = matcher.end();
                 }
                } 
                
                if(!isinitValue){
                 this.stype = value.substring(index);
                }
                if(!isFind){
                 name = value.substring(index);
               //PKY 08060201 S ��Ʈ����Ʈ/���۷��̼� ���̾�׷����� �̸����� �����Ұ�� �̸��� ���� �ʴ¹��� ����Ʈ �̸� ������ ����
                 if(name.trim().equals("")){
                	  name="attribute";
                 }
               //PKY 08060201 E ��Ʈ����Ʈ/���۷��̼� ���̾�׷����� �̸����� �����Ұ�� �̸��� ���� �ʴ¹��� ����Ʈ �̸� ������ ����
                 if(!isFindScope)
                 scope = 0;
                  if(!isFindDerived)
                   this.isDerived =0;
                 stype="String";
                }
        }
        
        
        }catch(Exception e){
        e.printStackTrace();	
        }
       }
    
    public AttributeEditableTableItem(Integer s, String n, String v, String i) {
        scope = s;
        name = n;
        stype = v;
        initValue = i;
    }

    public String toString() {   
	String isDerivedValue = "";
	String isConstValue = "";
	String isInitValue = "";
	if(this.isDerived==1){
	    isDerivedValue = "/";
	}
	if(this.isConst==1){
	    isConstValue = "{readOnly}";
	}
	if(this.initValue!=null && !this.initValue.trim().equals("")){
	    isInitValue = "="+initValue;
	}
	//PKY 08060201 S {ReadOnly} �ߺ��ؼ� ���� ���� ����
	if(stype.lastIndexOf("{readOnly}")>-1){
	    stype=stype.substring(0, stype.lastIndexOf("{readOnly}"));
	}
	//PKY 08060201 E {ReadOnly} �ߺ��ؼ� ���� ���� ����
	String value = AttributeDialog.SCOPEA_SET[scope]+isDerivedValue + " " + name.trim() + ":" + this.stype+isInitValue+isConstValue;
	return value;
    }

    public Object clone() {
        AttributeEditableTableItem clone = new AttributeEditableTableItem(this.scope, this.name, this.stype, this.initValue);
        //2008042401PKY S
        clone.desc=this.desc;
        clone.isConst=this.isConst;
        clone.isStatic=this.isStatic;
        clone.isDerived=this.isDerived;
      //2008042401PKY E
        return clone;
    }

	public Integer getIsDerived() {
		return isDerived;
	}

	public void setIsDerived(Integer isDerived) {
		this.isDerived = isDerived;
	}

	public Integer getIsStatic() {
		return isStatic;
	}

	public void setIsStatic(Integer isStatic) {
		this.isStatic = isStatic;
	}

	public Integer getIsConst() {
		return isConst;
	}

	public void setIsConst(Integer isConst) {
		this.isConst = isConst;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	//PKY 08060201 S �˾����� Type�����ϵ��� ����
	public String getStype() {
		return stype;
	}

	public void setStype(String stype) {
		this.stype = stype;
	}
	//PKY 08060201 E �˾����� Type�����ϵ��� ����
	//PKY 08060201 S ���� �ҷ����� �Ұ�� ��Ʈ����Ʈ,���۷��̼� ���� �̸� �ѹ������� �ʱ�ȭ �Ǵ¹���
	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}
	//PKY 08060201 E ���� �ҷ����� �Ұ�� ��Ʈ����Ʈ,���۷��̼� ���� �̸� �ѹ������� �ʱ�ȭ �Ǵ¹���
}
