package com.bytrustu.comment.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bytrustu.comment.model.CommentDAO;
import com.bytrustu.common.action.Action;
import com.bytrustu.common.action.ActionForward;

public class CommentDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		CommentDAO dao = CommentDAO.getInstance();
		request.setCharacterEncoding("UTF-8");
		
		int cNum = Integer.parseInt(request.getParameter("cNum"));
		String userID = (String)request.getSession().getAttribute("userID");

		/*System.out.println(">>>>>>>>>commentDeleteAction접근");*/
		
		if (dao.commentDelete(cNum, userID) ==1){
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter script = response.getWriter();
			script.println("1");
			script.close();
	/*		System.out.println(">>>>>>>>>comment글삭제 완료");*/
		} else {
/*			System.out.println(">>>>>>>>>코멘트등록실패 DB오류");*/
		}
		return null;
	}


}
