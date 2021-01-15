package kr.co.fileupdownload;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/upload")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = "";
		String orgfilename = "";
		String filename = "";
		
		String uploadpath = request.getServletContext().getRealPath("upload");
		System.out.println(uploadpath);
		
		MultipartRequest multi = new MultipartRequest(request, uploadpath, 1024*1024*100, "utf-8", new DefaultFileRenamePolicy());
		
		id = multi.getParameter("id");
		System.out.println(id);
		
		orgfilename = multi.getOriginalFileName("file");
		System.out.println(orgfilename);
		
		filename = multi.getFilesystemName("file");
		System.out.println(filename);
		
		request.setAttribute("id", id);
		request.setAttribute("orgfilename", orgfilename);
		request.setAttribute("filename", filename);
		
		request.getRequestDispatcher("check.jsp").forward(request, response);
		
	}

}
