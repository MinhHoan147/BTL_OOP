package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import connection.Connection;
import model.GetUserFriends.Friends;
import model.GetUserFriends.GetUserFriendReponse;
import utils.Constant;
import utils.Validations;

public class Login {
    private Scanner sc = new java.util.Scanner(System.in);

    public Login() {
        // TODO Auto-generated constructor stub
    }

    public void login() {
        System.out.println("Enter your phone number");
        String phone = sc.next();

        System.out.println("Enter your password");
        String pass = sc.next();

        if (Validations.checkValidation(phone, pass)) {
            try {
                StringBuffer stringBuffer = new StringBuffer();
                HttpURLConnection con = Connection
                        .conect(Constant.LOGIN + "?phonenumber=" + phone + "&password=" + pass, "POST");

                try {
                    int responseCode = con.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuffer.append(line);
                            stringBuffer.append(System.lineSeparator());
                        }
                        testCaseApi(stringBuffer);
                    } else {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getErrorStream()));

                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuffer.append(line);
                            stringBuffer.append(System.lineSeparator());
                        }
                        testCaseApi(stringBuffer);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {

            }
        }
    }

    private void testCaseApi(StringBuffer stringBuffer) {
        // TODO Auto-generated method stub
        Gson gson = new Gson();
        LoginReponse rp = gson.fromJson(stringBuffer.toString(), LoginReponse.class);

        switch (rp.getmCode() + "") {
            case "1000":
                System.out.println("Unit test 1: ");
                System.out.println("login success");
                rp.showInfo();
                break;
            case "1004":
                //System.out.println("Unit test 1: ");
                System.out.println("Parameter value is invalid");
                break;
            default:
                System.out.println(stringBuffer.toString());
                throw new IllegalArgumentException("Unexpected value: " + rp.getmCode());
        }
    }

    public class LoginReponse {
        @SerializedName("message")
        public String mMessage;

        @SerializedName("code")
        public int mCode;

        @SerializedName("data")
        public UserReponse userReponse;

        public LoginReponse() {

        }

        public LoginReponse(String mMessage, int mCode, UserReponse userReponse) {
            super();
            this.mMessage = mMessage;
            this.mCode = mCode;
            this.userReponse = userReponse;
        }

        public String getmMessage() {
            return mMessage;
        }

        public void setmMessage(String mMessage) {
            this.mMessage = mMessage;
        }

        public int getmCode() {
            return mCode;
        }

        public void setmCode(int mCode) {
            this.mCode = mCode;
        }

        public UserReponse getUserReponse() {
            return userReponse;
        }

        public void setUserReponse(UserReponse userReponse) {
            this.userReponse = userReponse;
        }

        public void showInfo() {
            System.out.println("message: " + mMessage);
            System.out.println("code: " + mCode);
            System.out.println("data: ");
            userReponse.showInfo();
        }

        public class UserReponse {
            @SerializedName("id")
            public String mId;

            @SerializedName("username")
            public String mUserName;

            @SerializedName("token")
            public String mToken;

            @SerializedName("avatar")
            public String mAvatar;

            public UserReponse() {

            }

            public UserReponse(String mId, String mUserName, String mToken, String mAvatar) {
                super();
                this.mId = mId;
                this.mUserName = mUserName;
                this.mToken = mToken;
                this.mAvatar = mAvatar;
            }

            public String getmId() {
                return mId;
            }

            public void setmId(String mId) {
                this.mId = mId;
            }

            public String getmUserName() {
                return mUserName;
            }

            public void setmUserName(String mUserName) {
                this.mUserName = mUserName;
            }

            public String getmToken() {
                return mToken;
            }

            public void setmToken(String mToken) {
                this.mToken = mToken;
            }

            public String getmAvatar() {
                return mAvatar;
            }

            public void setmAvatar(String mAvatar) {
                this.mAvatar = mAvatar;
            }

            public void showInfo() {
                System.out.println("id: " + this.mId);
                System.out.println("name: " + this.mUserName);
                System.out.println("token: " + this.mToken);
                System.out.println("avatar: " + this.mAvatar);
            }
        }
    }

}

