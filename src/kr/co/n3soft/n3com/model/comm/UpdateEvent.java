package kr.co.n3soft.n3com.model.comm;

public class UpdateEvent {
    //type:0 rename
    int type = -1;
    Object data = null;

    //	Object data = null;
    public UpdateEvent(int t, Object d) {
        this.type = t;
        this.data = d;
    }

    public int getType() {
        return this.type;
    }

    public Object getData() {
        return this.data;
    }
}
