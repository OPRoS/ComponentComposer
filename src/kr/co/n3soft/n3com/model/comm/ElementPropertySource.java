package kr.co.n3soft.n3com.model.comm;

import org.eclipse.draw2d.geometry.Dimension;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class ElementPropertySource implements IPropertySource {
    public static String ID_NAME = "name"; //$NON-NLS-1$
    public static String ID_COLOR = "color"; //$NON-NLS-1$
    //	public static String ID_HEIGHT = "height";//$NON-NLS-1$
    protected static IPropertyDescriptor[] descriptors;

    //
    static {
        descriptors = new IPropertyDescriptor[] { };
    }

    protected Dimension dimension = null;
    protected String name = null;

    //	protected Color background = null;
    public ElementPropertySource(String _name) {
        this.name = _name;
        //		this.
    }

    public Object getEditableValue() {
        return name;
    }

    public Object getPropertyValue(Object propName) {
        return getPropertyValue((String)propName);
    }

    public Object getPropertyValue(String propName) {
        if (ID_NAME.equals(propName)) {
            return name;
        }
        //		if(ID_WIDTH.equals(propName)){
        //			return new String(new Integer(dimension.width).toString());
        //		}
        return null;
    }

    public void setPropertyValue(Object propName, Object value) {
        setPropertyValue((String)propName, value);
    }

    public void setPropertyValue(String propName, Object value) {
        if (ID_NAME.equals(propName)) {
            //			Integer newInt = new Integer((String)value);
            //			dimension.height = newInt.intValue();
            this.name = (String)value;
        }
        //		if(ID_WIDTH.equals(propName)){
        //			Integer newInt = new Integer((String)value);
        //			dimension.width = newInt.intValue();
        //		}
    }

    public IPropertyDescriptor[] getPropertyDescriptors() {
        return descriptors;
    }

    public void resetPropertyValue(String propName) {
    }

    public void resetPropertyValue(Object propName) {
    }

    public boolean isPropertySet(Object propName) {
        return true;
    }

    public boolean isPropertySet(String propName) {
        if (ID_NAME.equals(propName)) return true; // || ID_WIDTH.equals(propName))return true;
        return false;
    }

    public String toString() {
        return name.toString(); //$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
    }
}
