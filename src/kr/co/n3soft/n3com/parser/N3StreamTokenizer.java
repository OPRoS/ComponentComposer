package kr.co.n3soft.n3com.parser;

import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StreamTokenizer;

public class N3StreamTokenizer extends StreamTokenizer{
	Reader p;
	 public N3StreamTokenizer(Reader r) {
	
		 super(r);
			p = r;
	 }
	 
	 public String getReadLine(){
		 try{
		 if(p instanceof LineNumberReader){
			 LineNumberReader lr = (LineNumberReader)p;
			 return lr.readLine();
		 }
		 
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		 return "";
	 }

}
