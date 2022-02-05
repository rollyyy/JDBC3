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
			System.out.println("autoCommit의 현재 설정 값 : " + con.getAutoCommit());
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
			pstmt1.setString(1, "기타");
			pstmt1.setInt(2, 1);
			
			result1 = pstmt1.executeUpdate();
			
			System.out.println("result1 : " + result1);
			
			pstmt2 = con.prepareStatement(query2);
			pstmt2.setString(1, "정어리비빔밥");
			pstmt2.setInt(2, 50000);
			/* TBL_CATEGORY 에 존재하지 않는 CATEGORY_CODE를 TBL_MENU 테이블의 CATEGORY_CODE 값으로 삽입하려고 하면
			 * 부모 키를 찾지 못하는 외래키 제약조건 위반 오류가 발생한다.
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
			
			/* 트랜잭션(논리적인 기능 수행 단위) 관리를 위해 2개의 insert가 모두 잘 동작했는지 판단하여
			 * 잘 동작했을 경우 commit, 둘 중 하나라도 잘 동작하지 않았을 경우 rollback을 수행한다.
			 * */
			if(result1 > 0 && result2 > 0) {
				System.out.println("신규 카테고리와 메뉴 등록 성공!");
				commit(con);
			} else {
				System.out.println("신규 카테고리와 메뉴 등록 실패!");
				rollback(con);
			}
			
			close(con);
		}
	
	}

}
