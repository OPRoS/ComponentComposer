package kr.co.n3soft.n3com.rcp.actions;

import org.eclipse.gef.examples.logicdesigner.LogicMessages;
import org.eclipse.gef.examples.logicdesigner.LogicPlugin;
import org.eclipse.gef.examples.logicdesigner.actions.IncrementDecrementAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.actions.RetargetAction;

public class N3IncrementRetargetAction extends RetargetAction {
    /**
     * Constructor for IncrementRetargetAction.
     * @param actionID
     * @param label
     */
    public N3IncrementRetargetAction() {
        super(IncrementDecrementAction.INCREMENT, LogicMessages.IncrementDecrementAction_Increment_ActionLabelText);
        setToolTipText(LogicMessages.IncrementDecrementAction_Increment_ActionToolTipText);
        setImageDescriptor(ImageDescriptor.createFromFile(LogicPlugin.class, "icons/plus.gif")); //$NON-NLS-1$
    }
}
