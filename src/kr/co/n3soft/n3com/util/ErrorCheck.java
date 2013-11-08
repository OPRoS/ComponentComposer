package kr.co.n3soft.n3com.util;

import kr.co.n3soft.n3com.message.N3MessageFactory;

public class ErrorCheck {
    private static ErrorCheck instance;
    private static boolean isDebug = true;

    public static ErrorCheck getInstance() {
        if (instance == null) {
            instance = new ErrorCheck();
            return instance;
        }
        else {
            return instance;
        }
    }

    public void handlingError(ErrorMessage errorMessage) {
        if (isDebug)
            errorMessage.e.printStackTrace();
    }
}
