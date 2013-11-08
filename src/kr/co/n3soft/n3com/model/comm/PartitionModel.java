package kr.co.n3soft.n3com.model.comm;

import kr.co.n3soft.n3com.model.activity.FinalActivityModel;
import kr.co.n3soft.n3com.model.activity.HPartitionModel;
import kr.co.n3soft.n3com.model.activity.SubPartitonModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.projectmanager.PartitionManager;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

public class PartitionModel extends ClassifierModel {
    //	CompartmentModel partitionModel = new CompartmentModel();
    PartitionManager pm = new PartitionManager();

    public PartitionModel(int type, int w, int h) {
        super(type, w, h);
        //		this.uMLDataModel.setElementProperty("partitionModel", partitionModel);
    }

    public PartitionModel(int w, int h) {
        super(w, h);
    }

    public PartitionModel(UMLDataModel p) {
        super(p);
    }

    public PartitionModel(UMLDataModel p, int w, int h) {
        super(p, w, h);
    }
    public PartitionModel(UMLDataModel p,boolean isLoad) {
        super(p,isLoad);
//        this.setUMLDataModel(p);
    }
    public void addPartion(HPartitionModel p) {
    	//PKY 08052101 S �����̳ʿ��� �׷����� ����
//      if (this.uMLDataModel.getElementProperty("NodeContainerModel") != null) {
//      SubPartitonModel sm = new SubPartitonModel();
//      sm.setIsMode(true);
//      boundaryModel.addChild(sm);
//      pm.addPartition(sm);
//      this.uMLDataModel.setElementProperty("Container", "true");//PKY 08051401 S ��Ƽ��Ƽ ��Ƽ�� �߰� �� �����̳ʸ� �ι� �߰� �Ǵ� ������ ���� ���� ��Ƽ�� ������ ������� ���� 
//      this.setSize(this.getSize());
//  }
//  else {
//      if (boundaryModel == null) {
//          boundaryModel = new NodeContainerModel();
//          boundaryModel.setParentModel(this);
//          boundaryModel.setLayout(BorderLayout.CENTER);
//          boundaryModel.setParentLayout(1);
//      }
//      else {
//          boundaryModel.setParentModel(this);
//          boundaryModel.setLayout(BorderLayout.CENTER);
//          boundaryModel.setParentLayout(1);
//      }
//      this.addChild(boundaryModel);
//      this.uMLDataModel.setElementProperty("NodeContainerModel", boundaryModel);
//      this.setSize(new Dimension(this.getSize().width + 150, this.getSize().height + 150));
//      SubPartitonModel sm = new SubPartitonModel();
//      sm.setIsMode(false);
//      boundaryModel.addChild(sm);
//      pm.addPartition(sm);
//      this.uMLDataModel.setElementProperty("Container", "true");//PKY 08051401 S ��Ƽ��Ƽ ��Ƽ�� �߰� �� �����̳ʸ� �ι� �߰� �Ǵ� ������ ���� ���� ��Ƽ�� ������ ������� ���� 
//  }
    	//PKY 08052101 E �����̳ʿ��� �׷����� ����
    	
    	//PKY 08052101 S �����̳ʿ��� �׷����� ����
//        SubPartitonModel sm = new SubPartitonModel();
//        sm.setIsMode(true);
//        
//        ((N3EditorDiagramModel)this.getAcceptParentModel()).addChild(sm);
//        sm.setParentModel(this);
//        sm.setAcceptParentModel(this);
//        Point point = new Point();
//        point=this.location;
//        point.y=point.y+15;
//        sm.setLocation(point);
//        sm.setSize(new Dimension(this.getSize().width,15));
//        ArrayList arrayItem = new ArrayList();
//        if(this.getUMLDataModel().getElementProperty("GROUP_CHILDRENS")!=null){
//        arrayItem=(ArrayList)this.getUMLDataModel().getElementProperty("GROUP_CHILDRENS");
//        arrayItem.add(sm);
//        this.getUMLDataModel().setElementProperty("GROUP_CHILDRENS", arrayItem);
//        }
//        else{
//        arrayItem.add(sm);
//        this.getUMLDataModel().setElementProperty("GROUP_CHILDRENS", arrayItem);
//        }
//        arrayItem=new ArrayList();
//        arrayItem.add(this);
//       sm.getUMLDataModel().setElementProperty("GROUP_PARENTS",arrayItem);
//        pm.addPartition(sm);
      //PKY 08052101 E �����̳ʿ��� �׷����� ����
    	//PKY 08081801 S ��Ƽ��Ƽ ��Ƽ�� �������� ���������� ����
    	SubPartitonModel sm = new SubPartitonModel();
    	sm.setIsMode(true);

    	((N3EditorDiagramModel)this.getAcceptParentModel()).addChild(sm);
    	sm.setParentModel(this);
    	sm.setAcceptParentModel(this);
    	Point point = new Point(this.location.x,this.location.y);//PKY 08082201 S ��Ƽ��Ƽ ��Ƽ�� �߰��� �������ɰ�� ��Ƽ��Ƽ ����¹���
    	point.y=point.y+15;
    	sm.setLocation(point);
    	sm.setSize(new Dimension(this.getSize().width,15));


    	pm.addPartition(sm);	
    	//PKY 08081801 e ��Ƽ��Ƽ ��Ƽ�� �������� ���������� ����
    }
  //PKY 08061801 S state pattion ���� �ҷ����� �� ��� ������ ����� ��Ƽ�� ����� ���� �þ�� �ʴ����� ����
    public void addPartition(SubPartitonModel p) {
    	  pm.addPartition(p);
    }
    
  //PKY 08061801 E state pattion ���� �ҷ����� �� ��� ������ ����� ��Ƽ�� ����� ���� �þ�� �ʴ����� ����

    public void addContainer(NodeContainerModel p) {
        boundaryModel = p;
        boundaryModel.setParentModel(this);
        boundaryModel.setLayout(BorderLayout.CENTER);
        boundaryModel.setParentLayout(1);
        this.addChild(boundaryModel);
        this.uMLDataModel.setElementProperty("NodeContainerModel", boundaryModel);
    }

    public void removeContainer(NodeContainerModel p) {
        this.removeChild(boundaryModel);
        this.uMLDataModel.setElementProperty("NodeContainerModel", null);
    }

    public void setSize(Dimension d) {
    	//PKY 08080501 S ��Ƽ��Ƽ�ҷ��帱�� ��Ƽ�� �����ؼ� �������� ��� �߻� ����
    	if(pm!=null){
    		pm.setSizePartitions(d);

    		super.setSize(d);
    	}
    	//PKY 08080501 E ��Ƽ��Ƽ�ҷ��帱�� ��Ƽ�� �����ؼ� �������� ��� �߻� ����
    }

    public Object clone() {
        PartitionModel clone = new PartitionModel((UMLDataModel)this.getUMLDataModel().clone());
        //		clone.temp_childs = this.activityModel.getChildren();
        for (int i = 0; i < this.boundaryModel.getChildren().size(); i++) {
            UMLModel um = (UMLModel)this.boundaryModel.getChildren().get(i);
            clone.boundaryModel.addChild((UMLElementModel)um.clone());
        }
        clone.setSize(this.getSize());
        clone.setLocation(this.getLocation());
        clone.sourceModel = this;
        ProjectManager.getInstance().setTempCopyMap(clone.sourceModel.getID(), clone);
        for (int i1 = 0; i1 < this.getSourceConnections().size(); i1++) {
            LineModel li = (LineModel)this.getSourceConnections().get(i1);
            LineModel liCopy = (LineModel)li.clone();
            ProjectManager.getInstance().addSelectLineModel(liCopy);
        }
        return clone;
    }

	public PartitionManager getPm() {
		return pm;
	}

	public void setPm(PartitionManager pm) {
		this.pm = pm;
	}
}
