package kr.co.n3soft.n3com.model.comm;

import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;

public class CommonStrcuturedModel extends ClassifierModel {
    N3EditorDiagramModel n3EditorDiagramModel = null;
    boolean isStrcuturedAccept = true;

    public void setN3EditorDiagramModel(N3EditorDiagramModel p) {
        //		this.n3EditorDiagramModel = p;
        this.getUMLDataModel().setN3EditorDiagramModel(p);
    }

    public N3EditorDiagramModel getN3EditorDiagramModel() {
        return this.getUMLDataModel().getN3EditorDiagramModel();
    }

    public void setIsStrcuturedAccept(boolean p) {
        this.isStrcuturedAccept = p;
    }

    public boolean getIsStructuredAccept() {
        return this.isStrcuturedAccept;
    }
}
