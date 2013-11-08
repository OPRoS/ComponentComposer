package kr.co.n3soft.n3com.message;

import kr.co.n3soft.n3com.util.ErrorCheck;
import kr.co.n3soft.n3com.util.ErrorMessage;

public class N3MessageFactory {
    private static N3MessageFactory instance;
    N3KorMessage n3KorMessage = null;
    N3EngMessage n3EngMessage = null;

	/*
	 * index
	 */

    public static int PropertyDescriptor_Index_Name = 0;

	/*index = 0 kor
	 * index = 1 eng
	*/

    private int index = 0;

    public N3MessageFactory() {
        n3KorMessage = new N3KorMessage();
        n3EngMessage = new N3EngMessage();
    }

    public static N3MessageFactory getInstance() {
        if (instance == null) {
            instance = new N3MessageFactory();
            return instance;
        }
        else {
            return instance;
        }
    }

    public String getMessage(int index) {
        String message = "";
        try {
            message = n3KorMessage.getMessage(index);
        }        
        catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.e = e;
            ErrorCheck.getInstance().handlingError(errorMessage);
        }
        return message;
    }
}
