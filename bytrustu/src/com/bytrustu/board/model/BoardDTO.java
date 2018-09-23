package com.bytrustu.board.model;

public class BoardDTO {
  
  private int bNum;
  private String bId;
  private String bTitle;
  private String bContent;
  private int bGroup;
  private int bStep;
  private int bIndent;
  private int bCount;
  private int bAvailable;
  private String bDate;
  private int commentCount;
  private String fileName;
  private int contentLike;
  
  public BoardDTO(){}
  
  public BoardDTO(int bNum, String bId, String bTitle, String bContent, String bDate, int bAvailable) {
    this.bNum = bNum;
    this.bId = bId;
    this.bTitle = bTitle;
    this.bContent = bContent;
    this.bDate = bDate;
    this.bAvailable = bAvailable;
  }
  
  public BoardDTO(int bNum, String bId, String bTitle, String bContent, int bGroup, int bStep, int bIndent, int bCount, int bAvailable, String bDate) {
    super();
    this.bNum = bNum;
    this.bId = bId;
    this.bTitle = bTitle;
    this.bContent = bContent;
    this.bGroup = bGroup;
    this.bStep = bStep;
    this.bIndent = bIndent;
    this.bCount = bCount;
    this.bAvailable = bAvailable;
    this.bDate = bDate;
  }
  
  public BoardDTO(int bNum, String bId, String bTitle, String bContent, int bGroup, int bStep, int bIndent, int bCount, int bAvailable, String bDate, int commentCount) {
    super();
    this.bNum = bNum;
    this.bId = bId;
    this.bTitle = bTitle;
    this.bContent = bContent;
    this.bGroup = bGroup;
    this.bStep = bStep;
    this.bIndent = bIndent;
    this.bCount = bCount;
    this.bAvailable = bAvailable;
    this.bDate = bDate;
    this.commentCount = commentCount;
  }
  
  public int getbNum() {
    return bNum;
  }
  
  public void setbNum(int bNum) {
    this.bNum = bNum;
  }
  
  public String getbId() {
    return bId;
  }
  
  public void setbId(String bId) {
    this.bId = bId;
  }
  
  public String getbTitle() {
    return bTitle;
  }
  
  public void setbTitle(String bTitle) {
    this.bTitle = bTitle;
  }
  
  public String getbContent() {
    return bContent;
  }
  
  public void setbContent(String bContent) {
    this.bContent = bContent;
  }
  
  public int getbGroup() {
    return bGroup;
  }
  
  public void setbGroup(int bGroup) {
    this.bGroup = bGroup;
  }
  
  public int getbStep() {
    return bStep;
  }
  
  public void setbStep(int bStep) {
    this.bStep = bStep;
  }
  
  public int getbIndent() {
    return bIndent;
  }
  
  public void setbIndent(int bIndent) {
    this.bIndent = bIndent;
  }
  
  public int getbCount() {
    return bCount;
  }
  
  public void setbCount(int bCount) {
    this.bCount = bCount;
  }
  
  public int getbAvailable() {
    return bAvailable;
  }
  
  public void setbAvailable(int bAvailable) {
    this.bAvailable = bAvailable;
  }
  
  public String getbDate() {
    return bDate;
  }
  
  public void setbDate(String bDate) {
    this.bDate = bDate;
  }
  
  public int getCommentCount() {
    return commentCount;
  }
  
  public void setCommentCount(int commentCount) {
    this.commentCount = commentCount;
  }

public String getFileName() {
    return fileName;
}

public void setFileName(String fileName) {
    this.fileName = fileName;
}

public int getContentLike() {
  return contentLike;
}

public void setContentLike(int contentLike) {
  this.contentLike = contentLike;
}


  
  
  
}