package kr.co.n3soft.n3com.ruler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import kr.co.n3soft.n3com.model.comm.UMLModelGuide;
import kr.co.n3soft.n3com.model.comm.UMLModelRuler;
import kr.co.n3soft.n3com.model.comm.command.CreateGuideCommand;
import kr.co.n3soft.n3com.model.comm.command.DeleteGuideCommand;
import kr.co.n3soft.n3com.model.comm.command.MoveGuideCommand;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.rulers.RulerChangeListener;
import org.eclipse.gef.rulers.RulerProvider;

public class UMLModelRulerProvider extends RulerProvider {
    private UMLModelRuler ruler;

    private PropertyChangeListener rulerListener = new PropertyChangeListener() {
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals(UMLModelRuler.PROPERTY_CHILDREN)) {
                UMLModelGuide guide = (UMLModelGuide)evt.getNewValue();
                if (getGuides().contains(guide)) {
                    guide.addPropertyChangeListener(guideListener);
                } else {
                    guide.removePropertyChangeListener(guideListener);
                }
                for (int i = 0; i < listeners.size(); i++) {
                    ((RulerChangeListener)listeners.get(i)).notifyGuideReparented(guide);
                }
            } else {
                for (int i = 0; i < listeners.size(); i++) {
                    ((RulerChangeListener)listeners.get(i)).notifyUnitsChanged(ruler.getUnit());
                }
            }
        }
    };

    private PropertyChangeListener guideListener = new PropertyChangeListener() {
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals(UMLModelGuide.PROPERTY_CHILDREN)) {
                for (int i = 0; i < listeners.size(); i++) {
                    ((RulerChangeListener)listeners.get(i)).notifyPartAttachmentChanged(evt.getNewValue(), evt.getSource());
                }
            } else {
                for (int i = 0; i < listeners.size(); i++) {
                    ((RulerChangeListener)listeners.get(i)).notifyGuideMoved(evt.getSource());
                }
            }
        }
    };

    public UMLModelRulerProvider(UMLModelRuler ruler) {
        this.ruler = ruler;
        this.ruler.addPropertyChangeListener(rulerListener);
        List guides = getGuides();
        for (int i = 0; i < guides.size(); i++) {
//        	
            ((UMLModelGuide)guides.get(i)).addPropertyChangeListener(guideListener);
        }
    }

    public List getAttachedModelObjects(Object guide) {
        return new ArrayList(((UMLModelGuide)guide).getParts());
    }

    public Command getCreateGuideCommand(int position) {
        return new CreateGuideCommand(ruler, position);
    }

    public Command getDeleteGuideCommand(Object guide) {
        return new DeleteGuideCommand((UMLModelGuide)guide, ruler);
    }

    public Command getMoveGuideCommand(Object guide, int pDelta) {
        return new MoveGuideCommand((UMLModelGuide)guide, pDelta);
    }

    public int[] getGuidePositions() {
        List guides = getGuides();
        int[] result = new int[guides.size()];
        for (int i = 0; i < guides.size(); i++) {
            result[i] = ((UMLModelGuide)guides.get(i)).getPosition();
        }
        return result;
    }

    public Object getRuler() {
        return ruler;
    }

    public int getUnit() {
        return ruler.getUnit();
    }

    public void setUnit(int newUnit) {
        ruler.setUnit(newUnit);
    }

    public int getGuidePosition(Object guide) {
        return ((UMLModelGuide)guide).getPosition();
    }

    public List getGuides() {
        return ruler.getGuides();
    }
}
