package com.bytrustu.study.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bytrustu.study.model.MapDAO;

@WebServlet("/MapGetData")
public class MapGetData extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public MapGetData() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doget");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	  MapDAO dao = MapDAO.getInstance();
	  ArrayList<String> mapData = new ArrayList<String>();
	  String userID = (String)request.getSession().getAttribute("userID");
	  mapData = dao.getMapData(userID);
	  
		String[] data = new String[2];
		data[0] = mapData.get(0);
		data[1] = mapData.get(1);
		
		
		response.getWriter().write(data[0].toString()+","+data[1].toString());
	}

}
