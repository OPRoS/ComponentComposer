package kr.co.n3soft.n3com.projectmanager;

import kr.co.n3soft.n3com.model.comm.LineBendpointModel;

import org.eclipse.draw2d.geometry.Dimension;

public class BendPointView {
	public String bendpoint ="";
	public LineBendpointModel lbm = new LineBendpointModel();
	public void setLineBendpointModel(){
		float weight = 0;
		int w1 = 0;
		int h1 = 0;
		int w2 = 0;
		int h2 = 0;
		String[] data = bendpoint.split(",");
		

		if(data.length==5){

			weight = Float.parseFloat(data[0]);
			w1 = Integer.parseInt(data[1]);
			h1 = Integer.parseInt(data[2]);
			w2 = Integer.parseInt(data[3]);
			h2 = Integer.parseInt(data[4]);

			lbm.setWeight(weight);
			lbm.setRelativeDimensions(new Dimension(w1,h1),new Dimension(w2,h2));
//			return lbm;
		}
//		return lbm;
	}

}
