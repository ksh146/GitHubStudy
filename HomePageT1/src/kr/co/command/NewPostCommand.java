package kr.co.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.DB.NoticeDAO;
import kr.co.DB.NoticeDTO;
import kr.co.command.CommandAction;

public class NewPostCommand implements Command {

	@Override
	public CommandAction execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String author = request.getParameter("author");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		NoticeDTO dto = new NoticeDTO(-1, author, title, content, null, 0, 0, 0, 0);
		NoticeDAO dao = new NoticeDAO();
		dao.newPost(dto);
		
		return new CommandAction(true, "list.do");
	}

}
