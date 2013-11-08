package kr.co.n3soft.n3com.figures;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import kr.co.n3soft.n3com.comm.figures.UMLFigure;
import kr.co.n3soft.n3com.model.component.AtomicComponentModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.project.dialog.DetailPropertyTableItem;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.examples.logicdesigner.figures.FixedConnectionAnchor;
import org.eclipse.gef.examples.logicdesigner.model.Circuit;
import org.eclipse.gef.handles.HandleBounds;
import org.eclipse.swt.graphics.Image;

public class ComponentFigure extends UMLFigure implements HandleBounds {
    private IFigure pane;
    private TextFlow textFlow;
    private Image image1;	// WJH 090801
    private Image image1_1;
    private Image image2;	// WJH 090801
    private Image image2_1;
    private Image image3;	// WJH 090801
    private Image image3_1;
    private Image image4;	// WJH 090801
    private Image image4_1;
    private ComponentModel model;	// WJH 090807
    private EditPart edit;
    
//    private String osName = System.getProperty("os.name");	//110823 SDM ����>> XP�� ��Ÿ �̻� �������� �ؽ�Ʈ ��Ʈ�� Ʋ�ȴ� �κ� ��Ʈ �������� ���󺹱�

    public ComponentFigure() {
//    	 setBorder(new UMLModelBorder());//PKY 08081101 S �𵨿� �׸��� �ֱ�
        createConnectionAnchors();
        setBackgroundColor(ColorConstants.tooltipBackground);        
        this.setForegroundColor(ProjectManager.getInstance().getDefaultColor());//PKY 08070901 S �� �÷� ����
        setOpaque(true);
    }

    protected void createConnectionAnchors() {
        ChopboxAnchor in, out;
        //	for(int i=0;i<10;i++){
        in = new ChopboxAnchor(this);
        out = new ChopboxAnchor(this);
        setOutputConnectionAnchor("B", out);
        setInputConnectionAnchor("A", in);
        outputConnectionAnchors.addElement(out);
        inputConnectionAnchors.addElement(in);
        //	}
    }

    //protected FixedConnectionAnchorFigure getInputConnectionAnchor(int i) {
    //	return (FixedConnectionAnchorFigure) connectionAnchors.get(Circuit.TERMINALS_IN[i]);
    //}
    public void setInputConnectionAnchor(String anName, ConnectionAnchor c) {
        connectionAnchors.put(anName, c);
    }

    public void setOutputConnectionAnchor(String anName, ConnectionAnchor c) {
        connectionAnchors.put(anName, c);
    }

    public ConnectionAnchor getTargetConnectionAnchorAt(Point p) {
        ConnectionAnchor anchor = new ChopboxAnchor(this);
        //	inputConnectionAnchors.addElement(anchor);
        return anchor;
    }

    public ConnectionAnchor getSourceConnectionAnchorAt(Point p) {
        ConnectionAnchor anchor = new ChopboxAnchor(this);
        //	outputConnectionAnchors.addElement(anchor);
        return anchor;
    }

    protected void createConnectionAnchors(Point pt) {
        FixedConnectionAnchorFigure in, out;
        out = new FixedConnectionAnchorFigure(this);
        out.getLocation(pt);
        outputConnectionAnchors.addElement(out);
    }

    public IFigure getContentsPane() {
        return pane;
    }

    protected FixedConnectionAnchor getInputConnectionAnchor(int i) {
        return (FixedConnectionAnchor)connectionAnchors.get(Circuit.TERMINALS_IN[i]);
    }

    /** @see org.eclipse.gef.handles.HandleBounds#getHandleBounds() */
    public Rectangle getHandleBounds() {
        return getBounds().getCropped(new Insets(2, 0, 2, 0));
    }

    protected FixedConnectionAnchor getOutputConnectionAnchor(int i) {
        return (FixedConnectionAnchor)connectionAnchors.get(Circuit.TERMINALS_OUT[i]);
    }

    public Dimension getPreferredSize(int w, int h) {
        Dimension prefSize = super.getPreferredSize(w, h);
        Dimension defaultSize = new Dimension(100, 50);
        prefSize.union(defaultSize);
        return prefSize;
    }

    protected void layoutConnectionAnchors() {
        int x;
        for (int i = 0; i < 4; i++) {
            x = (2 * i + 1) * getSize().width / 8;
            getOutputConnectionAnchor(i + 4).setOffsetH(x - 1);
            getInputConnectionAnchor(i).setOffsetH(x - 1);
            getInputConnectionAnchor(i + 4).setOffsetH(x);
            getOutputConnectionAnchor(i).setOffsetH(x);
        }
    }

    /** @see org.eclipse.draw2d.Figure#paintFigure(Graphics) */
    protected void paintFigure(Graphics graphics) {
//    	this.paintBody(graphics);
//    	WJH 090801    	
    	N3EditorDiagramModel diagram = (N3EditorDiagramModel)edit.getModel();    
    	Object obj = diagram.getUMLTreeModel().getParent();
    	String noid = "";
    	if(this.getModel() instanceof ComponentModel){
    		ComponentModel cm = this.getModel();    		
    		if(cm != null && !cm.getNode().equals("")){
    			noid = cm.getNode();
    		}
    	}
        Rectangle r = Rectangle.SINGLETON;
        r.setBounds(getBounds());
//        r.width = r.width - 2;
//        r.height = r.height - 2;
//      WJH 090817 S
//        System.out.println("change size : "+ProjectManager.getInstance().getSizeChange());
        if(image1 == null || this.getModel().getSizeChange()){
        	System.out.println("=============================  REPAINT  ===============================");
	        if(this.getModel().getStereotype().equals("<<composite>>") || this.getModel().getStereotype().equals("<<Composite>>")){
//	        	System.out.println("composite paint");
	        	image1_1 = ProjectManager.getInstance().getImageReSize(19003, r.width, r.height);
	        	image1 = ProjectManager.getInstance().getImageReSize(10000, r.width-4, r.height-4);	
	        }
	        else if(this.getModel().getStereotype().equals("<<atomic>>") || this.getModel().getStereotype().equals("<<Atomic>>") ){
//	        	System.out.println("atomic paint");
	        	if(this.getModel() instanceof AtomicComponentModel){
	        		image2_1 = ProjectManager.getInstance().getImageReSize(19012, r.width-10, 40);
	        		image2 = ProjectManager.getInstance().getImageReSize(10201, r.width-10-4, 40-4);
	        		image3_1 = ProjectManager.getInstance().getImageReSize(19001, r.width, 18);
		        	image3 = ProjectManager.getInstance().getImageReSize(10008, r.width-4, 18-2);
		        	image4_1 = ProjectManager.getInstance().getImageReSize(19004, r.width, 18);
		        	image4 = ProjectManager.getInstance().getImageReSize(10009, r.width-4, 18-2);
		        	
	        	}
	        	else
	        	image2_1 = ProjectManager.getInstance().getImageReSize(19012, r.width-10, 40);
	        	image2 = ProjectManager.getInstance().getImageReSize(10201, r.width-10-4, 40-4);
	        	image1_1 = ProjectManager.getInstance().getImageReSize(19002, r.width, r.height-15);
        		image1 = ProjectManager.getInstance().getImageReSize(18111, r.width-4, r.height-15-4);
        		image3_1 = ProjectManager.getInstance().getImageReSize(19001, r.width, 18-1);
	        	image3 = ProjectManager.getInstance().getImageReSize(10008, r.width-8, 18-2);
	        	image4_1 = ProjectManager.getInstance().getImageReSize(19004, r.width, 18-1);
	        	image4 = ProjectManager.getInstance().getImageReSize(10009, r.width-8, 18-2);
	        	
	        }
	        
	//        if(image1!=null){
	        if(((UMLTreeModel)obj).getRefModel() instanceof ComponentModel){
	        	if(this.getModel().getStereotype().equals("<<composite>>") || this.getModel().getStereotype().equals("<<Composite>>")){
	        		image2_1 = ProjectManager.getInstance().getImageReSize(19013, r.width-10, 40);
	        		image2 = ProjectManager.getInstance().getImageReSize(10010, r.width-10-4, 40-4);	        		
	            }
	            else if(this.getModel().getStereotype().equals("<<atomic>>") || this.getModel().getStereotype().equals("<<Atomic>>")){
//	            	System.out.println("atomic paint atomic paint atomic paint atomic paint atomic paint");;
	            }
	        	
	        }
	        else{        	
	        	if(this.getModel().getStereotype().equals("<<composite>>") || this.getModel().getStereotype().equals("<<Composite>>")){
	        		image2_1 = ProjectManager.getInstance().getImageReSize(19011, r.width-10, 40);
	        		image2 = ProjectManager.getInstance().getImageReSize(10001, r.width-10-4, 40-4);
	        		//�̹�����ü ->����
//	        		image3 = ProjectManager.getInstance().getImageReSize(10009, r.width/5, r.height/5);        	
	        	}
	        	else{
	        		
	        	}
	        }
	        this.getModel().setSizeChange(false);	//	WJH 090817
        }
//        WJH 090817 E
        if(this.getModel().getStereotype().equals("<<atomic>>")){
	       
	        
	      //�̹��� ��ü -> ����Ͱ� ������Ʈ ũ�� ��ȭ�� ��ġ ����..
			//����� �� ��ġ
			//�̹��� ��ü
			//����� ������Ʈ ��ġ������ ����
			if(image1 != null){
	    		graphics.drawImage(image1_1, r.x, r.y+15);
	    		graphics.drawImage(image1, r.x+2, r.y+15+2);
	    		
			}
			
			 if(image3 != null){
				graphics.drawImage(image3_1, r.x, r.y);
		    	graphics.drawImage(image3, r.x+6, r.y+2);	
		     }
			 
			 if(image4 != null){
				graphics.drawImage(image4_1, r.x, r.y);
		    	graphics.drawImage(image4, r.x+2, r.y+2);	
		     }
	        
	    	if(image2 != null){
	    		//110823 SDM S XP�� ��Ÿ �̻� �������� �ؽ�Ʈ ��Ʈ�� Ʋ�ȴ� �κ� ��Ʈ �������� ���󺹱�
		    	graphics.drawImage(image2_1, r.x+5, r.y+30);
			    graphics.drawImage(image2, r.x+5+2, r.y+30+2);
		    	//110823 SDM E XP�� ��Ÿ �̻� �������� �ؽ�Ʈ ��Ʈ�� Ʋ�ȴ� �κ� ��Ʈ �������� ���󺹱�
	    	}
        }
        else if(this.getModel().getStereotype().equals("<<composite>>")){
	        if(image1 != null){
	    		graphics.drawImage(image1_1, r.x, r.y);
	    		graphics.drawImage(image1, r.x+2, r.y+2);
	        }
	        
	      //������Ʈ �� ��ġ
	        if(image2 != null){
	        	//110823 SDM S XP�� ��Ÿ �̻� �������� �ؽ�Ʈ ��Ʈ�� Ʋ�ȴ� �κ� ��Ʈ �������� ���󺹱�
		    	graphics.drawImage(image2_1, r.x+5, r.y+15);
				graphics.drawImage(image2, r.x+5+2, r.y+15+2);
		    	//110823 SDM E XP�� ��Ÿ �̻� �������� �ؽ�Ʈ ��Ʈ�� Ʋ�ȴ� �κ� ��Ʈ �������� ���󺹱�
	        }
        }
//    	if(image3 != null){
//    		graphics.drawImage(image3, r.x+r.width/10*7, r.y+r.height/10*7);
//    	}    	
//        System.out.println("noid=====>"+noid);
        /*
    	if(image1 != null)
    		graphics.drawImage(image1_1, r.x, r.y);
    		graphics.drawImage(image1, r.x+2, r.y+2);
    	if(image2 != null){
    		//�̹��� ��ü -> ����Ͱ� ������Ʈ ũ�� ��ȭ�� ��ġ ����..
    		if(this.getModel().getStereotype().equals("<<atomic>>")){	//����� �� ��ġ
    			//�̹��� ��ü
    			//����� ������Ʈ ��ġ������ ����
    			if(image3 != null){
    	    		graphics.drawImage(image3_1, r.x, r.y+15);
    	    		graphics.drawImage(image3, r.x+2, r.y+15+2);
    	    	}   
    	    	
    	    	graphics.drawImage(image2_1, r.x+5, r.y+27);
    			graphics.drawImage(image2, r.x+5+2, r.y+27+2);
    			
    			
    		}
    		else if(this.getModel().getStereotype().equals("<<composite>>")){	//������Ʈ �� ��ġ
    			graphics.drawImage(image2_1, r.x+5, r.y+12);
    			graphics.drawImage(image2, r.x+5+2, r.y+12+2);
    		}
    	}
//    	if(image3 != null){
//    		graphics.drawImage(image3, r.x+r.width/10*7, r.y+r.height/10*7);
//    	}    	
//        System.out.println("noid=====>"+noid);
    	*/
        
        //110822 SDM S ���� ����͸� �ؽ�Ʈ �и� ��ǥ ����
        if(noid !=null && !noid.equals("")){
        	int index = noid.length()+2;
        	graphics.drawString("["+noid+"]",r.x+r.width/2-index*3, r.y+85);
        }
        if(this.getModel() instanceof ComponentModel){
        ComponentModel cm = this.getModel();
        try{
        if(cm.getMonitorVariables()!=null && cm.getMonitorVariables().size()>0 && cm.isMonitor){
        	String var = "[monitoring variable]";
        	int x = r.x+r.width/2-var.length()*3;
        	graphics.drawString(var,r.x+r.width/2-var.length()*3, r.y+(100));
        	for(int i=0;i<cm.getMonitorVariables().size();i++){
        		DetailPropertyTableItem dt =	(DetailPropertyTableItem)cm.getMonitorVariables().get(i);
        		if(dt.isCheck){
        		String name = dt.tapName+"="+dt.desc;
        		int index =  name.length()+2;
        		
        		graphics.drawString(name,x, r.y+(120+(i*12)));
        		
        		}
        		}
        }
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        }
        
        
//    	WJH 090801
    }

  
    public Rectangle getActorBounds(Rectangle bounds) {
        int height = bounds.height * 7 / 10;
        int width = bounds.width;
        int x = 0;
        int y = 0;
        if (width * 4 >= height * 3) { // Width�� �� Ŭ���
            width = height * 3 / 4;
            x += (bounds.width - width) / 2;
        } else {
            height = width * 4 / 3;
            y += (bounds.height * 7 / 10 - height) / 2;
        }
        return new Rectangle(x, y, width, height);
    }

    protected void paintBody(Graphics g2) {
        Ellipse2D.Float bounds = getCircle();
        g2.fillOval((int)bounds.getX() + (int)Math.round(1), (int)bounds.getY() + (int)Math.round(1), (int)bounds.getWidth() - (int)Math.round(1),
            (int)bounds.getHeight() - (int)Math.round(1));
        //�����ʿ� �ִ� ������Ʈ�� �簢���� �׸��� ///
        g2.fillOval((int)bounds.getX(), (int)bounds.getY(), (int)bounds.getWidth() - (int)Math.round(2),
            (int)bounds.getHeight() - (int)Math.round(2));
        g2.drawOval((int)bounds.getX(), (int)bounds.getY(), (int)bounds.getWidth() - (int)Math.round(2),
            (int)bounds.getHeight() - (int)Math.round(2));
        Line2D[] lines = getBodyLines();
        for (int i = 0; i < lines.length; i++) {
            g2.drawLine((int)lines[i].getX1(), (int)lines[i].getY1(), (int)lines[i].getX2(), (int)lines[i].getY2());
        }
    }

    protected Line2D[] getBodyLines() {
        Rectangle r = Rectangle.SINGLETON;
        r.setBounds(getBounds());
        Rectangle actorBounds = r;
        int x = actorBounds.x;
        int y = actorBounds.y;
        Line2D[] _lines = new Line2D[4];
        _lines[0] = new Line2D.Float(x + actorBounds.width / 2, y + actorBounds.height * 4 / 10 + 1, x + actorBounds.width / 2,
            y + actorBounds.height * 7 / 10);
        _lines[1] = new Line2D.Float(x + 1, y + actorBounds.height * 5 / 10, x + actorBounds.width - 1 * 2,
            y + actorBounds.height * 5 / 10);
        _lines[2] = new Line2D.Float(x + actorBounds.width / 2, y + actorBounds.height * 7 / 10, x + 1,
            y + actorBounds.height - 1);
        _lines[3] = new Line2D.Float(x + actorBounds.width / 2, y + actorBounds.height * 7 / 10, x + actorBounds.width - 1 * 2,
            y + actorBounds.height - 1);
        //N3soft V1.3013 2004.04.08 �ȼ��� 2���̻� ���������� ���� end
        return _lines;
    }

    protected Ellipse2D.Float getCircle() {
        Rectangle r = Rectangle.SINGLETON;
        r.setBounds(getBounds());
        Rectangle actorBounds = r;
        float _x = actorBounds.width * 2 / 9 + actorBounds.x;
        float _y = 1 + actorBounds.y;
        float _width = actorBounds.width * 5 / 9;
        float _height = 1 + actorBounds.height * 4 / 10;
        return new Ellipse2D.Float(_x, _y, _width, _height);
    }

    public void setInputConnectionAnchor(int i, ConnectionAnchor c) {
        connectionAnchors.put(Circuit.TERMINALS_IN[i], c);
    }

    public void setOutputConnectionAnchor(int i, ConnectionAnchor c) {
        connectionAnchors.put(Circuit.TERMINALS_OUT[i], c);
    }

    public String toString() {
        return "CircuitBoardFigure"; //$NON-NLS-1$
    }

    public void validate() {
        if (isValid()) return;
        //	layoutConnectionAnchors();
        super.validate();
    }

    protected boolean useLocalCoordinates() { return true; }

	public ComponentModel getModel() {
		return model;
	}

	public void setModel(ComponentModel model) {
		this.model = model;
	}

	public EditPart getEditpart() {
		return edit;
	}

	public void setEditpart(EditPart edit) {
		this.edit = edit;
	}
}
