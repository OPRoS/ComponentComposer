package kr.co.n3soft.n3com.model.component;

import opros.ExecutableInfo;

public class RepoComponent extends ComponentModel{
	ExecutableInfo executableInfo = null;

	public ExecutableInfo getExecutableInfo() {
		return executableInfo;
	}

	public void setExecutableInfo(ExecutableInfo executableInfo) {
		this.executableInfo = executableInfo;
		this.setName(executableInfo.name);
		this.setVersion(executableInfo.version);
		this.setId(executableInfo.id);
//		this.setDesc(executableInfo.description);
	}

}
