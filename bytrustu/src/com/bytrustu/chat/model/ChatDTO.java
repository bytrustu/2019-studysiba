package com.bytrustu.chat.model;

public class ChatDTO {
  private int chatID;
  private String fromID;
  private String toID;
  private String chatContent;
  private int read;
  private String chatTime;
  
  
  public ChatDTO(int chatID, String fromID, String toID, String chatContent, String chatTime) {
    super();
    this.chatID = chatID;
    this.fromID = fromID;
    this.toID = toID;
    this.chatContent = chatContent;
    this.chatTime = chatTime;
  }
  
  public ChatDTO() {
  }

  public int getChatID() {
    return chatID;
  }
  
  public void setChatID(int chatID) {
    this.chatID = chatID;
  }
  
  public String getFromID() {
    return fromID;
  }
  
  public void setFromID(String fromID) {
    this.fromID = fromID;
  }
  
  public String getToID() {
    return toID;
  }
  
  public void setToID(String toID) {
    this.toID = toID;
  }
  
  public String getChatContent() {
    return chatContent;
  }
  
  public void setChatContent(String chatContent) {
    this.chatContent = chatContent;
  }
  
  public int getRead() {
    return read;
  }

  public void setRead(int read) {
    this.read = read;
  }

  public String getChatTime() {
    return chatTime;
  }
  
  public void setChatTime(String chatTime) {
    this.chatTime = chatTime;
  }
  
}
