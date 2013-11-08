package kr.co.n3soft.n3com.model.comm;

import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.swt.graphics.Image;

public class FrameItem {
	private Image itemImage;
	private String name;
	private int type;
	private int width;
	private int height;
	public Image getItemImage() {
		return itemImage;
	}
	public void setItemImage(Image itemImage) {
		this.itemImage = itemImage;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
		itemImage = ProjectManager.getInstance().getImage(type);
		width = itemImage.getBounds().width;
		height = itemImage.getBounds().height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int p) {
		this.width = p;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	

}
