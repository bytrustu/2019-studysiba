package com.bytrustu.visit.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.bytrustu.common.util.DBConnection;

public class VisitCountDAO {			// 싱글톤 객체 instance

	private static VisitCountDAO instance;
	private VisitCountDAO(){}
	public static VisitCountDAO getInstance(){
		if( instance == null )
			instance = new VisitCountDAO();
		return instance;
	}
	
	public void setTotalCount(){		// 호출시 현재 날짜 등록 - 방문자 date 정보 insert
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String query = " insert into visit(bdate) values(sysdate) ";
		
		try {
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(query);
			pstmt.executeUpdate();
			conn.commit();
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
				try {
				if ( conn != null ) conn.close();
				if ( pstmt != null ) pstmt.close();
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
	}
	
	
public void setMemberVisit(String userID){  // 로그인시 접속자수 업데이트
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    String query = " insert into visit(bdate,vid) values(sysdate,?) ";
    
    try {
      conn = DBConnection.getConnection();
      conn.setAutoCommit(false);
      pstmt = conn.prepareStatement(query);
      pstmt.setString(1, userID);
      pstmt.executeUpdate();
      conn.commit();
    } catch ( Exception e ) {
      e.printStackTrace();
    } finally {
        try {
        if ( conn != null ) conn.close();
        if ( pstmt != null ) pstmt.close();
      } catch ( Exception e ) {
        e.printStackTrace();
      }
    }
  }

public int getMemberVisit(String userID){  // 로그인시 접속자수 업데이트
  
  Connection conn = null;
  PreparedStatement pstmt = null;
  ResultSet rs = null;
  
  int result = 0;
  String query = " select count(*) from visit where vid=? ";
  
  try {
    conn = DBConnection.getConnection();
    conn.setAutoCommit(false);
    pstmt = conn.prepareStatement(query);
    pstmt.setString(1, userID);
    rs = pstmt.executeQuery();
    if ( rs.next() ) {
      result = rs.getInt(1);
    }
    
  } catch ( Exception e ) {
    e.printStackTrace();
  } finally {
      try {
      if ( conn != null ) conn.close();
      if ( pstmt != null ) pstmt.close();
      if ( rs != null ) rs.close();
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  return result;
}
	
	public int getTotalCount() {			// 총 방문자 숫자 카운트
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalCount = 0;
		String query = " select count(*) as TotalCount from visit ";
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			if( rs.next() )
				totalCount = rs.getInt("TotalCount");
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
		return totalCount;
	}

	
	public int getTodayCount(){			// 오늘 방문자 숫자 카운트
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int todayCount = 0;
		String query = " select count(*) AS TodayCount from visit where to_date(bDate, 'YYYY-MM-DD') = to_date(sysdate, 'YYYY-MM-DD') ";
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			if ( rs.next() ) 
				todayCount = rs.getInt("TodayCount");
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
		return todayCount;
	}
	
	
	
public int getUserVisitCount(String userID) {     // 홈페이지 유저 방문 수
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int count = 0;
    String query = " select count(*) as count from visit where vid=?  ";
    
    try {
      conn = DBConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setString(1, userID);
      rs = pstmt.executeQuery();
      
      if( rs.next() )
        count = rs.getInt("count");
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
    return count;
  }
	
	
	
	 public ArrayList<ArrayList<Object>> getSibaCount(){     // 날짜별 방문자, 게시글 카운트
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    
	    ArrayList<ArrayList<Object>> list = new ArrayList<>();
	    
	    String query = " select t1.bdate, t1.count as vcount, t2.count as bcount from "
	                     + " (select count(*) as count, to_char(bdate,'yyyy,mm,dd') as bdate from visit "
	                     + " where bdate>=sysdate-7 and bdate<=sysdate group by to_char(bdate,'yyyy,mm,dd')) t1 "
	                     + " INNER JOIN "
	                     + " (select count(bdate) as count, to_char(bdate,'yyyy,mm,dd') as bdate from board "
	                     + " where bdate>=sysdate-7 and bdate<=sysdate group by to_char(bdate,'yyyy,mm,dd')) t2 "
	                     + " on t1.bdate = t2.bdate "
	                     + " order by t1.bdate ";
	    try {
	      conn = DBConnection.getConnection();
	      pstmt = conn.prepareStatement(query);
	      rs = pstmt.executeQuery();
	      
	      while ( rs.next() ) {
	        String date = rs.getString("bdate");
	        int vcount = rs.getInt("vcount");
	        int bcount = rs.getInt("bcount");
	        ArrayList<Object> value = new ArrayList<Object>();
	        value.add(date);
	        value.add(vcount);
	        value.add(bcount);
	        list.add(value);
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
