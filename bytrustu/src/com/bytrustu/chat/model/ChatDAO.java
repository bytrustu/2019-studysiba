package com.bytrustu.chat.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.bytrustu.common.util.DBConnection;

public class ChatDAO {
  
  private static ChatDAO instance = new ChatDAO();
  
  private ChatDAO() {
  }
  
  public static ChatDAO getInstance() {
    return instance;
  }
  
  // 마지막 chatID 조회
  public int getNextId() {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    int chatID = 0;
    String query = " select chatID from chat order by chatID DESC ";
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
    
    String query = " select chatNum from chat order by chatNum DESC ";
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
  
  
  
  
  // 두 사용자 간에 이미 대화중인 사람인지를 체크 ( 이미 대화내용이 있으면 : 1 , 내용이 없으면 : 0 )
  public int checkAlreadyWriter(String fromID, String toID) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    int reulst = 0;
    
    String query = " select * from chat  ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      rs = pstmt.executeQuery();
      while ( rs.next() ) {
        if ( ( rs.getString("fromID").equals(fromID) && rs.getString("toID").equals(toID) ) || ( rs.getString("fromID").equals(toID) && rs.getString("toID").equals(fromID) ) ) {
          reulst = rs.getInt("chatNum");
        }
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
    return reulst;
  }
  
  
  
  // 메세지 내용 저장
  public int writeChat(String fromID, String toID, String chatContent) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    
    System.out.println(chatContent);
    int result = 0;
    String query = " insert into chat values(?, ?, ?, ?, ?, 0, sysdate)  ";
    
    // chatNum : checkAlreadyWriter 메소드를 호출해서 사용자가 없을경우(0) getNextNum() 메소드를 호출해서 마지막값+1 을 부여한다.
    int chatNum = checkAlreadyWriter(fromID, toID);
    if ( chatNum == 0 ) {
      chatNum = getNextNum()+1;
    }
    
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, getNextId()+1);
      pstmt.setInt(2, chatNum);
      pstmt.setString(3, fromID);
      pstmt.setString(4, toID);
      pstmt.setString(5, chatContent);
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
  
  // 지정 된 사용자와의 대화내용 조회
  public ArrayList<ChatDTO> getChatListByID(String fromID, String toID) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    ChatDTO dto;
    ArrayList<ChatDTO> list = new ArrayList<ChatDTO>(); 
    
    String query = " select * from chat where ( ( fromID = ? AND toID = ? ) OR ( fromID = ? AND toID = ? ) )  order by chattime ASC ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setString(1, fromID);
      pstmt.setString(2, toID);
      pstmt.setString(3, toID);
      pstmt.setString(4, fromID);
      rs = pstmt.executeQuery();
      
      while ( rs.next() ) {
        dto = new ChatDTO();
        dto.setFromID(rs.getString("fromID"));
        dto.setToID(rs.getString("toID"));
        dto.setChatContent(rs.getString("chatContent"));
        dto.setRead(rs.getInt("read"));
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
  
  
  
  // 메세지를 나눈 각각 사용자간의 가장 마지막 메세지만 조회
  public ArrayList<ChatDTO> getChatList(String userID) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    ArrayList<ChatDTO> list = new ArrayList<ChatDTO>();
    ChatDTO dto;

    String query = " select * from chat where chatID IN (select max(chatID) from chat where toID = ? OR fromID =? group by chatNum) order by chatID DESC ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setString(1, userID);
      pstmt.setString(2, userID);
      rs = pstmt.executeQuery();
      
      while ( rs.next() ) {
          dto = new ChatDTO();
          dto.setFromID(rs.getString("fromID"));
          dto.setToID(rs.getString("toID"));
          dto.setChatContent(rs.getString("chatContent"));
          
          // 자신에게 온 메세지를 아직 안 읽은 경우 : 2
          if ( rs.getString("toID").equals(userID) && !rs.getString("fromID").equals(userID) && rs.getInt("read") == 0 ) {
            dto.setRead(2);
          } else {
            dto.setRead(rs.getInt("read"));
          }
          dto.setChatTime(rs.getString("chatTime").substring(2, 16));
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
  
  
  // 메세지를 읽을 경우 , read 를 1 로 update
  public int chatRead(String fromID, String toID) {
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    
    int result = 0;
    String query = " update chat set read = 1 where (fromID = ? AND toID = ?) ";
    
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setString(1, toID);
      pstmt.setString(2, fromID);
      result = pstmt.executeUpdate();
      if (result != 1) {
        result = -1;
      }
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
  
// 아직 읽지 않은 메세지 수 조회
public int getUnReadChat(String toID) {
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    int result = 0;
    
    String query = " select count(read) from chat where toID = ? AND read = 0 ";
    
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setString(1, toID);
      rs = pstmt.executeQuery();
      if ( rs.next() ) {
        result = rs.getInt("count(read)");
      }
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
  
  
  
}
