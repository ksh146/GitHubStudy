package kr.co.fileupdownload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileDownload {
	public static void download(HttpServletRequest request, HttpServletResponse response, String paramname, String uploadname) {
		
		String filename = request.getParameter(paramname);
		String uploadpath = request.getServletContext().getRealPath(uploadname);
		String filepath = uploadpath + File.separator + filename;
		
		InputStream in = null;
		try {
			in = new FileInputStream(filepath);
			
			String smt = request.getServletContext().getMimeType(filepath);
			if (smt == null) {
				smt = "application/octet-stream";
			}
			
			response.setContentType(smt);
			String encoding = new String(filename.getBytes("utf-8"), "8859_1");
			
			response.setHeader("Content-Disposition", "attachment;filename="+encoding);
			
			byte[] arr = new byte[1024];
			
			ServletOutputStream out = response.getOutputStream();
			
			int length;
			
			while (true) {
				length = in.read(arr, 0, arr.length);
				
				if (length == -1) {
					break;
				}
				
				out.write(arr, 0, length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
	
	}

}
