package com.bytrustu.comment.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bytrustu.comment.model.CommentDAO;
import com.bytrustu.comment.model.CommentDTO;
import com.bytrustu.common.action.Action;
import com.bytrustu.common.action.ActionForward;

public class CommentWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		CommentDAO dao = CommentDAO.getInstance();
		CommentDTO dto = new CommentDTO();
		
		int bNum = Integer.parseInt(request.getParameter("bNum"));
		String cId = (String)request.getSession().getAttribute("userID");
		String cContent = (String)request.getParameter("cContent");

		dto.setbNum(bNum);
		dto.setcId(cId);
		dto.setcContent(cContent);
		/*System.out.println(">>>>>>>>>commentWriteAction접근");*/
		
		if (dao.commentWrite(dto) ==1){
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter script = response.getWriter();
			script.println("1");
			script.close();
			/*System.out.println(">>>>>>>>>comment글등록 완료");*/

		} else {
		/*	System.out.println(">>>>>>>>>코멘트등록실패 DB오류");*/
		}
		return null;
	}


}
