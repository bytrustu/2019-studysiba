package com.bytrustu.study.action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bytrustu.study.model.MapDAO;

/**
 * Servlet implementation class MapData
 */
@WebServlet("/MapData")
public class MapData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MapData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		String str = request.getParameter("mapvalue").substring(1, request.getParameter("mapvalue").length()-1);
		String[] array = str.split("%2C%20");
		String lat = array[0].toString();
		String lng = array[1].toString();
		String userID = (String)request.getSession().getAttribute("userID");
		MapDAO dao = MapDAO.getInstance();
		int result = dao.insertMapData(userID, lat, lng);
		
		
		
		
		
	}

}
