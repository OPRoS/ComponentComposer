package kr.co.n3soft.n3com.parser;

import kr.co.n3soft.n3com.project.browser.UMLTreeParentModel;

public class LinkModel {
	UMLTreeParentModel ut = null;
	String linkPath = null;
	String linkName = null;

	public UMLTreeParentModel getUt() {
		return ut;
	}

	public void setUt(UMLTreeParentModel ut) {
		this.ut = ut;
	}

	public String getLinkPath() {
		return linkPath;
	}

	public void setLinkPath(String linkPath) {
		this.linkPath = linkPath;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
}
