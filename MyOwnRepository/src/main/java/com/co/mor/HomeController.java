package com.co.mor;

import java.text.DateFormat;
import java.util.ArrayList;
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
import com.co.dto.commentDTO;
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
	
	public int integration_id = -1;		// 회원가입 할 때 무결성 검사용 전역변수
	public int integration_nickname = -1;	// 회원가입 할 때 무결성 검사용 전역변수
	
	public String user_id = "";		// 세션에서 회원 정보 가져오기의 임시방편으로, 로그인한 사용자의 정보를 임시로 저장하기 위한 전역변수
	public String user_nickname = "";	// 세션에서 회원 정보 가져오기의 임시방편으로, 프로그램 이용시 자주 사용되는 사용자의 닉네임을 저장해놓는 전역변수
	
	public List<boardDTO> show_post;	// 사용자가 보는 게시글을 특정하여 해당 페이지로 이동할 때 사용 할 전역변수 (좋은 방법은 아닌거같음. 향후 개선요망)
										// 컨트롤러에서 컨트롤러로 데이터를 전송할 때의 대체제로 전역변수를 사용한것임.
	
	//public ArrayList<Object> pre_next_posts;	
	
	// 사용자가 보는 게시글에서 바로 이전과 다음 게시글을 의미하는 전역변수
	public List<boardDTO> pre_posts = null;
	public List<boardDTO> next_posts = null;
	
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
        List<boardDTO> board_list = b_service.printBoard();
    	model.addAttribute("BoardList", board_list);
 
        return "main";
    }
	
    @ResponseBody
    @RequestMapping(value="/idCheck",method=RequestMethod.GET)
    public int idCheck(HttpSession session, @RequestParam("id") String id, MemberVO member, HttpServletRequest req, RedirectAttributes rttr, Model model) throws Exception{
    	//String id = req.getParameter("id"); 	
    	
    	int result = service.idCheck(id);	// db에 중복된 아이디 있으면 1, 없으면 0   	
    	
    	if(result == 0) {			// 중복 x , 사용 가능
    		integration_id = 0;	
    	}
    	else {			// 중복 o , 사용 불가능
    		integration_id = 1;
    	}

    	return integration_id;
    }
    
    @ResponseBody
    @RequestMapping(value="/nickCheck",method=RequestMethod.GET)
    public int nicknameCheck(HttpSession session, @RequestParam("nickname") String nickname, MemberVO member, HttpServletRequest req, RedirectAttributes rttr, Model model) throws Exception {
    	System.out.println("닉네임1 : " + nickname);
    	int result = service.nicknameCheck(nickname);	// db에 중복된 닉네임이 있으면 1, 없으면 0   	
    	
    	System.out.println("닉네임2 : " + result);
    	
    	if(result == 0) {			// 중복 x , 사용 가능
    		integration_nickname = 0;	
    	}
    	else {			// 중복 o , 사용 불가능
    		integration_nickname = 1;
    	}
    	
		return integration_nickname;   	
    }
    
    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public String join(HttpSession session, MemberVO member, Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rttr) throws Exception{
    	// System.out.println("memberVO : " + member.id);
    	String url="join";
    	 	
    	List<MemberVO> saved_db = service.selectMember();
    	
    	if(member.id != null) {
    		if(integration_id == 0 && integration_nickname == 0) {
    			logger.info("회원가입 성공!");
    			service.joinMember(member);
    			url = "redirect:/";
    		}
    		else if(integration_id == 1 || integration_nickname == 1) {
    			logger.info("회원가입 실패..");
    			url = "redirect:/join";
    		}
    	}


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
    public String write(HttpServletRequest request, boardDTO letter, Model model) throws Exception {
    	HttpSession session = request.getSession(false);
    	
    	if(session == null) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		return "redirect:/LoginPage";
    	}
    	String url = "";
    	//boardDTO saved_board = b_service.writeBoard();
    	
    	if(letter.title != null && letter.content != null) {
    		if(letter.title.length() > 0 && letter.content.length() <= 1000) {
    			logger.info("글작성 성공!");
    			letter.id = user_id;			// 로그인할때 저장해놓은 사용자의 id와 nickname을 글 작성자의 개인정보로 사용 (글 작성시 사용자가 자신의 id와 nickname을 따로 작성하지 않기때문에 프로그램에서 자체적으로 저장해줌)
    			letter.nickname = user_nickname;
    			b_service.writeBoard(letter);
    			
    			url = "redirect:/user/userMain";
    		}
    		else if(letter.title.length() == 0 || letter.content.length() > 1000) {
    			logger.info("글작성 실패..");
    			model.addAttribute("b_msg", false);
    			url = "redirect:/user/write";
    		}
    	}
    	
		return url;	
    }
    
    @RequestMapping(value = "/user/update_board", method = RequestMethod.GET)
    public String update_boardpage(HttpServletRequest request, Model model) throws Exception {
    	HttpSession session = request.getSession(false);
    	if(session == null) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		return "redirect:/LoginPage";
    	}
    	boardDTO before_post = show_post.get(0);		// 현재 보고있는 게시글을 수정하기 전 게시글의 정보로 저장하기 위한 변수
    	
    	// 수정하기 전 게시글의 정보를 JSP로 보냄(고유번호, 작성자, 제목, 내용)
    	model.addAttribute("post_num", before_post.num);
    	model.addAttribute("post_nickname", before_post.nickname);
    	model.addAttribute("post_title", before_post.title);
    	model.addAttribute("post_content", before_post.content);
    	
    	
		return "user/update_board";
    }
    
    @RequestMapping(value = "/user/update_board", method = RequestMethod.POST)
    public String update_board(Model model, HttpServletRequest request, boardDTO letter) throws Exception {
    	HttpSession session = request.getSession(false);
    	
    	if(session == null) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		return "redirect:/LoginPage";
    	}
    	String url = "redirect:/user/posts/";

    	if(letter.title != null && letter.content != null) {
    		if(letter.title.length() <= 35 && letter.content.length() <= 1000) {
    			logger.info("글수정 성공!");
    			b_service.updateBoard(letter);
    			
    			url += letter.num;
    		}
    		else if(letter.title.length() > 35 || letter.content.length() > 1000) {
    			logger.info("글수정 실패..");
    			url = "redirect:/user/update_board";
    		}
    	}
    	
		return url;
    }
    
    // posts, main_posts는 로그인한 사용자의 게시글 조회에 관한 함수이다. (게시글 선택시 해당 게시글 보여주기, 조회수 증가 등)
    
    /*
    @SuppressWarnings("null")
	@RequestMapping(value = "/user/posts/{urlnum}")		// 게시글에 대한 내용을 jsp에서 받아오고 연산하는곳
    public void post(HttpServletRequest request, @PathVariable("urlnum") String urlnum) throws Exception{
    	HttpSession session = request.getSession(false);
    }
    */
    
    
    @SuppressWarnings("null")
	@RequestMapping(value = "/user/posts/{urlnum}", method = RequestMethod.GET)		// 게시글에 대한 내용을 jsp에서 받아오고 연산하는곳
    public String posts(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("urlnum") String urlnum, commentDTO letter) throws Exception {
    	HttpSession session = request.getSession(false);
    	if(session == null) {							
    		return "redirect:/LoginPage";
    	}
    	String path = "redirect:/user/posts";
    	
     	// 첫 게시글과 마지막 게시글의 고유 번호를 저장하는 first_num과 last_num
     	int first_num = b_service.selectMinNum();     	 	
     	int last_num = b_service.selectMaxNum();
     	     	  	
     	
     	int number = Integer.parseInt(urlnum);		// urlnum은 해당 게시글의 고유번호를 의미하는데, 이것을 정수형으로 바꿔주는 구문이다.   	
     	
     
     	int pre_num=1, next_num=1;		// 열람중인 해당 게시글의 이전글과 다음글의 고유번호를 찾을때 사용 할 정수형 변수 pre_num과 next_num;
     	
     	
     	// 열람중인 해당 게시글이 첫 글이 아닐 경우 실행되는 조건문
     	if(number != first_num) {		
     		// 이전글을 찾는 반복문
     		// 해당 글의 고유번호의 바로 이전 고유번호가 삭제된 글(번호)일때 실행된다.
         	while(b_service.selectBoard(number-pre_num).isEmpty() == true) {		       		
             	pre_num++;		// 보다 더 이전의 게시글의 고유번호를 찾기 위한 연산이다. (이전글중 삭제되지 않은 고유번호를 찾기위함)           	
     		}
         	pre_posts = b_service.selectBoard(number-pre_num);		// 이전글 저장
         	    	
     	}
     	
     		
     	// 열람중인 해당 게시글이 마지막 글이 아닐 경우 실행되는 조건문
     	if(number != last_num) {
     		// 다음글을 찾는 반복문
     		// 해당 글의 고유번호의 바로 다음 고유번호가 삭제된 글(번호)일때 실행된다.
         	while(b_service.selectBoard(number+next_num).isEmpty() == true) {		       		
             	next_num++;		// 다음 게시글의 고유번호를 찾기 위한 연산이다. (다음글중 삭제되지 않은 고유번호를 찾기위함)           	
     		}
         	next_posts = b_service.selectBoard(number+next_num);		// 다음글 저장
         		
     	}   
	
    	
    	show_post = b_service.selectBoard(number);		// 전역변수에 해당 게시글 조회결과 저장
    	if(show_post == null) {				// 열람을 위해 클릭한 다른 유저의 게시글이 이미 삭제되었을 경우 실행되는 조건문 
    		model.addAttribute("msg", false);
    		path = "redirect:/user/userMain";
    	}
    	else {
    		b_service.addView(number);			// 조회수 증가
    	}
    	
    	/*
    	if(letter.content != null) {		// 댓글 작성하는 구문
    		if(letter.content.length() > 0 && letter.content.length() <= 300) {
    			logger.info("댓글작성 성공!");
   			// 로그인할때 저장해놓은 사용자의 nickname을 댓글 작성자의 개인정보로 사용 (댓글 작성시 사용자가 자신의 nickname을 따로 작성하지 않기때문에 프로그램에서 자체적으로 저장해줌)
    			
        	//	letter.b_num = number;		// 게시글 고유번호 저장
    		//	letter.nickname = user_nickname; 	// 댓글 작성자 닉네임 저장
    			
    			b_service.writeComment(letter);   		
    		}
    		else {
    			logger.info("댓글작성 실패..");
    			model.addAttribute("c_msg", false);
    		}
    	}
    	*/
		return path;
    	
    }
    
    
    @RequestMapping(value = "/user/posts")
    public String main_posts(HttpServletRequest request, Model model) throws Exception {		// 연산(실행)이 끝난 결과값을 다시 jsp로 보내주는 역할 
    	HttpSession session = request.getSession(false);
    	if(session == null) {							
    		return "redirect:/LoginPage";
    	}
    	
    	model.addAttribute("page_name", show_post.get(0).nickname);		// JSP의 script에서 게시글 작성자와 게시글 열람하는 사용자가 동일 회원인지 아닌지 구분하기 위해
    																	// 게시글 작성자의 닉네임을 JSP로 보내는 구문이다.
    	
    	model.addAttribute("page_num", show_post.get(0).num);		// 게시글 삭제에 필요한 게시글의 고유번호를 JSP에 보내는 구문이다.
    	
    	model.addAttribute("SelectPost", show_post);		// 해당 게시글의 모든 정보(댓글 제외)를 JSP에 보내는 구문.    
    	
    	// 이전글과 다음글의 정보를 jsp로 보내기
    	model.addAttribute("pre_post", pre_posts);
    	model.addAttribute("next_post", next_posts);
    	
    	List<commentDTO> cmt = b_service.printComment(show_post.get(0).num);
    	
    	
    	model.addAttribute("printComment", cmt);		// 해당 고유번호를 가진 게시글의 모든 댓글의 정보를 jsp에 보내는 구문
    	
    	return "/user/posts";
    }
    
    // 게시글에 댓글 작성을 위해 jsp에서 정보를 받아오고 연산하기위해 만들어진 부분
    @RequestMapping(value = "/user/posts/comment", method = RequestMethod.POST)		
    public String comment(HttpServletRequest request, HttpServletResponse response, Model model, commentDTO letter) throws Exception {
    	HttpSession session = request.getSession(false);
		
    	if(letter.content != null) {		// 댓글 작성하는 구문
    		if(letter.content.length() > 0 && letter.content.length() <= 300) {
    			logger.info("댓글작성 성공!");
   			// 로그인할때 저장해놓은 사용자의 nickname을 댓글 작성자의 개인정보로 사용 (댓글 작성시 사용자가 자신의 nickname을 따로 작성하지 않기때문에 프로그램에서 자체적으로 저장해줌)
    			
        	//	letter.b_num = number;		// 게시글 고유번호 저장
    		//	letter.nickname = user_nickname; 	// 댓글 작성자 닉네임 저장
    			
    			b_service.writeComment(letter);
    			b_service.upComment(letter.b_num);
    		}
    		else {
    			logger.info("댓글작성 실패..");
    			model.addAttribute("c_msg", false);
    		}
    	}
    	String urlnum = Integer.toString(show_post.get(0).num);
    	return "redirect:/user/posts/"+urlnum;
    }
    
    @RequestMapping(value = "/user/delete_board")
    public String delete_board(HttpServletRequest request, @RequestParam("num") int num) throws Exception {
    	HttpSession session = request.getSession(false);
    	if(session == null) {							
    		return "redirect:/LoginPage";
    	}
    	String path = "";
    	  	
    	if(b_service.selectBoard(num) != null) {
    		b_service.deleteBoard(num);
    		path = "redirect:/user/userMain";
    	}
    	else {
    		path = "redirect:/user/posts/";
    		path += num;
    	}
    	
    	return path;
    }
    
    
    // unlogin_post, unlogin_main_posts는 비로그인 사용자를 위한 게시글 열람을 위한 함수이다. (조회수 증가 및 각종 로그인 사용자 기능 제한됨)
    
    @SuppressWarnings("null")
 	@RequestMapping(value = "/unlogin_posts/{urlnum}", method = RequestMethod.GET)
     public String unlogin_posts(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("urlnum") String urlnum) throws Exception {
     	
     	String path = "redirect:/unlogin_posts";
     	
     	// 첫 게시글과 마지막 게시글의 고유 번호를 저장하는 first_num과 last_num
     	int first_num = b_service.selectMinNum();     	 	
     	int last_num = b_service.selectMaxNum();
     	     	  	
     	
     	int number = Integer.parseInt(urlnum);		// urlnum은 해당 게시글의 고유번호를 의미하는데, 이것을 정수형으로 바꿔주는 구문이다.   	
     	
     
     	int pre_num=1, next_num=1;		// 열람중인 해당 게시글의 이전글과 다음글의 고유번호를 찾을때 사용 할 정수형 변수 pre_num과 next_num;
     	
     	
     	// 열람중인 해당 게시글이 첫 글이 아닐 경우 실행되는 조건문
     	if(number != first_num) {		
     		// 이전글을 찾는 반복문
     		// 해당 글의 고유번호의 바로 이전 고유번호가 삭제된 글(번호)일때 실행된다.
         	while(b_service.selectBoard(number-pre_num).isEmpty() == true) {		       		
             	pre_num++;		// 보다 더 이전의 게시글의 고유번호를 찾기 위한 연산이다. (이전글중 삭제되지 않은 고유번호를 찾기위함)           	
     		}
         	pre_posts = b_service.selectBoard(number-pre_num);		// 이전글 저장
         	    	
     	}
     	
     	
     	
     	// 열람중인 해당 게시글이 마지막 글이 아닐 경우 실행되는 조건문
     	if(number != last_num) {
     		// 다음글을 찾는 반복문
     		// 해당 글의 고유번호의 바로 다음 고유번호가 삭제된 글(번호)일때 실행된다.
         	while(b_service.selectBoard(number+next_num).isEmpty() == true) {		       		
             	next_num++;		// 다음 게시글의 고유번호를 찾기 위한 연산이다. (다음글중 삭제되지 않은 고유번호를 찾기위함)           	
     		}
         	next_posts = b_service.selectBoard(number+next_num);		// 다음글 저장
         		
     	}    	
     	
     	show_post = b_service.selectBoard(number);		// 전역변수에 해당 게시글 조회결과 저장
     	
 		return path;
     	
     }
    
    @RequestMapping(value = "/unlogin_posts")
    public String unlogin_main_posts(HttpServletRequest request, Model model) throws Exception {
    	
    	
    	model.addAttribute("SelectPost", show_post);		// 해당글의 정보를 jsp로 보내기		
    	
    	// 이전글과 다음글의 정보를 jsp로 보내기
    	model.addAttribute("pre_post", pre_posts);
    	model.addAttribute("next_post", next_posts);
    	
    	List<commentDTO> cmt = b_service.printComment(show_post.get(0).num);
    	  	
    	model.addAttribute("printComment", cmt);		// 해당 고유번호를 가진 게시글의 모든 댓글의 정보를 jsp에 보내는 구문
    	
    	return "/unlogin_posts";
    }
    
  
    
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Locale locale, Model model) throws Exception{
 
        logger.info("home");
        
        List<MemberVO> memberList = service.selectMember();
        
        model.addAttribute("memberList", memberList);
 
        return "home";
    }
    
 
	
}
