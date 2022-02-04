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
		newCategory.setName("��Ÿ");
		newCategory.setRefCategoryCode(null);
		
		int result1 = menuDAO.insertNewCategory(con, newCategory);
		

		int newCategoryCode = menuDAO.selectLastCategoryCode(con);
		

		MenuDTO newMenu = new MenuDTO();
		newMenu.setName("�޷ո޷ս�Ʃ");
		newMenu.setPrice(40000);
		newMenu.setCategoryCode(newCategoryCode);
		newMenu.setOrderableStatus("Y");
		
		int result2 = menuDAO.insertNewMenu(con, newMenu);
		

		if(result1 > 0 && result2 > 0) {
			System.out.println("�ű� ī�װ��� �޴��� �߰��Ͽ����ϴ�.");
			commit(con);
		} else {
			System.out.println("�ű� ī�װ��� �޴��� �߰����� ���߽��ϴ�.");
			rollback(con);
		}
		

		close(con);
		
	}

}

