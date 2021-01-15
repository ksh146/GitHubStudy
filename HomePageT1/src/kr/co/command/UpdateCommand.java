package kr.co.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.DB.NoticeDAO;
import kr.co.DB.NoticeDTO;

public class UpdateCommand implements Command {

	@Override
	public CommandAction execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String snum = request.getParameter("num");
		int num = Integer.parseInt(snum);
		
		String author = request.getParameter("author");
		String title = request.getParameter("title");	
		String content = request.getParameter("content");
		
		NoticeDTO dto = new NoticeDTO(num, author, title, content, null, -1, -1, -1, -1);
		
		new NoticeDAO().update(dto);
		
		return new CommandAction(true, "read.do?num="+num);
	}

}
