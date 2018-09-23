package com.bytrustu.study.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.bytrustu.board.model.BoardDTO;
import com.bytrustu.common.util.DBConnection;
import com.bytrustu.member.model.MemberDAO;

public class StudyDAO {
  
  private static StudyDAO instance = new StudyDAO();
  
  private StudyDAO() {
  }
  
  public static StudyDAO getInstance() {
    return instance;
  }
  
  
  
  
  
  /*
   * Study Board 관련
   */
  
  
  
  // 스터디 글 작성
  public int writeStudyContent(String userID, String groupName, String divide, String title, String content, String lat, String lng, String area, String address, String period, String stime, int person, String sfile) {
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String query = " insert into studyboard values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate) ";
    int result = 0;
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, getNext() + 1);
      pstmt.setInt(2, getNext() + 1);
      pstmt.setString(3, userID);
      pstmt.setString(4, groupName);
      pstmt.setString(5, divide);
      pstmt.setString(6, title);
      pstmt.setString(7, content);
      pstmt.setString(8, lat);
      pstmt.setString(9, lng);
      pstmt.setString(10, area);
      pstmt.setString(11, address);
      pstmt.setString(12, period);
      pstmt.setString(13, stime);
      pstmt.setString(14, sfile);
      pstmt.setInt(15, person);
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
  
  
  
  
//마지막 스터디글 넘버 출력 함수
  public int getNext() { 
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    int num = 0;
    String query = " select max(num) as num from studyboard ";
    
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      rs = pstmt.executeQuery();
      
      if (rs.next()) {
        /* System.out.println(">>>>>>>>>>bNum 마지막 페이지 함수 처리"); */
        num = rs.getInt("num");
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
    return num;
  }
  
  
  
  // 스터디 그룹 참여 여부
  public int getGroupNum(String groupName, String userID) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    int gNum = 0;
    String query = " select gnum from studyboard where groupName = ? AND userID = ? ";
    
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setString(1, groupName);
      pstmt.setString(2, userID);
      rs = pstmt.executeQuery();
      
      if (rs.next()) {
        gNum = rs.getInt("gnum");
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
    return gNum;
  }
  
  
  // 스터디 글 페이징처리 & 가져오기
  public ArrayList<StudyDTO> StudyGetList(int pageNum, int countList) { 
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    ArrayList<StudyDTO> list = new ArrayList<StudyDTO>();
    String query = " select Y.*,p.pfile from ( select X.*  from ( select rownum as rnum, A.*  from ( select * from studyboard  order by num desc ) A  where rownum <= ?) X where X.rnum >= ? )Y join profile p on y.userID = p.pid order by num desc ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, ((pageNum - 1) * countList + 1) + countList - 1);
      pstmt.setInt(2, (pageNum - 1) * countList + 1);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        int num = rs.getInt(2);
        int gNum = rs.getInt(3);
        String userID = rs.getString(4);
        String groupName = rs.getString(5);
        String divide = rs.getString(6);
        String title = rs.getString(7);
        String content = rs.getString(8);
        String lat = rs.getString(9);
        String lng = rs.getString(10);
        String area = rs.getString(11);
        String address = rs.getString(12);
        String period = rs.getString(13);
        String stime = rs.getString(14);
        String sfile = rs.getString(15);
        int person = rs.getInt(16);
        String date = rs.getString(17);
        String userFile = rs.getString(18);
        if (userFile == null) {
          userFile = "siba-default.gif";
        }
        
        // 글마다 속한 멤버의 이미지 파일
        ArrayList<String> listFile = new ArrayList<String>();
        listFile = getListMemberFile(gNum);
        
        StudyDTO dto = new StudyDTO(num, gNum, userID, groupName, divide, title, content, lat, lng, area, address, period, stime, sfile, person, date);
        dto.setListFile(listFile);
        dto.setUserFile(userFile);
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
  
  
  // 스터디 글 검색 필터
  public ArrayList<StudyDTO> StudyGetSearchList(String strDivide, String search) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    ArrayList<StudyDTO> list = new ArrayList<StudyDTO>();
    String query = null;
    
    if (strDivide.equals("전체")) {
      query = " select Y.*,p.pfile from ( select X.*  from ( select rownum as rnum, A.*  from ( select * from studyboard where title like '%" + search + "%'  order by num desc ) A  where rownum <= 4) X where X.rnum >= 1 )Y join profile p on y.userID = p.pid order by num desc ";
    } else {
      query = " select Y.*,p.pfile from ( select X.*  from ( select rownum as rnum, A.*  from ( select * from studyboard where divide=? AND title like '%" + search + "%'  order by num desc ) A  where rownum <= 4) X where X.rnum >= 1 )Y join profile p on y.userID = p.pid order by num desc ";
    }
    
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      if (!strDivide.equals("전체")) {
        pstmt.setString(1, strDivide);
      }
      rs = pstmt.executeQuery();
      while (rs.next()) {
        int num = rs.getInt(2);
        int gNum = rs.getInt(3);
        String userID = rs.getString(4);
        String groupName = rs.getString(5);
        String divide = rs.getString(6);
        String title = rs.getString(7);
        String content = rs.getString(8);
        String lat = rs.getString(9);
        String lng = rs.getString(10);
        String area = rs.getString(11);
        String address = rs.getString(12);
        String period = rs.getString(13);
        String stime = rs.getString(14);
        String sfile = rs.getString(15);
        int person = rs.getInt(16);
        String date = rs.getString(17);
        String userFile = rs.getString(18);
        if (userFile == null) {
          userFile = "siba-default.gif";
        }
        
        // 글마다 속한 멤버의 이미지 파일
        ArrayList<String> listFile = new ArrayList<String>();
        listFile = getListMemberFile(gNum);
        
        StudyDTO dto = new StudyDTO(num, gNum, userID, groupName, divide, title, content, lat, lng, area, address, period, stime, sfile, person, date);
        dto.setListFile(listFile);
        dto.setUserFile(userFile);
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
  
// 지정된 글번호 스터디 글 내용
  public StudyDTO studyGetListById(int contentNum) { 
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    StudyDTO dto = new StudyDTO();
    
    String query = " select * from studyboard where num = ? ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, contentNum);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        int num = rs.getInt(1);
        int gNum = rs.getInt(2);
        String userID = rs.getString(3);
        String groupName = rs.getString(4);
        String divide = rs.getString(5);
        String title = rs.getString(6);
        String content = rs.getString(7);
        String lat = rs.getString(8);
        String lng = rs.getString(9);
        String area = rs.getString(10);
        String address = rs.getString(11);
        String period = rs.getString(12);
        String stime = rs.getString(13);
        String sfile = rs.getString(14);
        int person = rs.getInt(15);
        String date = rs.getString(16);
        dto = new StudyDTO(num, gNum, userID, groupName, divide, title, content, lat, lng, area, address, period, stime, sfile, person, date);
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
    return dto;
  }
  
  
  // 지정 된 그룹번호 게시글 출력
  public StudyDTO studyGetListBygNum(int contentNum) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    StudyDTO dto = new StudyDTO();
    
    String query = " select * from studyboard where gnum = ? ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, contentNum);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        int num = rs.getInt(1);
        int gNum = rs.getInt(2);
        String userID = rs.getString(3);
        String groupName = rs.getString(4);
        String divide = rs.getString(5);
        String title = rs.getString(6);
        String content = rs.getString(7);
        String lat = rs.getString(8);
        String lng = rs.getString(9);
        String area = rs.getString(10);
        String address = rs.getString(11);
        String period = rs.getString(12);
        String stime = rs.getString(13);
        String sfile = rs.getString(14);
        int person = rs.getInt(15);
        String date = rs.getString(16);
        dto = new StudyDTO(num, gNum, userID, groupName, divide, title, content, lat, lng, area, address, period, stime, sfile, person, date);
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
    return dto;
  }
  
  
  // 클릭한 게시글 번호 업데이트
  public int StudyUpdateList(int gNum) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    
    int result = 0;
    
    String query = " update studyboard set num = ? where gnum = ? ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, getNext() + 1);
      pstmt.setInt(2, gNum);
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
  
  // 스터디 글 전체 갯수
  public int getContentCount() {
    int result = 0;
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String query = " select count(*) as totalCount from studyboard ";
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
  
  
  
  
  
  
  /*
   * Study Group 관련
   */
  
  // 그룹 마지막 번호
  public int getGroupNext() {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    int num = 0;
    String query = " select max(num) as num from studygroup ";
    
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      rs = pstmt.executeQuery();
      
      if (rs.next()) {
        num = rs.getInt("num");
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
    return num;
  }
  
  
  // 그룹 생성
  public int makeGroup(int gNum, String groupName, String userID) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    int result = 0;
    String query = " insert into studygroup values( ?, ?, ?, ?, sysdate ) ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, getGroupNext() + 1);
      pstmt.setInt(2, gNum);
      pstmt.setString(3, groupName);
      pstmt.setString(4, userID);
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
  
  
  // 그룹에 포함된 사용자 프로필 사진 파일명
  public ArrayList<String> getListMemberFile(int gNum) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    ArrayList<String> list = new ArrayList<String>();
    
    String query = " select p.pfile as pFile from studymember m join studygroup g on m.gnum = g.gnum join profile p on m.member = p.pid where g.gnum = ? ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, gNum);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        String pFile = rs.getString("pFile");
        if (pFile == null) {
          pFile = "siba-default.gif";
        }
        /* System.out.println(pFile); */
        list.add(pFile);
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
  
  // 리스트별 멤버 인원 카운트
  public int getListMemberCount(int gNum) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    int count = 0;
    
    String query = " select count(*) as count from studymember m join studygroup g on m.gnum = g.gnum where g.gnum = ? ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, gNum);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        count = rs.getInt("count");
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
  
  
  
  // 아이디에 포함된 그룹정보
  public ArrayList<HashMap<String, Object>> StudyGetGroupList(String userID) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    
    String query = " select b.gnum, b.divide, b.groupname, b.title, b.area || ' ' || b.address as address, b.period || ' ' || b.stime as time, b.person, b.sfile as image from studygroup g join studymember m on g.gnum = m.gnum join studyboard b on g.gnum = b.gnum where m.member = ? order by m.mdate desc ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setString(1, userID);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("gNum", rs.getInt("gNum"));
        map.put("divide", rs.getString("divide"));
        map.put("groupName", rs.getString("groupName"));
        map.put("address", rs.getString("address"));
        map.put("time", rs.getString("time"));
        map.put("currCount", StudyGetGroupMemberCount(rs.getInt("gNum")));
        map.put("person", rs.getInt("person"));
        map.put("image", rs.getString("image"));
        list.add(map);
      }
      
      rs.close();
      pstmt.close();
      String leaderQuery = "select b.gnum, b.divide, b.groupname, b.title, b.area || ' ' || b.address as address, b.period || ' ' || b.stime as time, b.person, b.sfile as image from studygroup g join studyboard b on g.gnum = b.gnum where b.userid=? order by g.gdate desc ";
      pstmt = conn.prepareStatement(leaderQuery);
      pstmt.setString(1, userID);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("gNum", rs.getInt("gNum"));
        map.put("divide", rs.getString("divide"));
        map.put("groupName", rs.getString("groupName"));
        map.put("address", rs.getString("address"));
        map.put("time", rs.getString("time"));
        map.put("currCount", StudyGetGroupMemberCount(rs.getInt("gNum")));
        map.put("person", rs.getInt("person"));
        map.put("image", rs.getString("image"));
        list.add(map);
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
  
  // 아이디에 포함된 그룹정보
  public ArrayList<HashMap<String, Object>> StudyGetGroupContent(int gNum) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    
    /*
     * String query = " select b.gnum, b.divide, b.groupname, b.title, b.area || ' ' || b.address as address, b.period || ' ' || b.stime as time, b.person, b.sfile as image from studygroup g join studymember m on g.gnum = m.gnum join studyboard b on g.gnum = b.gnum where m.gnum=? and rownum=1 order by m.mdate desc ";
     */
    String query = " select b.gnum, b.divide, b.groupname, b.title, b.area || ' ' || b.address as address, b.period || ' ' || b.stime as time, b.person, b.sfile as image from studygroup g join studyboard b on g.gnum = b.gnum where g.gnum=? ";
    
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, gNum);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("gNum", rs.getInt("gNum"));
        map.put("divide", rs.getString("divide"));
        map.put("groupName", rs.getString("groupName"));
        map.put("address", rs.getString("address"));
        map.put("time", rs.getString("time"));
        map.put("currCount", StudyGetGroupMemberCount(rs.getInt("gNum")));
        map.put("person", rs.getInt("person"));
        map.put("image", rs.getString("image"));
        list.add(map);
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
  
  // 아이디에 포함된 그룹 내용 정보
  public ArrayList<HashMap<String, Object>> StudyGetContent(String userID, int gNum) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    
    String query = " select b.gnum, b.divide, b.groupname, b.title, b.area || ' ' || b.address as address, b.period || ' ' || b.stime as time, b.person, b.sfile as image from studygroup g join studymember m on g.gnum = m.gnum join studyboard b on g.gnum = b.gnum where m.member=? AND m.gnum=? ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setString(1, userID);
      pstmt.setInt(2, gNum);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("gNum", rs.getInt("gNum"));
        map.put("divide", rs.getString("divide"));
        map.put("groupName", rs.getString("groupName"));
        map.put("address", rs.getString("address"));
        map.put("time", rs.getString("time"));
        map.put("currCount", StudyGetGroupMemberCount(rs.getInt("gNum")));
        map.put("person", rs.getInt("person"));
        map.put("image", rs.getString("image"));
        list.add(map);
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
  
  
  
  /*
   * Study Member 관련
   */
  
  public int getMemberNext() {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    int num = 0;
    String query = " select max(num) as num from studymember ";
    
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      rs = pstmt.executeQuery();
      
      if (rs.next()) {
        num = rs.getInt("num");
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
    return num;
  }
  
  // 멤버 등록
  public int insertMember(int gNum, String userID) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    
    int result = 0;
    String query = " insert into studymember values( ?, ?, ?, sysdate ) ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, getMemberNext() + 1);
      pstmt.setInt(2, gNum);
      pstmt.setString(3, userID);
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
  
  // 멤버 확인
  public int checkMember(int gNum, String userID) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    int result = 0;
    String query = " select * from studymember where gNum = ? AND member = ? ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, gNum);
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
  
  // 그룹에 포함된 멤버들의 ID와 파일
  public ArrayList<String> getMemberFile(int gNum) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    ArrayList<String> list = new ArrayList<String>();
    
    String query = " select * from profile where pid in(select member from (select * from studymember order by mdate asc) where gnum=? ) ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, gNum);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        list.add(rs.getString("pId"));
        list.add(rs.getString("pFile"));
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
  
  // 그룹에 포함되어있는 멤버수 카운트
  public int StudyGetGroupMemberCount(int gNum) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    int result = 0;
    
    String query = " select count(*) from studymember where gnum = ? ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, gNum);
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
  
  
  /*
   * Study GroupFile 관련
   */
  // 그룹 파일 업로드 글 작성
  public int writeGroupFile(int gNum, String userID, String content, String fileName) {
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String query = " insert into groupfile values(?,?,?,?,?,sysdate) ";
    int result = 0;
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, getGroupFileNext() + 1);
      pstmt.setInt(2, gNum);
      pstmt.setString(3, userID);
      pstmt.setString(4, content);
      pstmt.setString(5, fileName);
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
  
  
  public int getGroupFileNext() { // 마지막 글 넘버 출력 함수
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    int num = 0;
    String query = " select max(num) as num from groupFile ";
    
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      rs = pstmt.executeQuery();
      
      if (rs.next()) {
        /* System.out.println(">>>>>>>>>>bNum 마지막 페이지 함수 처리"); */
        num = rs.getInt("num");
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
    return num;
  }
  
  
  // 그룹 파일 게시글 전체 출력
  public ArrayList<GroupFileDTO> StudyGetGroupFileList(int pageNum, int countList, int groupNum) { // 게시글 전체 출력
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    ArrayList<GroupFileDTO> list = new ArrayList<GroupFileDTO>();
    String query = "  select X.*  from " + "( select rownum as rnum, A.*  from " + "( select * from groupfile where gNum = ? order by num desc) A  " + "where rownum <= ?) " + "X where X.rnum >= ? ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, groupNum);
      pstmt.setInt(2, ((pageNum - 1) * countList + 1) + countList - 1);
      pstmt.setInt(3, (pageNum - 1) * countList + 1);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        int num = rs.getInt(2);
        int gNum = rs.getInt(3);
        String userID = rs.getString(4);
        String content = rs.getString(5);
        String gFile = rs.getString(6);
        String gDate = rs.getString(7);
        GroupFileDTO dto = new GroupFileDTO(num, gNum, userID, content, gFile, gDate);
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
  
  // 그룹파일 첨부 게시판 글 마지막번호
  public int getGroupFileCount(int gNum) {
    int result = 0;
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String query = " select count(*) as totalCount from groupFile where gNum=? ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, gNum);
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
  
}
