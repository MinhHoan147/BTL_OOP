package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import connection.Connection;
import response.ObjectResponse;
import utils.Constant;
import utils.Validations;

public class GetRequestFriend {
    private Scanner mSc = new Scanner(System.in);
    private GetRequestedFriendsReponse rp = null;
    private Gson mGson = new Gson();

    public void getRequestFriend() {
        try {

            System.out.println("enter token");
            String token = mSc.next();

            System.out.println("enter index");
            int index = mSc.nextInt();

            System.out.println("enter count");
            int count = mSc.nextInt();

            try {
                int responseCode = 0;
                StringBuffer stringBuffer = new StringBuffer();
                HttpURLConnection con = Connection.conect(
                        Constant.GET_REQUEST_FRIENDS + "?index=" + index + "&count=" + count + "&token=" + token,
                        "GET");

                try {
                    responseCode = con.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String line;

                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuffer.append(line);
                            stringBuffer.append(System.lineSeparator());
                        }

                        testCase(stringBuffer.toString());
                    } else {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                        String line;

                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuffer.append(line);
                            stringBuffer.append(System.lineSeparator());
                        }

                        testCase(stringBuffer.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.out.println("The data entered is not in the correct format");// TODO: handle exception
        }

    }

    private void testCase(String reponse) {
        Gson gson = new Gson();
        rp = mGson.fromJson(reponse, GetRequestedFriendsReponse.class);
        switch (rp.getmCode()) {
            case "1000":
                System.out.println("Unit test 1: ");
                System.out.println(gson.toJson(rp));
                System.out.println("Unit test 6. ");
                Collections.sort(rp.getData().getmListRequest(), new Comparator<Request>() {
                    @Override
                    public int compare(Request o1, Request o2) {
                        return o1.getCreated().compareTo(o2.getCreated());
                    }
                });

                for (int i = 0; i < rp.getData().getmListRequest().size(); i++) {
                    Request request = rp.getData().getmListRequest().get(i);

                    // System.out.println("Unit test 5");
                    if (Validations.isValidName(request.getUsername())) {
                        System.out.println("Unit test 5");
                        rp.getData().getmListRequest().get(i).setUsername(null);
                    }

                    if (Validations.isNumeric(request.getId().toString()) == false) {
                        System.out.println("Unit test 5");
                        rp.getData().getmListRequest().get(i).setId(null);
                    }

                    // System.out.println("Unit test 7,8");
                    if (Validations.isNumeric(request.getSameFriends().toString()) == false) {
                        System.out.println("Unit test 7,8");
                        rp.getData().getmListRequest().get(i).setSameFriends(null);
                    }
                }

                if (Integer.parseInt(rp.getData().getTotal()) < rp.getData().getmListRequest().size()) {
                    System.out.println("Unit test 9,10");
                    rp.getData().setTotal(rp.getData().getmListRequest().size() + "");
                }

                break;
            case "9998":
                //System.out.println(rp.getmCode() + " " + rp.getmMessage());
                System.out.println(gson.toJson(rp));
                System.out.println("Unit test 2. ");
                new Login().login();
                break;
            case "1002":
                System.out.println(rp.getmCode() + " " + rp.getmMessage());
                System.out.println("Unit test 2. ");
                new Login().login();
                break;
            case "9994":
                System.out.println("Unit test 3: ");
                System.out.println("No result is found.");
                break;
            case "1005":
                System.out.println("Unit test 4. ");
                new Login().login();
                break;
            default:
                System.out.println(reponse);
                throw new IllegalArgumentException("Unexpected value: " + rp.getmCode());
        }
    }

    // System.out.println("Unit test 11");
    public void pullDown() {
        System.out.println("Unit test 11");
        getRequestFriend();
    }

    public void pullUp() {
        System.out.println("Unit test 11");
        System.out.println("goi api them moi vao danh sach yeu cau ket ban");
    }


    public void saveCache() {
        System.out.println("Unit test 12");
        ObjectOutputStream out;
        try {
            out = new ObjectOutputStream(new FileOutputStream(Constant.FILE_GetRequestFriends));
            out.writeObject(rp);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public class GetRequestedFriendsReponse extends ObjectResponse {
        @SerializedName("data")
        @Expose
        private Data data;

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public class Data {
            @SerializedName("request")
            @Expose
            private List<Request> mListRequest;

            @SerializedName("total")
            @Expose
            private String total;

            public Data() {

            }

            public List<Request> getmListRequest() {
                return mListRequest;
            }

            public void setmListRequest(List<Request> mListRequest) {
                this.mListRequest = mListRequest;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }
        }

    }

    public class Request {
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("avatar")
        @Expose
        private String avatar;
        @SerializedName("same_friends")
        @Expose
        private String sameFriends;
        @SerializedName("created")
        @Expose
        private String created;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getSameFriends() {
            return sameFriends;
        }

        public void setSameFriends(String sameFriends) {
            this.sameFriends = sameFriends;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

    }

}
