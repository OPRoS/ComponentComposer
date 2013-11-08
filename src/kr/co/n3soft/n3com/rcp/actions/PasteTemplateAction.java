package kr.co.n3soft.n3com.rcp.actions;

import java.util.List;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.examples.logicdesigner.LogicMessages;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;

abstract class PasteTemplateAction extends SelectionAction {
    /**
     * Constructor for PasteTemplateAction.
     * @param editor
     */
    public PasteTemplateAction(IWorkbenchPart editor) {
        super(editor);
    }

    /**
     * @return <code>true</code> if {@link #createPasteCommand()} returns an executable command
     * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
     */
    protected boolean calculateEnabled() {
        // TODO Workaround for Bug 82622/39369.  Should be removed when 39369 is fixed.
        return true;
    }

    /**
     * Creates and returns a command (which may be <code>null</code>) to create a new EditPart
     * based on the template on the clipboard.
     * @return the paste command
     */
    protected Command createPasteCommand() {
        Command result = null;
        List selection = getSelectedObjects();
        if (selection != null && selection.size() == 1) {
            Object obj = selection.get(0);
            if (obj instanceof GraphicalEditPart) {
                GraphicalEditPart gep = (GraphicalEditPart)obj;
                Object template = getClipboardContents();
                if (template != null) {
                    CreationFactory factory = getFactory(template);
                    if (factory != null) {
                        CreateRequest request = new CreateRequest();
                        request.setFactory(factory);
                        request.setLocation(getPasteLocation(gep));
                        result = gep.getCommand(request);
                    }
                }
            }
        }
        return result;
    }

    /**
     * Returns the template on the clipboard, if there is one.  Note that the template on
     * the clipboard might be from a palette from another type of editor.
     * @return the clipboard's contents
     */
    protected Object getClipboardContents() {
        return Clipboard.getDefault().getContents();
    }

    /**
     * Returns the appropriate Factory object to be used for the specified template. This
     * Factory is used on the CreateRequest that is sent to the target EditPart.  Note that
     * the given template might be from a palette for a different GEF-based editor.  In that
     * case, this method can return <code>null</code>.
     * @param template the template Object; it will never be <code>null</code>
     * @return a Factory
     */
    protected CreationFactory getFactory(Object template) {
        if (template instanceof CreationFactory)
            return (CreationFactory)template;
        return null;
    }

    /**
     * @param container the parent of the new part that is being pasted
     * @return the location at which
     */
    protected abstract Point getPasteLocation(GraphicalEditPart container);

    /** @see org.eclipse.gef.ui.actions.EditorPartAction#init() */
    protected void init() {
        setId(ActionFactory.PASTE.getId());
        setText(LogicMessages.PasteAction_ActionLabelText);
    }

    /** Executes the command returned by {@link #createPasteCommand()}. */
    public void run() {
        execute(createPasteCommand());
    }
}
