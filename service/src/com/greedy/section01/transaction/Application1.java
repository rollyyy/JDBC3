package com.greedy.section01.transaction;

import static com.greedy.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class Application1 {

	public static void main(String[] args) {
		
		Connection con = getConnection();
		
		
		try {
			System.out.println("autoCommit�� ���� ���� �� : " + con.getAutoCommit());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		Properties prop = new Properties();
		
		try {
			prop.loadFromXML(new FileInputStream("mapper/menu-query.xml"));
			
			String query = prop.getProperty("insertMenu");
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, "��������");
			pstmt.setInt(2, 50000);
			pstmt.setInt(3, 4);
			pstmt.setString(4, "Y");
			
			result = pstmt.executeUpdate();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(con);
		}
		
		if(result > 0) {
			System.out.println("�޴� ��� ����!");
		} else {
			System.out.println("�޴� ��� ����!");
		}
		
	}

}	