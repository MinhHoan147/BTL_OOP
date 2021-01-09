package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import connection.Connection;
import model.Login.LoginReponse;
import response.ObjectResponse;
import utils.Constant;

public class Logout {
    private Scanner mSc = new Scanner(System.in);

    public Logout() {
        // TODO Auto-generated constructor stub
    }

    public void logout() {
        try {
            System.out.println("enter token: ");
            String token = mSc.next();

            StringBuffer stringBuffer = new StringBuffer();
            HttpURLConnection con = Connection.conect(Constant.LOGOUT+"?token="+token,"POST");

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
                }else {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getErrorStream()));

                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuffer.append(line);
                        stringBuffer.append(System.lineSeparator());
                    }
                    testCaseApi(stringBuffer);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.out.println("err: "+e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {

        }
    }

    private void testCaseApi(StringBuffer stringBuffer) {
        // TODO Auto-generated method stub
        Gson gson = new Gson();
        LoginReponse rp = gson.fromJson(stringBuffer.toString(), LoginReponse.class);

        switch (rp.getmCode() + "") {
            case "1000":
                // System.out.println("Unit test 1: ");
                System.out.println("logout success");
                rp.showInfo();
                break;
            case "1004":
                // System.out.println("Unit test 1: ");
                System.out.println("Parameter value is invalid");
                break;
            default:

                System.out.println(stringBuffer.toString());
                throw new IllegalArgumentException("Unexpected value: " + rp.getmCode());
        }
    }

}

