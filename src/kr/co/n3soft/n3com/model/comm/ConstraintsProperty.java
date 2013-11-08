package kr.co.n3soft.n3com.model.comm;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class ConstraintsProperty implements IPropertySource {
    public static String ID_GUARD = "Guard";
    public static String ID_WEIGHT = "Weight";
    protected static IPropertyDescriptor[] descriptors;

    //	descriptors[0] = new ComboBoxPropertyDescriptor(LINESTYLE_PROP, LINESTYLE_PROP,
    //		new String[] {SOLID_STR, DASHED_STR});
    static {
        PropertyDescriptor guardProp = new TextPropertyDescriptor(ID_GUARD, "Guard");
        PropertyDescriptor weightProp = new TextPropertyDescriptor(ID_WEIGHT, "Weight");
        //		PropertyDescriptor aggregation =
        //			new ComboBoxPropertyDescriptor(ID_AGGREGATION, "包拌",
        //					new String[] {ID_NONE,ID_NAVI,ID_SHARED, ID_COMPOSITE});
        ////		widthProp.setValidator(LogicNumberCellEditorValidator.instance());
        //		PropertyDescriptor navigation =
        //			new ComboBoxPropertyDescriptor(ID_NAVIGATION, "规氢己",
        //					new String[] {"false", "true"});
        //		PropertyDescriptor multiplicity =
        //			new ComboBoxPropertyDescriptor(ID_MULT, "促吝己",
        //					new String[] {"0", "0..1","0..*","1","1..*"});
        descriptors = new IPropertyDescriptor[] { guardProp, weightProp };
    }

    protected ConstraintsModel roleModel = new ConstraintsModel();

    public ConstraintsProperty(ConstraintsModel p) {
        this.roleModel = p;
    }

    public Object getEditableValue() {
        return roleModel.clone();
    }

    public Object getPropertyValue(Object propName) {
        return getPropertyValue((String)propName);
    }

    public Object getPropertyValue(String propName) {
        if (ID_GUARD.equals(propName)) {
            return this.roleModel.getGuard();
        }
        else if (ID_WEIGHT.equals(propName)) {
            return this.roleModel.getWeight();
        }
        return null;
    }

    public void setPropertyValue(Object propName, Object value) {
        setPropertyValue((String)propName, value);
    }

    public void setPropertyValue(String propName, Object value) {
        if (ID_GUARD.equals(propName)) {
            this.roleModel.setGuard((String)value);
        }
        else if (ID_WEIGHT.equals(propName)) {
            this.roleModel.setWeight((String)value);
        }
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
        if (ID_GUARD.equals(propName)) {
            return true;
        }
        else if (ID_WEIGHT.equals(propName)) {
            return true;
        }
        return false;
    }

    public String toString() {
        return ""; //$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
    }
}
