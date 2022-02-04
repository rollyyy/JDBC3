package com.greedy.section01.transaction;

import static com.greedy.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class Application2 {

	public static void main(String[] args) {
		
		Connection con = getConnection();
		
		try {
			System.out.println("autoCommit�� ���� ���� �� : " + con.getAutoCommit());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		
		int result1 = 0;
		int result2 = 0;
		
		Properties prop = new Properties();
		
		try {
			prop.loadFromXML(new FileInputStream("mapper/menu-query.xml"));
			
			String query1 = prop.getProperty("insertCategory");
			String query2 = prop.getProperty("insertMenu");
			
			pstmt1 = con.prepareStatement(query1);
			pstmt1.setString(1, "��Ÿ");
			pstmt1.setInt(2, 1);
			
			result1 = pstmt1.executeUpdate();
			
			System.out.println("result1 : " + result1);
			
			pstmt2 = con.prepareStatement(query2);
			pstmt2.setString(1, "��������");
			pstmt2.setInt(2, 50000);
			/* TBL_CATEGORY �� �������� �ʴ� CATEGORY_CODE�� TBL_MENU ���̺��� CATEGORY_CODE ������ �����Ϸ��� �ϸ�
			 * �θ� Ű�� ã�� ���ϴ� �ܷ�Ű �������� ���� ������ �߻��Ѵ�.
			 * */
			pstmt2.setInt(3, 0);
			pstmt2.setString(4, "Y");
			
			result2 = pstmt2.executeUpdate();
			
			System.out.println("result2 : " + result2);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt1);
			close(pstmt2);
			
			/* Ʈ�����(������ ��� ���� ����) ������ ���� 2���� insert�� ��� �� �����ߴ��� �Ǵ��Ͽ�
			 * �� �������� ��� commit, �� �� �ϳ��� �� �������� �ʾ��� ��� rollback�� �����Ѵ�.
			 * */
			if(result1 > 0 && result2 > 0) {
				System.out.println("�ű� ī�װ��� �޴� ��� ����!");
				commit(con);
			} else {
				System.out.println("�ű� ī�װ��� �޴� ��� ����!");
				rollback(con);
			}
			
			close(con);
		}
	
		
	}

}
