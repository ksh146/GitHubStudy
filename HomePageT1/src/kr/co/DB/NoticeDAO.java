package kr.co.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import kr.co.DB.NoticeDTO;
import kr.co.DB.PageTO;

public class NoticeDAO {
	private DataSource dataFactory;
	
	public NoticeDAO() {
		
		try {
			Context ctx = new InitialContext();
			dataFactory = (DataSource) ctx.lookup("java:comp/env/jdbc/oracle11g");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private void closeAll(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
			}
			
			if (pstmt != null) {
				pstmt.close();
			}
			
			if (conn != null) {
				conn.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<NoticeDTO> list() {
		List<NoticeDTO> list = new ArrayList<NoticeDTO>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "SELECT num, author, title, "
				+ "to_char(writeday, 'yyyy/mm/dd') writeday, "
				+ "readcnt, reproot, repstep, repindent "
				+ "FROM NOTICE "
				+ "ORDER BY reproot desc, repstep asc";
		ResultSet rs = null;
		
		try {
			conn = dataFactory.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int num = rs.getInt("num");
				String author = rs.getString("author");
				String title = rs.getString("title");
				String writeday = rs.getString("writeday");
				int readcnt = rs.getInt("readcnt");
				int reproot = rs.getInt("reproot");
				int repstep = rs.getInt("repstep");
				int repindent = rs.getInt("repindent");
				
				NoticeDTO dto = new NoticeDTO(num, author, title, null, writeday, readcnt, reproot, repstep, repindent);
				list.add(dto);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		return list;
		
	}
	
	public void newPost(NoticeDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO NOTICE (num, author, title, content, reproot, repstep, repindent) VALUES(?,?,?,?,?,?,?)";
		
		try {
			conn = dataFactory.getConnection();
			int num = getNum(conn);
			
			pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, num);
	        pstmt.setString(2, dto.getAuthor());
	        pstmt.setString(3, dto.getTitle());
	        pstmt.setString(4, dto.getContent());
	        pstmt.setInt(5, num);
	        pstmt.setInt(6, 0);
	        pstmt.setInt(7, 0);
	        
	        pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(null, pstmt, conn);
		}
	}

	private int getNum(Connection conn) {
		int num = -1;
		PreparedStatement pstmt = null;
		String sql = "SELECT NVL2(MAX(num), MAX(num)+1, 1) FROM NOTICE";
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				num = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, null);
		}
		
		return num;
	}

	public PageTO page(int curpage) {
		PageTO to = new PageTO(curpage);
		List<NoticeDTO> list = new ArrayList<NoticeDTO>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "SELECT * FROM (SELECT ROWNUM rnum, num, author, title, writeday, readcnt, repindent from(SELECT * FROM NOTICE order by reproot desc, repstep asc)) WHERE rnum >= ? AND rnum <= ?";
		ResultSet rs = null;
		
		try {
			conn = dataFactory.getConnection();
			
			int amount = getAmount(conn);
			to.setAmount(amount);
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, to.getStartnum());
			pstmt.setInt(2, to.getEndnum());
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int num = rs.getInt("num");
				String author = rs.getString("author");
				String title = rs.getString("title");
				String writeday = rs.getString("writeday");
				int readcnt = rs.getInt("readcnt");
				int repindent = rs.getInt("repindent");
				
				NoticeDTO dto = new NoticeDTO(num, author, title, null, writeday, readcnt, -1, -1, repindent);
				
				list.add(dto);
			}
			to.setList(list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		
		return to;
	}

	private int getAmount(Connection conn) {
		int amount = 0;
		
		PreparedStatement pstmt = null;
		String sql = "SELECT COUNT(num) FROM NOTICE";
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				amount = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, null);
		}
		
		return amount;
	}

	public NoticeDTO read(int num) {
		NoticeDTO dto = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "SELECT * FROM NOTICE WHERE num = ?";
		ResultSet rs = null;
		
		boolean isok = false;
		
		try {
			conn = dataFactory.getConnection();
			conn.setAutoCommit(false);
			
			increaseReadcnt(conn, num);
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
		        String author = rs.getString("author");
		        String title = rs.getString("title");
		        String content = rs.getString("content");
		        String writeday = rs.getString("writeday");
		        int readcnt = rs.getInt("readcnt");
		            
		        dto = new NoticeDTO(num, author, title, content, writeday, readcnt, -1, -1, -1);
			}
			
			isok = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (isok) {
					conn.commit();
				}else {
					conn.rollback();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeAll(rs, pstmt, conn);
		}
			
		return dto;
	}

	public NoticeDTO updateui(int num) {
		NoticeDTO dto = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "SELECT * FROM NOTICE WHERE num = ?";
		ResultSet rs = null;
		
		try {
			conn = dataFactory.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String author = rs.getString("author");
		        String title = rs.getString("title");
		        String content = rs.getString("content");
		        String writeday = rs.getString("writeday");
		        int readcnt = rs.getInt("readcnt");
		        int reproot = rs.getInt("reproot");
		        int repstep = rs.getInt("repstep");
		        int repindent = rs.getInt("repindent");
		        
		        dto = new NoticeDTO(num, author, title, content, writeday, readcnt, reproot, repstep, repindent);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		
		return dto;
	}

	public void update(NoticeDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "UPDATE NOTICE SET author = ?, title = ?, content = ?, writeday=sysdate WHERE num = ?";
		
		try {
			conn = dataFactory.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getAuthor());
			pstmt.setString(2, dto.getTitle());		
			pstmt.setString(3, dto.getContent());
			pstmt.setInt(4, dto.getNum());
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(null, pstmt, conn);
		}
	}

	public void reply(int orinum, NoticeDTO repdto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO NOTICE" + "(num, author, title, content, reproot, repstep, repindent) " + "VALUES (?,?,?,?,?,?,?)";
		boolean isok = false;
		
		try {
			conn = dataFactory.getConnection();
			conn.setAutoCommit(false);
			
			int num = getNum(conn);
			
			NoticeDTO oridto = updateui(orinum);
			increaseRepstepOtherReply(conn, oridto);
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			pstmt.setString(2, repdto.getAuthor());
			pstmt.setString(3, repdto.getTitle());
			pstmt.setString(4, repdto.getContent());
			pstmt.setInt(5, oridto.getReproot());
			pstmt.setInt(6, oridto.getRepstep()+1);
			pstmt.setInt(7, oridto.getRepindent()+1);
			
			pstmt.executeUpdate();
			
			isok = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (isok) {
					conn.commit();
				}else {
					conn.rollback();
				}				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeAll(null, pstmt, conn);
		}
		
	}

	private void increaseRepstepOtherReply(Connection conn, NoticeDTO oridto) {
		PreparedStatement pstmt = null;
		String sql = "UPDATE NOTICE SET repstep = repstep + 1 " + "WHERE reproot = ? AND repstep > ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, oridto.getReproot());
			pstmt.setInt(2, oridto.getRepstep());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(null, pstmt, null);
		}
		
	}
	
	public void increaseReadcnt(Connection conn, int num) {
		PreparedStatement pstmt = null;
		String sql = "UPDATE NOTICE SET readcnt = readcnt + 1 WHERE num = ?";
		  
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			  
		} catch (Exception e) {
			e.printStackTrace();	
		} finally {
			closeAll(null, pstmt, null);
			
		}
		
	}

	public List<NoticeDTO> search(String so, String sk) {
		List<NoticeDTO> list = new ArrayList<NoticeDTO>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "SELECT * FROM NOTICE WHERE UPPER("+so+") LIKE UPPER(?) ORDER BY reproot DESC, repstep ASC";
		
		ResultSet rs = null;
		
		try {
			conn = dataFactory.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+sk+"%");
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int num = rs.getInt("num");
				String author = rs.getString("author");
				String title = rs.getString("title");
				String writeday = rs.getString("writeday");
				int readcnt = rs.getInt("readcnt");
				int repindent = rs.getInt("repindent");
				
				NoticeDTO dto = new NoticeDTO(num, author, title, null, writeday, readcnt, -1, -1, repindent);
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		
		return list;
	}

	public void delete(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM NOTICE WHERE num = ?";
		
		try {
			conn = dataFactory.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(null, pstmt, conn);
		}
		
	}

}