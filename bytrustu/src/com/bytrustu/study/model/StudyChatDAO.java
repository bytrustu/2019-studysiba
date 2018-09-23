package com.bytrustu.study.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.bytrustu.common.util.DBConnection;

public class StudyChatDAO {
  
  private static StudyChatDAO instance = new StudyChatDAO();
  
  private StudyChatDAO() {
  }
  
  public static StudyChatDAO getInstance() {
    return instance;
  }
  
  // 마지막 chatID 조회
  public int getNextId() {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    int chatID = 0;
    String query = " select chatID from groupchat order by chatID DESC ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      rs = pstmt.executeQuery();
      if (rs.next()) {
         chatID = rs.getInt("chatID");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null) rs.close();
        if (pstmt != null) pstmt.close();
        if (conn != null) conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return chatID;
  }
  
  // 마지막 chatNum 조회
  public int getNextNum() {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int chatNum=0;
    
    String query = " select chatNum from groupchat order by chatNum DESC ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      rs = pstmt.executeQuery();
      if (rs.next()) {
         chatNum = rs.getInt("chatNum");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null) rs.close();
        if (pstmt != null) pstmt.close();
        if (conn != null) conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return chatNum;
  }
  
  
  // 메세지 내용 저장
  public int writeChat(String userID, String chatContent, int chatNum) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    
    System.out.println(chatContent);
    int result = 0;
    String query = " insert into groupchat values(?, ?, ?, ?, sysdate)  ";
    
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, getNextId()+1);
      pstmt.setInt(2, chatNum);
      pstmt.setString(3, userID);
      pstmt.setString(4, chatContent);
      result = pstmt.executeUpdate();
      
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (pstmt != null) pstmt.close();
        if (conn != null) conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return result;
  }
  
  // 지정 된 그룹의 대화내용 조회
  public ArrayList<StudyChatDTO> getChatListByID(int chatNum) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    StudyChatDTO dto;
    ArrayList<StudyChatDTO> list = new ArrayList<StudyChatDTO>(); 
    
    String query = " select * from groupchat where chatNum = ?  order by chattime ASC ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, chatNum);
      rs = pstmt.executeQuery();
      
      while ( rs.next() ) {
        dto = new StudyChatDTO();
        dto.setUserID(rs.getString("userID"));
        dto.setChatContent(rs.getString("chatContent"));
        dto.setChatTime(rs.getString("chatTime"));
        list.add(dto);
      }
    } catch ( Exception e ) {
      e.printStackTrace();
    } finally { 
      try {
        if ( rs != null ) rs.close();
        if ( pstmt != null ) pstmt.close();
        if ( conn != null ) conn.close();
      } catch ( Exception e ) {
        e.printStackTrace();
      }
    }
    return list;
  }
  
  
  
}
