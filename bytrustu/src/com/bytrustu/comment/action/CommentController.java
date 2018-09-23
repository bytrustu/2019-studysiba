package com.bytrustu.comment.action;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bytrustu.common.action.Action;
import com.bytrustu.common.action.ActionForward;

/**
 * Servlet implementation class CommentController
 */
@WebServlet("*.co")
public class CommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HashMap<String, Action> commandMap;
       

	public CommentController() {
        super();
        // TODO Auto-generated constructor stub
    }
	
	public void init(ServletConfig config) throws ServletException {
		loadProperties("com.bytrustu.comment.properties.CommentCommand");
	}
	
	private void loadProperties(String filePath){
		commandMap = new HashMap<String, Action>();
		ResourceBundle rb = ResourceBundle.getBundle(filePath);
		
		Enumeration<String> actionEnum = rb.getKeys();
		
		while ( actionEnum.hasMoreElements() ){
			
			String command = actionEnum.nextElement();
			String className = rb.getString(command);
			
			try {
				Class<?> actionClass = Class.forName(className);
				Action actionInstance = (Action) actionClass.newInstance();
				
				commandMap.put(command, actionInstance);
				
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*System.out.println("doGet");*/
		actionCo(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	/*	System.out.println("doPost");*/
		actionCo(request, response);
	}
	
	private void actionCo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	/*	System.out.println("actionCo");*/
		
		request.setCharacterEncoding("UTF-8");
		
		String uri = request.getRequestURI();
		int conPath = uri.lastIndexOf("/") +1;
		String command = uri.substring(conPath);
		
		/*System.out.println("command >>>>>>>>>>" + command);*/
		
		ActionForward forward = null;
		Action action = null;
		
		try {
			
			action = commandMap.get(command);
			
			if(action == null){
				System.out.println("command : " + command + "는 잘못된 명령어 입니다.");
				return;
			}
			
			forward = action.execute(request, response);
			
			if (forward != null) {
				if (forward.isRedirect()) {
					response.sendRedirect(forward.getNextPath());
				} else {
					RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getNextPath());
					dispatcher.forward(request, response);
				}
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

}
