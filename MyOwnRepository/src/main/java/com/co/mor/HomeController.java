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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.co.dto.LoginDTO;
import com.co.dto.MemberVO;
import com.co.dto.boardDTO;
import com.co.service.BoardService;
import com.co.service.MemberService;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
/**
 * Handles requests for the application home page.
 */

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	public int integration_id = -1;		// 로그인 할 때 무결성 검사용 전역변수
	
	public String user_id = "";		// 세션에서 회원 정보 가져오기의 임시방편으로, 로그인한 사용자의 정보를 임시로 저장하기 위한 전역변수
	public String user_nickname = "";	// 세션에서 회원 정보 가져오기의 임시방편으로, 프로그램 이용시 자주 사용되는 사용자의 닉네임을 저장해놓는 전역변수
	
	public List<boardDTO> show_post;	// 사용자가 보는 게시글을 특정하여 해당 페이지로 이동할 때 사용 할 전역변수 (좋은 방법은 아닌거같음. 향후 개선요망)
								// 컨트롤러에서 컨트롤러로 데이터를 전송할 때의 대체제로 전역변수를 사용한것임.
	
	private static String SESSION_ID = "session_Id";
	
	private static final String LOGIN_MEMBER = "LOGIN_MEMBER";
	private final MemberService memberservice = null;
	
    @Inject
    private MemberService service;
    
    @Inject
    private BoardService b_service;
   
    
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

    
    @RequestMapping(value = "/LoginPage", method = RequestMethod.GET)
    public String loginPage() throws Exception {
    	return "login";
    }

    
    @RequestMapping(value = "/LoginPage", method = RequestMethod.POST)
    public String login(Model model, HttpServletRequest request, LoginDTO member, @RequestParam("id") String id, @RequestParam("pw") String pw) throws Exception {
    	String path = "";
    	HttpSession session = request.getSession(false);
    	
    	String doesnt_str = "org.apache.catalina.session.StandardSessionFacade@";		// 세션에 자꾸 쓰레기값이 들어가서 임시로 거름망 변수선언
    	
    	if(session != null && session.toString().contains(doesnt_str) == false ) {		// 정상적인 세션이 존재할 경우 사용자 메인페이지로 이동
    		System.out.println("session 값 >> " + session);
    		return "redirect:/user/userMain";
    	}
    	
    	/*
    	//String sessionId = (String)session.getAttribute(SESSION_ID);
    	LoginDTO select_member = service.selectPerson(SESSION_ID);
    	
    	if(select_member == null) {		// 세션에 등록된 아이디가 회원탈퇴 등으로 인해 db에 저장되어 있지 않은 아이디 인지 확인하는 조건문
    		return "join";
    	}
    	session.setAttribute(LOGIN_MEMBER, select_member);
    	model.addAttribute("select_member", select_member);  		
    	*/
    	  	
        
        session = request.getSession();		// 세션이 없으면 새로운 세션을 생성하고, 이미 존재하면 해당 세션을 반환
        
        LoginDTO l_member = service.loginMember(member);      
        
        if (l_member == null) {
        	model.addAttribute("msg", false);
            path = "login";
        }
        else {
        	session.setAttribute(LOGIN_MEMBER, l_member);	// 세션에 사용자 정보 저장
            SESSION_ID = l_member.id;
            
            System.out.println(session);
            
            session.setAttribute("userNickname", l_member.nickname);		// (임시) 로그인이 정상적으로 됐는지 확인하기 위해 사용자 닉네임 따로 세션저장
            
            user_id = l_member.id;
            user_nickname = l_member.nickname;	// 세션에서 회원 정보 가져오기의 임시방편으로, 전역변수에 닉네임 저장
            
            System.out.println(session);
            path = "redirect:/user/userMain";
        }
        
        
        
        return path;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
  
        if(session != null) {
        	model.addAttribute("logout_msg", true);
        	session.invalidate();
        }
        
        return "redirect:/";
    }
    
    
    @RequestMapping(value = "/user/userMain", method = RequestMethod.GET)
    public String userMainPage(HttpServletRequest request, Model model) throws Exception {
    	HttpSession session = request.getSession(false);
    	
    	if(session == null) {			// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		return "LoginPage";
    	}
    	List<boardDTO> board_list = b_service.printBoard();
    	model.addAttribute("BoardList", board_list);
    	
    	System.out.println(LOGIN_MEMBER);
    	return "user/userMain";
	
    }
    
    @RequestMapping(value = "/user/mypage", method = RequestMethod.GET)
    public String mypage(HttpServletRequest request) {
    	HttpSession session = request.getSession(false);
    	
    	if(session == null) {			// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		return "redirect:/LoginPage";
    	}
		return "user/mypage";	
    }
    
    @RequestMapping(value = "/user/write", method = RequestMethod.GET)
    public String write_page(HttpServletRequest request) throws Exception {
    	HttpSession session = request.getSession(false);
    	if(session == null) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		return "redirect:/LoginPage";
    	}
		return "user/write";
    }
    
    
    @RequestMapping(value = "/user/write", method = RequestMethod.POST)
    public String write(HttpServletRequest request, boardDTO letter) throws Exception {
    	HttpSession session = request.getSession(false);
    	
    	if(session == null) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		return "redirect:/LoginPage";
    	}
    	String url = "";
    	//boardDTO saved_board = b_service.writeBoard();
    	
    	if(letter.title != null && letter.content != null) {
    		if(letter.title.length() <= 35 && letter.content.length() <= 1000) {
    			logger.info("글작성 성공!");
    			letter.id = user_id;			// 로그인할때 저장해놓은 사용자의 id와 nickname을 글 작성자의 개인정보로 사용 (글 작성시 사용자가 자신의 id와 nickname을 따로 작성하지 않기때문에 프로그램에서 자체적으로 저장해줌)
    			letter.nickname = user_nickname;
    			b_service.writeBoard(letter);
    			
    			url = "redirect:/user/userMain";
    		}
    		else if(letter.title.length() > 35 || letter.content.length() > 1000) {
    			logger.info("글작성 실패..");
    			url = "redirect:/user/write";
    		}
    	}
    	
		return url;	
    }
    
   
    @SuppressWarnings("null")
	@RequestMapping(value = "/user/posts/{urlnum}", method = RequestMethod.GET)
    public String posts(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("urlnum") String urlnum) throws Exception {
    	HttpSession session = request.getSession(false);
    	if(session == null) {							
    		return "redirect:/LoginPage";
    	}
    	String path = "redirect:/user/posts";
    	
    	System.out.println("n의 값1 = " + urlnum);
    	int number = Integer.parseInt(urlnum);
    	System.out.println("n의 값2 = " + number);
    	
    	
    	show_post = b_service.selectBoard(number);		// 전역변수에 해당 게시글 조회결과 저장
    	if(show_post == null) {				// 열람을 위해 클릭한 다른 유저의 게시글이 이미 삭제되었을 경우 실행되는 조건문 
    		model.addAttribute("msg", false);
    		path = "redirect:/user/userMain";
    	}
    	else {
    		b_service.addView(number);			// 조회수 증가
    	}
    	
		return path;
    	
    }
    
    @RequestMapping(value = "/user/posts")
    public String main_posts(HttpServletRequest request, Model model) throws Exception {
    	HttpSession session = request.getSession(false);
    	if(session == null) {							
    		return "redirect:/LoginPage";
    	}
    	
    	model.addAttribute("SelectPost", show_post);
    	
    	
    	return "/user/posts";
    }
    
    
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Locale locale, Model model) throws Exception{
 
        logger.info("home");
        
        List<MemberVO> memberList = service.selectMember();
        
        model.addAttribute("memberList", memberList);
 
        return "home";
    }
    
 
	
}
