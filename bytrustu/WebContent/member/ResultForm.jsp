<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<!-- 메세지 형식을 세션으로 바꾸어서 더이상 사용하지 않는 페이지 -->







	<%
		// 회원 가입시 조건처리 메세지
		String msgJ = (String)session.getAttribute("msgJ");
		PrintWriter script = response.getWriter();
		if(msgJ != null) {
			if(msgJ.equals("1")){
				script.println("<script>");
				script.println("alert('회원가입을 축하합니다.');");
				script.println("location.href='MainForm.do';");
				script.println("</script>");
				msgJ = null;
			} else if(msgJ.equals("0")){
				script.println("<script>");
				script.println("alert('비정상적인 오류 입니다.');");
				script.println("history.back();");
				script.println("</script>");
			} else if(msgJ.equals("-1")){
				script.println("<script>");
				script.println("alert('이미 등록 된 아이디 입니다.');");
				script.println("history.back();");
				script.println("</script>");
			} else if(msgJ.equals("-2")){
				script.println("<script>");
				script.println("alert('정보를 모두 입력 해주세요.');");
				script.println("history.back();");
				script.println("</script>");
			}
			session.removeAttribute("msgJ");
		}
		
		// 회원 로그인시 조건처리 메세지
		String msgL = (String)session.getAttribute("msgL");
		out.println(">>>>>>>>"+msgL);
		if(msgL != null) {
			if(msgL.equals("1")){
				script.println("<script>");
				script.println("alert('로그인 되었습니다.');");
				script.println("location.href='MainForm.do';");
				script.println("</script>");
			} else if(msgL.equals("0")){
				script.println("<script>");
				script.println("alert('아이디 혹은 비밀번호가 틀렸습니다.');");
				script.println("history.back();");
				script.println("</script>");
			}
			session.removeAttribute("msgL");
		}

		// 회원  로그아웃, 탈퇴 , 정보수정 조건처리 메세지
		String msgO = (String)session.getAttribute("msgO");
		if(msgO != null) {
			if(msgO.equals("1")){
				script.println("<script>");
				script.println("alert('정상적으로 로그아웃 되었습니다.');;");
				script.println("location.href='MainForm.do';");
				script.println("</script>");
			} else if(msgO.equals("2")){
				script.println("<script>");
				script.println("alert('정상적으로 회원탈퇴 되었습니다.');;");
				script.println("location.href='MainForm.do';");
				script.println("</script>");
			} else if(msgO.equals("3")){
				script.println("<script>");
				script.println("alert('정상적으로 회원수정가 되었습니다.');;");
				script.println("location.href='MainForm.do';");
				script.println("</script>");
			} else if(msgO.equals("0")){
				script.println("<script>");
				script.println("alert('비정상적인 오류 입니다.');;");
				script.println("history.back();");
				script.println("</script>");
			} else if(msgO.equals("-1")){
				script.println("<script>");
				script.println("alert('비밀번호가 틀렸습니다.');;");
				script.println("history.back();");
				script.println("</script>");
			} else if(msgO.equals("-2")){
				script.println("<script>");
				script.println("alert('회원정보를 모두 입력해주세요.');;");
				script.println("history.back();");
				script.println("</script>");
			}
			session.removeAttribute("msgO");
		}
				
	%>
	
