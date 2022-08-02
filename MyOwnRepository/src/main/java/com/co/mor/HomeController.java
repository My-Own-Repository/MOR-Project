package com.co.mor;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.co.dto.MemberVO;
import com.co.service.MemberService;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
/**
 * Handles requests for the application home page.
 */

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	public int integration_id = -1;
	
    @Inject
    private MemberService service;
    
   
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main(Locale locale, Model model) throws Exception{
 
        logger.info("main");
        
 
        return "main";
    }
	
    @ResponseBody
    @RequestMapping(value="/idCheck",method=RequestMethod.GET)
    public int idCheck(HttpSession session, @RequestParam("id") String id, MemberVO member, HttpServletRequest req, RedirectAttributes rttr, Model model) throws Exception{
    	//String id = req.getParameter("id");
    	
    	System.out.println("입력한 id는 바로바로\t" + id);
    	
    	int result = service.idCheck(id);//중복아이디 있으면 1, 없으면 0
    	
    	System.out.println("result의 값은 바로바로\t" + result);
    	
    	if(result == 0) {
    		System.out.println("result 0임ㅋㅋ");
    		//model.addAttribute("msg", "CORRECT\n사용할 수 있는 아이디입니다.");
    		integration_id = 0;	
    	}
    	else {
    		System.out.println("result 1임ㅋㅋ");
    		//model.addAttribute("msg", "ERROR\n이미 사용중인 아이디입니다.");
    		integration_id = 1;
    	}

    	return integration_id;
    }
    
    
    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public String join(HttpSession session, MemberVO member, Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rttr) throws Exception{
    	System.out.println("memberVO : " + member.id);
    	String url="join";
    	 	
    	List<MemberVO> saved_db = service.selectMember();
    	
    	if(member.id != null) {
    		if(integration_id == 0) {
    			logger.info("회원가입 성공!");
    			service.joinMember(member);
    			url = "redirect:/";
    		}
    		else if(integration_id == 1) {
    			logger.info("회원가입 실패..");
    			url = "redirect:/join";
    		}
    	}
    	// HttpSession session = request.getSession();
    	/*
    	if (member.id != null) {
    		for(int i=0; i<saved_db.size(); i++) {			// db에 동일한 id가 이미 존재할 경우 회원가입 오류가 발생함을 jsp에 알림
        		if (saved_db.get(i).id.equals(member.id) == true) {
        			logger.info("회원가입 실패");  	
        			//rttr.addFlashAttribute("msg", false);
                    break;
        		}
        		else if(i == saved_db.size()-1){		// db의 모든 회원을 조회해도 중복된 id가 발견 되지 않으면 실행되는 조건문
        			logger.info("회원가입 성공!");
        			rttr.addFlashAttribute("msg", true);
        			service.joinMember(member);  
        			url = "redirect:/";
        		}
        	}
    	}
    	*/
    	

    	integration_id = -1;
    	return url;
       

    }
    
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Locale locale, Model model) throws Exception{
 
        logger.info("home");
        
        List<MemberVO> memberList = service.selectMember();
        
        model.addAttribute("memberList", memberList);
 
        return "home";
    }
    
 
	
}
