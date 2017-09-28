package com.pknu.bbs.bbs.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;

import com.pknu.bbs.bbs.dto.BBSDto;

public interface BBSDao {

	int getTotalCount();

	List<BBSDto> getArticleList(HashMap<Object, Object> paramMap) throws SQLException;
	
	BBSDto getContent(String articleNum) throws SQLException;
	
	void write(BBSDto article) throws ServletException, IOException;
	
	void join(HashMap<Object,Object> paramMap);
	
	
	
	void delete(String articleNum) throws SQLException;
	
	BBSDto getUpdateArticle(String articleNum) throws SQLException;
	
	void updateArticle(HashMap<Object,Object> paramMap) throws SQLException;
	
	String loginCheck(String id) throws SQLException;


	String joinCheck(String id);

	void posUpdate(HashMap<Object, Object> paramMap);

	void reply(BBSDto article);
	
	int commentsCount(int articleNum);

}
