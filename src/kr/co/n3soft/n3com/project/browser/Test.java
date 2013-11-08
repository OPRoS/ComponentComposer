package kr.co.n3soft.n3com.project.browser;

import java.io.File;

import kr.co.n3soft.n3com.projectmanager.CompAssemManager;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File f = new File(CompAssemManager.getInstance().getNetFoler());
		CompAssemManager.getInstance().getComponentFile(f);
		System.out.println(CompAssemManager.getInstance().getCompFiles());
}
}
