package com.greedy.section02.service.model.service;

import java.sql.Connection;

import com.greedy.section02.service.model.dao.MenuDAO;
import com.greedy.section02.service.model.dto.CategoryDTO;
import com.greedy.section02.service.model.dto.MenuDTO;

import static com.greedy.common.JDBCTemplate.*;


public class MenuService {


	public void registNewMenu() {
		

		Connection con = getConnection();
		

		MenuDAO menuDAO = new MenuDAO();
		

		CategoryDTO newCategory = new CategoryDTO();
		newCategory.setName("기타");
		newCategory.setRefCategoryCode(null);
		
		int result1 = menuDAO.insertNewCategory(con, newCategory);
		

		int newCategoryCode = menuDAO.selectLastCategoryCode(con);
		

		MenuDTO newMenu = new MenuDTO();
		newMenu.setName("메롱메롱스튜");
		newMenu.setPrice(40000);
		newMenu.setCategoryCode(newCategoryCode);
		newMenu.setOrderableStatus("Y");
		
		int result2 = menuDAO.insertNewMenu(con, newMenu);
		

		if(result1 > 0 && result2 > 0) {
			System.out.println("신규 카테고리와 메뉴를 추가하였습니다.");
			commit(con);
		} else {
			System.out.println("신규 카테고리와 메뉴를 추가하지 못했습니다.");
			rollback(con);
		}
		

		close(con);
		
	}

}

