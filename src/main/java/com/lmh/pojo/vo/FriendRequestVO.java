package com.lmh.pojo.vo;

/**
 * @ClassName: FriendRequestVO
 * @Description: 好友请求发送方的信息
 * @author: ALin
 * @date: 2020/3/31 上午11:49
 */
public class FriendRequestVO {

    private String sendUserId;
    private String sendUsername;
    private String sendFaceImage;
    private String sendNickname;

    public String getSendUserId() {
        return sendUserId;
    }
    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }
    public String getSendUsername() {
        return sendUsername;
    }
    public void setSendUsername(String sendUsername) {
        this.sendUsername = sendUsername;
    }
    public String getSendFaceImage() {
        return sendFaceImage;
    }
    public void setSendFaceImage(String sendFaceImage) {
        this.sendFaceImage = sendFaceImage;
    }
    public String getSendNickname() {
        return sendNickname;
    }
    public void setSendNickname(String sendNickname) {
        this.sendNickname = sendNickname;
    }
}