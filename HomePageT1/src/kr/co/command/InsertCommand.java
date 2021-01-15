package kr.co.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.DB.MemberDAO;
import kr.co.DB.MemberDTO;

public class InsertCommand implements Command {

	@Override
	public CommandAction execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		String id = request.getParameter("id");
		String password = request.getParameter("pw1");
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		String email = request.getParameter("email");
		String phoneNumber = request.getParameter("phoneNumber");
		String address = request.getParameter("address");
		
		MemberDTO dto = new MemberDTO(id, password, name, email, phoneNumber, address, gender, null , "user");
		MemberDAO dao = new MemberDAO();
		dao.insertMember(dto);
		
		System.out.println(dto);
		
		return new CommandAction(true, "login.jsp");
	}

}
