package com.bytrustu.comment.model;

public class CommentDTO {
  
  private int cNum; // 코멘트 번호
  private int bNum; // 게시글 번호
  private String cId; // userID
  private String cContent; // 코멘트 내용
  private int cGroup; // 코멘트 글 하나의 묶음 번호
  private int cStep; // 코멘트 한 글의 묶음중에 생성된 순서
  private int cIndent; // 코멘트 답글일 경우 들여쓰기 할 수
  private int cAvailable; //
  private String cDate; // 코멘트 쓰여진 Date
  private String precId; // 부모 댓글 userID
  private String profile;
  
  public CommentDTO() {
  }
  
  public CommentDTO(int cNum, int bNum, String cId, String cContent, int cGroup, int cStep, int cIndent, int cAvailable, String cDate, String precId) {
    super();
    this.cNum = cNum;
    this.bNum = bNum;
    this.cId = cId;
    this.cContent = cContent;
    this.cGroup = cGroup;
    this.cStep = cStep;
    this.cIndent = cIndent;
    this.cAvailable = cAvailable;
    this.cDate = cDate;
    this.precId = precId;
  }
  
  public CommentDTO(int cNum, int bNum, String cId, String cContent, int cGroup, int cStep, int cIndent, int cAvailable, String cDate) {
    super();
    this.cNum = cNum;
    this.bNum = bNum;
    this.cId = cId;
    this.cContent = cContent;
    this.cGroup = cGroup;
    this.cStep = cStep;
    this.cIndent = cIndent;
    this.cAvailable = cAvailable;
    this.cDate = cDate;
  }
  
  public int getcNum() {
    return cNum;
  }
  
  public void setcNum(int cNum) {
    this.cNum = cNum;
  }
  
  public int getbNum() {
    return bNum;
  }
  
  public void setbNum(int bNum) {
    this.bNum = bNum;
  }
  
  public String getcId() {
    return cId;
  }
  
  public void setcId(String cId) {
    this.cId = cId;
  }
  
  public String getcContent() {
    return cContent;
  }
  
  public void setcContent(String cContent) {
    this.cContent = cContent;
  }
  
  public int getcGroup() {
    return cGroup;
  }
  
  public void setcGroup(int cGroup) {
    this.cGroup = cGroup;
  }
  
  public int getcStep() {
    return cStep;
  }
  
  public void setcStep(int cStep) {
    this.cStep = cStep;
  }
  
  public int getcIndent() {
    return cIndent;
  }
  
  public void setcIndent(int cIndent) {
    this.cIndent = cIndent;
  }
  
  public int getcAvailable() {
    return cAvailable;
  }
  
  public void setcAvailable(int cAvailable) {
    this.cAvailable = cAvailable;
  }
  
  public String getcDate() {
    return cDate;
  }
  
  public void setcDate(String cDate) {
    this.cDate = cDate;
  }
  
  public String getPrecId() {
    return precId;
  }
  
  public void setPrecId(String precId) {
    this.precId = precId;
  }

  public String getProfile() {
    return profile;
  }

  public void setProfile(String profile) {
    this.profile = profile;
  }
  
  
  
}
