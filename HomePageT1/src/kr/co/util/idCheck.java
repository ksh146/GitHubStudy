package kr.co.util;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.DB.MemberDAO;

/**
 * Servlet implementation class idCheck
 */
@WebServlet("/idcheck")
public class idCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public idCheck() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		
		MemberDAO dao = new MemberDAO();
		String rid = dao.idCheck(id);
		String idCheckMsg = null;
		String isok = "사용 가능합니다.";
		if(rid == null) {
			idCheckMsg = isok;
		}else {
			idCheckMsg = "이미 존재하는 아이디입니다..";
		}
		request.setAttribute("nid", id);
		request.setAttribute("isok", isok);
		request.setAttribute("idCheckMsg", idCheckMsg);
		
		request.getRequestDispatcher("idCheckResult.jsp")
	      .forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
