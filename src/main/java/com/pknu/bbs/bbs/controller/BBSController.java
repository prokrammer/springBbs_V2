package com.pknu.bbs.bbs.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pknu.bbs.bbs.dto.BBSDto;
import com.pknu.bbs.bbs.join.BBSJoin;
import com.pknu.bbs.bbs.login.BBSLogin;
import com.pknu.bbs.bbs.reply.BBSReply;
import com.pknu.bbs.bbs.service.BBSService;
import com.pknu.bbs.bbs.write.BBSWrite;

@Controller
public class BBSController {
	
	@Autowired
	private BBSService bbsService;
	
	@Autowired
	private BBSLogin bbsLogin;
	
	@Autowired
	private BBSWrite bbswrite;
	
	@Autowired
	private BBSJoin bbsjoin;
	
	@Autowired
	private BBSReply bbsreply;
	
	@RequestMapping(value="/list.bbs")
		public String list(int pageNum, Model model) {
		bbsService.list(pageNum, model);
		model.addAttribute("pageNum",pageNum);
		return "list";
		
	}
	
	@RequestMapping(value="/joinForm.bbs")
	public String joinForm(String pageNum, Model model) {
		model.addAttribute("pageNum", pageNum);
		
		return "joinform";
	}
	
	@RequestMapping(value="/join.bbs")
	public String join(Model model,String id, String pass) {
		
		bbsjoin.join(model,id,pass);
		return "redirect:list.bbs?pageNum=1";
	}
	
	@RequestMapping(value="/write.bbs", method=RequestMethod.GET)
	public String writeForm(HttpSession session, HttpServletRequest req) {
		if((String)session.getAttribute("id")==null){
			req.setAttribute("pageNum", "1");
			return "login";
		}
		return "writeForm";
	}
//	value���� method�� ��û���� ���� ��� ���� �Ƚᵵ �ȴ�
	@RequestMapping(value="/write.bbs", method=RequestMethod.POST)
	public String write(/*HttpServletRequest req*/BBSDto article, HttpSession session) {
//		�Ű������� DTO�� �޾ƿ���  Spring������ ������ jsp�� �ִ� ���� DTO�� �ִ� �Ķ���Ϳ� ��ġ�ϴ� ���� ������ DTO �ȿ� ���� �־���ü �Ѿ�´�. 		
		System.out.println(article);
		article.setId((String)session.getAttribute("id"));
		try {
			bbswrite.write(article);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:list.bbs?pageNum=1";
	}
	@RequestMapping(value="/content.bbs")
	public String content(@RequestParam("pageNum") String pageNum, 
			@RequestParam String articleNum, Model model) {
		bbsService.content(pageNum, articleNum, model);
		return "content";
	}
	@RequestMapping(value="/delete.bbs")
	public String delete(String articleNum,String pageNum) {
			try {
				bbsService.delete(articleNum);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		
		return "redirect:list.bbs?pageNum="+pageNum;
	}
	
	@RequestMapping(value="/update.bbs", method=RequestMethod.GET)
	public String updateForm(String articleNum, String pageNum, Model model) {
		model.addAttribute("articleNum", articleNum);
		model.addAttribute("pageNum", pageNum);
		try {
			bbsService.updateForm(articleNum, model);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
		return "updateForm";
	}
	
	@RequestMapping(value="/update.bbs", method=RequestMethod.POST)
	public String update(Model model, String pageNum, String articleNum, String title, String content) {
		try {
			bbsService.update(model, articleNum, title, content);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:list.bbs?pageNum="+pageNum;
	}
	
	@RequestMapping(value="/replyForm.bbs")
	public String replyForm(BBSDto bd, Model model,/*HttpServletRequest req, */String pageNum/*, String depth, String pos, String groupId*/) {
		/*BBSDto bd = new BBSDto();
		bd.setDepth(Integer.parseInt(req.getParameter("depth")depth));
		bd.setPos(Integer.parseInt(req.getParameter("pos")pos));
		bd.setGroupId(Integer.parseInt(req.getParameter("groupId")groupId));
		*/
		/*req.setAttribute("replyDto", bd);
		req.setAttribute("pageNum", pageNum);*/
		/*System.out.println("���� : " + bd.getDepth());
		System.out.println(bd);*/
		model.addAttribute("replyDto", bd);
		model.addAttribute("pageNum", pageNum);
		return "replyForm"; 
	}
	
	@RequestMapping(value="/reply.bbs", method=RequestMethod.POST)
	public String reply(String pageNum, Model model, String depth, String pos, String groupId, String title, String content, HttpSession session) {
		String id = (String)session.getAttribute("id");
		try {
			bbsreply.reply(model, depth, pos, groupId, title, content, id);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:list.bbs?pageNum="+pageNum;
	}
	
	@RequestMapping(value="/login.bbs", method=RequestMethod.POST)
	public String login(HttpServletRequest req) {
			
		String view=null;
		try {
			view = bbsLogin.loginCheck(req);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return view;
	}
	
/*	@ResponseBody
	@RequestMapping(value="/commentRead.bbs")
	public String commentRead(HttpServletRequest req, HttpServletResponse resp) {
		try {
			bbscomment.read(req, resp);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping(value="/commentWrite.bbs")
	public String commentWrite(HttpServletRequest req, HttpServletResponse resp) {
		try {
			bbscomment.write(req, resp);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	*/
	@RequestMapping(value="/logout.bbs")
	public String logout(HttpSession session, String pageNum) {
		session.invalidate();
		return "redirect:list.bbs?pageNum="+pageNum;
	}

}
