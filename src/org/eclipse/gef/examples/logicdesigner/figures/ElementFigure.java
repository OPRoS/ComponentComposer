package org.eclipse.gef.examples.logicdesigner.figures;

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.ParagraphTextLayout;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.swt.SWT;


public class ElementFigure extends BaseElementFigure
{

	/** The inner TextFlow **/
	private TextFlow textFlow;
//	private Text textField;
	
	Label tt = new Label();

	/**
	 *  Creates a new StickyNoteFigure with a default MarginBorder size of DEFAULT_CORNER_SIZE
	 *  - 3 and a FlowPage containing a TextFlow with the style WORD_WRAP_SOFT.
	 */
	public ElementFigure() {
		this(BentCornerFigure.DEFAULT_CORNER_SIZE - 3);
	}
	
	public ElementFigure(String text,int align) {
		this(BentCornerFigure.DEFAULT_CORNER_SIZE - 3,text,align);
	}

	/** 
	 * Creates a new StickyNoteFigure with a MarginBorder that is the given size and a
	 * FlowPage containing a TextFlow with the style WORD_WRAP_SOFT.
	 * 
	 * @param borderSize the size of the MarginBorder
	 */
	public ElementFigure(int borderSize) {
		
		
		tt.setText("Class");
		tt.setLabelAlignment(PositionConstants.CENTER);
		this.setPreferredSize(null);
		
//		figure.add(tt);
//		tt.setSize(50, 10);
//		this.setSize(borderSize, 10);
//		tt.setVisible(true);
//		tt.setBackgroundColor(LogicColorConstants.logicGreen);
		this.add(tt);
		
		
//		setBorder(new MarginBorder(borderSize));
//		FlowPage flowPage = new FlowPage();
//
//		textFlow = new TextFlow();
//		textFlow.setText("Class");
//	
//
//		textFlow.setLayoutManager(new ParagraphTextLayout(textFlow,
//						ParagraphTextLayout.WORD_WRAP_SOFT));
//
//		flowPage.add(textFlow);
//
		setLayoutManager(new StackLayout());
//		add(flowPage);
	}
	
public ElementFigure(int borderSize,String text,int align) {
		

		tt.setText(text);
		tt.setLabelAlignment(align);
		this.setPreferredSize(null);
		
//		figure.add(tt);
//		tt.setSize(50, 10);
//		this.setSize(borderSize, 10);
//		tt.setVisible(true);
//		tt.setBackgroundColor(LogicColorConstants.logicGreen);
		this.add(tt);
		
		
//		setBorder(new MarginBorder(borderSize));
//		FlowPage flowPage = new FlowPage();
//
//		textFlow = new TextFlow();
//		textFlow.setText("Class");
//	
//
//		textFlow.setLayoutManager(new ParagraphTextLayout(textFlow,
//						ParagraphTextLayout.WORD_WRAP_SOFT));
//
//		flowPage.add(textFlow);
//
		setLayoutManager(new StackLayout());
//		add(flowPage);
	}

	/**
	 * Returns the text inside the TextFlow.
	 * 
	 * @return the text flow inside the text.
	 */
	public String getText() {
		return tt.getText();
	}

	/**
	 * Sets the text of the TextFlow to the given value.
	 * 
	 * @param newText the new text value.
	 */
	public void setText(String newText) {
		tt.setText(newText);
	}
	
	public Dimension getPreferredSize(int w, int h) {
//		super.setPreferredSize(null);
		Dimension prefSize = super.getPreferredSize(w, h);
		Dimension defaultSize = new Dimension(w,15);
		prefSize.union(defaultSize);
		return prefSize;
	}

	}