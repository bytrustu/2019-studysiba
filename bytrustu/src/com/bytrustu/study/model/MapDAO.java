package com.bytrustu.study.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.bytrustu.board.model.BoardDTO;
import com.bytrustu.common.util.DBConnection;
import com.bytrustu.member.model.MemberDAO;

public class MapDAO {
  
  private static MapDAO instance = new MapDAO();
  
  private MapDAO() {
  }
  
  public static MapDAO getInstance() {
    return instance;
  }
  
 
  public int getNext() { // 마지막 글 넘버 출력 함수
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int num = 0;
    String query = " select num from mapdata order by num DESC ";
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
  
  
  // 입력 받은 맵 데이터 저장
  public int insertMapData(String userID, String lat, String lng ) { 
    Connection conn = null;
    PreparedStatement pstmt = null;
    int result = 0;
    String query = " insert into mapdata(num,id,lat,lng) values(?,?,?,?) ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, getNext()+1);
      pstmt.setString(2, userID);
      pstmt.setString(3, lat);
      pstmt.setString(4, lng);
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
  
  // 아이디가 입력한 마지막 맵 데이터
  public ArrayList<String> getMapData(String userID) { 
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    ArrayList<String> mapData = new ArrayList<String>();
    String query = " select lat, lng from mapdata where id=? AND rownum=1 order by num desc ";
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setString(1, userID);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        mapData.add(rs.getString("lat"));
        mapData.add(rs.getString("lng"));
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
    return mapData;
  }
  
  
  
  
  
}
