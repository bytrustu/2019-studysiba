package com.bytrustu.study.action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bytrustu.study.model.MapDAO;
import com.bytrustu.study.model.StudyDAO;

/**
 * Servlet implementation class MapData
 */
@WebServlet("/StudyUpdateList")
public class StudyUpdateList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public StudyUpdateList() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		int gNum = Integer.parseInt(request.getParameter("gNum"));
		StudyDAO studyDAO = StudyDAO.getInstance();
		int result = studyDAO.StudyUpdateList(gNum);
		
		if ( result == 1 ) {
		  response.getWriter().write("1");
		} else {
      response.getWriter().write("0");
		}
	}

}
