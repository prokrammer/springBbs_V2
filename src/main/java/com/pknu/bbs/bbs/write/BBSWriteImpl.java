package com.pknu.bbs.bbs.write;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pknu.bbs.bbs.dao.BBSDao;
import com.pknu.bbs.bbs.dto.BBSDto;
@Service
public class BBSWriteImpl implements BBSWrite {
	
	@Autowired
	BBSDao bbsdao;
	
//	@Resource(name="saveDir") //type�� String�� ���� DI���ش� ���߿��� id->name->class ������ "pageSize"�� �༮�� DI�Ѵ�.
//	String saveDir;
//	
	public String write(BBSDto article) throws ServletException, IOException {
		System.out.println(article);
/*		req.setCharacterEncoding("UTF-8");
		
		
		BBSDto article = new BBSDto();
		
		article.setTitle(readParameterValue(req.getPart("title"))req.getParameter("title"));
		article.setContent(readParameterValue(req.getPart("content"))req.getParameter("content"));
		article.setId((String)req.getSession().getAttribute("id"));*/
//		article.setFname(req.getParameter("fname"));
		
		//fileInputStream�� fileReader���� ���� ��� -> text������ �ƴ� 2�� ������ ���
		/*if(req.getPart("fname").getSize()!=0) {
			Part filePart = req.getPart("fname");
			String originFname = getFileName(filePart);
			article.setFname(originFname);
			
			File file = new File(saveDir + originFname);
			InputStream is = filePart.getInputStream();
			FileOutputStream os = new FileOutputStream(file);
			
			int temp = -1;
			while((temp = is.read()) != -1) {
				os.write(temp);
			}
			is.close();
			os.close();
		}*/
		
		
		try {
			bbsdao.write(article);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/list.bbs?pageNum=1";
	}
	
	/*private String getFileName(Part filePart) {
		String originFname = null;
		for(String cd:filePart.getHeader("content-disposition").split(";")) {
			if(cd.trim().startsWith("filename")) {
				originFname = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return originFname;
	}

	private String readParameterValue(Part part) throws IOException{
		InputStreamReader reader = new InputStreamReader(part.getInputStream(),"utf-8");
		int temp = -1;
		StringBuffer builder = new StringBuffer();
		while((temp = reader.read())!=-1) {
			//char�� ����ȯ�ؾ� ���ڷε�
			builder.append((char)temp);
		}
		return builder.toString();
	}*/
	
}
