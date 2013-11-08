/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.gef.examples.logicdesigner.dnd;

import kr.co.n3soft.n3com.edit.UMLGraphicalPartFactory;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.dnd.AbstractTransferDropTargetListener;
import org.eclipse.gef.examples.logicdesigner.edit.NativeDropRequest;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;

public class TextTransferDropTargetListener 
	extends AbstractTransferDropTargetListener 
{

public TextTransferDropTargetListener(EditPartViewer viewer, Transfer xfer) {
	super(viewer, xfer);
}

protected Request createTargetRequest() {
	return new NativeDropRequest();
}

protected NativeDropRequest getNativeDropRequest() {
	return (NativeDropRequest)getTargetRequest();
}

protected void updateTargetRequest(){
	getNativeDropRequest().setData(getCurrentEvent().data);
}

public boolean isEnabled(DropTargetEvent event) {
//	return true;
	return super.isEnabled(event);
}

public void drop(DropTargetEvent event) {
super.drop(event);
}
protected void handleDrop() {
	super.handleDrop();
	selectAddedObject();
}
private void selectAddedObject() {
//	Object model = getCreateRequest().getNewObject();
	UMLModel model = ProjectManager.getInstance().getDragUMLModel();
	if (model == null){
//		return;
		
			return;
		
	}
	EditPartViewer viewer = getViewer();
	viewer.getControl().forceFocus();
	Object editpart = viewer.getEditPartRegistry().get(model);
	if(editpart==null){
		UMLGraphicalPartFactory uMLGraphicalPartFactory = new UMLGraphicalPartFactory();
		editpart = uMLGraphicalPartFactory.createEditPart(null, model);
	}
	if (editpart instanceof EditPart) {
		//Force a layout first.
		getViewer().flush();
		viewer.select((EditPart)editpart);
	}
}

}
