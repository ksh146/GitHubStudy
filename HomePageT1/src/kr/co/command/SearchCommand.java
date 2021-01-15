package kr.co.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.DB.NoticeDAO;
import kr.co.DB.NoticeDTO;

public class SearchCommand implements Command {

	@Override
	public CommandAction execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String so = request.getParameter("searchoption");
		String sk = request.getParameter("searchkeyword");
		
		List<NoticeDTO> list = new NoticeDAO().search(so, sk);
		request.setAttribute("list", list);
		
		return new CommandAction(false, "search.jsp");
	}

}
