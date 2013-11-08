package kr.co.n3soft.n3com.model.comm;

import java.io.IOException;
import java.util.ArrayList;

import kr.co.n3soft.n3com.comm.figures.UMLElementFigure;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.communication.MessageCommunicationModel;
import kr.co.n3soft.n3com.model.umlclass.OperationEditableTableItem;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.MessageBox;

public class ElementLabelModel extends UMLModel {
	String portId;
	int messageIntType = 0;
	static final long serialVersionUID = 1;
	private String text = "";
	public int align;
	private Integer layout = null;
	private int parentLayout = 0;
	private String elementType = "";
	//ijs080429
	public  Image LOGIC_LABEL_ICON = null; //$NON-NLS-1$

	private static int count;
	private UMLModel refModel = null;
	private String num = "";
	UMLElementFigure uMLElementFigure = null;
	private String message = null;
	private String multi = "";
	boolean isViewMulti = false;
	OperationEditableTableItem oti;
	OperationElementModel oem;
	TypeRefModel trm;
	boolean isSetOti = false;
	boolean isReadOnly = false;

	public LineModel lineModel = null;

	public ElementLabelModel() {
		super();
//		ijs080429
		LOGIC_LABEL_ICON	= ProjectManager.getInstance().getImage(ProjectManager.getInstance().getElementType(this, -1));
		//		size.width = 50;
		size.height = 15;
		this.align = PositionConstants.LEFT;

	}
	
	// 110823 SDM S - �������� ����
	public ElementLabelModel(boolean bReadOnly) {
		super();
//		ijs080429
		LOGIC_LABEL_ICON	= ProjectManager.getInstance().getImage(ProjectManager.getInstance().getElementType(this, -1));
		//		size.width = 50;
		size.height = 15;
		this.align = PositionConstants.LEFT;
		isReadOnly = bReadOnly;
	}
	// 110823 SDM E - �������� ����

	public ElementLabelModel(int align) {
		super();
		//		size.width = 50;
		size.height = 15;
		this.align = align;
	}

	public String getLabelContents() {
		return text;
		//		return text;
	}
	public void setViewMulti(boolean p){
		isViewMulti = p;
	}



	public boolean getViewMulti(){
		return isViewMulti;
	}

	public void setMulti(String p){
		this.multi = p;
	}
	public String getMulti(){
		return this.multi;
	}

//	public void setMulti(String m){
//	this.multi = m;
//	}



	//msg
	public void setE(UMLElementFigure p) {
		uMLElementFigure = p;
	}

	public UMLElementFigure getE() {
		return this.uMLElementFigure;
	}

	public Image getIconImage() {
		return LOGIC_LABEL_ICON;
	}

	protected String getNewID() {
		return Integer.toString(count++);
	}

	public void setRefModel(UMLModel p) {
		this.refModel = p;
	}

	public UMLModel getRefModel() {
		return this.refModel;
	}

	public Dimension getSize() {
		return new Dimension(size.width, size.height);
	}

	private void readObject(java.io.ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
	}

	public void setSize(Dimension d) {
		d.height = size.height;
		super.setSize(d);
	}
	public void setNumber(String p){
		num = p;

	}
	//PKY 08070701 S Ŀ�´����̼� ��� ����
	public void setName(String s) {
		text=s;
	}
	//PKY 08070701 E Ŀ�´����̼� ��� ����

	//	public void setUpdateListener(java.util.ArrayList p){
	//		updateListener = p;
	//	}
	public void setLabelContents(String s) {

		if(this.isReadOnly){
			if(s.trim().equals("composite") || s.trim().equals("atomic")){
			
			}
			else
			return;
		}
//		if(!s.trim().equals("composite") || !s.trim().equals("atomic"))
		
		if(this.getType().equals("NAME")){
			UMLModel um =	(UMLModel)this.getAcceptParentModel();
			UMLTreeModel ut = this.getUMLTreeModel();
			if(ut!=null && !this.text.equals(s)){
				//20080811IJS
				boolean isOverlapping = ProjectManager.getInstance().isOverlapping(ut.getParent(), ut.getModelType(), s,ut);
				if(isOverlapping) {
					//111025 SDM S - �̸��ߺ� ����â �ߺ� �߻� �ӽ��ذ�
					if(!this.temp_name.equals(s)){
						MessageBox dialog = new MessageBox(ProjectManager.getInstance().window.getShell(),SWT.ICON_WARNING);
						dialog.setText("Message");
						dialog.setMessage(N3Messages.DIALOG_SAME_NAME_CONFLICT_MESSAGE); //KDI 080908 0002
						
						this.temp_name = s;
						dialog.open();
						
					}
					
					s = this.text;
					//111025 SDM E - �̸��ߺ� ����â �ߺ� �߻� �ӽ��ذ�
					
					return ;
				}
				
			}
		}
		if(this instanceof OperationElementModel){
			OperationElementModel om = (OperationElementModel)this;
			ArrayList paramsList=(ArrayList) om.getAttributeEditableTableItem().getParams().clone();//2008042515PKY S
			om.getAttributeEditableTableItem().setString(s);
//			om.getAttributeEditableTableItem().setParams(paramsList);//2008042515PKY S
			text =om.getAttributeEditableTableItem().toString();

			firePropertyChange("labelContents", null, text); //$NON-NLS-2$//$NON-NLS-1$
//			UpdateEvent e = new UpdateEvent(IUpdateType.REMOVE_NAME, null);
//			this.fireChildUpdate(e);
			if(om.getAttributeEditableTableItem().getMessageCommunicationModel()!=null){
				om.getAttributeEditableTableItem().getMessageCommunicationModel().getLabelContents().setSetOti(true);
				om.getAttributeEditableTableItem().getMessageCommunicationModel().setName(":"+om.getAttributeEditableTableItem().toMessageString());
				om.getAttributeEditableTableItem().getMessageCommunicationModel().getLabelContents().setSetOti(false);
			}
			

			return;


		}
		else if(this instanceof AttributeElementModel){
			AttributeElementModel om = (AttributeElementModel)this;

			om.getAttributeEditableTableItem().setString(s);

			text =om.getAttributeEditableTableItem().toString();

			firePropertyChange("labelContents", null, text); //$NON-NLS-2$//$NON-NLS-1$
			UpdateEvent e = new UpdateEvent(IUpdateType.REMOVE_NAME, null);
			this.fireChildUpdate(e);

			return;


		}
		if(this.isSetOti){
//			s = ":"+this.getOti().toMessageString();//PKY 08060201 S ���� �ҷ����� �� ��� �����޼��� ���۷��̼ǰ� ������ �������� ����� ������ �ȵǴ¹���
			s = ":"+this.getOti().toMessageString();
		}
		if (this.getType().equals("STREOTYPE") && s.trim().length()>0) {
			s = s.replaceAll("<<", "");
			s = s.replaceAll(">>", "");
			if(s.trim().length()>0)
				s = "<<"+s+">>";

		}
		//����	
		if (this.refModel != null) {
			String p = ":" + refModel.getName();
			int i = s.indexOf(":");
			if (i >= 0) {//PKY 08081801 S ������ ������ ���¿��� �ι� ������ ��� : Actor :Actor ������ ������ ����
				s = s.substring(0, i) + p;
			}
			else {
				s = s + p;
			}
		}
		else{

			int i = s.indexOf(":");
			if(this.oti==null)//PKY 08081801 S ������ �̸� ����� ���۷��̼� ����Ÿ���� ������� �ʴ� ����
				if (i >= 0) {//PKY 08081801 S ������ ������ ���¿��� �ι� ������ ��� : Actor :Actor ������ ������ ����
					s = s.substring(0, i);
				}
		}
/**
 * ��ȣ ������ ���� WJH
 */
//		if(this.getType().equals("Message")){
//			int i = s.indexOf(":");
//			if (i > -1 && !num.trim().equals("")) {
//				message = s.substring(i+1);
//				if(this.getOti()!=null && !this.isSetOti){
//					this.getOti().setString(s);//PKY 08081801 S ������ �̸� ����� ���۷��̼� ����Ÿ���� ������� �ʴ� ����
//					s = this.num+":" +this.getOti().toMessageString();
//					this.trm.setOperations((java.util.ArrayList)trm.getOperations().clone());//PKY 08050701 S Timing Line����
//				}
//				else
//					s = this.num+":" + s.substring(i+1);
//			}
//			else{
//				if(!this.num.trim().equals("")){
//					if(this.getOti()!=null && !this.isSetOti){
//						this.getOti().setString(s);
//						message = s;
//						s = this.num+":" +this.getOti().toMessageString();
//						this.trm.setOperations((java.util.ArrayList)trm.getOperations().clone());
//					}
//					else{
//						message = s;
//						s = this.num+":" +s;
//					}
//				}
//				else{
//					if(this.getOti()!=null && !this.isSetOti){
//						this.getOti().setString(s);
//						message = s;
//						s = this.num+":" +this.getOti().toMessageString();
//						this.trm.setOperations((java.util.ArrayList)trm.getOperations().clone());
//					}
//					else
//						message = s;
//				}
//			}
//			if(this.getMessageIntType()==1){
//				s=message;
//			}
//		}
		/**
		 * ��ȣ �� ��  ���� WJH
		 * ������ ���� �� �� �ҽ��� ���� ��ų 
		 */

		if(this.getType().equals("Message")){
			int i = s.indexOf(":");
			if (i > -1 && !num.trim().equals("")) {
				message = s.substring(i+1);
				if(this.getOti()!=null && !this.isSetOti){
					this.getOti().setString(s);//PKY 08081801 S ������ �̸� ����� ���۷��̼� ����Ÿ���� ������� �ʴ� ����
					s = this.getOti().toMessageString();
					this.trm.setOperations((java.util.ArrayList)trm.getOperations().clone());//PKY 08050701 S Timing Line����
				}
				else
					s = s.substring(i+1);
			}
			else{
				if(!this.num.trim().equals("")){
					if(this.getOti()!=null && !this.isSetOti){
						this.getOti().setString(s);
						message = s;
						s =this.getOti().toMessageString();
						this.trm.setOperations((java.util.ArrayList)trm.getOperations().clone());
					}
					else{
						message = s;
						s = s;
					}
				}
				else{
					if(this.getOti()!=null && !this.isSetOti){
						this.getOti().setString(s);
						message = s;
						s =this.getOti().toMessageString();
						this.trm.setOperations((java.util.ArrayList)trm.getOperations().clone());
					}
					else
						message = s;
				}
			}
			if(this.getMessageIntType()==1){
				s=message;
			}
		}
		//2008042202PKY S
		if(this.getType().equals("SelfMessage")){


			int i = s.indexOf(":");

			if (i > -1 && !num.trim().equals("")) {
//				s.substring(0,);
				message = s.substring(i+1);
				if(this.getOti()!=null && !this.isSetOti){
//					if()
					this.getOti().setString(s);//PKY 08081801 S �����޼��� ��ȯŸ�� �����̾ȵǴ¹���
//					s = this.num+":" +this.getOti().toMessageString();//PKY 08060201 S ���� �ҷ����� �� ��� �����޼��� ���۷��̼ǰ� ������ �������� ����� ������ �ȵǴ¹���
					s = this.getOti().toMessageString();//PKY 08060201 S ���� �ҷ����� �� ��� �����޼��� ���۷��̼ǰ� ������ �������� ����� ������ �ȵǴ¹���
//					oem.setLabelContents(message);
					this.trm.setOperations((java.util.ArrayList)trm.getOperations().clone());


				}
				else
//					s = this.num+":" + s.substring(i+1);//PKY 08060201 S ���� �ҷ����� �� ��� �����޼��� ���۷��̼ǰ� ������ �������� ����� ������ �ȵǴ¹���
					s = s.substring(i+1);//PKY 08060201 S ���� �ҷ����� �� ��� �����޼��� ���۷��̼ǰ� ������ �������� ����� ������ �ȵǴ¹���      		 
			}
			else{
				if(!this.num.trim().equals("")){
					if(this.getOti()!=null && !this.isSetOti){
						this.getOti().setString(s);
						message = s;
//						oem.setLabelContents(message);
//						s = this.num+":" +this.getOti().toMessageString();
						s = this.getOti().toMessageString();//PKY 08060201 S ���� �ҷ����� �� ��� �����޼��� ���۷��̼ǰ� ������ �������� ����� ������ �ȵǴ¹���
						this.trm.setOperations((java.util.ArrayList)trm.getOperations().clone());
					}
					else{
						message = s;
//						s = this.num+":" +s;;//PKY 08060201 S ���� �ҷ����� �� ��� �����޼��� ���۷��̼ǰ� ������ �������� ����� ������ �ȵǴ¹���
						s = s;//PKY 08060201 S ���� �ҷ����� �� ��� �����޼��� ���۷��̼ǰ� ������ �������� ����� ������ �ȵǴ¹���
					}
				}
				else{
					if(this.getOti()!=null && !this.isSetOti){
						this.getOti().setString(s);
						message = s;
//						oem.setLabelContents(message);
//						s = this.num+":" +this.getOti().toMessageString();//PKY 08060201 S ���� �ҷ����� �� ��� �����޼��� ���۷��̼ǰ� ������ �������� ����� ������ �ȵǴ¹���
						s = this.getOti().toMessageString();//PKY 08060201 S ���� �ҷ����� �� ��� �����޼��� ���۷��̼ǰ� ������ �������� ����� ������ �ȵǴ¹���
						this.trm.setOperations((java.util.ArrayList)trm.getOperations().clone());//PKY 08060201 S ���� �ҷ����� �� ��� �����޼��� ���۷��̼ǰ� ������ �������� ����� ������ �ȵǴ¹���
					}
					else
						message = s;

				}
			}
			if(this.getMessageIntType()==1){
				s=message;

			}

//			StringBuffer hangul      = new StringBuffer();
//			StringBuffer notHangul = new StringBuffer();



//			for(int j= 0;j < s.length();j++) {

//			if(s.charAt(j) >= 0xAC00 && s.charAt(j) <= 0xD743){
//			hangul.append(s.charAt(j));
//			} else {
//			notHangul.append(s.charAt(j));
//			}
//			}


//			Dimension reSize= new Dimension();
//			reSize.height=this.getSize().height;
//			reSize.width=hangul.length()+notHangul.length()*32;
//			if(this.lineText!=null)
//			this.lineText.setSize(reSize);

////			lineText.setSize(reSize);

//			System.out.print("");
			firePropertyChange(this.ID_NAME, null, text); //$NON-NLS-2$//$NON-NLS-1$

		}
		//2008042202PKY E
		if(this.getType().equals("Condition")){
			//PKY 08072201 S ������ Condition ���̾�׷����� �̸� �Է� �� {{}} �������� ����� ���� ����
			int is = s.indexOf("{");
			int ie = s.lastIndexOf("}");
			if (is >= 0 && ie>=0) {
				message = s.substring(is+1,ie);
				s = "{" + message+"}";
			}
			else{
				message = s;
				s = "{"+s+"}";
			}
			//PKY 08072201 E ������ Condition ���̾�׷����� �̸� �Է� �� {{}} �������� ����� ���� ����

		}
		//����
		if (this.getUMLTreeModel() != null) {
			try{
				ProjectManager.getInstance().getModelBrowser().refresh(this.getUMLTreeModel());

				this.getUMLTreeModel().setName(s);
//				this.getUMLTreeModel().getRefModel()
				//				String ptId = this.getUMLDataModel().getId();
//				UMLModel um = (UMLModel)this.getAcceptParentModel();
				
				
				ProjectManager.getInstance().getModelBrowser().refresh(this.getUMLTreeModel());
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		if(multi!=null && multi.trim().length()>0){
			int index = s.lastIndexOf("[");
			if(index>0){
				s = s.substring(0,index);
			}
//			multi = multi.replaceAll("[", "");
//			multi = multi.replaceAll("]", "");
			s = s+"["+multi+"]";
		}

		if(this.getType().equals(LineModel.ID_TRAN_TRIGERS)){

			System.out.print("");

		}


		text = s;
		
		

		firePropertyChange("labelContents", null, text); //$NON-NLS-2$//$NON-NLS-1$
//		UpdateEvent e = new UpdateEvent(IUpdateType.RENAME_TYPE, null);
//		this.fireChildUpdate(e);
	}



	public String getMessage(){
		return this.message;

	}

	public void setText(String s) {
		text = s;
	}

	public void setLayout(Integer s) {
		this.layout = s;
		//		text = s;
		//		firePropertyChange("labelContents", null, text); //$NON-NLS-2$//$NON-NLS-1$
	}

	public void setParentLayout(int s) {
		this.parentLayout = s;
		//		text = s;
		//		firePropertyChange("labelContents", null, text); //$NON-NLS-2$//$NON-NLS-1$
	}

	public Integer getLayout() {
		return this.layout;
		//		text = s;
		//		firePropertyChange("labelContents", null, text); //$NON-NLS-2$//$NON-NLS-1$
	}

	public int getParentLayout() {
		return this.parentLayout;
		//		text = s;
		//		firePropertyChange("labelContents", null, text); //$NON-NLS-2$//$NON-NLS-1$
	}

	public String getType() {
		return this.elementType;
		//		text = s;
		//		firePropertyChange("labelContents", null, text); //$NON-NLS-2$//$NON-NLS-1$
	}

	public void setType(String pElementType) {
		this.elementType = pElementType;
		LOGIC_LABEL_ICON	= ProjectManager.getInstance().getImage(ProjectManager.getInstance().getElementType(this, -1));
		//		text = s;
		//		firePropertyChange("labelContents", null, text); //$NON-NLS-2$//$NON-NLS-1$
	}

	public String toString() {
		return  getLabelContents(); //$NON-NLS-1$
	}

	//	public void setUMLDataModel(UMLDataModel p){
	//		 uMLDataModel = p;
	//		
	//	}
	public Object clone() {
		ElementLabelModel clone = new ElementLabelModel(this.align);
		clone.setLabelContents(this.text);
		clone.setSize(this.getSize());
		clone.setLayout(this.getLayout());
		clone.setType(this.getType());
		clone.setParentLayout(this.getParentLayout());
		return clone;
	}

	public OperationEditableTableItem getOti() {
		return oti;
	}

	public void setOti(OperationEditableTableItem oti) {
		this.isSetOti = true;
		this.oti = oti;
		this.setLabelContents("");
		this.isSetOti = false;
	}

	public OperationElementModel getOem() {
		return oem;
	}

	public void setOem(OperationElementModel oem) {
		this.oem = oem;
	}

	public TypeRefModel getTrm() {
		return trm;
	}

	public void setTrm(TypeRefModel trm) {
		this.trm = trm;
	}

	public boolean isSetOti() {
		return isSetOti;
	}

	public void setSetOti(boolean isSetOti) {
		this.isSetOti = isSetOti;
	}

	public int getMessageIntType() {
		return messageIntType;
	}

	public void setMessageIntType(int messageIntType) {
		this.messageIntType = messageIntType;
	}

	public LineModel getLineModel() {
		return lineModel;
	}

	public void setLineModel(LineModel lineModel) {
		this.lineModel = lineModel;
	}

	public String getPortId() {
		return portId;
	}

	public void setPortId(String portId) {
		this.portId = portId;
	}

	public boolean isReadOnly() {
		return isReadOnly;
	}

	public void setReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}
}