package kr.co.n3soft.n3com.rcp;

import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import org.eclipse.core.runtime.IPath;
import org.eclipse.gef.examples.shapes.ShapesPlugin;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.IPersistableElement;

public class N3EditorInput implements IPathEditorInput {
    private IPath path;

    public N3EditorInput(IPath path) {
        this.path = path;
    }

    public boolean exists() {
        return path.toFile().exists();
    }

    public ImageDescriptor getImageDescriptor() {
        return ShapesPlugin.imageDescriptorFromPlugin("org.eclipse.gef.examples.shapes.rcp", "shapes.gif");
    }

    public String getName() {
        return path.toString();
    }

    public IPersistableElement getPersistable() {
        return null;
    }

    public String getToolTipText() {
        return path.toString();
    }

    public Object getAdapter(Class adapter) {
        return null;
    }

    public int hashCode() {
        return path.hashCode();
    }

    public UMLDiagramModel getUMLDiagramModel() {
        return new UMLDiagramModel();
    }

    public IPath getPath() {
        return path;
    }
}
