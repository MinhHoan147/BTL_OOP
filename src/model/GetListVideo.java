package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import connection.Connection;
import response.ObjectResponse;
import utils.Constant;
import utils.Validations;

public class GetListVideo {
    private Scanner mSc = new Scanner(System.in);

    public void getListVideo() {
        System.out.println("enter token: ");
        String token = mSc.next();

        System.out.println("enter user_id: ");
        String user_id = mSc.next();

        System.out.println("enter in_campaign: ");
        String in_campaign = mSc.next();

        System.out.println("enter campaign_id: ");
        String campaign_id = mSc.next();

        System.out.println("enter latitude: ");
        String latitude = mSc.next();

        System.out.println("enter longtitude: ");
        String longitude = mSc.next();

        System.out.println("enter last_id: ");
        String last_id = mSc.next();

        System.out.println("enter index: ");
        String index = mSc.next();

        System.out.println("enter count: ");
        String count = mSc.next();



        // System.out.println("Unit test 12,13,14:");
        if (last_id.isEmpty() || Validations.isNumeric(index) || Validations.isNumeric(count)) {
            System.out.println("The parameter's value is not valid");
        } else {
            try {
                StringBuffer stringBuffer = new StringBuffer();
                HttpURLConnection con = Connection.conect(Constant.GET_LIST_VIDEO + "?token=" + token +  "&user_id="
                        + user_id + "&in_campaign=" + in_campaign + "&campaign_id=" + campaign_id + "&latitude=" + latitude + "&longitude=" + longitude + "&last_id=" + last_id + "&index=" + index +"&count=" + count , "POST");
                System.out.println(con);
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

    private void testCaseApi(StringBuffer content) {
        Gson gson = new Gson();
        Example rp = gson.fromJson(content.toString(), Example.class);

        switch ( rp.getCode()) {
            case "1000":
                //System.out.println("Unit test 1: ");
                //System.out.println("get list video success");
                System.out.println(gson.toJson(rp));

                for (int i = 0; i < rp.getData().getPost().size(); i++) {
                    Post post = rp.getData().getPost().get(i);

                    if (post.getDescribed().isEmpty()) {
                        System.out.println("Unit test 5,9,10:");
                        rp.getData().getPost().remove(i);
                        continue;
                    }

                    if (post.getAuthor().getId().isEmpty()) {
                        System.out.println("Unit test 8:");
                        rp.getData().getPost().remove(i);
                        continue;
                    }

                    if (Validations.isNumeric(post.getLike())) {
                        System.out.println("Unit test 6:");
                        rp.getData().getPost().get(i).setLike("0");
                    }

                    if (Validations.isNumeric(post.getComment())) {
                        System.out.println("Unit test 6:");
                        rp.getData().getPost().get(i).setComment("0");
                    }

                    if (!Validations.checkIsLiked(post.getIsLiked())) {
                        System.out.println("Unit test 6:");
                        rp.getData().getPost().get(i).setIsLiked("0");
                    }

                }
                System.out.println(gson.toJson(rp));

                break;
            case "9998":
                System.out.println(gson.toJson(rp));
                System.out.println("Unit test 2. ");
                new Login().login();
                break;
            case "9994":
                System.out.println("Unit test 3:");
                System.out.println("No result is found.");
                break;
            case "1005":
                System.out.println("Unit test 4. ");
                new Logout().logout();
                new Login().login();
                break;

            case "1004":
                System.out.println("Unit test 12,13:");
                System.out.println("The parameter's value is not valid");
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + rp.getCode());
        }
    }

    // -------------------------------------------------------------------------
    public class Author {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("avatar")
        @Expose
        private Object avatar;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Object getAvatar() {
            return avatar;
        }

        public void setAvatar(Object avatar) {
            this.avatar = avatar;
        }

    }

    // -----------------------------------com.example.Data.java-----------------------------------
    public class Data{
        @SerializedName("post")
        @Expose
        private List<Post> post = null;
        @SerializedName("new_items")
        @Expose
        private String newItems;
        @SerializedName("last_id")
        @Expose
        private String lastId;

        public List<Post> getPost() {
            return post;
        }

        public void setPost(List<Post> post) {
            this.post = post;
        }

        public String getNewItems() {
            return newItems;
        }

        public void setNewItems(String newItems) {
            this.newItems = newItems;
        }

        public String getLastId() {
            return lastId;
        }

        public void setLastId(String lastId) {
            this.lastId = lastId;
        }

    }

    // -----------------------------------com.example.Example.java-----------------------------------
    public class Example {

        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private Data data;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

    }
    // -----------------------------------com.example.Post.java-----------------------------------

    public class Post {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("video")
        @Expose
        private Video video;
        @SerializedName("described")
        @Expose
        private String described;
        @SerializedName("created")
        @Expose
        private String created;
        @SerializedName("modified")
        @Expose
        private String modified;
        @SerializedName("like")
        @Expose
        private String like;
        @SerializedName("comment")
        @Expose
        private String comment;
        @SerializedName("is_liked")
        @Expose
        private String isLiked;
        @SerializedName("is_blocked")
        @Expose
        private String isBlocked;
        @SerializedName("can_comment")
        @Expose
        private String canComment;
        @SerializedName("can_edit")
        @Expose
        private String canEdit;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("author")
        @Expose
        private Author author;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Video getVideo() {
            return video;
        }

        public void setVideo(Video video) {
            this.video = video;
        }

        public String getDescribed() {
            return described;
        }

        public void setDescribed(String described) {
            this.described = described;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getModified() {
            return modified;
        }

        public void setModified(String modified) {
            this.modified = modified;
        }

        public String getLike() {
            return like;
        }

        public void setLike(String like) {
            this.like = like;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getIsLiked() {
            return isLiked;
        }

        public void setIsLiked(String isLiked) {
            this.isLiked = isLiked;
        }

        public String getIsBlocked() {
            return isBlocked;
        }

        public void setIsBlocked(String isBlocked) {
            this.isBlocked = isBlocked;
        }

        public String getCanComment() {
            return canComment;
        }

        public void setCanComment(String canComment) {
            this.canComment = canComment;
        }

        public String getCanEdit() {
            return canEdit;
        }

        public void setCanEdit(String canEdit) {
            this.canEdit = canEdit;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public Author getAuthor() {
            return author;
        }

        public void setAuthor(Author author) {
            this.author = author;
        }

    }

    // -----------------------------------com.example.Video.java-----------------------------------
    public class Video {

        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("thumb")
        @Expose
        private Object thumb;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Object getThumb() {
            return thumb;
        }

        public void setThumb(Object thumb) {
            this.thumb = thumb;
        }
    }
}

