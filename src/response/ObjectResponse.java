package response;

import com.google.gson.annotations.SerializedName;

public class ObjectResponse {
    @SerializedName("code")
    public String mCode;

    @SerializedName("message")
    public String mMessage;

    public ObjectResponse() {

    }

    public ObjectResponse(String mCode, String mMessage) {
        super();
        this.mCode = mCode;
        this.mMessage = mMessage;
    }

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public void showInfo() {
        // TODO Auto-generated method stub
        System.out.println("code: "+getmCode());
        System.out.println("message: "+getmMessage());

    }
}

