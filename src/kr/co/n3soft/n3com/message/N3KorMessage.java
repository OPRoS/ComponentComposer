package kr.co.n3soft.n3com.message;

import java.util.HashMap;

public class N3KorMessage {
    HashMap messages = new HashMap();

    public N3KorMessage() {
        messages.put(String.valueOf(N3MessageFactory.PropertyDescriptor_Index_Name), PropertyDescriptor_Content_Name);
    }

	/*
	 * content
	 */

    public String PropertyDescriptor_Content_Name = "�̸�";
    public String N3Plugin_Tool_CreationTool_UseCase_Label = "�������̽�";

    public String getMessage(int message) {
        return (String)messages.get(String.valueOf(message));
    }
}
