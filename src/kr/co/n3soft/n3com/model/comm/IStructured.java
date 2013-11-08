package kr.co.n3soft.n3com.model.comm;

import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;

public interface IStructured {
    public void setN3EditorDiagramModel(N3EditorDiagramModel p);

    public N3EditorDiagramModel getN3EditorDiagramModel();

    public void setIsStrcuturedAccept(boolean p);

    public boolean getIsStructuredAccept();

    public void setType(int p);

    public int getType();
}
