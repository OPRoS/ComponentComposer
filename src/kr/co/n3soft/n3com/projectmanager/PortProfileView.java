package kr.co.n3soft.n3com.projectmanager;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

public class PortProfileView extends PortProfile{
	public Point location = new Point(0, 0);
	public Dimension ptDifference = new Dimension(-1, -1);
	public Dimension attachSize = new Dimension(-1, -1);
	public String component_name;
	public String port_name;
	public String port_type;
	public String port_dir;
	public String port_view;
	
	public void setPortProfileView(){
		String[] svalue = port_view.split(",");
		if(svalue.length==6){
			String x = svalue[0];
			String y = svalue[1];
			String dw = svalue[2];
			String dh = svalue[3];
			String aw = svalue[4];
			String ah = svalue[5];
//			String b = svalue[6];
			
			this.location = new Point(Integer.valueOf(x), Integer.valueOf(y));
			this.ptDifference = new Dimension(Integer.valueOf(dw), Integer.valueOf(dh));
			this.attachSize = new Dimension(Integer.valueOf(aw), Integer.valueOf(ah));
			
			
		}
	}

}
