package com.bytrustu.board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.bytrustu.board.model.BoardDTO;
import com.bytrustu.common.util.DBConnection;
import com.bytrustu.member.model.MemberDAO;

public class BoardDAO {
  
  private static BoardDAO instance = new BoardDAO();
  
  private BoardDAO() {
  }
  
  public static BoardDAO getInstance() {
    return instance;
  }
  
  // 공통 함수
  
  public int getNext() { // 마지막 글 넘버 출력 함수
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    int bNum = 0;
    String query = " select bNum from board order by bNum DESC ";
    
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      rs = pstmt.executeQuery();
      
      if (rs.next()) {
        /* System.out.println(">>>>>>>>>>bNum 마지막 페이지 함수 처리"); */
        bNum = rs.getInt("bNum");
      }
    } catch (Exception e) {
      /* System.out.println(">>>>>>>>>>bNum 마지막 페이지 함수 처리 실패"); */
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
    return bNum;
  }
  
  public boolean nextPage(int pageNumber) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    boolean result = false;
    String query = " select * from board bNum<? AND bAvailable=1 ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        result = true;
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
    return result;
  }
  
  // 게시판 리스트
  
  public ArrayList<BoardDTO> boardGetList(int pageNum, int countList) { // 게시글 전체 출력
    ArrayList<BoardDTO> dtos = new ArrayList<BoardDTO>();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    /*
     * String query = " select bNum,bId,bTitle,bContent,bGroup,bStep,bIndent,bCount,bAvailable,to_char(bDate,'YY-MM-DD HH24:MI') as bDate from " + "( select * from board order by bGroup desc, bStep asc ) " + "where rownum <=10 ";
     */
    
    String query = "  select X.*, to_char(X.bDate, 'YY-MM-DD') as bDate  from "
        + "( select rownum as rnum, A.*  from "
        + "( select * from board  order by bGroup desc, bStep asc) A  "
        + "where rownum <= ?) "
        + "X where X.rnum >= ? ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, ((pageNum - 1) * countList + 1) + countList - 1);
      pstmt.setInt(2, (pageNum - 1) * countList + 1);
      rs = pstmt.executeQuery();
      /* System.out.println(">>>>>>>>>> boardGetList 접근"); */
      while (rs.next()) {
        /* System.out.println(">>>>>>>>>> boardGetList 값있음"); */
        int bNum = rs.getInt(2);
        String bId = rs.getString(3);
        String bTitle = rs.getString(4);
        String bContent = rs.getString(5);
        int bGroup = rs.getInt(6);
        int bStep = rs.getInt(7);
        int bIndent = rs.getInt(8);
        int bCount = rs.getInt(9);
        int bAvailable = rs.getInt(10);
        String bDate = rs.getString(12);
        int commentCount = getCommentCount(bNum);
        BoardDTO dto = new BoardDTO(bNum, bId, bTitle, bContent, bGroup, bStep, bIndent, bCount, bAvailable, bDate, commentCount);
        // 글마다 좋아요 개수 추가
        dto.setContentLike(getContentLikeCount(bNum));
        
        // 프로필 사진 파일이름
        dto.setFileName(MemberDAO.getInstance().getFile(bId));
        
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
  
  public ArrayList<BoardDTO> boardGetListById(String userID) { // 지정 된 사용자 게시글 출력
    ArrayList<BoardDTO> dtos = new ArrayList<BoardDTO>();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    /*
     * String query = " select bNum,bId,bTitle,bContent,bGroup,bStep,bIndent,bCount,bAvailable,to_char(bDate,'YY-MM-DD HH24:MI') as bDate from " + "( select * from board order by bGroup desc, bStep asc ) " + "where rownum <=10 ";
     */
    
    String query = " select board.* , to_char(bDate, 'YY-MM-DD') as bDate from board where bId = ? order by bNum DESC ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setString(1, userID);
      rs = pstmt.executeQuery();
      /* System.out.println(">>>>>>>>>> boardGetList 접근"); */
      while (rs.next()) {
        /* System.out.println(">>>>>>>>>> boardGetList 값있음"); */
        int bNum = rs.getInt(1);
        String bId = rs.getString(2);
        String bTitle = rs.getString(3);
        String bContent = rs.getString(4);
        int bGroup = rs.getInt(5);
        int bStep = rs.getInt(6);
        int bIndent = rs.getInt(7);
        int bCount = rs.getInt(8);
        int bAvailable = rs.getInt(9);
        String bDate = rs.getString(11);
        int commentCount = getCommentCount(bNum);
        BoardDTO dto = new BoardDTO(bNum, bId, bTitle, bContent, bGroup, bStep, bIndent, bCount, bAvailable, bDate, commentCount);
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
  
  public int getCommentCount(int bNum) { // 게시글의 코멘트 카운트 출력
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    int result = 0;
    String query = " select count(*) from comments where bNum = ?  ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, bNum);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        result = rs.getInt(1);
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
    return result;
  }
  
  // 게시판 글(Content) SELECT
  
  public BoardDTO boardGetContent(int intNum) { // 게시글 데이터 가져오기
    BoardDTO dto = null;
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String query = " select bNum, bId, bTitle, bContent, bGroup, bStep, bIndent, bCount, bAvailable, to_char(bDate,'YY-MM-DD HH24:MI') as bDate from board where bNum = ?  ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, intNum);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        int bNum = rs.getInt("bNum");
        String bId = rs.getString("bId");
        String bTitle = rs.getString("bTitle");
        String bContent = rs.getString("bContent");
        bContent = bContent.replace("\r\n", "<br>");
        int bGroup = rs.getInt("bGroup");
        int bStep = rs.getInt("bStep");
        int bIndent = rs.getInt("bIndent");
        int bCount = rs.getInt("bCount");
        int bAvailable = rs.getInt("bAvailable");
        String bDate = rs.getString("bDate");
        dto = new BoardDTO(bNum, bId, bTitle, bContent, bGroup, bStep, bIndent, bCount, bAvailable, bDate);
        dto.setFileName(MemberDAO.getInstance().getFile(bId));
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null) rs.close();
        if (pstmt != null) rs.close();
        if (conn != null) rs.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return dto;
  }
  
  public int getCount(int bNum) { // 조회수 가져오기
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    int bCount = 0;
    String query = " select bCount from board where bNum=? ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, bNum);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        bCount = rs.getInt("bCount");
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
    return bCount;
  }
  
  // 게시판 글(Content) INSERT / UPDATE
  
  public int boardContentWirte(String bId, String bTitle, String bContent) { // 게시글 작성
    Connection conn = null;
    PreparedStatement pstmt = null;
    
    int result = 0;
    String query = " insert into board(bNum, bId, bTitle, bContent, bGroup, bStep, bIndent, bCount, bAvailable) " + "values(?, ?, ?, ?, ?, 0, 0, 0, 1)";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, getNext() + 1);
      pstmt.setString(2, bId);
      pstmt.setString(3, bTitle);
      pstmt.setString(4, bContent);
      pstmt.setInt(5, getNext() + 1);
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
  
  public int boardContentUpdate(int bNum, String bTitle, String bContent) { // 게시글 업데이트 . 수정
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    
    int result = 0;
    String query = " update board set bTitle=?, bContent=? where bNum=? ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setString(1, bTitle);
      pstmt.setString(2, bContent);
      pstmt.setInt(3, bNum);
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
  
  public int boardContentDelete(int bNum) { // 게시글 삭제. 업데이트 bAvailable = 0
    Connection conn = null;
    PreparedStatement pstmt = null;
    
    int result = 0;
    String query = " update board set bAvailable = ? where bNum = ? ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, 0);
      pstmt.setInt(2, bNum);
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
  
  public void boardContentCountUp(int bNum) { // 조회수 카운트 업
    Connection conn = null;
    PreparedStatement pstmt = null;
    String query = " update board set bCount=?  where bNum=?  ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, getCount(bNum) + 1);
      pstmt.setInt(2, bNum);
      pstmt.executeUpdate();
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
  }
  
  // 게시글 , 댓글 사용자 카운트
  
  // 사용자 게시글 카운트
  public int getUserContentCount(String userID) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    int result = 0;
    String query = " select count(*) from board where bId = ?  ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setString(1, userID);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        result = rs.getInt(1);
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
    return result;
  }
  
  // 사용자 댓글 카운트
  public int getUserReplyCount(String userID) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    int result = 0;
    String query = " select count(*) from comments where cId = ?  ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setString(1, userID);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        result = rs.getInt(1);
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
    return result;
  }
  
  // 게시판 답변(Reply)
  
  public int boardReplyWrite(String bId, String bTitle, String bContent, int bGroup, int bStep, int bIndent) { // 답변(Reply) 작성
    Connection conn = null;
    PreparedStatement pstmt = null;
    boardReplyShape(bGroup, bStep);
    
    int result = 0;
    String query = " insert into board(bNum, bId, bTitle, bContent, bGroup, bStep, bIndent, bCount, bAvailable) " + "values(?, ?, ?, ?, ?, ?, ?, 0, 1 ) ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, getNext() + 1);
      pstmt.setString(2, bId);
      pstmt.setString(3, bTitle);
      pstmt.setString(4, bContent);
      pstmt.setInt(5, bGroup);
      pstmt.setInt(6, bStep + 1);
      pstmt.setInt(7, bIndent + 1);
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
  
  // 해당 글답변 작성시 자신의 쓸 글 보다 먼저 작성 된 글(bStep)의 경우 모두 +1
  public int boardReplyShape(int bGroup, int bStep) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    
    int result = 0;
    String query = " update board set bStep = bStep+1 where bGroup = ? AND bStep > ? ";
    try {
      conn = DBConnection.getConnection();
      /* System.out.println(">>>>>>>>>>>> ReplyShape 접근"); */
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, bGroup);
      pstmt.setInt(2, bStep);
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
  
  // 페이징 처리
  
  public int getContentCount() {
    int result = 0;
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String query = " select count(*) as totalCount from board ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        result = rs.getInt("totalCount");
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
    return result;
  }
  
  // 좋아요 처리
  
  public int getContentLikeCount(int contentNo) { // 글 좋아요 카운트
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    int result = 0;
    String checkLikeQuery = " select count(*) as count from contentlike where content_no = ?  ";
    
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(checkLikeQuery);
      pstmt.setInt(1, contentNo);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        result = rs.getInt("count");
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
    return result;
  }
  
  public int getCheckContentLike(int contentNo, String userID) { // 사용자가 글 좋아요 눌렀는지 확인
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    int result = 0;
    String checkLikeQuery = " select * from contentlike where content_no = ? AND like_id = ?  ";
    
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(checkLikeQuery);
      pstmt.setInt(1, contentNo);
      pstmt.setString(2, userID);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        result = 1;
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
    return result;
  }
  
  public int setContentLike(int contentNo, String userID) { // 좋아요 +1
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    int result = 0;
    int maxLikeNo = 0;
    
    String likeNoQuery = " select max(like_no) as like_no from contentlike ";
    String addLike = " insert into contentlike(like_no, content_no, like_id, regdate) values(?, ?, ?, sysdate) ";
    
    try {
      
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(likeNoQuery);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        maxLikeNo = rs.getInt("like_no");
      }
      pstmt.close();
      pstmt = conn.prepareStatement(addLike);
      pstmt.setInt(1, maxLikeNo + 1);
      pstmt.setInt(2, contentNo);
      pstmt.setString(3, userID);
      result = pstmt.executeUpdate();
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
    return result;
  }
  
  public int setContentUnLike(int contentNo, String userID) { // 좋아요 -1
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    
    int result = 0;
    
    String addLike = " delete from contentlike where content_no = ? AND like_id = ? ";
    
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(addLike);
      pstmt.setInt(1, contentNo);
      pstmt.setString(2, userID);
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
  
  
  public ArrayList<BoardDTO> getRankLikeContent() { // 좋아요 랭크 3개 게시물
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    BoardDTO dto = null;
    ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
    
    /*String rankQuery = " select * from board where bnum in ( select content_no from (select content_no ,count(*) as content_count from contentlike group by content_no order by content_count desc) where rownum<=3 ) ";
    */
    String rankQuery = " select t1.bnum, t1.bid, t1.btitle from ( select * from board) t1 "
                           + " INNER JOIN "
                           + " (select content_no, count(*) as content_count from contentlike group by content_no order by content_count DESC) t2"
                           + " ON t1.bnum = t2.content_no"
                           + " where rownum<=3 " ;
    
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(rankQuery);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        dto = new BoardDTO();
        dto.setbNum(rs.getInt("bNum"));
        dto.setbTitle(rs.getString("bTitle"));
        dto.setbId(rs.getString("bId"));
        list.add(dto);
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
    return list;
  }
  
}
