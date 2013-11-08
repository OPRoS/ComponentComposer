package kr.co.n3soft.n3com.rcp.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.internal.GEFMessages;
import org.eclipse.gef.internal.InternalImages;
import org.eclipse.gef.requests.AlignmentRequest;
import org.eclipse.gef.tools.ToolUtilities;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;

public class AlignAction extends SelectionAction{
	/**
	 * Indicates that the bottom edges should be aligned.
	 */
	public static final String ID_ALIGN_BOTTOM = GEFActionConstants.ALIGN_BOTTOM;

	/**
	 * Indicates that the horizontal centers should be aligned.
	 */
	public static final String ID_ALIGN_CENTER = GEFActionConstants.ALIGN_CENTER;

	/**
	 * Indicates that the left edges should be aligned.
	 */
	public static final String ID_ALIGN_LEFT   = GEFActionConstants.ALIGN_LEFT;

	/**
	 * Indicates that the vertical midpoints should be aligned.
	 */
	public static final String ID_ALIGN_MIDDLE = GEFActionConstants.ALIGN_MIDDLE;

	/**
	 * Indicates that the right edges should be aligned.
	 */
	public static final String ID_ALIGN_RIGHT  = GEFActionConstants.ALIGN_RIGHT;

	/**
	 * Indicates that the top edges should be aligned.
	 */
	public static final String ID_ALIGN_TOP    = GEFActionConstants.ALIGN_TOP;
	private int alignment;
	
	boolean bAlignAction = false;
	
	public String debugText = "";	
	public String getDebugText(){
		return debugText;
	}
	
	private List operationSet;

	/**
	 * @deprecated use AlignmentAction(IWorkbenchPart, int align)
	 * @param editor the editor
	 * @param align the alignment ID
	 */
	public AlignAction(IEditorPart editor, int align) {
		this((IWorkbenchPart)editor, align);
	}

	/**
	 * Constructs an AlignmentAction with the given part and alignment ID.  The alignment ID
	 * must by one of:
	 * <UL>
	 *   <LI>GEFActionConstants.ALIGN_LEFT
	 *   <LI>GEFActionConstants.ALIGN_RIGHT
	 *   <LI>GEFActionConstants.ALIGN_CENTER
	 *   <LI>GEFActionConstants.ALIGN_TOP
	 *   <LI>GEFActionConstants.ALIGN_BOTTOM
	 *   <LI>GEFActionConstants.ALIGN_MIDDLE
	 * </UL>  
	 * @param part the workbench part used to obtain context
	 * @param align the aligment ID.
	 */
	public AlignAction(IWorkbenchPart part, int align) {
		super(part);
		alignment = align;
		initUI();		
	}

	/**
	 * Returns the alignment rectangle to which all selected parts should be aligned.
	 * @param request the alignment Request
	 * @return the alignment rectangle
	 */
	protected Rectangle calculateAlignmentRectangle(Request request) {
		List editparts = getOperationSet(request);
		if (editparts == null || editparts.isEmpty())
			return null;
		
		GraphicalEditPart part = (GraphicalEditPart)editparts.get(0);
		if(bAlignAction){
			String id = this.getId();
			int index = id.lastIndexOf(".")+1;
			id = id.substring(index, id.length());
			for(int i=0; i<editparts.size(); i++){
//				if(editparts.size()==i+1)
//					break;
				GraphicalEditPart tempPart;

				if(id.endsWith("right")){
//					V1.02 WJH E 080901 S ���� �κ� ����
					if(editparts.size()>i+1)
						tempPart  = (GraphicalEditPart)editparts.get(i+1);
					else 
						break;
//					V1.02 WJH E 080901 E ���� �κ� ����
					int partX = part.getFigure().getBounds().x+part.getFigure().getBounds().width;
					int tempX = tempPart.getFigure().getBounds().x+tempPart.getFigure().getBounds().width;
					if(partX<tempX)
						part = tempPart;					
				}
				else if(id.endsWith("left")){
//					V1.02 WJH E 080901 S ���� �κ� ����
					if(editparts.size()>i+1)
						tempPart  = (GraphicalEditPart)editparts.get(i+1);
					else 
						break;
//					V1.02 WJH E 080901 E ���� �κ� ����
					int partX = part.getFigure().getBounds().x;
					int tempX = tempPart.getFigure().getBounds().x;
					if(partX>tempX)
						part = tempPart;					
				}
				else if(id.endsWith("bottom")){
//					V1.02 WJH E 080901 S ���� �κ� ����
					if(editparts.size()>i+1)
						tempPart  = (GraphicalEditPart)editparts.get(i+1);
					else 
						break;
//					V1.02 WJH E 080901 E ���� �κ� ����
					int partY = part.getFigure().getBounds().y+part.getFigure().getBounds().height;
					int tempY = tempPart.getFigure().getBounds().y+tempPart.getFigure().getBounds().height;
					if(partY<tempY)
						part = tempPart;					
				}
				else if(id.endsWith("top")){
//					V1.02 WJH E 080901 S ���� �κ� ����
					if(editparts.size()>i+1)
						tempPart  = (GraphicalEditPart)editparts.get(i+1);
					else 
						break;
//					V1.02 WJH E 080901 E ���� �κ� ����
					int partY = part.getFigure().getBounds().y;
					int tempY = tempPart.getFigure().getBounds().y;
					if(partY>tempY)
						part = tempPart;					
				}
				else if(id.endsWith("center")){
					int count = 0;
					for(int j=0; j<editparts.size(); j++){
						part = (GraphicalEditPart)editparts.get(i);
						tempPart = (GraphicalEditPart)editparts.get(j);
						System.out.println("partx : "+part.getFigure().getBounds().x);
						System.out.println("tempx : "+tempPart.getFigure().getBounds().x);
						if(part.getFigure().getBounds().x>tempPart.getFigure().getBounds().x)
							count++;
					}					
					int center = editparts.size()/2;
					if(count==center){
						break;
					}				
				}				
				else if(id.endsWith("middle")){
					int count = 0;
					for(int j=0; j<editparts.size(); j++){
						part = (GraphicalEditPart)editparts.get(i);
						tempPart = (GraphicalEditPart)editparts.get(j);
						System.out.println("partx : "+part.getFigure().getBounds().x);
						System.out.println("tempx : "+tempPart.getFigure().getBounds().x);
						if(part.getFigure().getBounds().y>tempPart.getFigure().getBounds().y)
							count++;
					}					
					int center = editparts.size()/2;
					if(count==center){						
						break;
					}				
				}
			}
		}
		bAlignAction = false;
		Rectangle rect = new PrecisionRectangle(part.getFigure().getBounds());
		part.getFigure().translateToAbsolute(rect);
		return rect;
	}

	/**
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	protected boolean calculateEnabled() {
		operationSet = null;
		Command cmd = createAlignmentCommand();
		if (cmd == null)
			return false;
		return cmd.canExecute();
	}

	public Command createAlignmentCommand() {
		AlignmentRequest request = new AlignmentRequest(RequestConstants.REQ_ALIGN);
		request.setAlignmentRectangle(calculateAlignmentRectangle(request));
		request.setAlignment(alignment);
		List editparts = getOperationSet(request);
		if (editparts.size() < 2)
			return null;

		CompoundCommand command = new CompoundCommand();
		command.setDebugLabel(getText());
		for (int i = 0; i < editparts.size(); i++) {
			EditPart editpart = (EditPart)editparts.get(i);
			command.add(editpart.getCommand(request));
		}
//		this.debugText = command.getLabel();
		return command;
	}

	/**
	 * @see org.eclipse.gef.Disposable#dispose()
	 */
	public void dispose() {
		operationSet = Collections.EMPTY_LIST;
		super.dispose();
	}

	/**
	 * Returns the list of editparts which will participate in alignment.
	 * @param request the alignment request
	 * @return the list of parts which will be aligned
	 */
	protected List getOperationSet(Request request) {
		if (operationSet != null)
			return operationSet;
		List editparts = new ArrayList(getSelectedObjects());
		if (editparts.isEmpty() || !(editparts.get(0) instanceof GraphicalEditPart))
			return Collections.EMPTY_LIST;
		Object primary = editparts.get(editparts.size() - 1);
		editparts = ToolUtilities.getSelectionWithoutDependants(editparts);
		ToolUtilities.filterEditPartsUnderstanding(editparts, request);
		if (editparts.size() < 2 || !editparts.contains(primary))
			return Collections.EMPTY_LIST;
		EditPart parent = ((EditPart)editparts.get(0)).getParent();
		for (int i = 1; i < editparts.size(); i++) {
			EditPart part = (EditPart)editparts.get(i);
			if (part.getParent() != parent)
				return Collections.EMPTY_LIST;
		}
		return editparts;
	}

	/**
	 * Initializes the actions UI presentation.
	 */
	protected void initUI() {
		switch (alignment) {
			case PositionConstants.LEFT:
				setId(GEFActionConstants.ALIGN_LEFT);
				setText(GEFMessages.AlignLeftAction_Label);
				setToolTipText(GEFMessages.AlignLeftAction_Tooltip);
				setImageDescriptor(InternalImages.DESC_HORZ_ALIGN_LEFT);
				setDisabledImageDescriptor(InternalImages.DESC_HORZ_ALIGN_LEFT_DIS);
				break;
			
			case PositionConstants.RIGHT:
				setId(GEFActionConstants.ALIGN_RIGHT);
				setText(GEFMessages.AlignRightAction_Label);
				setToolTipText(GEFMessages.AlignRightAction_Tooltip);
				setImageDescriptor(InternalImages.DESC_HORZ_ALIGN_RIGHT);
				setDisabledImageDescriptor(InternalImages.DESC_HORZ_ALIGN_RIGHT_DIS);
				break;
			
			case PositionConstants.TOP:
				setId(GEFActionConstants.ALIGN_TOP);
				setText(GEFMessages.AlignTopAction_Label);
				setToolTipText(GEFMessages.AlignTopAction_Tooltip);
				setImageDescriptor(InternalImages.DESC_VERT_ALIGN_TOP);
				setDisabledImageDescriptor(InternalImages.DESC_VERT_ALIGN_TOP_DIS);
				break;
			
			case PositionConstants.BOTTOM:
				setId(GEFActionConstants.ALIGN_BOTTOM);
				setText(GEFMessages.AlignBottomAction_Label);
				setToolTipText(GEFMessages.AlignBottomAction_Tooltip);
				setImageDescriptor(InternalImages.DESC_VERT_ALIGN_BOTTOM);
				setDisabledImageDescriptor(InternalImages.DESC_VERT_ALIGN_BOTTOM_DIS);
				break;
			
			case PositionConstants.CENTER:
				setId(GEFActionConstants.ALIGN_CENTER);
				setText(GEFMessages.AlignCenterAction_Label);
				setToolTipText(GEFMessages.AlignCenterAction_Tooltip);
				setImageDescriptor(InternalImages.DESC_HORZ_ALIGN_CENTER);
				setDisabledImageDescriptor(InternalImages.DESC_HORZ_ALIGN_CENTER_DIS);
				break;
			
			case PositionConstants.MIDDLE:
				setId(GEFActionConstants.ALIGN_MIDDLE);
				setText(GEFMessages.AlignMiddleAction_Label);
				setToolTipText(GEFMessages.AlignMiddleAction_Tooltip);
				setImageDescriptor(InternalImages.DESC_VERT_ALIGN_MIDDLE);
				setDisabledImageDescriptor(InternalImages.DESC_VERT_ALIGN_MIDDLE_DIS);
				break;
		}
	}

	/**
	 * @see org.eclipse.jface.action.IAction#run()
	 */
	public void run() {
		// WJH 090801 S
		  //PKY 08101368 S
//		  if(ProjectManager.getInstance()!=null)
//		   ProjectManager.getInstance().setModify(true);
		  operationSet = null;
		  bAlignAction = true;
		  Command cmd = createAlignmentCommand();
		//  try{
		//  CompoundCommand cc = (CompoundCommand)cmd;
		//  cc.getDebugLabel();
		//  this.debugText = ((CompoundCommand)cmd).getDebugLabel();
		//  }
		//  catch(Exception ex){
		//  ex.printStackTrace();
		//  }
		  //PKY 08101477 S
		  if(ProjectManager.getInstance()!=null)
		   ProjectManager.getInstance().setAlignAction(true);
		  execute(cmd);
		  bAlignAction = false;
		  if(ProjectManager.getInstance()!=null)
		   ProjectManager.getInstance().setAlignAction(false);
		  //PKY 08101477 E

		 }
//	WJH 090801 E
	
}