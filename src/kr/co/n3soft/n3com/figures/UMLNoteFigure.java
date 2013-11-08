package kr.co.n3soft.n3com.figures;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.ParagraphTextLayout;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.swt.graphics.Color;

public class UMLNoteFigure extends NoteBentCornerFigure {
    /** The inner TextFlow * */
    private TextFlow textFlow;

    /**
     *  Creates a new StickyNoteFigure with a default MarginBorder size of DEFAULT_CORNER_SIZE
     * - 3 and a FlowPage containing a TextFlow with the style WORD_WRAP_SOFT.
     */
    public UMLNoteFigure() {
        this(NoteBentCornerFigure.DEFAULT_CORNER_SIZE - 3);
    }

    /**
     * Creates a new StickyNoteFigure with a MarginBorder that is the given size and a
     * FlowPage containing a TextFlow with the style WORD_WRAP_SOFT.
     * @param borderSize the size of the MarginBorder
     */
    public UMLNoteFigure(int borderSize) {
        setBorder(new MarginBorder(borderSize));
        FlowPage flowPage = new FlowPage();
        textFlow = new TextFlow();
        textFlow.setLayoutManager(new ParagraphTextLayout(textFlow, ParagraphTextLayout.WORD_WRAP_SOFT));
        flowPage.add(textFlow);
        setLayoutManager(new StackLayout());
        add(flowPage);
        createConnectionAnchors();
    }

    /**
     * Returns the text inside the TextFlow.
     * @return the text flow inside the text.
     */
    public String getText() {
        return textFlow.getText();
    }

    /**
     * Sets the text of the TextFlow to the given value.
     * @param newText the new text value.
     */
    public void setText(String newText) {
        textFlow.setText(newText);
    }

    protected void createConnectionAnchors() {
        ChopboxAnchor in, out;
        //		for(int i=0;i<10;i++){
        in = new ChopboxAnchor(this);
        out = new ChopboxAnchor(this);
        setOutputConnectionAnchor("B", out);
        setInputConnectionAnchor("A", in);
        outputConnectionAnchors.addElement(out);
        inputConnectionAnchors.addElement(in);
        //		}
    }

    //protected FixedConnectionAnchorFigure getInputConnectionAnchor(int i) {
    //		return (FixedConnectionAnchorFigure) connectionAnchors.get(Circuit.TERMINALS_IN[i]);
    //}
    public void setInputConnectionAnchor(String anName, ConnectionAnchor c) {
        connectionAnchors.put(anName, c);
    }

    public void setOutputConnectionAnchor(String anName, ConnectionAnchor c) {
        connectionAnchors.put(anName, c);
    }

    public ConnectionAnchor getTargetConnectionAnchorAt(Point p) {
        ConnectionAnchor anchor = new ChopboxAnchor(this);
        //		inputConnectionAnchors.addElement(anchor);
        return anchor;
    }

    public ConnectionAnchor getSourceConnectionAnchorAt(Point p) {
        ConnectionAnchor anchor = new ChopboxAnchor(this);
        //		outputConnectionAnchors.addElement(anchor);
        return anchor;
    }

    protected void createConnectionAnchors(Point pt) {
        FixedConnectionAnchorFigure in, out;
        out = new FixedConnectionAnchorFigure(this);
        out.getLocation(pt);
        outputConnectionAnchors.addElement(out);
    }

    /** @see org.eclipse.gef.handles.HandleBounds#getHandleBounds() */
    public Rectangle getHandleBounds() {
        return getBounds().getCropped(new Insets(2, 0, 2, 0));
    }
}
