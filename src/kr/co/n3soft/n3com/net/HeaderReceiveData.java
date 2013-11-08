package kr.co.n3soft.n3com.net;

import java.util.StringTokenizer;

import kr.co.n3soft.n3com.project.dialog.DetailPropertyTableItem;
import kr.co.n3soft.n3com.project.dialog.OpenMonitoringDialog;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

public class HeaderReceiveData {
	public String ver = "1.0";
	public String target = "";
	public String cmd = "";
	public String success = "";
	public String ret = "";
	public String payloadSize = "";
	public String cmpName = "";
	public String appName = "";
	public java.util.ArrayList var = null;
	
	public boolean isT = false;
	public String classname = "";
	public StringBuffer sb = new StringBuffer();
//	public String payloadSize = "";
	
	public String getStringFileDownLoadHeader(){
		String ret = this.getVerKeyMapping()+";"+
		this.getTargetKeyMapping()+";"+
		this.getCmdKeyMapping()+";"+
		this.getSuccessKeyMapping()+";"+
		this.getRetKeyMapping()+";"+
		this.getPayloadSizeKeyMapping()+NetManager.ln;
		
		return ret;
	}
	
	public byte[] getByteFileDownLoadHeader(){
		return this.getStringFileDownLoadHeader().getBytes();
	}
	
	public String getVerKeyMapping(){
		String ret = NetManager.ver_key + "="+this.ver;
		return ret;
	}
	public String getTargetKeyMapping(){
		String ret = NetManager.target_key + "="+this.target;
		return ret;
	}
	public String getCmdKeyMapping(){
		String ret = NetManager.cmd_key + "="+this.cmd;
		return ret;
	}
	public String getSuccessKeyMapping(){
		String ret = NetManager.success_key + "="+this.success;
		return ret;
	}
	public String getRetKeyMapping(){
		String ret = NetManager.ret_key + "="+this.ret;
		return ret;
	}
	
	public String getPayloadSizeKeyMapping(){
		String ret = NetManager.payloadSize_key + "="+this.payloadSize;
		return ret;
	}
	
	public String[] getRet(){
		if(this.ret!=null){
			return this.ret.split(",");
		}
		return null;
	}
	
//	public String[] getRet2(){
//		if(this.ret!=null){
////			this.ret.indexOf("¼­¹ö")
//			return this.ret.split(",");
//		}
//		return null;
//	}

	public void setHeaderReceiveData(String str){
		StringTokenizer stk = new StringTokenizer(str,";");
		while(stk.hasMoreTokens()){
			String token = stk.nextToken();
			if(token!=null && token.indexOf("=")!=-1){
				String[] arr = token.split("=");
				if(arr.length ==2){
			
				if(NetManager.ver_key.equals(arr[0])){
					this.ver = arr[1];
				}
				if(NetManager.target_key.equals(arr[0])){
					this.target = arr[1];
				}
				if(NetManager.cmd_key.equals(arr[0])){
					this.cmd = arr[1];
				}
				if(NetManager.success_key.equals(arr[0])){
					this.success = arr[1];
				}
				if(NetManager.ret_key.equals(arr[0])){
					this.ret = arr[1];
				}
				if(NetManager.payloadSize_key.equals(arr[0])){
					this.payloadSize = arr[1];
				}
				}
				
			}
			
		}
		
	}
	
	public void setReceiveData(String str){
//	 String ddd	= "{app=HelloWorld;var=hello.message;var=hello.count}";
		StringTokenizer stk = new StringTokenizer(str,";");
		String varName = "";
		String varType = "";
		String varValueFormat = "";
		String varValue = "";
		while(stk.hasMoreTokens()){
			String token = stk.nextToken();
			
			if(token!=null && token.indexOf("=")!=-1){
				String[] arr = token.split("=");
				if(arr.length ==2){
					if(NetManager.app_key.equals(arr[0])){
						this.cmpName = arr[1];
					}
					else if(NetManager.var_key.equals(arr[0])){
						this.cmpName = arr[1];
					}
				}
			}
		}
		
		
	}
	
//	hello.message:type=string:valueformat=str:value="hello world";
	public void setVar(String str){
		
		
		if(str!=null){
			StringTokenizer stk1 = new StringTokenizer(str,"}");
			while(stk1.hasMoreTokens()){
				String token1 = stk1.nextToken();
			System.out.println("token1==============>"+token1);
			int i1 = token1.indexOf("{");
//			int i2 = token1.lastIndexOf("}");
//			if(i1<i2)
			String str1 = token1.substring(i1+1);
			StringTokenizer stk = new StringTokenizer(str1,";");
			String cmpName = "";
			String varName = "";
			String varType = "";
			String varValueFormat = "";
			String varValue = "";
			String tempkey = "";
			String tempvalue = "";
			boolean isString = false;
			while(stk.hasMoreTokens()){
				String token = stk.nextToken();

//				String temp = arr[i];
				String t[]= token.split("=");
				if(t.length==2){
					this.isT = false;
					sb.setLength(0);
					this.classname = "";
					 tempkey = t[0];
					 tempvalue = t[1];
					 
					 
					
//					if("type".equals(tempkey)){
//						varType = tempvalue;
//					}
					 if("valueformat".equals(tempkey)){
						varValueFormat = tempvalue;
					}
					else if("value".equals(tempkey)){
//						varValue = tempvalue;
						int k = tempvalue.trim().indexOf("T[std::string]");
						if(k!=-1 && stk.hasMoreTokens()){
							tempvalue = stk.nextToken();
						}
						tempvalue = tempvalue.replace('"', ' ');
						int index1 = tempvalue.indexOf("std::string");
						if(index1>0 && "str".equals(varValueFormat) && stk.hasMoreTokens()){
							tempvalue = stk.nextToken();
							tempvalue = tempvalue.replace('"', ' ');
						}
//						 if(tempvalue!=null){
//							 tempvalue = tempvalue.trim();
//						 }
						
						varValue= this.getType(tempvalue);
						if(ProjectManager.mtest){
							if("true".equals(varValue)){
								varValue = "false";
							}
							else if("false".equals(varValue)){
								varValue = "true";
							}
							else{
							varValue = varValue + OpenMonitoringDialog.im;
						OpenMonitoringDialog.im++;
							}
						
						}
						if(this.isT){
							sb.append(varValue);
						}
						System.out.println("varValue=====>"+varValue);
//						isString = false;
					}
					else if("var".equals(tempkey)){
						int i3 = tempvalue.indexOf(".");
						if(i3>0){
							appName = tempvalue.substring(0,i3);
							String tmpData = tempvalue.substring(i3+1);
							
							int i4 = tmpData.indexOf(".");
							if(i4>0){
								cmpName = tmpData.substring(0,i4);
								varName = tmpData.substring(i4+1);
							}
							else{
								cmpName = tempvalue.substring(0,i3);
								varName = tempvalue.substring(i3+1);
							}
						}
					}
					
				}
				else if(t.length==1 && this.isT){
					if(token.indexOf("T[")==0){
						varValue= this.getType(token);
						if(ProjectManager.mtest){
							if("true".equals(varValue)){
								varValue = "false";
							}
							else if("false".equals(varValue)){
								varValue = "true";
							}
							else{
							varValue = varValue + OpenMonitoringDialog.im;
						OpenMonitoringDialog.im++;
							}
						}
						sb.append(varValue);
					}
					else{
						if(!"\"".equals(token)){
							if(!"".equals(token.trim())){
						varValue= this.getType(this.classname+token);
						if(ProjectManager.mtest){
							if("true".equals(varValue)){
								varValue = "false";
							}
							else if("false".equals(varValue)){
								varValue = "true";
							}
							else{
//								if(OpenMonitoringDialog.isTestD){
									varValue = varValue + OpenMonitoringDialog.im;
								OpenMonitoringDialog.im++;
								
//								}
							}
						
						}
						sb.append(","+varValue);
						}
						}
					}
					
				System.out.println("sb==>"+sb.toString());
//					if(token.indexOf(ch))
				}
//				else if(isString){
//					tempvalue = tempvalue.replace('"', ' ');
//					
//					varValue= this.getType(tempvalue);
//					System.out.println("varValue=====>"+varValue);
//					isString = false;
//				}
			
			}
			
			if(this.isT){
				varValue = sb.toString();
				sb.setLength(0);
				this.isT = false;
			}
//			String[] arr = str1.split(";");
//			if(arr.length==4){
//				String var = arr[1];
////				String type = arr[1];
//				String valueFormat = arr[2];
//				String value = arr[3];
//				String t[] = var.split(".");
//				String cmpName = "";
//				String varName = "";
//				String varType = "";
//				String varValueFormat = "";
//				String varValue = "";
//				String tempkey = "";
//				String tempvalue = "";
//				if(t.length==2){
//					cmpName = t[0];
//					varName = t[1];
//				}
//				
//				for(int i=0;i<arr.length;i++){
//					String temp = arr[i];
//					t = temp.split("=");
//					if(t.length==2){
//						tempkey = t[0];
//						tempvalue = t[1];
//						if("type".equals(tempkey)){
//							varType = tempvalue;
//						}
//						else if("valueformat".equals(tempkey)){
//							varValueFormat = tempvalue;
//						}
//						else if("value".equals(tempkey)){
////							varValue = tempvalue;
//							tempvalue = tempvalue.replace('"', ' ');
//							varValue= this.getType(tempvalue);
//							System.out.println("varValue=====>"+varValue);
//						}
//						else if("var".equals(tempkey)){
//							int i3 = tempvalue.indexOf(".");
//							if(i3>0){
//								varName = tempvalue.substring(i3+1);
//							}
//						}
//						
//					}
//				}
				
				for(int i=0;i<this.var.size();i++){
					DetailPropertyTableItem dp = (DetailPropertyTableItem)this.var.get(i);
					System.out.println("dp.cmpName===>"+dp.cmpName);
					System.out.println("cmpName===>"+cmpName);
					if(dp.cmpName.equals(cmpName)){
						dp.setValue(varType, varName, varValue,varValueFormat);
					}
				}
				
				
			}
//		}
		}
		
		}
		
//	}
	
	public String getType(String value){
		boolean isFirst = true;
		char code = value.charAt(0);
		if(code==' '){
			code = value.trim().charAt(0);
			isFirst = false;
		}
		
		char[] chars =  value.toCharArray();
		int f = value.indexOf("[");
		int e = value.indexOf("]");
		if('A'==code){
//			if(f==2 && f<e && !isFirst){
				return this.getValue(value, e,";");
//			}
//			else{
//				return this.getValue(value, e,";");
//			}
		}
		else if('T'==code){
			this.isT = true;
			String tempclass = value.substring(f,e+1);
			if(!this.classname.equals(tempclass)){
				this.classname = tempclass;
				
				sb.append(this.classname+":");
			}
			if(f==2 && f<e){
				return this.getValue(value, e, ";");
			}
			
		}
		else{
			return this.getValue(value, e, ";");
		}
		
		return "";
	}
	
	
	public String getValue(String value,int e,String split){
		char code;
		String hv = value.substring(e+1);
		code = hv.charAt(0);
		if('C'==code){
			hv = hv.replace("\\", "");
			String vVal = hv.substring(1, hv.length());
			return vVal;
		}
		else if('T'==code){
			StringTokenizer stk = new StringTokenizer(value,"T[");
			StringBuffer sb = new StringBuffer("");
			while(stk.hasMoreElements()){
				String token = stk.nextToken();
				if(token!=null){
					int i = token.indexOf("]");
					String name = token.substring(0, i);
					String data = token.substring(i+1);
					sb.append(name);
					sb.append("(");
					data = this.getVarData(data, ";");
					sb.append(data);
					sb.append(")");
					if(stk.hasMoreElements()){
						sb.append(",");
					}
				}
				
			}
			
			return sb.toString();
		}
		else{
			return this.getVarData(hv, split);
		}
	
	}
	
	public String getVarData(String hv,String split){
		String vVal = hv.substring(0, hv.length());
		String[] vars = vVal.split(split);
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<vars.length;i++){
			String var = vars[i];
			var = this.getPrimitive(var);
			sb.append(var);
			if(i!=vars.length-1){
				sb.append(",");
			}
		}
		System.out.println("sb=====>"+sb.toString());
		return sb.toString();
	}
	
	
	public String getPrimitive(String value){
		String retValue = value;
		if("".equals(value.trim())){
			return "";
		}
		else if(value.charAt(0)!=' '){
			value = " "+ value;
		}
		if(value==null){
			return null;
		}
//		else if("".equals(value.trim())){
//			return "";
//		}
		else if(value.length()<2){
			return value;
		}
//		value.
		char code = ' ';
		char hexa = 'N';
		
		
		 code = value.charAt(1);
		
		if(value.length()>2){
			hexa = value.charAt(2);
		}
		
		 switch (code) {
		case 'Z':
			 char t = value.charAt(2);
			 if('T'==t)
				 retValue =  "true";
			 else
				 retValue =  "false";
			break;
		case 'i':
			if('X'==hexa){
				String hexab = this.hexaLittleEndianConvert(value);
				int v = Integer.parseInt(hexab, 16);
				retValue = String.valueOf(v);
			}
			else{
				
				retValue = this.delSemicolonValue(value);
			}
			
			break;
		case 'I':
			if('X'==hexa){
				String hexab = this.hexaLittleEndianConvert(value);
				int v = Integer.parseInt(hexab, 16);
				retValue = String.valueOf(v);
			}
			else{
				retValue = this.delSemicolonValue(value.substring(1));
			}
			
			break;
		case 'L':
			if('X'==hexa){
//				Long.parseLong(s, radix)
				String hexab = this.hexaLittleEndianConvert(value);
				long v = Long.parseLong(hexab, 16);
				retValue = String.valueOf(v);
			}
			else{
				retValue = this.delSemicolonValue(value.substring(1));
			}
			
			break;
		case 'F':
			if('X'==hexa){
				String hexab = this.hexaLittleEndianConvert(value);
				float v = Float.floatToIntBits(Integer.parseInt(hexab,
						16));
				
				retValue = String.valueOf(v);
			}
			else{
				retValue = this.delSemicolonValue(value.substring(1));
			}
			
			break;
		case 'd':
			if('X'==hexa){
				String hexab = this.hexaLittleEndianConvert(value);
				Double v = Double.longBitsToDouble(Long.parseLong(hexab,
						16));
				
				retValue = String.valueOf(v);
			}
			else{
				retValue = this.delSemicolonValue(value.substring(1));
			}
			break;
		case 'D':
			if('X'==hexa){
				String hexab = this.hexaLittleEndianConvert(value);
				Double v = Double.longBitsToDouble(Long.parseLong(hexab,
						16));
				
				retValue = String.valueOf(v);
			}
			else{
				retValue = this.delSemicolonValue(value.substring(1));
			}
			break;
		case 'C':
			if('X'==hexa){
//				java.lang.Character.forDigit(digit, radix)
				String s = "0x"+value;
				int ic = Integer.valueOf(s);
				char c = (char)ic;
				retValue = String.valueOf(c);
			}
			else{
				retValue = this.delSemicolonValue(value.substring(1));
				int pp = Integer.valueOf(retValue);
				char c = (char)pp;
				retValue = String.valueOf(c);
			}
			break;

		default:
			break;
		}
		
		return retValue;
		
	}
	
	public String delSemicolonValue(String value){
		
		int index = value.lastIndexOf(";");
		if(index==value.length()-1){
			return  value.substring(1,index);
		}
		else{
			return  value.substring(1);
		}
	}
	
	
	
	
	public static  String hexaLittleEndianConvert(String value){
		StringBuffer sb = new StringBuffer();
		String tValue = "";
		if(value!=null){
			tValue = value.substring(3).trim();
			char[] arr = tValue.toCharArray();
			if(arr.length>2){
				int length = arr.length;
				int tlength = length/2;
				int mod = length % 2;
				if(mod==0){
				for(int i=1;i<=tlength;i++){
					char a = arr[length-2];
					char b = arr[length-1];
					System.out.println("a===>"+a);
					System.out.println("b===>"+b);
					sb.append(a);
					sb.append(b);
					length = length -2;

				}
				return sb.toString();
				}
			}
			else{
				return value;
			}
		}
		return "";

	}
	
	
	
	
	
	
	
	
}
