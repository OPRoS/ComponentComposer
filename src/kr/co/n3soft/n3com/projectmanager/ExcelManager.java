package kr.co.n3soft.n3com.projectmanager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import kr.co.n3soft.n3com.model.activity.CentralBufferNodeModel;
import kr.co.n3soft.n3com.model.activity.DataStoreModel;
import kr.co.n3soft.n3com.model.activity.ExceptionModel;
import kr.co.n3soft.n3com.model.activity.FinalActiionModel;
import kr.co.n3soft.n3com.model.activity.FinalActivityModel;
import kr.co.n3soft.n3com.model.activity.FinalObjectNodeModel;
import kr.co.n3soft.n3com.model.activity.FinalStrcuturedActivityModel;
import kr.co.n3soft.n3com.model.comm.TypeRefModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.component.ArtfifactModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.composite.PartModel;
import kr.co.n3soft.n3com.model.umlclass.AttributeEditableTableItem;
import kr.co.n3soft.n3com.model.umlclass.FinalClassModel;
import kr.co.n3soft.n3com.model.umlclass.InterfaceModel;
import kr.co.n3soft.n3com.model.umlclass.OperationEditableTableItem;
import kr.co.n3soft.n3com.model.umlclass.ParameterEditableTableItem;
import kr.co.n3soft.n3com.model.usecase.CollaborationModel;
import kr.co.n3soft.n3com.model.usecase.FinalActorModel;
import kr.co.n3soft.n3com.model.usecase.UseCaseModel;
import kr.co.n3soft.n3com.project.browser.PackageTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeParentModel;

public class ExcelManager{
	
	private static ExcelManager instance;
	
	WritableWorkbook workbook = null;
	WritableSheet sheet = null;
	Workbook workbook2 = null;
	public int rowIndex = 1;
	TypeRefModel trm = null;
//	Vector newDiagrams = new Vector();
//	Vector newDiagramsName = new Vector();
	HashMap newDiagrams = new HashMap();
	
	Vector newObject = new Vector();		// V2.502 WJH E 070517 변수 추가
	
	public static ExcelManager getInstance() {		 
        if (instance == null) {
            instance = new ExcelManager();            
            return instance;
        }
        else {
            return instance;
        }
    }
	
//	V1.01 WJH E 080428 S 엑셀 저장 추가 
	public void writeExcelObject(UMLTreeParentModel model) {
		UMLTreeParentModel treeObject = model;
		
	}
	public void initGetWorkbook(String vFilename){
		try{
		workbook2 = Workbook.getWorkbook(new File(vFilename));		
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void initCreateWorkbook(String vFilename, int type){
		try{
//		String temp = System.getProperty("user.dir", ".") +
//						File.separator + "Projects" +
//						File.separator+vFilename;
//		File f = new File(vFilename);
//		if(!f.exists()){
//			vFilename = vFilename+".xls";
//		}
		workbook = Workbook.createWorkbook(new File(vFilename));

		sheet = workbook.createSheet("Object", 0);
//		 V1.02 WJH 080908 S 요구사항 추적표 추가
		if(type==1)
			this.writeObjectLabel();
		else if(type == 2)
			this.writeRequiredLabel();
//		 V1.02 WJH 080908 E 요구사항 추적표 추가
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

		// if(ATDDiagramModelType==Type){
		// sheet = workbook.createSheet(SHEET_2, 0);
		// this.writeATDLabel();
		// }
		// else if(ATDType==Type){
		// sheet = workbook.createSheet(SHEET_2, 0);
		// this.writeATDLabel();
		// }
		// else if(FODDiagramModelType==Type){
		// sheet = workbook.createSheet(SHEET_3 ,0);
		// this.writeFODLabel();
		// }
		// else if(FODType==Type){
		// sheet = workbook.createSheet(SHEET_3, 0);
		// this.writeFODLabel();
		// }
		// else if(DPDDiagramModelType==Type){
		// sheet = workbook.createSheet(SHEET_5, 0);
		// writeDPDLabel();
		// }
		// else if(DPDType==Type){
		// sheet = workbook.createSheet(SHEET_5, 0);
		// writeDPDLabel();
		// }
		// else if(SchemaDiagramModel==Type){
		// sheet = workbook.createSheet(SHEET_6, 0);
		// writeSchemaLabel();
		// }
		// else if(SchemaType==Type){
		// sheet = workbook.createSheet(SHEET_6, 0);
		// writeSchemaLabel();
		// }
		// else if(this.ComponentType==Type){
		// sheet = workbook.createSheet(SHEET_4,0);
		// writeComponentLabel();
		// }
		// else if(this.ComponentDiagramModelType==Type){
		// sheet = workbook.createSheet(SHEET_4,0);
		// writeComponentLabel();
		// }
		// // N3soft 2006.06.09 윤치영 상태 다이어그램 추가 Start
		// else if(this.STDDiagramModelType==Type){
		// sheet = workbook.createSheet(SHEET_7,0);
		// this.writeSTDLabel();
		// }
		// else if(this.STDType==Type){
		// sheet = workbook.createSheet(SHEET_7,0);
		// this.writeSTDLabel();
		// }
		// // N3soft 2006.06.09 윤치영 상태 다이어그램 추가 End
		//
		// else if(this.CUDType==Type){
		// sheet = workbook.createSheet(SHEET_7,0);
		// this.writeCUDLabel();
		// }
		// else if(this.CUDDiagramModelType==Type){
		// sheet = workbook.createSheet(SHEET_7,0);
		// this.writeCUDLabel();
		// }
		// // V2.502 WJH E 070430 S 패키지 타입 추가
		// // V2.502 WJH E 070510 S 패키지 타입 변경
		// else if(this.FPackageType==Type){
		// sheet = workbook.createSheet(SHEET_3,0);
		// this.writeFODLabel();
		// }
		// // V2.502 WJH E 070510 E 패키지 타입 변경
		//	
		// else if(this.LGPackageType==Type){
		// sheet = workbook.createSheet(SHEET_3,0);
		// this.writeLGObjectLabel();
		// }
		// // V2.502 WJH E 070510 S 패키지 타입 추가
		//	
		// // V2.502 WJH E 070820 S 디스크립션 없는 오퍼레이션을 가진 객체 출력
		// else if(10000 == Type){
		// sheet = workbook.createSheet(SHEET_3, 0);
		// this.writeNoDescription();
		// }
		// // V2.502 WJH E 070820 E 디스크립션 없는 오퍼레이션을 가진 객체 출력
		//	
		// // V2.502 WJH E 070820 S 시퀀스 다이어그램 객체 순서대로 나열
		// else if(10001 == Type){
		// sheet = workbook.createSheet(SHEET_3, 0);
		// this.writeSequenceObject();
		// }
		// // V2.502 WJH E 070820 E 시퀀스 다이어그램 객체 순서대로 나열
		//	
		// else if(10002 == Type){
		// sheet = workbook.createSheet(SHEET_3, 0);
		// this.writeDiagramNode();
		// }
		// else if(this.APackageType==Type){
		// sheet = workbook.createSheet(SHEET_2,0);
		// this.writeATDLabel();
		// }
		// else if(this.SPackageType==Type){
		// sheet = workbook.createSheet(SHEET_7,0);
		// this.writeSTDLabel();
		// }
		// else if(this.DPackageType==Type){
		// sheet = workbook.createSheet(SHEET_5,0);
		// this.writeDPDLabel();
		// }
		// else if(this.CPackageType==Type){
		// sheet = workbook.createSheet(SHEET_7,0);
		// this.writeDPDLabel();
		// }
		// V2.502 WJH E 070510 E 패키지 타입 추가
	}
	
	// V2.502 WJH E 070511 S LG양식에 맞춘 오브젝트
	public void writeObjectLabel(){
		WritableCellFormat format1 = new WritableCellFormat();
		WritableCellFormat format2 = new WritableCellFormat();
		WritableCellFormat format3 = new WritableCellFormat();
		try{
		format1.setBackground(jxl.format.Colour.YELLOW);
		format1.setAlignment(jxl.format.Alignment.CENTRE);
		format2.setBackground(jxl.format.Colour.AQUA);
		format2.setAlignment(jxl.format.Alignment.CENTRE);
		format3.setBackground(jxl.format.Colour.LIGHT_BLUE);
		format3.setAlignment(jxl.format.Alignment.CENTRE);

		Label Label0 = new Label(0, 0, "");
		Label0.setCellFormat(format1);
		sheet.addCell(Label0);

		Label LabelClass = new Label(1, 0, "");
		LabelClass.setCellFormat(format1);
		sheet.addCell(LabelClass);

		Label Label2 = new Label(2, 0, "");
		Label2.setCellFormat(format1);
		sheet.addCell(Label2);
		// Label LabelClass = new Label();
		Label Label3 = new Label(3, 0, "class/Interface");
		Label3.setCellFormat(format1);
		sheet.addCell(Label3);

		Label Label4 = new Label(4, 0, "");
		Label4.setCellFormat(format1);
		sheet.addCell(Label4);

		Label Label5 = new Label(5, 0, "");
		Label5.setCellFormat(format1);
		sheet.addCell(Label5);

		Label LabelOp = new Label(6, 0, "");
		LabelOp.setCellFormat(format2);
		sheet.addCell(LabelOp);

		Label Label7 = new Label(7, 0, "");
		Label7.setCellFormat(format2);
		sheet.addCell(Label7);

		Label Label8 = new Label(8, 0, "");
		Label8.setCellFormat(format2);
		sheet.addCell(Label8);

		Label Label9 = new Label(9, 0, "Operation");
		Label9.setCellFormat(format2);
		sheet.addCell(Label9);

		Label Label10 = new Label(10, 0, "");
		Label10.setCellFormat(format2);
		sheet.addCell(Label10);

		Label LabelAtt = new Label(11, 0, "");
		LabelAtt.setCellFormat(format2);
		sheet.addCell(LabelAtt);

		Label Label12 = new Label(12, 0, "");
		Label12.setCellFormat(format2);
		sheet.addCell(Label12);

		Label Label13 = new Label(13, 0, "");
		Label13.setCellFormat(format2);
		sheet.addCell(Label13);

		Label Label14 = new Label(14, 0, "");
		Label14.setCellFormat(format3);
		sheet.addCell(Label14);
		
		Label Label14_1 = new Label(17, 0, "");
		Label14_1.setCellFormat(format3);
		sheet.addCell(Label14_1);

		Label Label15 = new Label(15, 0, "Attribute");
		Label15.setCellFormat(format3);
		sheet.addCell(Label15);

		Label Label16 = new Label(16, 0, "");
		Label16.setCellFormat(format3);
		sheet.addCell(Label16);

		Label Label17 = new Label(18, 0, "");
		Label16.setCellFormat(format3);
		sheet.addCell(Label17);

		Label Label18 = new Label(19, 0, "");
		Label16.setCellFormat(format3);
		sheet.addCell(Label18);

		Label Label19 = new Label(20, 0, "");
		Label16.setCellFormat(format3);
		sheet.addCell(Label19);

		Label labelPackage = new Label(0, rowIndex, "Package");
		sheet.addCell(labelPackage);
		Label labelStereotype = new Label(1, rowIndex, "StereoType");
		sheet.addCell(labelStereotype);
		Label labelName = new Label(2, rowIndex, "Name");
		sheet.addCell(labelName);
		Label labelClassDescription = new Label(3, rowIndex, "Description");
		sheet.addCell(labelClassDescription);

		// V2.502 WJH E 070817 S 가변성/알고리즘 추가
		Label labelClassVariable = new Label(4, rowIndex, "Variablelity");
		sheet.addCell(labelClassVariable);
		Label labelClassAlgorithm = new Label(5, rowIndex, "Algorithm");
		sheet.addCell(labelClassAlgorithm);
		// V2.502 WJH E 070817 S 가변성/알고리즘 추가

		Label labelValue = new Label(6, rowIndex, "Modifier");
		sheet.addCell(labelValue);
		Label labelOperation = new Label(7, rowIndex, "Operation_Name");
		sheet.addCell(labelOperation);
		Label labelOperDesc = new Label(8, rowIndex, "Operation_Description");
		sheet.addCell(labelOperDesc);

		// V2.502 WJH E 070817 S 사전/사후조건 추가
		Label labelOperPreCondition = new Label(9, rowIndex,
				"Operation_PreCondition");
		sheet.addCell(labelOperPreCondition);
		Label labelOperPostCondition = new Label(10, rowIndex,
				"Operation_PostCondition");
		sheet.addCell(labelOperPostCondition);
		// V2.502 WJH E 070817 E 사전/사후조건 추가

		Label labelOperReturn = new Label(11, rowIndex, "Operation_ReturnType");
		sheet.addCell(labelOperReturn);
		Label labelArgumentName = new Label(12, rowIndex,
				"Operation_ArgumentName");
		sheet.addCell(labelArgumentName);
		Label labelArgumentType = new Label(13, rowIndex,
				"Operation_ArgumentType");
		sheet.addCell(labelArgumentType);
		Label labelAttrModefier = new Label(14, rowIndex, "Attribute_Modefier");
		sheet.addCell(labelAttrModefier);
		Label labelAttributeName = new Label(15, rowIndex, "Attribute_Name");
		sheet.addCell(labelAttributeName);
		Label labelAttributeType = new Label(16, rowIndex, "Attribute_Type");
		sheet.addCell(labelAttributeType);
		Label labeAttributeDesc = new Label(17, rowIndex,
				"Attribute_Description");
		sheet.addCell(labeAttributeDesc);
		Label labeID = new Label(18, rowIndex, "ID");
		sheet.addCell(labeID);
		Label labeDiagramID = new Label(19, rowIndex, "DID");
		sheet.addCell(labeDiagramID);
		Label labelEnd = new Label(20, rowIndex, "End");
		sheet.addCell(labelEnd);
		rowIndex++;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
//	 V1.02 WJH 080908 S 요구사항 추적표 추가
	public void writeRequiredLabel(){
		try{
		Label Label0 = new Label(0, 0, "Required ID");		
		sheet.addCell(Label0);

		Label LabelClass = new Label(1, 0, "usecase");		
		sheet.addCell(LabelClass);

		Label Label2 = new Label(2, 0, "actor");		
		sheet.addCell(Label2);
		// Label LabelClass = new Label();
		Label Label3 = new Label(3, 0, "class");		
		sheet.addCell(Label3);

		Label Label4 = new Label(4, 0, "Interface");		
		sheet.addCell(Label4);

		Label Label5 = new Label(5, 0, "collaboration");		
		sheet.addCell(Label5);

		Label LabelOp = new Label(6, 0, "activity");		
		sheet.addCell(LabelOp);

		Label Label7 = new Label(7, 0, "struct activity");		
		sheet.addCell(Label7);

		Label Label8 = new Label(8, 0, "action");		
		sheet.addCell(Label8);

		Label Label9 = new Label(9, 0, "object");		
		sheet.addCell(Label9);

		Label Label10 = new Label(10, 0, "central");		
		sheet.addCell(Label10);

		Label LabelAtt = new Label(11, 0, "data store");		
		sheet.addCell(LabelAtt);

		Label Label12 = new Label(12, 0, "send");		
		sheet.addCell(Label12);

		Label Label13 = new Label(13, 0, "receive");		
		sheet.addCell(Label13);

		Label Label14 = new Label(14, 0, "exception");		
		sheet.addCell(Label14);

		Label Label15 = new Label(15, 0, "part");		
		sheet.addCell(Label15);

		Label Label16 = new Label(16, 0, "component");		
		sheet.addCell(Label16);

		Label Label17 = new Label(17, 0, "state");		
		sheet.addCell(Label17);

		Label Label18 = new Label(18, 0, "sub state");		
		sheet.addCell(Label18);

		Label Label19 = new Label(19, 0, "node");	
		sheet.addCell(Label19);

		Label Label20 = new Label(20, 0, "device");	
		sheet.addCell(Label20);
		
		Label Label21 = new Label(21, 0, "execution");		
		sheet.addCell(Label21);
		
		Label Label22 = new Label(22, 0, "artifact");		
		sheet.addCell(Label22);
		
		Label Label23 = new Label(23, 0, "specfication");		
		sheet.addCell(Label23);
						
//		rowIndex++;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
//	 V1.02 WJH 080908 E 요구사항 추적표 추가
	
	public void closeWorkbook(){
		rowIndex = 1;
		try{
		if(workbook != null){
			workbook.write();
			workbook.close();
		}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

	public void readExcelObject(UMLTreeParentModel treeObject){
		
		UMLTreeModel[] tree = treeObject.getChildren();
		Cell[] row = null;
		for(int sheet=0; sheet<workbook2.getNumberOfSheets(); sheet++){
			Sheet s = workbook2.getSheet(sheet);
			if(!s.getName().equals("Object"))
				continue;
			
			for(int i=2; i<s.getRows(); i++){
				row = s.getRow(i);
				String findID = row[18].getContents();
				for (int j = 0; j < treeObject.getChildren().length; j++) {
					Object obj = treeObject.getChildren()[j];
					UMLTreeModel utm = null;
					if (obj instanceof PackageTreeModel) {
						PackageTreeModel ptm = (PackageTreeModel)obj;
						utm = ptm.searchChildModel("id", findID, false);
						if(utm == null){
							
						}
						else{
//							FinalClassModel fcm = (FinalClassModel)utm.getRefModel();
//							trm = fcm.getClassModel();
//							 kr.co.n3soft.n3com.model.comm.ClassModel classmodel =
//							 (kr.co.n3soft.n3com.model.comm.ClassModel)fcm.uMLDataModel.getElementProperty("ClassModel");
							 kr.co.n3soft.n3com.model.comm.ClassModel classmodel =
								 (kr.co.n3soft.n3com.model.comm.ClassModel)((UMLModel)utm.getRefModel()).getUMLDataModel().getElementProperty("ClassModel");
							 if(classmodel == null){
								 FinalActorModel fam = (FinalActorModel)utm.getRefModel();
								 fam.setName(row[2].getContents());
							 }
							 else{
								 classmodel.setStereotype(row[1].getContents());
								 classmodel.setName(row[2].getContents());
								 classmodel.setDesc(row[3].getContents());
								
								 ArrayList op = classmodel.getOperations();

									AttributeEditableTableItem att = null;
									
									String opName = "";
									boolean isOPAdd = true;
									OperationEditableTableItem cp = null;
									ArrayList opers = new ArrayList();
									ArrayList attrs = new ArrayList();
									ArrayList pfl = null;								
									while(true){
										if(row[7] != null && isOPAdd && !row[7].getContents().equals("")){
											cp = new OperationEditableTableItem(new Integer(0), row[7].getContents(), "void", "");
											pfl = new ArrayList();
//											V1.02 WJH 080909 S 오퍼레이션 scope 설정 추가
											String scope = row[6].getContents();
											int modefier = 0;
											if(scope.equals("public")){
												modefier = 0;
											}
											if(scope.equals("protected")){
												modefier = 1;
											}
											if(scope.equals("private")){
												modefier = 2;
											}																						
											cp.scope = modefier;
//											V1.02 WJH 080909 E 오퍼레이션 scope 설정 추가
	//										cp.name = row[7].getContents();
	//										System.out.println("row[8].getContents()=="+row[8].getContents());
											cp.setDesc(row[8].getContents());
											
	////										V2.502 WJ E 070817 S 사전/사후 조건
	//										cp.setpreCondition(row[9].getContents());
	//										cp.setpostCondition(row[10].getContents());
	////										V2.502 WJ E 070817 S 사전/사후 조건
											
											System.out.println("row[11].getContents()=="+row[11].getContents());
											cp.stype = row[11].getContents();
				
											if(!row[12].getContents().equals("")){
												ParameterEditableTableItem pl = new ParameterEditableTableItem();
												pl.name = row[12].getContents();
												pl.stype = row[13].getContents();
												pfl.add(pl);
											}
										}
										else{
											if(!row[12].getContents().equals("")){
												ParameterEditableTableItem pl = new ParameterEditableTableItem();
												pl.name = row[12].getContents();
												pl.stype = row[13].getContents();
												pfl.add(pl);
											}
										}
										
										try{
											System.out.println("row[14].getContents()="+row[14].getContents());
											if(row[10] != null && !row[15].getContents().equals("")){
												att = new AttributeEditableTableItem(new Integer(0), row[15].getContents(), row[16].getContents(), "");
//												V1.02 WJH 080909 S 오퍼레이션 scope 설정 추가
												String scope = row[14].getContents();
												int modefier = 0;
												if(scope.equals("public")){
													modefier = 0;
												}
												if(scope.equals("protected")){
													modefier = 1;
												}
												if(scope.equals("private")){
													modefier = 2;
												}																						
												att.scope = modefier;
//												V1.02 WJH 080909 E 오퍼레이션 scope 설정 추가											
//												att.setStype(row[16].getContents());
												att.setDesc(row[17].getContents());
												attrs.add(att);
	//											classmodel.setAttribute(attrs);
	//											classmodel.getAttributes().add(att);
											}
										}
										catch(Exception e){
											e.printStackTrace();
										}
				
										i++;
										if(i < s.getRows())
											row = s.getRow(i);
										else{
											if(pfl.size()>0)
												cp.setParams(pfl);
											if(cp.toString() != null && !cp.toString().equals("")){
												opers.add(cp);
	//											classmodel.setOperation(opers);
	//											classmodel.getOperations().add(cp);											
	//											cp.setIsState(true);
	//											classmodel.getOperations().add(cp);
	//											classmodel.setOperations(classmodel.getOperations());
	//											ProjectManager.getInstance().autoSize(classmodel);
											}
	
											break;
										}
										if(row[7].getContents()!=null && !row[7].getContents().trim().equals("")){
											opName = row[7].getContents();
											if(cp != null){
												String name1 = cp.name;
												if(opName!=null && !opName.equals("")){
													isOPAdd = true;
													cp.setParams(pfl);											
													if(cp.toString() != null && !cp.toString().equals("")){
														opers.add(cp);
		//												classmodel.getOperations().add(cp);
		//												cp.setIsState(true);
		//												classmodel.getOperations().add(cp);
		//												classmodel.setOperations(classmodel.getOperations());
		//												ProjectManager.getInstance().autoSize(classmodel);
													}
												}
												else{
													//KDI20071107 주석 해제 s
		//											if(cName.equals(row[2].getContents())){
														isOPAdd = false; //KDI20071107 주석 없었음
		//											}
		//											else{
		//												isOPAdd = true;
		//												cp.setParameter(pfl);
		//												if(cp.toString() != null && !cp.toString().equals(""))
		//													classModel.addOperation(cp);
		//												classModel.setOperationShow("true");											
		//											}
													//KDI20071107 주석 해제 e
												}
											}
										}
	//									V2.502 WJH E 070814 E 같은 클래스가 아닐경우
										else if(row[2].getContents() != null && !row[2].getContents().equals("")){
											if(cp != null && pfl != null && pfl.size()>0){
												cp.getParams().add(pfl);
												if(cp.toString() != null && !cp.toString().equals("")){
													opers.add(cp);
		//											classmodel.getOperations().add(cp);					
												}
											}
											isOPAdd = false;
										}
	//									V2.502 WJH E 070814 E 같은 클래스가 아닐경우
										else{
											isOPAdd = false;							
										}
																					
										try{
											if(row[18].getContents()!=null && !row[18].getContents().trim().equals("")){
												break;
											}
											
										}
										catch(Exception e){
											e.printStackTrace();
										}
									}
									i--;
									
									classmodel.setOperations(opers);
									classmodel.setAttributes(attrs);
									ProjectManager.getInstance().autoSize(classmodel);
							}
						}
					}
				}
			}
		}
	}
//	V1.01 WJH E 080428 E 엑셀 저장 추가
	// V1.01 WJH E 080423 E 추가
	
//	 V1.02 WJH 080908 S 요구사항 추적표 추가
	public void saveRequiredExcel(java.util.ArrayList list, String reqName){
		
		java.util.ArrayList tree = list;
		
		java.util.ArrayList actorList = new java.util.ArrayList();
		java.util.ArrayList usecaseList = new java.util.ArrayList();
		java.util.ArrayList collaboList = new java.util.ArrayList();
		java.util.ArrayList classList = new java.util.ArrayList();
		java.util.ArrayList interfaceList = new java.util.ArrayList();
		java.util.ArrayList activityList = new java.util.ArrayList();
		java.util.ArrayList structactivityList = new java.util.ArrayList();
		java.util.ArrayList objectList = new java.util.ArrayList();
		java.util.ArrayList datastoreList = new java.util.ArrayList();
		java.util.ArrayList centralList = new java.util.ArrayList();
		java.util.ArrayList sendList = new java.util.ArrayList();
		java.util.ArrayList receiveList = new java.util.ArrayList();
		java.util.ArrayList actionList = new java.util.ArrayList();
		java.util.ArrayList exceptionList = new java.util.ArrayList();
		java.util.ArrayList stateList = new java.util.ArrayList();
		java.util.ArrayList substateList = new java.util.ArrayList();		
		java.util.ArrayList nodeList = new java.util.ArrayList();
		java.util.ArrayList deviceList = new java.util.ArrayList();
		java.util.ArrayList artifactList = new java.util.ArrayList();
		java.util.ArrayList specifiList = new java.util.ArrayList();
		java.util.ArrayList executionList = new java.util.ArrayList();
		java.util.ArrayList componentList = new java.util.ArrayList();
		java.util.ArrayList partList = new java.util.ArrayList();		
		
		Cell[] row = null;
		boolean useList = false;
		int allCount = 0;
		try{
		for (int i = 0; i < tree.size(); i++) {
			Object obj = tree.get(i);
			
			if (obj instanceof UMLTreeModel) {
				UMLTreeModel utm = (UMLTreeModel) obj;				
				String fullname = ((UMLModel)utm.getRefModel()).getPackage()+"."+utm.getName();
				System.out.println("modelType = "+utm.getModelType());
				 if(utm.getModelType()!=0){
					 if(utm.getModelType()==6){
						 classList.add(fullname);
						 useList = true;
					 }
					 else if(utm.getModelType()==7){
						 interfaceList.add(fullname);
						 useList = true;
					 }
					 else if(utm.getModelType()==3){
						 actorList.add(fullname);
						 useList = true;
					 }
					 else if(utm.getModelType()==2){
						 usecaseList.add(fullname);
						 useList = true;
					 }
					 else if(utm.getModelType()==9){
						 exceptionList.add(fullname);
						 useList = true;
					 }
					 else if(utm.getModelType()==10){
						 activityList.add(fullname);
						 useList = true;
					 }
					 else if(utm.getModelType()==26){
						 structactivityList.add(fullname);
						 useList = true;
					 }
					 else if(utm.getModelType()==11){
						 actionList.add(fullname);
						 useList = true;
					 }
					 else if(utm.getModelType()==19){
						 objectList.add(fullname);
						 useList = true;
					 }
					 else if(utm.getModelType()==20){
						 centralList.add(fullname);
						 useList = true;
					 }
					 else if(utm.getModelType()==21){
						 datastoreList.add(fullname);
						 useList = true;
					 }
					 else if(utm.getModelType()==27){
						 partList.add(fullname);
						 useList = true;
					 }
					 else if(utm.getModelType()==5){
						 collaboList.add(fullname);
						 useList = true;
					 }
					 else if(utm.getModelType()==29){
						 componentList.add(fullname);
						 useList = true;
					 }
					 else if(utm.getModelType()==31){
						 stateList.add(fullname);
						 useList = true;
					 }
					 else if(utm.getModelType()==32){
						 substateList.add(fullname);
						 useList = true;
					 }
					 else if(utm.getModelType()==35){
						 nodeList.add(fullname);
						 useList = true;
					 }
					 else if(utm.getModelType()==37){
						 deviceList.add(fullname);
						 useList = true;
					 }
					 else if(utm.getModelType()==38){
						 executionList.add(fullname);
						 useList = true;
					 }
					 else if(utm.getModelType()==30){
						 artifactList.add(fullname);
						 useList = true;
					 }
					 else if(utm.getModelType()==39){
						 specifiList.add(fullname);
						 useList = true;
					 }
				 }				 
				 allCount++;
			}
		}		
		if(allCount>0){
			int lowCount=0;
			boolean bModel = false;
			for(int i=0; i<allCount; i++){
				Label labelUsecase = null;
				if(usecaseList.size()>i){
					labelUsecase = new Label(1, rowIndex, usecaseList.get(i).toString());
					sheet.addCell(labelUsecase);
					bModel = true;
				}
				Label labelactor = null;
				if(actorList.size()>i){
					labelactor = new Label(2, rowIndex, actorList.get(i).toString());
					sheet.addCell(labelactor);
					bModel = true;
				}
				Label labelClass = null;
				if(classList.size()>i){
					labelClass = new Label(3, rowIndex, classList.get(i).toString());
					sheet.addCell(labelClass);
					bModel = true;
				}
				Label labelinterface = null;
				if(interfaceList.size()>i){
					labelinterface = new Label(4, rowIndex, interfaceList.get(i).toString());
					sheet.addCell(labelinterface);
					bModel = true;
				}
				Label labelcollaboration = null;
				if(collaboList.size()>i){
					labelcollaboration = new Label(5, rowIndex, collaboList.get(i).toString());
					sheet.addCell(labelcollaboration);
					bModel = true;
				}
				Label labelActivity = null;
				if(activityList.size()>i){
					labelActivity = new Label(6, rowIndex, activityList.get(i).toString());
					sheet.addCell(labelActivity);
					bModel = true;
				}
				Label labelstructActivity = null;
				if(structactivityList.size()>i){
					labelstructActivity = new Label(7, rowIndex, structactivityList.get(i).toString());
					sheet.addCell(labelstructActivity);
					bModel = true;
				}
				Label labelAction = null;
				if(actionList.size()>i){
					labelAction = new Label(8, rowIndex, actionList.get(i).toString());
					sheet.addCell(labelAction);
					bModel = true;
				}
				Label labelObject = null;
				if(objectList.size()>i){
					labelObject = new Label(9, rowIndex, objectList.get(i).toString());
					sheet.addCell(labelObject);
					bModel = true;
				}
				Label labelCentral = null;
				if(centralList.size()>i){
					labelCentral = new Label(10, rowIndex, centralList.get(i).toString());
					sheet.addCell(labelCentral);
					bModel = true;
				}
				Label labelDatastore = null;
				if(datastoreList.size()>i){
					labelDatastore = new Label(11, rowIndex, datastoreList.get(i).toString());
					sheet.addCell(labelDatastore);
					bModel = true;
				}
				Label labelSend = null;
				if(sendList.size()>i){
					labelSend = new Label(12, rowIndex, sendList.get(i).toString());
					sheet.addCell(labelSend);
					bModel = true;
				}
				Label labelReceive = null;
				if(receiveList.size()>i){
					labelReceive = new Label(13, rowIndex, receiveList.get(i).toString());
					sheet.addCell(labelReceive);
					bModel = true;
				}
				Label labelException = null;
				if(exceptionList.size()>i){
					labelException = new Label(14, rowIndex, exceptionList.get(i).toString());
					sheet.addCell(labelException);
					bModel = true;
				}
				Label labelPart = null;
				if(partList.size()>i){
					labelPart = new Label(15, rowIndex, partList.get(i).toString());
					sheet.addCell(labelPart);
					bModel = true;
				}
				Label labelComponent = null;
				if(componentList.size()>i){
					labelComponent = new Label(16, rowIndex, componentList.get(i).toString());
					sheet.addCell(labelComponent);
					bModel = true;
				}
				Label labelState = null;
				if(stateList.size()>i){
					labelState = new Label(17, rowIndex, stateList.get(i).toString());
					sheet.addCell(labelState);
					bModel = true;
				}
				Label labelsubState = null;
				if(substateList.size()>i){
					labelsubState = new Label(18, rowIndex, substateList.get(i).toString());
					sheet.addCell(labelsubState);
					bModel = true;
				}
				Label labelNode = null;
				if(nodeList.size()>i){
					labelNode = new Label(19, rowIndex, nodeList.get(i).toString());
					sheet.addCell(labelNode);
					bModel = true;
				}
				Label labelDevice = null;
				if(deviceList.size()>i){
					labelDevice = new Label(20, rowIndex, deviceList.get(i).toString());
					sheet.addCell(labelDevice);
					bModel = true;
				}
				Label labelExecution = null;
				if(executionList.size()>i){
					labelExecution = new Label(21, rowIndex, executionList.get(i).toString());
					sheet.addCell(labelExecution);
					bModel = true;
				}
				Label labelArtifact = null;
				if(artifactList.size()>i){
					labelArtifact = new Label(22, rowIndex, artifactList.get(i).toString());
					sheet.addCell(labelArtifact);
					bModel = true;
				}
				Label labelspecfi = null;
				if(specifiList.size()>i){
					labelspecfi = new Label(23, rowIndex, specifiList.get(i).toString());
					sheet.addCell(labelspecfi);
					bModel = true;
				}
				
				Label labelReq = null;
				if(bModel == true){
					labelReq = new Label (0, rowIndex, reqName);
					sheet.addCell(labelReq);					
					rowIndex++;
					bModel = false;
				}
			}
		}
	}
	catch(Exception ex){
		ex.printStackTrace();
	}	
	}
//	 V1.02 WJH 080908 E 요구사항 추적표 추가
}

