package kr.co.n3soft.n3com.figures;

import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;

public class CompartmentFigureBorder extends AbstractBorder {
    public Insets getInsets(IFigure figure) {
        return new Insets(1, 0, 0, 0);
    }

    public void paint(IFigure figure, Graphics graphics, Insets insets) {
    	graphics.setForegroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08081101 S ������Ʈ ��ü�� ��ü �׵θ� ���� ���� ��  ���ֵ��� ����
    	graphics.drawLine(getPaintRectangle(figure, insets).getTopLeft(), tempRect.getTopRight());
    }
}
