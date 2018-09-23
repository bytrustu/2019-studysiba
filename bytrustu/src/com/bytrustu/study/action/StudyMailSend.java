package com.bytrustu.study.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class StudyMailSend
 */
@WebServlet("/StudyMailSend")
public class StudyMailSend extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudyMailSend() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
		PrintWriter out = response.getWriter();
		
		request.setCharacterEncoding("UTF-8");
		String sender = "dddddd";
		String receiver = "bytrustu@gmail.com";
		String subject = request.getParameter("reportSubject");
		String content = request.getParameter("content");
		response.setContentType("text/html; charset=UTF-8");
		
		try{
		  Properties properties = System.getProperties();
		  properties.put("mail.smtp.starttls.enable", "true");
		  properties.put("mail.smtp.host", "smtp.gmail.com");
		  properties.put("mail.smtp.auth", "true");
		  properties.put("mail.smtp.port", "587");
		  Authenticator auth = new GoogleAuthentication();
		  Session s = Session.getDefaultInstance(properties, auth);
		  Message message = new MimeMessage(s);
		 Address sender_address = new InternetAddress(sender);
		 Address receiver_address = new InternetAddress(receiver);
		 message.setFrom(sender_address);
		 message.addRecipient(Message.RecipientType.TO, receiver_address);
		 message.setSubject(subject);
		 message.setContent(content, "text/html; charset=UTF-8");
		 message.setSentDate(new java.util.Date());
		 Transport.send(message);
		 out.println("<h3>메일이 정상적으로 전송 되었습니다.</h3>");
		} catch ( Exception e ) {
		  out.println("SMTP 서버가 잘못 설정되었거나, 서비스에 문제가 있습니다.");
		  e.printStackTrace();
		}
		
		
	}

}
