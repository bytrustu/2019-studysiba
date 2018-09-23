package com.bytrustu.member.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.bytrustu.common.util.DBConnection;

public class MemberDAO {

    private static MemberDAO instance = new MemberDAO();

    private MemberDAO() {
    }

    public static MemberDAO getInstance() { // 싱글톤패턴 객체화
        return instance;
    }

   /*
    *   회원정보 조회 
    */

    public int memberIDCheck(String userID) { // 중복체크 처리
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int result = 0;
        String query = " select userID from member where userID = ? ";
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = 1; // 이미 아이디 있음
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public int memberIDPWChecker(String userID, String userPassword) { // 아이디 비번 체크
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int result = 0;
        String query = " select userPassword from member where userID=? ";
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userID);
            pstmt.executeUpdate();
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getString("userPassword").equals(userPassword)) {
                    result = 1; // 아이디와 비밀번호가 동일
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
                if (rs != null)
                    rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result; // 아이디가 없음
    }

    public MemberDTO memberInfo(String strID) { // 회원정보 출력 처리
        MemberDTO dto = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = " select * from member where userID = ? ";
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, strID);
            pstmt.executeUpdate();
            rs = pstmt.executeQuery();
            if (rs.next()) {
                String userID = rs.getString(1);
                String userPassword = rs.getString(2);
                String userName = rs.getString(3);
                String userGender = rs.getString(4);
                String userEmail = rs.getString(5);
                dto = new MemberDTO(userID, userPassword, userName, userGender, userEmail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dto;
    }

    public ArrayList<MemberDTO> memberAllList() { // 모든 회원정보 출력 처리

        ArrayList<MemberDTO> dtos = new ArrayList<MemberDTO>();
        MemberDTO dto = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = " select * from member ";
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                dto = new MemberDTO();
                dto.setUserID(rs.getString(1));
                dto.setUserPassword(rs.getString(2));
                dto.setUserName(rs.getString(3));
                dto.setUserGender(rs.getString(4));
                dto.setUserEmail(rs.getString(5));
                dtos.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dtos;
    }


    /*
     *   회원정보 Insert / Update / Delete
     */
    
    public int memberJoin(MemberDTO dto) { // INSERT 회원가입처리
        Connection conn = null;
        PreparedStatement pstmt = null;

        int result = 0;
        String query = " insert into member values(?,?,?,?,?) ";
        String queryProfile = " insert into profile values(?, null) ";
        String queryLastDate = " insert into lastdate values(?, sysdate, sysdate) ";
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, dto.getUserID());
            pstmt.setString(2, dto.getUserPassword());
            pstmt.setString(3, dto.getUserName());
            pstmt.setString(4, dto.getUserGender());
            pstmt.setString(5, dto.getUserEmail());
            result = pstmt.executeUpdate(); // 가입성공

            if (result == 1) { 
                pstmt.close(); // 프로필 사진 테이블 아이디 생성
                pstmt = conn.prepareStatement(queryProfile);
                pstmt.setString(1, dto.getUserID());
                pstmt.executeUpdate();
                
                pstmt.close();  // 접속로그 생성
                pstmt  = conn.prepareStatement(queryLastDate);
                pstmt.setString(1, dto.getUserID());
                pstmt.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result; // 데이터베이스 오류
    }

    public int memberDelete(String userID) { // DELETE 회원탈퇴 처리
        Connection conn = null;
        PreparedStatement pstmt = null;

        int result = 0;
        String memberQuery = " delete from member where userID = ? ";
        String lastDateQuery = " delete from lastDate where lid = ? ";
        String profileQuery = " delete from profile where pid = ? ";
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(memberQuery);
            pstmt.setString(1, userID);
            pstmt.executeUpdate(); // 삭제성공
            
            pstmt.close();
            pstmt = conn.prepareStatement(lastDateQuery);
            pstmt.setString(1, userID);
            pstmt.executeUpdate();
            
            pstmt.close();
            pstmt = conn.prepareStatement(profileQuery);
            pstmt.setString(1, userID);
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result; // 데이터베이스 오류
    }

    public int memberUpdate(String userID, String userName, String userGender, String userEmail) {
        // UPDATE 회원정보 수정 처리
        Connection conn = null;
        PreparedStatement pstmt = null;

        int result = 0;
        String query = " update member set userName=?, userGender=?, userEmail=? where userID=? ";
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userName);
            pstmt.setString(2, userGender);
            pstmt.setString(3, userEmail);
            pstmt.setString(4, userID);
            result = pstmt.executeUpdate(); // 수정 성공
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result; // 데이터베이스 오류
    }

    /*
     * 사용자별 프로필 이미지 파일명
     */
    public String getFile(String userID) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String fileName = null;
        String query = " select * from profile where pid = ? ";
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                fileName = rs.getString("pfile");
               /* System.out.println(">>>>>>>>>>"+fileName);*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

    /*
     * 프로필 이미지 파일 등록
     */
    public int updateFile(String userID, String fileName) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int result = 0;
        String query = " update profile set pfile=? where pid=? ";
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, fileName);
            pstmt.setString(2, userID);
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    
    
    // 실시간 접속자 ID 가져오기
    public ArrayList<String> getConnectedUser() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        ArrayList<String> list = new ArrayList<String>();
        
        String query = " select lid FROM lastdate WHERE  ldate between sysdate-1/24/60 AND sysdate ";
        
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            
            while( rs.next() ) {
               // System.out.println(rs.getString("lid"));
                list.add(rs.getString("lid"));
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
    
 // 실시간 접속자 수 가져오기
    public int getConnectedUserCount() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        int result=0;
        
        String query = " select count(*) FROM lastdate WHERE  ldate between sysdate-1/24/60 AND sysdate ";
        
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            
            while( rs.next() ) {
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
    
    
    // 접속 시간 로그
    public String getConnectDate(String userID) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String result = "";
        
        String query = " select lconnect from lastdate where lid = ? ";
        
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();
            
            while( rs.next() ) {
                result = rs.getString(1);
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
    
    
    // 실시간 접속 시간 갱신
    public int updateLastDate(String userID) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        int result = 0;
        
        String query = " update lastdate set ldate = sysdate where lid = ? ";
        
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userID);
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
    
 // 접속 로그 시간 갱신
    public int updateLastConnected(String userID) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        int result = 0;
        
        String query = " update lastdate set lconnect = sysdate where lid = ? ";
        
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userID);
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
    
    // 접속 로그 시간 갱신
    public int updateLogoutConnected(String userID) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        int result = 0;
        
        String query = " update lastdate set ldate = sysdate-1/24/30 where lid = ? ";
        
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userID);
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
