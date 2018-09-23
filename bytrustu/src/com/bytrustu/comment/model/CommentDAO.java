package com.bytrustu.comment.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.bytrustu.common.util.DBConnection;
import com.bytrustu.member.model.MemberDAO;

public class CommentDAO {
  
  private static CommentDAO instance = new CommentDAO();
  
  private CommentDAO() {
  }
  
  public static CommentDAO getInstance() {
    return instance;
  }
  
  // 마지막 코멘트 넘버 출력 함수
  public int getNext() {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String query = " select cNum from comments order by bNum DESC ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        /*System.out.println(">>>>>>>>>>cNum 마지막 페이지 함수 처리");*/
        return rs.getInt("cNum");
      }
    } catch (Exception e) {
      /*System.out.println(">>>>>>>>>>cNum 마지막 페이지 함수 처리 실패");*/
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
    return 0;
  }
  
  // 코멘트 작성
  public int commentWrite(CommentDTO dto) {
    
    int result = 0;
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    String query = " insert into comments(cNum, bNum, cId, cContent, cGroup, cStep, cIndent, cAvailable) " + " values(comments_seq.nextval, ?, ?, ?,comments_seq.currval, 0, 0, 1) ";
    try {
      conn = DBConnection.getConnection();
      conn.setAutoCommit(false);
     /* System.out.println("SQL>>>>>>>" + dto.getbNum() + "," + dto.getcId() + "," + dto.getcContent());*/
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, dto.getbNum());
      pstmt.setString(2, dto.getcId());
      pstmt.setString(3, dto.getcContent());
      
      int flag = pstmt.executeUpdate();
      if (flag > 0) {
        result = 1;
        conn.commit();
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
  
  // 코멘트 수정
  public int commentUpdate(int cNum, String cContent) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    
    int result = 0;
    String query = " update comments set cContent = ? where cNum = ? ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setString(1, cContent);
      pstmt.setInt(2, cNum);
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
  
  // 코멘트 삭제 : cAvailable 0으로 변경
  public int commentDelete(int cNum, String userID) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    
    int result = 0;
    String query = " update comments set cAvailable = 0 where cNum = ? and cId = ?  ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, cNum);
      pstmt.setString(2, userID);
      result = pstmt.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (pstmt != null) pstmt.close();
        if (conn != null) pstmt.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return result;
  }
  
  public ArrayList<CommentDTO> getCommentList(int intNum) {
    ArrayList<CommentDTO> dtos = new ArrayList<CommentDTO>();
    CommentDTO dto = null;
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String query = " select * from ( select * from comments order by cGroup DESC, cStep ASC ) where bNum = ? AND cAvailable = 1  ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, intNum);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        int cNum = rs.getInt("cNum");
        int bNum = rs.getInt("bNum");
        String cId = rs.getString("cId");
        String cContent = rs.getString("cContent");
        int cGroup = rs.getInt("cGroup");
        int cStep = rs.getInt("cStep");
        int cIndent = rs.getInt("cIndent");
        int cAvailable = rs.getInt("cAvailable");
        String cDate = rs.getString("cDate");
        String precId = rs.getString("precId");
        dto = new CommentDTO(cNum, bNum, cId, cContent, cGroup, cStep, cIndent, cAvailable, cDate, precId);
       // 프로필 사진
        String profile = MemberDAO.getInstance().getFile(cId);
        if ( profile == null ) {
          profile = "siba-default.gif";
        }
        dto.setProfile(profile);
        
        dtos.add(dto);
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
    return dtos;
  }
  
  /* 글번호에 해당하는 댓글 수 확인 */
  public int getCommentCount(int bNum) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    int count = 0;
    String query = " select count(*) from comments where bNum=? ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, bNum);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        count = rs.getInt(1);
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
    return count;
  }
  
  /* 댓글 그룹번호에 해당하는 전체 댓글 수 확인 */
  public int getCommentSubCount(int cGroup) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    int count = 0;
    String query = " select count(*) from comments where cGroup=? ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, cGroup);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        count = rs.getInt(1);
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
    return count;
  }
  
  // 댓글 답변(CommentReply)
  
  public int commentReplyWrite(int bNum, String cId, String cContent, int cGroup, int cStep, int cIndent, String precId) { // 답변(Reply) 작성
    Connection conn = null;
    PreparedStatement pstmt = null;
 /*   System.out.println("commentReplyWrite디비접근");*/
    commentReplyShape(cGroup, cStep);
    
    int result = 0;
    String query = " insert into comments(cNum, bNum, cId, cContent, cGroup, cStep, cIndent, cAvailable, precId) " + "values( comments_seq.nextval, ?, ?, ?, ?, ?, ?, 1, ? ) ";
    
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, bNum);
      pstmt.setString(2, cId);
      pstmt.setString(3, cContent);
      pstmt.setInt(4, cGroup);
      pstmt.setInt(5, cStep + 1);
      pstmt.setInt(6, cIndent + 1);
      pstmt.setString(7, precId);
    /*  System.out.println("commentReplyWrite디비완료");*/
      result = pstmt.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
     /* System.out.println("commentReplyWrite디비실패");*/
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
  
  // 해당 글 댓글 작성시 자신의 쓸 댓글 보다 먼저 작성 된 뎃글(cStep)의 경우 같은 그룹 상위 코멘트는 cStep +1
  public int commentReplyShape(int cGroup, int cStep) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    
    int result = 0;
    String query = " update comments set cStep = cStep+1 where cGroup = ? AND cStep > ? ";
    try {
      conn = DBConnection.getConnection();
     /* System.out.println(">>>>>>>>>>>> CommentReplyShape 접근");*/
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, cGroup);
      pstmt.setInt(2, cStep);
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
  
  
}
