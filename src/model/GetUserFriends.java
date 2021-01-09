package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import connection.Connection;
import response.ObjectResponse;
import utils.Constant;
import utils.Validations;

public class GetUserFriends {
    private Scanner mSc = new Scanner(System.in);
    public void getUserFriends() {
        try {
            System.out.println("enter token");
            String token = mSc.next();

            System.out.println("enter user_id: ");
            String userId = mSc.next();

            System.out.println("enter index");
            int index = mSc.nextInt();

            System.out.println("enter count");
            int count = mSc.nextInt();



            try {
                int responseCode = 0;
                StringBuffer stringBuffer = new StringBuffer();
                HttpURLConnection con = Connection.conect(
                        Constant.GET_USER_FRIENDS + "index=" + index + "&count=" + count +"&user_id="+userId,
                        "POST");
                con.setRequestProperty("Authorization","Bearer "+token);
                try {
                    responseCode = con.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String line;

                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuffer.append(line);
                            stringBuffer.append(System.lineSeparator());
                        }

                        testCaseApi(stringBuffer,userId);

                    } else {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                        String line;

                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuffer.append(line);
                            stringBuffer.append(System.lineSeparator());
                        }

                        testCaseApi(stringBuffer,userId);
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.out.println("The data entered is not in the correct format");// TODO: handle exception
        }

    }
    private void testCaseApi(StringBuffer stringBuffer, String userId) {
        // TODO Auto-generated method stub
        Gson gson = new Gson();
        GetUserFriendReponse rp = gson.fromJson(stringBuffer.toString(), GetUserFriendReponse.class);

        switch (rp.getmCode()) {
            case "1000":
                // System.out.println("Unit test 1: ");
                System.out.println("get request friends success");

                //System.out.println("Unit test 5,7,8,9,10");
                for (int i = 0; i < rp.getmData().getmFriends().size(); i++) {
                    Friends friends = rp.getmData().getmFriends().get(i);

                    if(!Validations.checkContentPost(friends.getmUserName())) {
                        rp.getmData().getmFriends().get(i).setmUserName(null);
                    }

                    if(!Validations.isNumeric(friends.getmId()+"")) {
                        rp.getmData().getmFriends().get(i).setmId(0);
                    }

                    if(!Validations.validateJavaDate(friends.getmCreated())) {
                        if(Validations.isNumeric(userId+"") == false) {
                            // banj cua chinh minh thoi gian ket ban co the an di
                        }else {
                            // ban cua mot ai do ko can hien thi t/g ket ban
                            rp.getmData().getmFriends().get(i).setmCreated(null);
                        }

                    }

                    if(!Validations.isNumeric(friends.getmSameFriends())) {
                        if(Validations.isNumeric(userId+"") == false) {
                            // banj cua chinh minh so luong ban be chung co the an di
                        }else {
                            // ban cua mot ai do ko can hien thi so luong ban be chung
                            rp.getmData().getmFriends().get(i).setmSameFriends(null);
                        }
                    }
                }

                // System.out.println("Unit test 11,12");
                if(Integer.parseInt(rp.getmData().getmTotal()) < rp.getmData().getmFriends().size()) {
                    rp.getmData().setmTotal(rp.getmData().getmFriends().size()+"");
                }

                // System.out.println("Unit test 6");
                Collections.sort(rp.getmData().getmFriends(), new Comparator<Friends>() {
                    @Override
                    public int compare(Friends o1, Friends o2) {
                        return o1.getmUserName().compareTo(o2.getmUserName());
                    }
                });

                break;
            case "9998":
                // System.out.println("Unit test 2. ");
                new Login().login();
                break;
            case "9994":
                // System.out.println("Unit test 3:");
                System.out.println("No result is found.");
                break;
            case "1005":
                // System.out.println("Unit test 4. ");
                new Login().login();
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + rp.getmCode());
        }
    }

    // System.out.println("Unit test 11");
    public void pullDown() {
        getUserFriends();
    }

    public void pullUp() {
        System.out.println("goi api them moi vao danh sach yeu cau ket ban");
    }

    public class GetUserFriendReponse extends ObjectResponse {
        @SerializedName("data")
        private Data mData;

        public Data getmData() {
            return mData;
        }

        public void setmData(Data mData) {
            this.mData = mData;
        }

        public class Data {
            @SerializedName("friends")
            private List<Friends> mFriends;

            @SerializedName("total")
            private String mTotal;

            public Data() {

            }

            public String getmTotal() {
                return mTotal;
            }

            public void setmTotal(String mTotal) {
                this.mTotal = mTotal;
            }

            public List<Friends> getmFriends() {
                return mFriends;
            }

            public void setmFriends(List<Friends> mFriends) {
                this.mFriends = mFriends;
            }


        }
    }

    public class Friends {
        @SerializedName("id")
        private int mId;

        @SerializedName("username")
        private String mUserName;

        @SerializedName("avatar")
        private String mAvarta;

        @SerializedName("same_friends")
        private String mSameFriends;

        @SerializedName("created")
        private String mCreated;

        public Friends() {

        }

        public int getmId() {
            return mId;
        }

        public void setmId(int mId) {
            this.mId = mId;
        }

        public String getmUserName() {
            return mUserName;
        }

        public void setmUserName(String mUserName) {
            this.mUserName = mUserName;
        }

        public String getmAvarta() {
            return mAvarta;
        }

        public void setmAvarta(String mAvarta) {
            this.mAvarta = mAvarta;
        }

        public String getmSameFriends() {
            return mSameFriends;
        }

        public void setmSameFriends(String mSameFriends) {
            this.mSameFriends = mSameFriends;
        }

        public String getmCreated() {
            return mCreated;
        }

        public void setmCreated(String mCreated) {
            this.mCreated = mCreated;
        }


    }

}
