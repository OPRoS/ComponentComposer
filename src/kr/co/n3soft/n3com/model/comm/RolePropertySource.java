package kr.co.n3soft.n3com.model.comm;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class RolePropertySource implements IPropertySource {
    public static String ID_AGGREGATION = "aggregation"; //$NON-NLS-1$
    public static String ID_NAVIGATION = "navigation"; //$NON-NLS-1$
    public static String ID_MULT = "multiplicity";
    public static String ID_NONE = "N/A";
    public static String ID_SHARED = "집합연관";
    public static String ID_COMPOSITE = "복합연관";
    public static String ID_NAVI = "방향성";
    protected static IPropertyDescriptor[] descriptors;

    //	descriptors[0] = new ComboBoxPropertyDescriptor(LINESTYLE_PROP, LINESTYLE_PROP,
    //		new String[] {SOLID_STR, DASHED_STR});
    static {
        PropertyDescriptor aggregation = new ComboBoxPropertyDescriptor(ID_AGGREGATION, "관계",
            new String[] { ID_NONE, ID_NAVI, ID_SHARED, ID_COMPOSITE });
        //		widthProp.setValidator(LogicNumberCellEditorValidator.instance());
        PropertyDescriptor navigation = new ComboBoxPropertyDescriptor(ID_NAVIGATION, "방향성",
            new String[] { "false", "true" });
        PropertyDescriptor multiplicity = new ComboBoxPropertyDescriptor(ID_MULT, "다중성",
            new String[] { "0", "0..1", "0..*", "1", "1..*" });
        descriptors = new IPropertyDescriptor[] { aggregation, multiplicity };
    }

    protected RoleModel roleModel = null;

    public RolePropertySource(RoleModel p) {
        this.roleModel = p;
    }

    public Object getEditableValue() {
        return roleModel.clone();
    }

    public Object getPropertyValue(Object propName) {
        return getPropertyValue((String)propName);
    }

    public Object getPropertyValue(String propName) {
        if (ID_AGGREGATION.equals(propName)) {
            return this.roleModel.getAggregation();
        }
        else if (ID_NAVIGATION.equals(propName)) {
            return this.roleModel.getNavigation();
        }
        else if (ID_MULT.equals(propName)) {
            return this.roleModel.getMultiplicity();
        }
        return null;
    }

    public void setPropertyValue(Object propName, Object value) {
        setPropertyValue((String)propName, value);
    }

    public void setPropertyValue(String propName, Object value) {
        if (ID_AGGREGATION.equals(propName)) {
            this.roleModel.setAggregation((Integer)value);
        }
        else if (ID_NAVIGATION.equals(propName)) {
            this.roleModel.setNavigation((Integer)value);
        }
        else if (ID_MULT.equals(propName)) {
            this.roleModel.setMultiplicity((Integer)value);
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
        if (ID_AGGREGATION.equals(propName)) {
            return true;
        }
        else if (ID_NAVIGATION.equals(propName)) {
            return true;
        }
        else if (ID_MULT.equals(propName)) {
            return true;
        }
        return false;
    }

    public String toString() {
        return ""; //$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
    }
}
