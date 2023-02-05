package com.co.mor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
// import javax.mail.MessagingException;
// import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.co.dto.FileDTO;
import com.co.dto.FindIDPWDTO;
import com.co.dto.LoginDTO;
import com.co.dto.MemberVO;
import com.co.dto.boardDTO;
import com.co.dto.commentDTO;
import com.co.dto.deleteFileDTO;
import com.co.dto.searchVO;
import com.co.service.BoardService;
import com.co.service.MemberService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
/**
 * Handles requests for the application home page.
 */

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private JavaMailSenderImpl mailSender;
	
	
	// 스프링 서블릿이 제공하는 HttpSession에 데이터를 보관하고 조회할 때 이용하기 위한 상수
	public abstract class SessionConst {	
	    public static final String LOGIN_MEMBER = "loginMember";
	    public static final String MEMBER_SHOWPOST = "MEMBER_SHOWPOST";

	}
	
	// 게시글이 현재날짜 기준으로 1일이상 지난 게시글인지 판별하는 함수
	public String compareDate(String b_date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentTime = new Date();
		String date = format.format(currentTime);
		
		// 같은 년도에 같은 날짜이면 "HH:mm" 형식으로 몇시 몇분인지 리턴 
		if(date.substring(0, 10).equals(b_date.substring(0, 10)) == true) {
			return b_date.substring(11, 16);
		}
		// 같은 년도지만 다른 날짜이면 "MM-dd" 형식으로 몇월 며칠인지 리턴
		else if(date.substring(0, 4).equals(b_date.substring(0, 4)) == true && date.substring(5, 10).equals(b_date.substring(5, 10)) == false) {
			return b_date.substring(5, 10);
		}
		// 같은 년도가 아닐 경우 "yyyy-MM-dd" 형식으로 몇년도 몇월 며칠인지 리턴
		else {
			return b_date.substring(0, 10);
		}
			
	}
	
	// 이메일 인증번호 난수 발생함수
	public int makeRandomNum() {
		// 난수의 범위 111111 ~ 999999 (6자리 난수)
		Random r = new Random();
		int randomNum = r.nextInt(899999) + 100000;
		System.out.println("인증번호 >>>>>>>>> " + randomNum);
		return randomNum;
	}
	
	public int integration_id = -1;		// 회원가입 할 때 무결성 검사용 전역변수
	public int integration_nickname = -1;	// 회원가입 할 때 무결성 검사용 전역변수
	
	//public String user_id = "";		// 세션에서 회원 정보 가져오기의 임시방편으로, 로그인한 사용자의 정보를 임시로 저장하기 위한 전역변수
	//public String user_nickname = "";	// 세션에서 회원 정보 가져오기의 임시방편으로, 프로그램 이용시 자주 사용되는 사용자의 닉네임을 저장해놓는 전역변수
	
	//public List<boardDTO> show_post;	// 사용자가 보는 게시글을 특정하여 해당 페이지로 이동할 때 사용 할 전역변수 (좋은 방법은 아닌거같음. 향후 개선요망)
										// 컨트롤러에서 컨트롤러로 데이터를 전송할 때의 대체제로 전역변수를 사용한것임.
	
	public int board_first_index = 0;		// 사용자가 보는 게시글을 20개씩 나누어 페이징하기 위한 쿼리문에서 사용되는 전역변수.
										// = MYSQL의 쿼리문인 limitBoard의 LIMIT의 시작 인덱스가 될 값
	
	public int secretboard_first_index = 0;		// 비밀 게시판 페이징용 전역변수
	public int repo_first_index = 0;			// 공유저장소 페이징 변수
	public int secretRepo_first_index = 0;		// 비밀저장소 페이징 변수
	
	
	
	private static String SESSION_ID = "session_Id";
	
	private static String ALLOW_POST = "allow_post:";
	
	
	private final MemberService memberservice = null;
	
    @Inject
    private MemberService service;
    
    @Inject
    private BoardService b_service;
   
    // {page}는 사용자가 선택한 게시판의 페이지 번호
    @RequestMapping(value = "/{page}", method = RequestMethod.GET)
    public String main(Locale locale, Model model, HttpServletRequest request, @PathVariable("page") int page) throws Exception{ 
        logger.info("main");
        
        // 사용자가 선택한 페이지에 따라 sql에 보낼 파라미터 값(LIMIT 시작 인덱스)이 바뀜 (1번째 페이지 일 경우 값은 1, 2번째의 경우 값은 21 .... 20씩 증가)      
        board_first_index = (page-1) * 20;
 
        return "redirect:/";
    }
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String view_main(Locale locale, Model model, HttpServletRequest request) throws Exception{ 
        logger.info("main");
        int total = b_service.totalNum();
        int page_count=1;
        
        System.out.println("유효 게시글 총 개수 >>>> " + total);
        
        // 한 페이지에 20개의 글이 보이게 한다.
        // 총 게시글 수의 대입해, 필요한 총 페이지 수(page_count)를 알아 내기위한 반복문.
        while(true) {
        	if((total != 0) && (total / 21) != 0) {
        		page_count++;
        		total -= 20;
        	}
        	else {
        		break;
        	}
        }
        
        /* 하단에 페이지 숫자 범위지정을 위한 과정 ( ex, <이전 1 2 3 4 5 6 7 8 9 10 다음>) */
        
        // 현재 선택한 페이지
        int page = board_first_index / 20 + 1;
        model.addAttribute("select_page", page);
        
        // 처음 페이지의 숫자
        int first_page = 1;
        if(page % 10 == 0) {
        	first_page = page - 9;		
        }
        else {
        	first_page = page - (page % 10) + 1;		
        }
        model.addAttribute("first_page", first_page);
        
        // 마지막 페이지의 숫자
        int last_page = 10;
        if(page % 10 == 0) {
        	last_page = page;
        }
        else if((page / 10 + 1) * 10 > page_count) {		// 마지막 페이지 그룹일 경우 실행 되는 조건문
        	last_page = page_count;
        }
        else {
        	last_page = ((page / 10) + 1) * 10;
        }
        model.addAttribute("last_page", last_page);
        
        // 총 페이지의 숫자
        model.addAttribute("page_count", page_count);
        
        
        //System.out.println("메인페이지 페이지수 (선택,처음,마지막,총개수) >>>> " + page + first_page + last_page + page_count);
        
        
        // 처음 웹 사이트를 실행시켰을때는 페이지 디폴트값인 첫번째 페이지를 보여주도록 유도(board_first_num의 값을 선언과 동시에 1로 초기화)하고
        // 이후에는 사용자가 고르는 페이지가 보여지도록 함.       
        List<boardDTO> board_list = b_service.limitBoard(board_first_index);
        String dateResult = null;
        // 게시글의 날짜를 현재시간을 기준으로 판별하여
        // 1. 같은 날짜의 게시글 			>> 몇시:몇분
        // 2. 같은 년도의 다른 날짜의 게시글 	>> 몇월-며칠
        // 3. 다른 년도의 게시글				>> 몇년도-몇월-며칠
        // 형식으로 바꾸어 JSP로 보낸다.
        for(int i=0; i<board_list.size(); i++) {
        	dateResult = compareDate(board_list.get(i).date);
        	board_list.get(i).setdate(dateResult);
        }
        model.addAttribute("BoardList", board_list);
        
        
        // 관리자 게시글 목록(전체-펼치기ver)
        List<boardDTO> admin_board_list = b_service.printAdminBoard();
        model.addAttribute("adminBoardList", admin_board_list);
 
        // 관리자 게시글 목록(5개-접기ver)
        List<boardDTO> admin_board_foldList = b_service.limitAdminBoard();
        model.addAttribute("adminFoldList", admin_board_foldList);
        
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
    
    
    // 이메일 인증
    @ResponseBody
    @RequestMapping(value="/checkEmail.do",method=RequestMethod.GET)
    public int emailCheck(HttpSession session, @RequestParam("userEmail") String userEmail) throws Exception {
    	System.out.println("이메일 >>>>>>> " + userEmail);
		
    	// 인증번호 만들기
    	int confirmNum = makeRandomNum();
    	
    	// 이메일로 보낼 양식
    	String sender = "ajkl12345@naver.com";
    	String receiver = userEmail;
    	String title = "MOR 회원가입 인증번호 입니다.";
    	String content = "홈페이지를 방문 해주셔서 감사합니다."+"<br><br>"+"인증 번호는 " + confirmNum + "입니다.";
    	
    	MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
			helper.setFrom(sender);
			helper.setTo(receiver);
			helper.setSubject(title);
			// true 전달 > html 형식으로 전송 , 작성하지 않으면 단순 텍스트로 전달.
			helper.setText(content,true);
			mailSender.send(message);
			return confirmNum;
		} catch (MessagingException e) {
			e.printStackTrace();
			return -1;
		}
    	
		
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
    			model.addAttribute("sign_complete_msg", true);	// 회원가입 완료시 팝업창을 닫기 위해 JSP로 신호를 보냄.
    		}
    		else if(integration_id == 1 || integration_nickname == 1) {
    			logger.info("회원가입 실패..");
    			url = "redirect:/join";
    		}
    	}

    	integration_id = -1;
    	   
    	
    	//return url;
    	return "join";
    }

    // 아이디/비밀번호 찾기 view
    @RequestMapping(value = "/find_Info")
    public String viewFindIDPW(HttpServletRequest request) throws Exception{
    	
    	return "find_IDPW";
    }
    
    // 아이디/비밀번호 찾기
    @ResponseBody
    @RequestMapping(value = "/findIDPW.do", method = RequestMethod.POST)
    public Object findIDPW(FindIDPWDTO find) throws Exception{
    	
    	
    	System.out.println("찾기 이름 >>>>>>>>>>>>>>>>>> "+ find.name);
    	System.out.println("찾기 아이디 >>>>>>>>>>>>>>>>>> "+ find.id);
    	System.out.println("찾기 찾으려는것 >>>>>>>>>>>>>>>>>> "+ find.which);
    	System.out.println("찾기 이메일 >>>>>>>>>>>>>>>>>> "+ find.email); 	
    	
    	
    	List<FindIDPWDTO> findUser = service.findIDPW(find);		// ajax로 받은 데이터와 일치하는 회원이 있는지 찾는 쿼리문.
    	Map<String, Object> result = new HashMap<String, Object>();		// ajax로 보낼 데이터 저장할 map
    	
    	// 일치하는 회원(아이디)이 없을 경우 실행 (db와 비교해서 이름이 다르거나, 이메일이 다르거나 등)
    	if(findUser == null || findUser.size() == 0) {
    		//System.out.println("일치하는 아이디가 없습니다@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    		result.put("check", "NO");
    		return result;
    	}
    	// 회원을 찾았을 경우 인증 이메일 발송
    	else {
        	// 인증번호 만들기
        	int confirmNum = makeRandomNum();
        	
        	// 이메일로 보낼 양식
        	String sender = "ajkl12345@naver.com";
        	String receiver = find.email;
        	String title = "MOR 아이디/비밀번호 찾기 인증번호 입니다.";
        	String content = "홈페이지를 방문 해주셔서 감사합니다."+"<br><br>"+"인증 번호는 " + confirmNum + "입니다.";
        	
        	MimeMessage message = mailSender.createMimeMessage();
    		try {
    			MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
    			helper.setFrom(sender);
    			helper.setTo(receiver);
    			helper.setSubject(title);
    			// true 전달 > html 형식으로 전송 , 작성하지 않으면 단순 텍스트로 전달.
    			helper.setText(content,true);
    			mailSender.send(message);
    			result.put("check", "OK");
    			result.put("confirmNum", confirmNum);
    			return result;
    		} catch (MessagingException e) {
    			e.printStackTrace();
    			return -1;
    		}
    	}
    	
    }

    @SuppressWarnings("null")
	@RequestMapping(value = "/show_myIDPW/{user}/{email}/{which}")
    public String show_myIDPW(Model model, @PathVariable("which") String which, @PathVariable("user") String user, @PathVariable("email") String email) throws Exception{
    	
    	FindIDPWDTO find = new FindIDPWDTO();
    	
    	find.setwhich(which);
        if(which.equals("id") == true) {
        	find.setid("-1");
            find.setname(user);
            find.setemail(email);
        }
        else if(which.equals("pw") == true) {
        	find.setname("-1");
        	find.setid(user);
            find.setemail(email);
        }
        if(find != null) {
        	System.out.println("찾는 이름 >>>>>>>>>>>>>>>>>> "+ find.name);
        	System.out.println("찾는 이메일 >>>>>>>>>>>>>>>>>> "+ find.email);
        	System.out.println("찾는 것 >>>>>>>>>>>>>>>>>> "+ find.which);
        	System.out.println("찾는 아이디 >>>>>>>>>>>>>>>>>> "+ find.id);
        }
        
        List<FindIDPWDTO> findUser = service.findIDPW(find);
        
        if(findUser == null) {
        	System.out.println("findUser값이 NULL이야 @@@@@@@@@@@@@@@@@ ");
        }
        else {
        	System.out.println("찾은 객체 >>>>>>>>>>>>>>>>>> "+ findUser.size());
        }
        
        model.addAttribute("userInfo", findUser);
    	model.addAttribute("which", which);
    	
    	return "show_IDPW";
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
    	   	  	
        
        
        
        LoginDTO l_member = service.loginMember(member);      
        
        if (l_member == null) {
        	model.addAttribute("msg", false);
            path = "login";
        }
        else {
        	session = request.getSession();		// 세션이 없으면 새로운 세션을 생성하고, 이미 존재하면 해당 세션을 반환
        	session.setAttribute(SessionConst.LOGIN_MEMBER, l_member);
        	//session.setAttribute(LOGIN_MEMBER, l_member);	// 세션에 사용자 정보 저장
        	//session.setAttribute(MEMBER_ID, l_member.id);
        	//session.setAttribute(MEMBER_NAME, l_member.name);
        	//session.setAttribute(MEMBER_NICKNAME, l_member.nickname);
        	
        	/*
            SESSION_ID = l_member.id;
            
            System.out.println(session);
            
            session.setAttribute("userNickname", l_member.nickname);		// (임시) 로그인이 정상적으로 됐는지 확인하기 위해 사용자 닉네임 따로 세션저장
            session.setAttribute("userId", l_member.id);
            session.setAttribute("userName", l_member.name);
            
            
            user_id = l_member.id;
            user_nickname = l_member.nickname;	// 세션에서 회원 정보 가져오기의 임시방편으로, 전역변수에 닉네임 저장
            */
        	
            System.out.println("세션 아이디 >>>> " + session.getId());
            System.out.println("세션 정보 >>>> " + session.getAttribute(SessionConst.LOGIN_MEMBER));
           
                                   
            System.out.println(session);
            path = "redirect:/user/userMain";
        }
        
        
        
        return path;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
  
        if(session != null) {	// 사실상 표면적으로 로그인이 되어있기만 하다면 실행 되는 조건문이다. (세션이 만료가 되어도 session이 완전히 비워지진 않기때문)
        	model.addAttribute("logout_msg", true);
        	session.invalidate();
        }
        
        return "redirect:/";
    }
    
    @RequestMapping(value = "/user/userMain/{page}", method = RequestMethod.GET)
    public String userMainPage(HttpServletRequest request, Model model, @PathVariable("page") int page) throws Exception {
    	HttpSession session = request.getSession(false);
    	//System.out.println("사용자 닉네임 >> " + user_nickname);
   	
    	if(session == null || !request.isRequestedSessionIdValid()) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		model.addAttribute("session_msg", false);
    		return "redirect:/LoginPage";
    	}  	
    	if(session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
    		model.addAttribute("session_msg", false);
    		session.invalidate();
    		return "redirect:/";
    	}
    	
    	
        // 사용자가 선택한 페이지에 따라 sql에 보낼 파라미터 값(LIMIT 시작 인덱스)이 바뀜 (1번째 페이지 일 경우 값은 1, 2번째의 경우 값은 21 .... 20씩 증가)      
        board_first_index = (page-1) * 20;
    	
    	//System.out.println(LOGIN_MEMBER);
    	return "redirect:/user/userMain";
	
    }
    
    @RequestMapping(value = "/user/userMain", method = RequestMethod.GET)
    public String view_userMainPage(HttpServletRequest request, Model model) throws Exception {
    	HttpSession session = request.getSession(false);
    	
    	if(session == null || !request.isRequestedSessionIdValid()) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		model.addAttribute("session_msg", false);
    		return "redirect:/LoginPage";
    	}    
    	
    	if(session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
    		model.addAttribute("session_msg", false);
    		session.invalidate();
    		return "redirect:/";
    	}
    	
    	// 세션에 보관한 회원 객체를 새로운 멤버 객체에 찾아 넣어준다.
    	LoginDTO loginmember = (LoginDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
    	if (loginmember == null) {
    		model.addAttribute("session_msg", false);
    		return "redirect:/";
    	}
    	model.addAttribute("member", loginmember);
    	System.out.println("로그인 객체값 확인 >>>>> " + loginmember.nickname);
    	
        int total = b_service.totalNum();
        int page_count=1;
        
        // 한 페이지에 20개의 글이 보이게 한다.
        // 총 게시글 수의 대입해, 필요한 총 페이지 수(page_count)를 알아 내기위한 반복문.
        while(true) {
        	if((total != 0) && (total / 21) != 0) {
        		page_count++;
        		total -= 20;
        	}
        	else {
        		break;
        	}
        }
        
        /* 하단에 페이지 숫자 범위지정을 위한 과정 ( ex, <이전 1 2 3 4 5 6 7 8 9 10 다음>) */
        
        // 현재 선택한 페이지
        int page = board_first_index / 20 + 1;
        model.addAttribute("select_page", page);
        
        // 처음 페이지의 숫자
        int first_page = 1;
        if(page % 10 == 0) {
        	first_page = page - 9;		
        }
        else {
        	first_page = page - (page % 10) + 1;		
        }
        model.addAttribute("first_page", first_page);
        
        // 마지막 페이지의 숫자
        int last_page = 10;
        if(page % 10 == 0) {
        	last_page = page;
        }
        else if((page / 10 + 1) * 10 > page_count) {		// 마지막 페이지 그룹일 경우 실행 되는 조건문
        	last_page = page_count;
        }
        else {
        	last_page = ((page / 10) + 1) * 10;
        }
        model.addAttribute("last_page", last_page);
        
        // 총 페이지의 숫자
        model.addAttribute("page_count", page_count);
        
        
        System.out.println("메인페이지 페이지수 (선택,처음,마지막,총개수) >>>> " + page + first_page + last_page + page_count);
        
        
        // 처음 웹 사이트를 실행시켰을때는 페이지 디폴트값인 첫번째 페이지를 보여주도록 유도(board_first_num의 값을 선언과 동시에 1로 초기화)하고
        // 이후에는 사용자가 고르는 페이지가 보여지도록 함.       
        List<boardDTO> board_list = b_service.limitBoard(board_first_index);
        String dateResult = null;
        // 게시글의 날짜를 현재시간을 기준으로 판별하여
        // 1. 같은 날짜의 게시글 			>> 몇시:몇분
        // 2. 같은 년도의 다른 날짜의 게시글 	>> 몇월-며칠
        // 3. 다른 년도의 게시글				>> 몇년도-몇월-며칠
        // 형식으로 바꾸어 JSP로 보낸다.
        for(int i=0; i<board_list.size(); i++) {
        	dateResult = compareDate(board_list.get(i).date);
        	board_list.get(i).setdate(dateResult);
        }
        model.addAttribute("BoardList", board_list);
    	
        // 관리자 게시글 목록(전체-펼치기ver)
        List<boardDTO> admin_board_list = b_service.printAdminBoard();
        model.addAttribute("adminBoardList", admin_board_list);
 
        // 관리자 게시글 목록(5개-접기ver)
        List<boardDTO> admin_board_foldList = b_service.limitAdminBoard();
        model.addAttribute("adminFoldList", admin_board_foldList);
        
    	return "user/userMain";
	
    }
    
    @RequestMapping(value = "/user/mypage", method = RequestMethod.GET)
    public String mypage(HttpServletRequest request, Model model) {
    	HttpSession session = request.getSession(false);
    	
    	if(session == null || !request.isRequestedSessionIdValid()) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		model.addAttribute("session_msg", false);
    		return "redirect:/LoginPage";
    	}
    	else if(session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
    		model.addAttribute("session_msg", true);
        	session.invalidate();
        	return "redirect:/LoginPage";
    	}
		return "user/mypage";	
    }
    
    @RequestMapping(value = "/user/write", method = RequestMethod.GET)
    public String write_page(HttpServletRequest request, Model model) throws Exception {
    	HttpSession session = request.getSession(false);
    	
    	if(session == null || !request.isRequestedSessionIdValid()) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		model.addAttribute("session_msg", false);
    		return "redirect:/LoginPage";
    	}
    	else if(session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
    		model.addAttribute("session_msg", true);
        	session.invalidate();
        	return "redirect:/LoginPage";
    	}
    	
    	LoginDTO loginmember = (LoginDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
    	if (loginmember == null) {
    		model.addAttribute("session_msg", false);
    		return "redirect:/";
    	}
    	model.addAttribute("member", loginmember);
    	
		return "user/write";
    }
    
     
    
    @SuppressWarnings("null")
	@RequestMapping(value = "/user/write", method = RequestMethod.POST)
    public String write(HttpServletRequest request, boardDTO letter, Model model, FileDTO uploadfile, @RequestParam(value = "files", required = false) MultipartFile[] files) throws Exception {
    	// HttpServletRequest request, boardDTO letter, Model model, @RequestParam(value = "file", required = false) MultipartHttpServletRequest frequest
    	HttpSession session = request.getSession(false);
    	
    	if(session == null || !request.isRequestedSessionIdValid()) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		model.addAttribute("session_msg", false);
    		return "redirect:/LoginPage";
    	}
    	else if(session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
    		model.addAttribute("session_msg", true);
        	session.invalidate();
        	return "redirect:/LoginPage";
    	}
    	String url = "";
    	
    	// 세션에 보관한 회원 객체를 새로운 멤버 객체에 찾아 넣어준다.
    	LoginDTO loginmember = (LoginDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
    	if (loginmember == null) {
    		model.addAttribute("session_msg", false);
    		return "redirect:/";
    	}
    	model.addAttribute("member", loginmember);
    	//model.addAttribute("userNickname", loginmember.nickname);
    	
    	if(letter.title != null && letter.content != null) {
    		if(letter.title.length() <= 50 && letter.content.length() <= 1000) {
    			logger.info("글작성 성공!");
    			letter.id = loginmember.id;			// 로그인할때 저장해놓은 사용자의 id와 nickname을 글 작성자의 개인정보로 사용 (글 작성시 사용자가 자신의 id와 nickname을 따로 작성하지 않기때문에 프로그램에서 자체적으로 저장해줌)
    			letter.nickname = loginmember.nickname;
    			
    			if(letter.nickname == null) {	// 글 작성시 세션이 만료될 경우 실행 (익명으로 글이 작성되는 오류 방지)
    				session.invalidate();
    	    		return "redirect:/LoginPage";
    			}
    			
    			
    			// 파일 업로드 구문
    			
    			if(!files[0].isEmpty()) {		// 첫 번째 인덱스가 비었을 경우 실행이 되지않음(첫 번째 인덱스가 비었다 = 파일이 등록된게 하나도 없다)
    				
    				int b_num = b_service.maxNum()+1;		// 파일의 정보에 게시글 고유번호를 넣어야한다.
															// 게시글이 먼저 올라오고 파일이 올라가므로 마지막 게시글의 고유번호를 가져오는 방법을 썼다.
															// 그다지 좋은 방법은 아니니 대안책을 고려해야할듯.
															// (다른 사용자의 게시글이 거의 동시에 올라올경우 정보가 꼬일 가능성이 있어서 파일을 먼저 db에 등록하는 방법을 사용)
    				
    				for(int i=0; i<files.length; i++) {
    					String fileName = null;
	    	   	    	
    					// jsp에서 가져온 파일의 이름을 가져와 용도에 맞게 처리한다.
            	    	String originalFileName = files[i].getOriginalFilename();
            	    	String ext = FilenameUtils.getExtension(originalFileName);
            	    	UUID uuid = UUID.randomUUID();
            	    	fileName = uuid + "." + ext;
            	    		      	    		
            	    	// 파일을 해당 경로에 저장시키는 실질적인 구문.
            	    	files[i].transferTo(new File("D:\\MyOwnRepository_DATA\\upload\\" + fileName));
            	    		
            	    	// 파일 업로드용 vo(dto)에 데이터를 저장 시킨 후 파일업로드 쿼리를 실행시킨다.             	    	
            	    	uploadfile.setb_num(b_num);
            	    	uploadfile.setuser_id(letter.id);
            	    	uploadfile.setoriginal_file_name(originalFileName);
            	    	uploadfile.setstored_file_name(fileName);
            	    	uploadfile.setstored_path("D:\\MyOwnRepository_DATA\\upload\\"+fileName);
            	    	uploadfile.setfile_size(files[i].getSize());
            	    	
            	    	// 파일 업로드시 파일 타입도 같이 저장하기 위해 추가했다.
            	    	File file = new File("D:\\MyOwnRepository_DATA\\upload\\"+fileName);
            	    	String mimeType	= new Tika().detect(file);		// 파일 다운로드 방식에 대해 알아보다가 
																		// Apache Tika 라는 컨텐츠 분석 라이브러리를 발견하여 사용했다. 
            	    	uploadfile.settype(mimeType);				
            	    	
            	    	b_service.fileUpload(uploadfile);
    				}
  				
    	    	}
    			b_service.writeBoard(letter);		// 게시글 작성 완료
    			
    			
    			url = "redirect:/user/userMain";
    		}
    		else if(letter.title.length() > 50 || letter.content.length() > 1000) {
    			logger.info("글작성 실패..");
    			model.addAttribute("b_msg", false);
    			url = "redirect:/user/write";
    		}
    	}
    	
    	
    	
		return url;	
    }
    
    @RequestMapping(value = "/user/write_board/{what}", method = RequestMethod.GET)
    public String write_board_page(HttpServletRequest request, Model model, @PathVariable("what") String what) throws Exception {
    	HttpSession session = request.getSession(false);
    	
    	if(session == null || !request.isRequestedSessionIdValid()) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		model.addAttribute("session_msg", false);
    		return "redirect:/LoginPage";
    	}
    	else if(session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
    		model.addAttribute("session_msg", true);
        	session.invalidate();
        	return "redirect:/LoginPage";
    	}
    	
    	LoginDTO loginmember = (LoginDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
    	if (loginmember == null) {
    		model.addAttribute("session_msg", false);
    		return "redirect:/";
    	}
    	model.addAttribute("member", loginmember);
    	
    	model.addAttribute("what", what);
    	
		return "user/write_board";
    }
    
    
    @SuppressWarnings("null")
	@RequestMapping(value = "/user/write_board", method = RequestMethod.POST)
    public String write_board(HttpServletRequest request, boardDTO letter, Model model, FileDTO uploadfile, @RequestParam(value = "files", required = false) MultipartFile[] files) throws Exception {
    	// HttpServletRequest request, boardDTO letter, Model model, @RequestParam(value = "file", required = false) MultipartHttpServletRequest frequest
    	HttpSession session = request.getSession(false);
    	
    	if(session == null || !request.isRequestedSessionIdValid()) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		model.addAttribute("session_msg", false);
    		return "redirect:/LoginPage";
    	}
    	else if(session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
    		model.addAttribute("session_msg", true);
        	session.invalidate();
        	return "redirect:/LoginPage";
    	}
    	String url = "";
    	
    	// 세션에 보관한 회원 객체를 새로운 멤버 객체에 찾아 넣어준다.
    	LoginDTO loginmember = (LoginDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
    	if (loginmember == null) {
    		model.addAttribute("session_msg", false);
    		return "redirect:/";
    	}
    	model.addAttribute("member", loginmember);
    	//model.addAttribute("userNickname", loginmember.nickname);
    	
    	if(letter.title != null && letter.content != null) {
    		if(letter.title.length() <= 50 && letter.content.length() <= 1000) {
    			logger.info("글작성 성공!");
    			letter.id = loginmember.id;			// 로그인할때 저장해놓은 사용자의 id와 nickname을 글 작성자의 개인정보로 사용 (글 작성시 사용자가 자신의 id와 nickname을 따로 작성하지 않기때문에 프로그램에서 자체적으로 저장해줌)
    			letter.nickname = loginmember.nickname;
    			
    			if(letter.nickname == null) {	// 글 작성시 세션이 만료될 경우 실행 (익명으로 글이 작성되는 오류 방지)
    				session.invalidate();
    	    		return "redirect:/LoginPage";
    			}
    		
    		
    			// 파일 업로드 구문
    			
    			if(!files[0].isEmpty() || files.length != 0) {		// 첫 번째 인덱스가 비었을 경우 실행이 되지않음(첫 번째 인덱스가 비었다 = 파일이 등록된게 하나도 없다)
    				
    				int b_num = b_service.maxNum()+1;		// 파일의 정보에 게시글 고유번호를 넣어야한다.
															// 게시글이 먼저 올라오고 파일이 올라가므로 마지막 게시글의 고유번호를 가져오는 방법을 썼다.
															// 그다지 좋은 방법은 아니니 대안책을 고려해야할듯.
															// (다른 사용자의 게시글이 거의 동시에 올라올경우 정보가 꼬일 가능성이 있어서 파일을 먼저 db에 등록하는 방법을 사용)
    				
    				for(int i=0; i<files.length; i++) {
    					if(!files[i].isEmpty()) {			// 비어있는 것은 생략
    						String fileName = null;
    	    	   	    	
        					// jsp에서 가져온 파일의 이름을 가져와 용도에 맞게 처리한다.
                	    	String originalFileName = files[i].getOriginalFilename();
                	    	String ext = FilenameUtils.getExtension(originalFileName);
                	    	UUID uuid = UUID.randomUUID();
                	    	fileName = uuid + "." + ext;
                	    		      	    		
                	    	// 파일을 해당 경로에 저장시키는 실질적인 구문.
                	    	files[i].transferTo(new File("D:\\MyOwnRepository_DATA\\upload\\" + fileName));
                	    		
                	    	// 파일 업로드용 vo(dto)에 데이터를 저장 시킨 후 파일업로드 쿼리를 실행시킨다.             	    	
                	    	uploadfile.setb_num(b_num);
                	    	uploadfile.setuser_id(letter.id);
                	    	uploadfile.setoriginal_file_name(originalFileName);
                	    	uploadfile.setstored_file_name(fileName);
                	    	uploadfile.setstored_path("D:\\MyOwnRepository_DATA\\upload\\"+fileName);
                	    	uploadfile.setfile_size(files[i].getSize());
                	    	
                	    	// 파일 업로드시 파일 타입도 같이 저장하기 위해 추가했다.
                	    	File file = new File("D:\\MyOwnRepository_DATA\\upload\\"+fileName);
                	    	String mimeType	= new Tika().detect(file);		// 파일 다운로드 방식에 대해 알아보다가 
    																		// Apache Tika 라는 컨텐츠 분석 라이브러리를 발견하여 사용했다. 
                	    	uploadfile.settype(mimeType);				
                	    	
                	    	b_service.fileUpload(uploadfile);
    					}
    					
    					
    				}
  				
    	    	}
    			b_service.writeBoard(letter);		// 게시글 작성 완료
    			
    			if(letter.is_secret == 0 && letter.is_repo == 0) {
    				url = "redirect:/user/userMain";
    			}
    			else if(letter.is_secret == 1 && letter.is_repo == 0){
    				url = "redirect:/user/secretBoard";
    			}
    			else if(letter.is_secret == 0 && letter.is_repo == 1) {
    				url = "redirect:/user/sharingRepo";
    			}
    			else if(letter.is_secret == 1 && letter.is_repo == 1) {
    				url = "redirect:/user/myRepo";
    			}
    		}
    		else if(letter.title.length() > 50 || letter.content.length() > 1000) {
    			logger.info("글작성 실패..");
    			model.addAttribute("b_msg", false);
    			url = "redirect:/user/write_board";
    		}
    	}
    	
    	
    	
		return url;	
    }
    
    
    @RequestMapping(value = "/user/update_board", method = RequestMethod.GET)
    public String update_boardpage(HttpServletRequest request, Model model, @RequestParam("urlnum") int urlnum) throws Exception {
    	HttpSession session = request.getSession(false);
    	if(session == null || !request.isRequestedSessionIdValid()) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		model.addAttribute("session_msg", false);
    		return "redirect:/LoginPage";
    	}
    	else if(session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
    		model.addAttribute("session_msg", true);
        	session.invalidate();
        	return "redirect:/LoginPage";
    	}
    	
    	boardDTO before_post = null;		// 현재 보고있는 게시글을 수정하기 전 게시글의 정보로 저장하기 위한 변수
    	if(b_service.selectBoard(urlnum) != null && b_service.selectBoard(urlnum).is_secret == 0 && b_service.selectBoard(urlnum).is_repo == 0) {
    		before_post = b_service.selectBoard(urlnum);
    	}
    	else if(b_service.SCselectBoard(urlnum) != null && b_service.SCselectBoard(urlnum).is_secret == 1 && b_service.SCselectBoard(urlnum).is_repo == 0) {
    		before_post = b_service.SCselectBoard(urlnum);
    	}
    	else if(b_service.REPOselectBoard(urlnum) != null && b_service.REPOselectBoard(urlnum).is_secret == 0 && b_service.REPOselectBoard(urlnum).is_repo == 1) {
    		before_post = b_service.REPOselectBoard(urlnum);
    	}
    	else if(b_service.REPOSCselectBoard(urlnum) != null && b_service.REPOSCselectBoard(urlnum).is_secret == 1 && b_service.REPOSCselectBoard(urlnum).is_repo == 1) {
    		before_post = b_service.REPOSCselectBoard(urlnum);
    	}
    	
    	// 세션에 보관한 회원 객체를 새로운 멤버 객체에 찾아 넣어준다.
    	LoginDTO loginmember = (LoginDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
    	if (loginmember == null) {
    		model.addAttribute("session_msg", false);
    		return "redirect:/";
    	}
    	else if((loginmember.id).equals(before_post.id) == false) {
    		//model.addAttribute("another_msg", false);
    		return "redirect:/user/ERROR_PAGE";
    	}
    	
    	model.addAttribute("member", loginmember);
    	
    	
    	before_post.setcontent(before_post.getcontent().replace("\r\n", "<br>")); 	// 게시글 수정 시 사용한 Enter(줄바꿈)이 적용 되도록 재저장. (구글링을 통해 해당 정보 습득)
    	
    	// 수정하기 전 게시글의 정보를 JSP로 보냄(고유번호, 작성자, 제목, 내용)
    	model.addAttribute("SelectPost", before_post);
    	model.addAttribute("post_num", before_post.num);
    	model.addAttribute("post_id", before_post.id);
    	model.addAttribute("post_nickname", before_post.nickname);
    	model.addAttribute("post_title", before_post.title);
    	model.addAttribute("post_content", before_post.content);
    	
    	
    	int b_num = urlnum;
    	
    	List<FileDTO> fileList = b_service.fileViewer(b_num);
			
		
		// 이미지와 동영상 파일만 가져오는 쿼리문을 통해 가져와서 JSP로 보낸다.
		List<FileDTO> fileViewer = b_service.viewFile(b_num);
		model.addAttribute("fileViewer", fileViewer);
		
		
		model.addAttribute("fileDown", fileList);	// 해당 게시글의 파일 전체 리스트이다. (다운로드 기능 구현때 사용할 예정) 
    	
		
		return "user/update_board";
    }
    
    @RequestMapping(value = "/user/update_board", method = RequestMethod.POST)			// , @RequestParam(value = "existing_Delete_fileNum", required = false) int[] delete_fn								, @RequestParam(value = "existing_Delete_fileNum", required = false) List<String> delete_file_str
    public String update_board(Model model, HttpServletRequest request, HttpServletResponse response, boardDTO letter, FileDTO uploadfile, @RequestParam(value = "files", required = false) MultipartFile[] files, deleteFileDTO deletefile) throws Exception {
    	HttpSession session = request.getSession(false);
    	
    	if(session == null || !request.isRequestedSessionIdValid()) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		model.addAttribute("session_msg", false);
    		return "redirect:/LoginPage";
    	}
    	else if(session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
    		model.addAttribute("session_msg", true);
        	session.invalidate();
        	return "redirect:/LoginPage";
    	}
    	
    	LoginDTO loginmember = (LoginDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
    	if (loginmember == null) {
    		model.addAttribute("session_msg", false);
    		return "redirect:/";
    	}
    	model.addAttribute("member", loginmember);
    	
    	
    	String url = "redirect:/user/posts?urlnum=";

    	if(letter.title != null && letter.content != null && letter.title.length() != 0 && letter.content.length() != 0) {
    		if(letter.title.length() <= 50 && letter.content.length() <= 1000) {
    			logger.info("글수정 성공!");
    			
    			//System.out.println("hidden 값 >>>>>>"+ existing_Delete_fileNum.get(0) + existing_Delete_fileNum.get(1));
    			
    			/* form으로 삭제할 파일들의 고유번호를 배열로 받아오기 실패... 
    			 * 현재는 자바스크립트에서 컨트롤러의 파일삭제 주소를 직접 호출하는 방식을 시도중...
    			 * 이는 계속해서 프론트단과 백엔드단이 통신하는 방식이기에 느려지고 비효율적이므로 향후 수정요망.
    			 * 는 이것도 안되구요 ㅎㅎ
    			 * 
    			 * 
    			 * 결국 deleteFileDTO를 만들어서 해당 객체에 삭제할 파일의 번호들을 배열 형식으로 저장하는 방식으로 해결했다.
    			 * RequestParam은 key에 매칭되는 하나의 필드값을 받을수 있다는 특성을 깨닫고, RequestParam을 사용하지않고 객체를 통해 통채로 받는 방식을 선택했다.
    			*/
 	 			
    			// 개별 파일 삭제구문
    	    	if(deletefile != null && deletefile.existing_Delete_fileNum.length != 0) {
    	    		int[] file_num = new int[deletefile.existing_Delete_fileNum.length];
    	    		for(int i=0; i<file_num.length; i++) {
    	    			if(file_num[i] != -1) {
    	    				file_num[i] = Integer.parseInt(deletefile.existing_Delete_fileNum[i]);
        	    	    	b_service.deleteFile(file_num[i]);
    	    			}
    	        	}
    	    	}
    			
    			
    			// 파일 업로드 구문
    			if(!files[0].isEmpty() || files.length != 0) {		// 첫 번째 인덱스가 비었을 경우 실행이 되지않음(첫 번째 인덱스가 비었다 = 파일이 등록된게 하나도 없다)
    				
    				int b_num = letter.num;		
    				String user_id = letter.id;
    				
    				for(int i=0; i<files.length; i++) {
    					if(!files[i].isEmpty()) {		// 비어있는 것은 생략함.
    						String fileName = null;
    	    	   	    	
        					// jsp에서 가져온 파일의 이름을 가져와 용도에 맞게 처리한다.
                	    	String originalFileName = files[i].getOriginalFilename();
                	    	String ext = FilenameUtils.getExtension(originalFileName);
                	    	UUID uuid = UUID.randomUUID();
                	    	fileName = uuid + "." + ext;
                	    		      	    		
                	    	// 파일을 해당 경로에 저장시키는 실질적인 구문.
                	    	files[i].transferTo(new File("D:\\MyOwnRepository_DATA\\upload\\" + fileName));
                	    		
                	    	// 파일 업로드용 vo(dto)에 데이터를 저장 시킨 후 파일업로드 쿼리를 실행시킨다.             	    	
                	    	uploadfile.setb_num(b_num);
                	    	uploadfile.setuser_id(user_id);
                	    	uploadfile.setoriginal_file_name(originalFileName);
                	    	uploadfile.setstored_file_name(fileName);
                	    	uploadfile.setstored_path("D:\\MyOwnRepository_DATA\\upload\\"+fileName);
                	    	uploadfile.setfile_size(files[i].getSize());
                	    	
                	    	// 파일 업로드시 파일 타입도 같이 저장하기 위해 추가했다.
                	    	File file = new File("D:\\MyOwnRepository_DATA\\upload\\"+fileName);
                	    	String mimeType	= new Tika().detect(file);		// 파일 다운로드 방식에 대해 알아보다가 
    																		// Apache Tika 라는 컨텐츠 분석 라이브러리를 발견하여 사용했다. 
                	    	uploadfile.settype(mimeType);				
                	    	
                	    	b_service.fileUpload(uploadfile);
    					}
    						
    				}
  				
    	    	}
    			
    			b_service.updateBoard(letter);
    			
    			url += letter.num;
    		}
    		else if(letter.title.length() > 35 || letter.content.length() > 1000) {
    			logger.info("글수정 실패..");
    			model.addAttribute("effectiveness_msg", false);
    			url = "redirect:/user/update_board?urlnum="+letter.num;
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
	@RequestMapping(value = "/user/posts", method = RequestMethod.GET)		// 게시글에 대한 내용을 jsp에서 받아오고 연산하는곳
    public String posts(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam("urlnum") int urlnum, commentDTO letter) throws Exception {
    	HttpSession session = request.getSession(false);
    	if(session == null || !request.isRequestedSessionIdValid()) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		model.addAttribute("session_msg", false);
    		return "redirect:/LoginPage";
    	}
    	else if(session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
    		model.addAttribute("session_msg", true);
        	session.invalidate();
        	return "redirect:/LoginPage";
    	}
    	
    	// 세션에 보관한 회원 객체를 새로운 멤버 객체에 찾아 넣어준다.
    	LoginDTO loginmember = (LoginDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
    	if (loginmember == null) {
    		model.addAttribute("session_msg", false);
    		return "redirect:/";
    	}
    	
    	model.addAttribute("member", loginmember);
    	model.addAttribute("userNickname", loginmember.nickname);
    	
    	String path = "redirect:/user/posts";
    	
    	int number = urlnum;		// urlnum은 해당 게시글의 고유번호를 의미
    	boardDTO user_show_post = null;
    	
    	int first_num = -1;
    	int last_num = -1;
    	
    	int pre_num=1, next_num=1;		// 열람중인 해당 게시글의 이전글과 다음글의 고유번호를 찾을때 사용 할 정수형 변수 pre_num과 next_num;
    	
     	boardDTO pre_posts = null;
     	boardDTO next_posts = null;
    	
     	
     	String is_what = "";
     	
     	// 자유게시판
    	if(b_service.selectBoard(number) != null && b_service.selectBoard(number).is_exist == 1 && b_service.selectBoard(number).is_secret == 0 && b_service.selectBoard(number).is_repo == 0) {
    		System.out.println("is_secret >>>>>>>> "+b_service.selectBoard(number).is_secret);
    		user_show_post = b_service.selectBoard(number);
    		
         	// 첫 게시글과 마지막 게시글의 고유 번호를 저장하는 first_num과 last_num
         	first_num = b_service.selectMinNum();     	 	
         	last_num = b_service.selectMaxNum();
         	
         	// 열람중인 해당 게시글이 첫 글이 아닐 경우 실행되는 조건문
         	if(number != first_num) {		
         		// 이전글을 찾는 반복문
         		// 해당 글의 고유번호의 바로 이전 고유번호가 삭제된 글(번호)일때 실행된다.
             	while(b_service.selectBoard(number-pre_num) == null || b_service.selectBoard(number-pre_num).is_exist == 0) {
             		if((number-pre_num) == first_num) {		// 해당 글이 첫 게시글이면 멈춤
                 		break;
                 	}
                 	pre_num++;		// 보다 더 이전의 게시글의 고유번호를 찾기 위한 연산이다. (이전글중 삭제되지 않은 고유번호를 찾기위함)           	
         		}
             	pre_posts = b_service.selectBoard(number-pre_num);		// 이전글 저장
             	    	
         	}
         	else if(number == first_num) {		// 처음 선택한 게시글이 첫 글 일경우 이전글에 해당 글이 나오도록 함. 
         		pre_posts = b_service.selectBoard(first_num);
         	}
         		
         	// 열람중인 해당 게시글이 마지막 글이 아닐 경우 실행되는 조건문
         	if(number != last_num) {
         		// 다음글을 찾는 반복문
         		// 해당 글의 고유번호의 바로 다음 고유번호가 삭제된 글(번호)일때 실행된다.
             	while(b_service.selectBoard(number+next_num) == null || b_service.selectBoard(number+next_num).is_exist == 0 ) {
             		if((number+next_num) == last_num) {		// 해당 글이 마지막 게시글이면 멈춤
                 		break;
                 	}
                 	next_num++;		// 다음 게시글의 고유번호를 찾기 위한 연산이다. (다음글중 삭제되지 않은 고유번호를 찾기위함)           	
         		}
             	next_posts = b_service.selectBoard(number+next_num);		// 다음글 저장
             		
         	}   
         	else if(number == last_num) {		// 처음 선택한 게시글이 마지막 글 일경우 이전글에 해당 글이 나오도록 함. 
         		next_posts = b_service.selectBoard(last_num);
         	}
       	
        	
        	
        	user_show_post.setcontent(user_show_post.getcontent().replace("\r\n", "<br>")); 	// 게시글 작성 시 사용한 Enter(줄바꿈)이 적용 되도록 재저장. (구글링을 통해 해당 정보 습득)
        	
        	if(user_show_post == null || b_service.selectBoard(number).is_exist == 0) {				// 열람을 위해 클릭한 다른 유저의 게시글이 이미 삭제되었을 경우 실행되는 조건문 
        		model.addAttribute("msg", false);
        		path = "redirect:/user/userMain/1";
        	}
        	else {
        		b_service.addView(number);			// 조회수 증가
        	}
        	is_what = "s0r0";
        	model.addAttribute("what", is_what);
    	}
    	
    	// 비밀 게시판
    	else if(b_service.SCselectBoard(number) != null && b_service.SCselectBoard(number).is_exist == 1 && b_service.SCselectBoard(number).is_secret == 1 && b_service.SCselectBoard(number).is_repo == 0) {
    		System.out.println("is_secret >>>>>>>> "+b_service.SCselectBoard(number).is_secret);
    		
    		// 해당 비밀 게시글을 열람할 수 있는 권한이 있는지 여부 검사
        	String allow_post = (String) session.getAttribute(ALLOW_POST);		// 사용자 세션에 저장된 허용된 게시글 목록을 가져온다.
        	String want_post = "n"+Integer.toString(urlnum)+",";		// 접속하려는 게시글의 번호를 용도에 맞게 변환
       
        	if(allow_post == null || allow_post.contains(want_post) == false) {
        		System.out.println("해당 비밀게시글의 인가된 사용자가 아님!!!!!!!");
        		return "redirect:/user/ERROR_PAGE";
        	}
    		
        	
        	
    		user_show_post = b_service.SCselectBoard(number);
    		
         	first_num = b_service.SCselectMinNum();     	 	
         	last_num = b_service.SCselectMaxNum();
         	
         	// 열람중인 해당 게시글이 첫 글이 아닐 경우 실행되는 조건문
         	if(number != first_num) {		
         		// 이전글을 찾는 반복문
         		// 해당 글의 고유번호의 바로 이전 고유번호가 삭제된 글(번호)일때 실행된다.
             	while(b_service.SCselectBoard(number-pre_num) == null || b_service.SCselectBoard(number-pre_num).is_exist == 0) {
             		if((number-pre_num) == first_num) {		// 해당 글이 첫 게시글이면 멈춤
                 		break;
                 	}
                 	pre_num++;		// 보다 더 이전의 게시글의 고유번호를 찾기 위한 연산이다. (이전글중 삭제되지 않은 고유번호를 찾기위함)           	
         		}
             	pre_posts = b_service.SCselectBoard(number-pre_num);		// 이전글 저장
             	    	
         	}
         	else if(number == first_num) {		// 처음 선택한 게시글이 첫 글 일경우 이전글에 해당 글이 나오도록 함. 
         		pre_posts = b_service.SCselectBoard(first_num);
         	}
         		
         	// 열람중인 해당 게시글이 마지막 글이 아닐 경우 실행되는 조건문
         	if(number != last_num) {
         		// 다음글을 찾는 반복문
         		// 해당 글의 고유번호의 바로 다음 고유번호가 삭제된 글(번호)일때 실행된다.
             	while(b_service.SCselectBoard(number+next_num) == null || b_service.SCselectBoard(number+next_num).is_exist == 0 ) {
             		if((number+next_num) == last_num) {		// 해당 글이 마지막 게시글이면 멈춤
                 		break;
                 	}
                 	next_num++;		// 다음 게시글의 고유번호를 찾기 위한 연산이다. (다음글중 삭제되지 않은 고유번호를 찾기위함)           	
         		}
             	next_posts = b_service.SCselectBoard(number+next_num);		// 다음글 저장
             		
         	}   
         	else if(number == last_num) {		// 처음 선택한 게시글이 마지막 글 일경우 이전글에 해당 글이 나오도록 함. 
         		next_posts = b_service.SCselectBoard(last_num);
         	}
       	
        	
        	
        	user_show_post.setcontent(user_show_post.getcontent().replace("\r\n", "<br>")); 	// 게시글 작성 시 사용한 Enter(줄바꿈)이 적용 되도록 재저장. (구글링을 통해 해당 정보 습득)
        	
        	if(user_show_post == null || b_service.SCselectBoard(number).is_exist == 0) {				// 열람을 위해 클릭한 다른 유저의 게시글이 이미 삭제되었을 경우 실행되는 조건문 
        		model.addAttribute("msg", false);
        		path = "redirect:/user/secretBoard/1";
        	}
        	else {
        		b_service.addView(number);			// 조회수 증가
        	}  
        	is_what = "s1r0";
        	model.addAttribute("what", is_what);
        	
    	}
    	
    	// 공유 저장소
    	else if(b_service.REPOselectBoard(number) != null && b_service.REPOselectBoard(number).is_exist == 1 && b_service.REPOselectBoard(number).is_secret == 0 && b_service.REPOselectBoard(number).is_repo == 1){
    		System.out.println("is_secret >>>>>>>> "+b_service.REPOselectBoard(number).is_secret);
    		user_show_post = b_service.REPOselectBoard(number);
    		
         	// 첫 게시글과 마지막 게시글의 고유 번호를 저장하는 first_num과 last_num
         	first_num = b_service.REPOselectMinNum(0);     	 	
         	last_num = b_service.REPOselectMaxNum(0);
         	
         	// 열람중인 해당 게시글이 첫 글이 아닐 경우 실행되는 조건문
         	if(number != first_num) {		
         		// 이전글을 찾는 반복문
         		// 해당 글의 고유번호의 바로 이전 고유번호가 삭제된 글(번호)일때 실행된다.
             	while(b_service.REPOselectBoard(number-pre_num) == null || b_service.REPOselectBoard(number-pre_num).is_exist == 0) {
             		if((number-pre_num) == first_num) {		// 해당 글이 첫 게시글이면 멈춤
                 		break;
                 	}
                 	pre_num++;		// 보다 더 이전의 게시글의 고유번호를 찾기 위한 연산이다. (이전글중 삭제되지 않은 고유번호를 찾기위함)           	
         		}
             	pre_posts = b_service.REPOselectBoard(number-pre_num);		// 이전글 저장
             	    	
         	}
         	else if(number == first_num) {		// 처음 선택한 게시글이 첫 글 일경우 이전글에 해당 글이 나오도록 함. 
         		pre_posts = b_service.REPOselectBoard(first_num);
         	}
         		
         	// 열람중인 해당 게시글이 마지막 글이 아닐 경우 실행되는 조건문
         	if(number != last_num) {
         		// 다음글을 찾는 반복문
         		// 해당 글의 고유번호의 바로 다음 고유번호가 삭제된 글(번호)일때 실행된다.
             	while(b_service.REPOselectBoard(number+next_num) == null || b_service.REPOselectBoard(number+next_num).is_exist == 0 ) {
             		if((number+next_num) == last_num) {		// 해당 글이 마지막 게시글이면 멈춤
                 		break;
                 	}
                 	next_num++;		// 다음 게시글의 고유번호를 찾기 위한 연산이다. (다음글중 삭제되지 않은 고유번호를 찾기위함)           	
         		}
             	next_posts = b_service.REPOselectBoard(number+next_num);		// 다음글 저장
             		
         	}   
         	else if(number == last_num) {		// 처음 선택한 게시글이 마지막 글 일경우 이전글에 해당 글이 나오도록 함. 
         		next_posts = b_service.REPOselectBoard(last_num);
         	}
       	
        	
        	
        	user_show_post.setcontent(user_show_post.getcontent().replace("\r\n", "<br>")); 	// 게시글 작성 시 사용한 Enter(줄바꿈)이 적용 되도록 재저장. (구글링을 통해 해당 정보 습득)
        	
        	if(user_show_post == null || b_service.REPOselectBoard(number).is_exist == 0) {				// 열람을 위해 클릭한 다른 유저의 게시글이 이미 삭제되었을 경우 실행되는 조건문 
        		model.addAttribute("msg", false);
        		path = "redirect:/user/userMain/1";
        	}
        	else {
        		b_service.addView(number);			// 조회수 증가
        	}
        	is_what = "s0r1";
        	model.addAttribute("what", is_what);
    	}
    	
    	// 비밀 저장소
    	else if(b_service.REPOSCselectBoard(number) != null && b_service.REPOSCselectBoard(number).is_exist == 1 && b_service.REPOSCselectBoard(number).is_secret == 1 && b_service.REPOSCselectBoard(number).is_repo == 1) {
    		System.out.println("is_secret >>>>>>>> "+b_service.REPOSCselectBoard(number).is_secret);
    		
    		// 해당 비밀 게시글을 열람할 수 있는 권한이 있는지 여부 검사
        	String allow_post = (String) session.getAttribute(ALLOW_POST);		// 사용자 세션에 저장된 허용된 게시글 목록을 가져온다.
        	String want_post = "n"+Integer.toString(urlnum)+",";		// 접속하려는 게시글의 번호를 용도에 맞게 변환
       
        	if(allow_post == null || allow_post.contains(want_post) == false) {
        		System.out.println("해당 비밀게시글의 인가된 사용자가 아님!!!!!!!");
        		return "redirect:/user/ERROR_PAGE";
        	}
    		
        	
        	
    		user_show_post = b_service.REPOSCselectBoard(number);
    		
         	first_num = b_service.REPOselectMinNum(1);     	 	
         	last_num = b_service.REPOselectMaxNum(1);
         	
         	// 열람중인 해당 게시글이 첫 글이 아닐 경우 실행되는 조건문
         	if(number != first_num) {		
         		// 이전글을 찾는 반복문
         		// 해당 글의 고유번호의 바로 이전 고유번호가 삭제된 글(번호)일때 실행된다.
             	while(b_service.REPOSCselectBoard(number-pre_num) == null || b_service.REPOSCselectBoard(number-pre_num).is_exist == 0) {
             		if((number-pre_num) == first_num) {		// 해당 글이 첫 게시글이면 멈춤
                 		break;
                 	}
                 	pre_num++;		// 보다 더 이전의 게시글의 고유번호를 찾기 위한 연산이다. (이전글중 삭제되지 않은 고유번호를 찾기위함)           	
         		}
             	pre_posts = b_service.REPOSCselectBoard(number-pre_num);		// 이전글 저장
             	    	
         	}
         	else if(number == first_num) {		// 처음 선택한 게시글이 첫 글 일경우 이전글에 해당 글이 나오도록 함. 
         		pre_posts = b_service.REPOSCselectBoard(first_num);
         	}
         		
         	// 열람중인 해당 게시글이 마지막 글이 아닐 경우 실행되는 조건문
         	if(number != last_num) {
         		// 다음글을 찾는 반복문
         		// 해당 글의 고유번호의 바로 다음 고유번호가 삭제된 글(번호)일때 실행된다.
             	while(b_service.REPOSCselectBoard(number+next_num) == null || b_service.REPOSCselectBoard(number+next_num).is_exist == 0 ) {
             		if((number+next_num) == last_num) {		// 해당 글이 마지막 게시글이면 멈춤
                 		break;
                 	}
                 	next_num++;		// 다음 게시글의 고유번호를 찾기 위한 연산이다. (다음글중 삭제되지 않은 고유번호를 찾기위함)           	
         		}
             	next_posts = b_service.REPOSCselectBoard(number+next_num);		// 다음글 저장
             		
         	}   
         	else if(number == last_num) {		// 처음 선택한 게시글이 마지막 글 일경우 이전글에 해당 글이 나오도록 함. 
         		next_posts = b_service.REPOSCselectBoard(last_num);
         	}
       	
        	
        	
        	user_show_post.setcontent(user_show_post.getcontent().replace("\r\n", "<br>")); 	// 게시글 작성 시 사용한 Enter(줄바꿈)이 적용 되도록 재저장. (구글링을 통해 해당 정보 습득)
        	
        	if(user_show_post == null || b_service.REPOSCselectBoard(number).is_exist == 0) {				// 열람을 위해 클릭한 다른 유저의 게시글이 이미 삭제되었을 경우 실행되는 조건문 
        		model.addAttribute("msg", false);
        		path = "redirect:/user/secretBoard/1";
        	}
        	else {
        		b_service.addView(number);			// 조회수 증가
        	}  
        	is_what = "s1r1";
        	model.addAttribute("what", is_what);
    	}
    	
    	
     	 /*	
     	// 열람중인 해당 게시글이 첫 글이 아닐 경우 실행되는 조건문
     	if(number != first_num) {		
     		// 이전글을 찾는 반복문
     		// 해당 글의 고유번호의 바로 이전 고유번호가 삭제된 글(번호)일때 실행된다.
         	while(b_service.selectBoard(number-pre_num) == null || b_service.selectBoard(number-pre_num).is_exist == 0) {
         		if((number-pre_num) == first_num) {		// 해당 글이 첫 게시글이면 멈춤
             		break;
             	}
             	pre_num++;		// 보다 더 이전의 게시글의 고유번호를 찾기 위한 연산이다. (이전글중 삭제되지 않은 고유번호를 찾기위함)           	
     		}
         	pre_posts = b_service.selectBoard(number-pre_num);		// 이전글 저장
         	    	
     	}
     	else if(number == first_num) {		// 처음 선택한 게시글이 첫 글 일경우 이전글에 해당 글이 나오도록 함. 
     		pre_posts = b_service.selectBoard(first_num);
     	}
     		
     	// 열람중인 해당 게시글이 마지막 글이 아닐 경우 실행되는 조건문
     	if(number != last_num) {
     		// 다음글을 찾는 반복문
     		// 해당 글의 고유번호의 바로 다음 고유번호가 삭제된 글(번호)일때 실행된다.
         	while(b_service.selectBoard(number+next_num) == null || b_service.selectBoard(number+next_num).is_exist == 0 ) {
         		if((number+next_num) == last_num) {		// 해당 글이 마지막 게시글이면 멈춤
             		break;
             	}
             	next_num++;		// 다음 게시글의 고유번호를 찾기 위한 연산이다. (다음글중 삭제되지 않은 고유번호를 찾기위함)           	
     		}
         	next_posts = b_service.selectBoard(number+next_num);		// 다음글 저장
         		
     	}   
     	else if(number == last_num) {		// 처음 선택한 게시글이 마지막 글 일경우 이전글에 해당 글이 나오도록 함. 
     		next_posts = b_service.selectBoard(last_num);
     	}
   	
    	
    	
    	user_show_post.setcontent(user_show_post.getcontent().replace("\r\n", "<br>")); 	// 게시글 작성 시 사용한 Enter(줄바꿈)이 적용 되도록 재저장. (구글링을 통해 해당 정보 습득)
    	
    	if(user_show_post == null || b_service.selectBoard(number).is_exist == 0) {				// 열람을 위해 클릭한 다른 유저의 게시글이 이미 삭제되었을 경우 실행되는 조건문 
    		model.addAttribute("msg", false);
    		path = "redirect:/user/userMain";
    	}
    	else {
    		b_service.addView(number);			// 조회수 증가
    	}
    	*/
    	
    	model.addAttribute("page_name", user_show_post.nickname);		// JSP의 script에서 게시글 작성자와 게시글 열람하는 사용자가 동일 회원인지 아닌지 구분하기 위해
		// 게시글 작성자의 닉네임을 JSP로 보내는 구문이다.

		model.addAttribute("page_num", user_show_post.num);		// 게시글 삭제에 필요한 게시글의 고유번호를 JSP에 보내는 구문이다.
		
		model.addAttribute("SelectPost", user_show_post);		// 해당 게시글의 모든 정보(댓글 제외)를 JSP에 보내는 구문.    
		
		// 이전글과 다음글의 정보를 jsp로 보내기
		model.addAttribute("pre_post", pre_posts);
		model.addAttribute("next_post", next_posts);
		
		List<commentDTO> cmt = b_service.printComment(user_show_post.num);
		
		for(int i=0; i<cmt.size(); i++) {	// 댓글의 모든 줄바꿈문자 <br>형식으로 변경하여 저장
		cmt.get(i).setcontent(cmt.get(i).getcontent().replace("\r\n", "<br>")); 	// 댓글 작성 시 사용한 Enter(줄바꿈)이 적용 되도록 재저장. (구글링을 통해 해당 정보 습득)	
		}
		
		model.addAttribute("printComment", cmt);		// 해당 고유번호를 가진 게시글의 모든 댓글의 정보를 jsp에 보내는 구문
		
		List<FileDTO> fileList = b_service.fileViewer(user_show_post.num);
		
		
		
		// 이미지와 동영상 파일만 가져오는 쿼리문을 통해 가져와서 JSP로 보낸다.
		List<FileDTO> fileViewer = b_service.viewFile(user_show_post.num);
		model.addAttribute("fileViewer", fileViewer);
		
		
		model.addAttribute("fileDown", fileList);	// 해당 게시글의 파일 전체 리스트이다. (다운로드 기능 구현때 사용할 예정) 
		
		
		String[] files_type = new String[fileList.size()];
		for(int i=0; i<fileList.size(); i++) {
		files_type[i] = fileList.get(i).type;
		}
		model.addAttribute("files_type", files_type);
		
		
		return "/user/posts";   	
    }
    
   
    // 게시글에 댓글 작성을 위해 jsp에서 정보를 받아오고 연산하기위해 만들어진 부분
    @RequestMapping(value = "/user/posts/comment", method = RequestMethod.POST)		
    public String comment(HttpServletRequest request, HttpServletResponse response, Model model, commentDTO letter) throws Exception {
    	HttpSession session = request.getSession(false);
    	  	
    	// 이 부분은 기능을 수행할 때 세션과 연관된 부분이 아니기에, 세션 만료시 로그인 페이지로 넘기는 작업을 할 필요없어서 생략했다.
    	// 세션이 필요 한 것 같아서 일단 넣어놓고 보류. (단, 세션 종료 알림은 넣지않음)  	
    	if(session == null || !request.isRequestedSessionIdValid()) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		return "redirect:/LoginPage";
    	}
    	else if(session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
        	session.invalidate();
        	return "redirect:/LoginPage";
    	}
    	
    	// 세션에 보관한 회원 객체를 새로운 멤버 객체에 찾아 넣어준다.
    	LoginDTO loginmember = (LoginDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
    	if (loginmember == null) {
    		model.addAttribute("session_msg", false);
    		return "redirect:/";
    	}  	
    	
    	
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
    	String urlnum = Integer.toString(letter.b_num);
    	return "redirect:/user/posts?urlnum="+urlnum;
    }
    
    // 댓글 수정
    @RequestMapping(value = "/user/posts/comment_update", method = RequestMethod.POST)
    public String update_comment(HttpServletRequest request, Model model, commentDTO cmt) throws Exception{		//, @PathVariable("b_num") int b_num, @PathVariable("c_num") int c_num, 
    	HttpSession session = request.getSession(false);
    	
    	// 이 부분은 기능을 수행할 때 세션과 연관된 부분이 아니기에, 세션 만료시 로그인 페이지로 넘기는 작업을 할 필요없어서 생략했다.
    	// 세션이 필요 한 것 같아서 일단 넣어놓고 보류. (단, 세션 종료 알림은 넣지않음)  	
    	if(session == null || !request.isRequestedSessionIdValid()) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		return "redirect:/LoginPage";
    	}
    	else if(session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
        	session.invalidate();
        	return "redirect:/LoginPage";
    	}
    
    	if(cmt.content != null) {		// 댓글 작성하는 구문
    		if(cmt.content.length() > 0 && cmt.content.length() <= 300) {
    			logger.info("댓글수정 성공!");
   		
    			b_service.updateComment(cmt);		// 게시글의 댓글 수정
    			model.addAttribute("cu_msg", true);		// 업데이트 성공 메시지 - 댓글 수정이 완료 되면 댓글 수정 폼을 숨기고 수정된 뷰를 보여주기 위함.
    		}
    		else {
    			logger.info("댓글수정 실패..");
    			model.addAttribute("c_msg", false);
    		}
    	}
    	
    	return "redirect:/user/posts?urlnum="+cmt.b_num;
    }
    
    
    
    // 댓글 삭제
    @RequestMapping(value = {"/user/posts/comment_delete/{b_num}/{c_num}"})
    public String delete_comment(HttpServletRequest request, @PathVariable("b_num") String b_num, @PathVariable("c_num") String c_num) throws Exception{
    	HttpSession session = request.getSession(false);
    	
    	// 이 부분은 기능을 수행할 때 세션과 연관된 부분이 아니기에, 세션 만료시 로그인 페이지로 넘기는 작업을 할 필요없어서 생략했다.
    	// 세션이 필요 한 것 같아서 일단 넣어놓고 보류. (단, 세션 종료 알림은 넣지않음)  	
    	if(session == null || !request.isRequestedSessionIdValid()) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		return "redirect:/LoginPage";
    	}
    	else if(session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
        	session.invalidate();
        	return "redirect:/LoginPage";
    	}
    	
    	int board_num = Integer.parseInt(b_num);
    	int comment_num = Integer.parseInt(c_num);
    	
    	b_service.deleteComment(comment_num);		// 게시글의 댓글 삭제
    	b_service.downComment(board_num);		// 게시글의 댓글 수 1개 줄이기
    	
    	return "redirect:/user/posts?urlnum="+board_num;
    }
    
    // 게시글의 첨부 파일들을 사용자들에게 보여줌.
    
    @RequestMapping(value = "/loadfiles.do/{files_num}" , method = RequestMethod.GET)
    public void displayFiles(Model model, HttpServletRequest request, HttpServletResponse response, @PathVariable("files_num") int files_num) throws Exception{
    	//System.out.println("일단 실행은 됐고 files_number 값은? >>>> " + files_num);
    	
    	FileDTO fileOne = b_service.fileView(files_num);
    	
    	File file = new File(fileOne.stored_path);
    	String mimeType	= new Tika().detect(file);		// 파일 다운로드 방식에 대해 알아보다가 
    														// Apache Tika 라는 컨텐츠 분석 라이브러리를 발견하여 사용했다.
    	byte[] fileData = (byte[]) FileUtils.readFileToByteArray(file);
    		
    	response.setContentType(mimeType);
    	response.getOutputStream().write(fileData);
    		   		

    }
    
    // 게시글의 첨부 파일을 다운로드 할 수 있도록 한다.
    
    @RequestMapping(value = "/downfiles.do/{files_num}")
    public void downloadFile(HttpServletResponse response, @PathVariable("files_num") int files_num) throws Exception {
    	FileDTO fileOne = b_service.fileView(files_num);
    	
    	File file = new File(fileOne.stored_path);
    	String mimeType = new Tika().detect(file);
    	
    	if(mimeType==null) {	// 파일의 type이 딱히 없을 경우 mimeType을 임의로 설정함 (ex >> .ini 등)
    		mimeType = "application/octet-stream";
    	}
    	
    	String encodeType = "utf-8";
 
    	response.setContentType(mimeType);		// 파일의 type 설정
    	response.setHeader("Content-Disposition", "attachment; filename=\""+ URLEncoder.encode(fileOne.original_file_name, encodeType).replaceAll("\\+", "%20"));	// 브라우저에 다운로드 할 파일임을 알림.
    	response.setContentLength((int)fileOne.file_size);	// 파일의 size 설정 (file_size는 long으로 선언했으나, 해당 기능의 원활한 사용을 위해 int형으로 변환.)
    														// 향후 long 형식 그대로 사용할 방법 찾기를 요망 (동영상 파일의 경우 파일의 사이즈가 클 수 있기 때문)
    	
    	InputStream inputStream = new BufferedInputStream(new FileInputStream(file));	// 파일에 대한 inputstream 객체를 생성한다.
    	FileCopyUtils.copy(inputStream, response.getOutputStream());	// 다운로드 할 파일을 복사해서 보내준다.
    }
    
    // 게시글 수정시 기존 파일삭제
    @RequestMapping(value = "/deletefiles.do")
    public void deleteFile(HttpServletRequest request, @RequestParam(value = "existing_Delete_fileNum", required = false) String[] delete_file_str) throws Exception{
    	if(!delete_file_str[0].isEmpty()) {
    		int[] file_num = new int[delete_file_str.length];
    		for(int i=0; i<delete_file_str.length; i++) {
    			file_num[i] = Integer.parseInt(delete_file_str[i]);
    	    	b_service.deleteFile(file_num[i]);
        	}
    	}
    	
    }
    
    
    
    // 게시글 삭제
    @RequestMapping(value = "/user/delete_board")
    public String delete_board(HttpServletRequest request, @RequestParam("num") int num) throws Exception {
    	HttpSession session = request.getSession(false);
    	if(session == null || !request.isRequestedSessionIdValid()) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		return "redirect:/LoginPage";
    	}
    	else if(session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
        	session.invalidate();
        	return "redirect:/LoginPage";
    	}
    	
    	String path = "";
    	// 자유게시글 삭제
    	if(b_service.selectBoard(num) != null && b_service.selectBoard(num).is_exist == 1) {
    		b_service.allDeleteComment(num);		// 모든 댓글 삭제
    		b_service.allDeleteFile(num);		// 모든 파일 삭제
    		b_service.deleteBoard(num);		// 게시글 삭제
    		path = "redirect:/user/userMain";
    	}
    	// 비밀게시글 삭제
    	else if(b_service.SCselectBoard(num) != null && b_service.SCselectBoard(num).is_exist == 1) {
    		b_service.allDeleteComment(num);		// 모든 댓글 삭제
    		b_service.allDeleteFile(num);		// 모든 파일 삭제
    		b_service.deleteBoard(num);		// 게시글 삭제
    		path = "redirect:/user/secretBoard";
    	}
    	// 공유저장소글 삭제
    	else if(b_service.REPOselectBoard(num) != null && b_service.REPOselectBoard(num).is_exist == 1) {
    		b_service.allDeleteComment(num);		// 모든 댓글 삭제
    		b_service.allDeleteFile(num);		// 모든 파일 삭제
    		b_service.deleteBoard(num);		// 게시글 삭제
    		path = "redirect:/user/sharingRepo";
    	}
    	// 비밀저장소글 삭제
    	else if(b_service.REPOSCselectBoard(num) != null && b_service.REPOSCselectBoard(num).is_exist == 1) {
    		b_service.allDeleteComment(num);		// 모든 댓글 삭제
    		b_service.allDeleteFile(num);		// 모든 파일 삭제
    		b_service.deleteBoard(num);		// 게시글 삭제
    		path = "redirect:/user/myRepo";
    	}
    	else {
    		path = "redirect:/user/posts?urlnum=";
    		path += num;
    	}
    	
    	return path;
    }
    
    // 비밀게시판
    @RequestMapping(value = "/user/secretBoard/{urlnum}")	
    public String secretBoardShow(HttpServletRequest request, Model model, @PathVariable("urlnum") int urlnum) throws Exception {
    	HttpSession session = request.getSession(false);
    	
    	if(session == null || !request.isRequestedSessionIdValid()) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		model.addAttribute("session_msg", false);
    		return "redirect:/LoginPage";
    	}
    	else if(session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
    		model.addAttribute("session_msg", true);
        	session.invalidate();
        	return "redirect:/LoginPage";
    	}
    	
    	secretboard_first_index = (urlnum-1) * 20;
    	
    	return "redirect:/user/secretBoard";
    }
    @RequestMapping(value = "/user/secretBoard")	
    public String secretBoard(HttpServletRequest request, Model model) throws Exception {
    	HttpSession session = request.getSession(false);
    	
    	if(session == null || !request.isRequestedSessionIdValid()) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		model.addAttribute("session_msg", false);
    		return "redirect:/LoginPage";
    	}
    	else if(session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
    		model.addAttribute("session_msg", true);
        	session.invalidate();
        	return "redirect:/LoginPage";
    	}

    	// 세션에 보관한 회원 객체를 새로운 멤버 객체에 찾아 넣어준다.
    	LoginDTO loginmember = (LoginDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
    	if (loginmember == null) {
    		model.addAttribute("session_msg", false);
    		return "redirect:/";
    	}
    	model.addAttribute("member", loginmember);
    	
        int total = b_service.SCtotalNum();
        int page_count=1;
        
        // 한 페이지에 20개의 글이 보이게 한다.
        // 총 게시글 수의 대입해, 필요한 총 페이지 수(page_count)를 알아 내기위한 반복문.
        while(true) {
        	if((total != 0) && (total / 21) != 0) {
        		page_count++;
        		total -= 20;
        	}
        	else {
        		break;
        	}
        }
        
        /* 하단에 페이지 숫자 범위지정을 위한 과정 ( ex, <이전 1 2 3 4 5 6 7 8 9 10 다음>) */
        
        // 현재 선택한 페이지
        int page = secretboard_first_index / 20 + 1;
        model.addAttribute("select_page", page);
        
        // 처음 페이지의 숫자
        int first_page = 1;
        if(page % 10 == 0) {
        	first_page = page - 9;		
        }
        else {
        	first_page = page - (page % 10) + 1;		
        }
        model.addAttribute("first_page", first_page);
        
        // 마지막 페이지의 숫자
        int last_page = 10;
        if(page % 10 == 0) {
        	last_page = page;
        }
        else if((page / 10 + 1) * 10 > page_count) {		// 마지막 페이지 그룹일 경우 실행 되는 조건문
        	last_page = page_count;
        }
        else {
        	last_page = ((page / 10) + 1) * 10;
        }
        model.addAttribute("last_page", last_page);
        
        // 총 페이지의 숫자
        model.addAttribute("page_count", page_count);
        
        
        System.out.println("메인페이지 페이지수 (선택,처음,마지막,총개수) >>>> " + page + first_page + last_page + page_count);
        
        
        // 처음 웹 사이트를 실행시켰을때는 페이지 디폴트값인 첫번째 페이지를 보여주도록 유도(board_first_num의 값을 선언과 동시에 1로 초기화)하고
        // 이후에는 사용자가 고르는 페이지가 보여지도록 함.       
        List<boardDTO> board_list = b_service.SClimitBoard(secretboard_first_index);
        model.addAttribute("BoardList", board_list);
        
        String dateResult = null;
        // 게시글의 날짜를 현재시간을 기준으로 판별하여
        // 1. 같은 날짜의 게시글 			>> 몇시:몇분
        // 2. 같은 년도의 다른 날짜의 게시글 	>> 몇월-며칠
        // 3. 다른 년도의 게시글				>> 몇년도-몇월-며칠
        // 형식으로 바꾸어 JSP로 보낸다.
        for(int i=0; i<board_list.size(); i++) {
        	dateResult = compareDate(board_list.get(i).date);
        	board_list.get(i).setdate(dateResult);
        }
        
        // 관리자 게시글 목록(전체-펼치기ver)
        List<boardDTO> admin_board_list = b_service.printAdminBoard();
        model.addAttribute("adminBoardList", admin_board_list);
 
        // 관리자 게시글 목록(5개-접기ver)
        List<boardDTO> admin_board_foldList = b_service.limitAdminBoard();
        model.addAttribute("adminFoldList", admin_board_foldList);
    	
    	return "user/secretBoard";
    }
    // 비밀게시판 비밀번호 체크
    @ResponseBody
    @RequestMapping(value = "/user/CheckPassword.do", method = RequestMethod.POST)		// @RequestParam("num") int num		// @RequestParam Map<String, Object> map
    public int CheckSecretNum(HttpServletRequest request, @RequestParam("num") int num, @RequestParam("input") String input_s) throws Exception {
    	HttpSession session = request.getSession(false);
    	
    	//System.out.println("비밀게시글 비밀번호 체크 ajax로 넘어온 값 >>>>>> "+ map);
    	System.out.println("비밀게시글 비밀번호 체크 ajax로 넘어온 값 >>>>>> "+ num + input_s);
    	
    	/*
    	
    	int num = (int) map.get("num"); 	// ajax로 넘어온 게시글 번호
    	String input_s = (String) map.get("input");		// ajax로 넘어온 사용자가 입력한 비밀번호
    	
    	int input = Integer.parseInt(input_s);		// String으로 비밀번호를 int형으로 변환
    	
    	*/
    	int input = Integer.parseInt(input_s);
    	
    	System.out.println("ajax 들어온 num 값 >>>>>>>>>>>>>>>>>>>> "+ num);
    	
    	boardDTO checkSecretNum = b_service.SCselectBoard(num);		// 해당 게시글의 비밀번호를 가져온다
    	if(checkSecretNum == null) {
    		checkSecretNum = b_service.REPOSCselectBoard(num);	
    	}
    	/*	세션에 맵 자체로 넣는 방안1
    	  
    	// 사용자가 입력한 비밀번호와 db에 저장된 게시글 비밀번호가 일치할 경우 실행
		// map을 모두 비우고, 게시글 번호를 key로 두고, 인가 여부를 value로 추가한다.
    	// 작업이 완료된 map을 로그인 세션에 ALLOW_POST라는 이름으로 저장한다.
    	 
    	 */
    	
    	// 사용자가 입력한 비밀번호와 db에 저장된 게시글 비밀번호가 일치할 경우 실행
    	// 세션에 허영된 비밀게시글 번호를 String 형식으로 ALLOW_POST라는 변수에 값을 추가.
    	// 예상 ALLOW_POST 값 예시 >> ALLOW_POST = "allow_post:n146,n147,n163,"
    	// 특정 비밀게시글이 특정 사용자에게 인가된 게시글인지 확인할때는 사용자 세션의 allow_post안에 'n146,' 이나 'n417,'이 포함되어 있는지 체크하여 판별 
    	if(input == checkSecretNum.secret_num) {
    		String allow_num = (String) session.getAttribute(ALLOW_POST);
    		allow_num += "n";
    		allow_num += Integer.toString(num);
    		allow_num += ",";
    		// map.clear();							 
    		// map.put(allow_num, "true");
    		session.setAttribute(ALLOW_POST, allow_num);
    		return 1;
    	}
    	else {
    		return 0;
    	}
    	// map.containsKey("num"); 이걸로 해당 키값이 있는지 확인하면 됨.
    }
    
    
   
    // 공유저장소
    @RequestMapping(value = "/user/sharingRepo/{page}")	
    public String sharingRepoPage(HttpServletRequest request, Model model, @PathVariable("page") int page) throws Exception {
    	HttpSession session = request.getSession(false);
    	
    	/*
    	if(session == null || !request.isRequestedSessionIdValid()) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		model.addAttribute("session_msg", false);
    		return "redirect:/LoginPage";
    	}
    	else if(session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
    		model.addAttribute("session_msg", true);
        	session.invalidate();
        	return "redirect:/LoginPage";
    	}
    	*/
        // 사용자가 선택한 페이지에 따라 sql에 보낼 파라미터 값(LIMIT 시작 인덱스)이 바뀜 (1번째 페이지 일 경우 값은 1, 2번째의 경우 값은 21 .... 20씩 증가)      
        repo_first_index = (page-1) * 20;
    	 
    	
    	return "redirect:/user/sharingRepo";
    }
    
    @RequestMapping(value = "/user/sharingRepo")
    public String sharingRepo(HttpServletRequest request, Model model) throws Exception {
    	HttpSession session = request.getSession(false);
    	
    	/*
    	if(session == null || !request.isRequestedSessionIdValid()) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		model.addAttribute("session_msg", false);
    		return "redirect:/LoginPage";
    	}    
    	
    	if(session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
    		model.addAttribute("session_msg", false);
    		session.invalidate();
    		return "redirect:/";
    	}
    	
    	// 세션에 보관한 회원 객체를 새로운 멤버 객체에 찾아 넣어준다.
    	LoginDTO loginmember = (LoginDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
    	if (loginmember == null) {
    		model.addAttribute("session_msg", false);
    		return "redirect:/";
    	}
    	*/
    	LoginDTO loginmember = (LoginDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
    	model.addAttribute("member", loginmember);
    	
    	model.addAttribute("is_SCrepo", "0");		// 비밀 저장소가 아닌 경우 0값을 보냄
    	
        int total = b_service.REPOtotalNum(0);
        int page_count=1;
        
        // 한 페이지에 20개의 글이 보이게 한다.
        // 총 게시글 수의 대입해, 필요한 총 페이지 수(page_count)를 알아 내기위한 반복문.
        while(true) {
        	if((total != 0) && (total / 21) != 0) {
        		page_count++;
        		total -= 20;
        	}
        	else {
        		break;
        	}
        }
        
        /* 하단에 페이지 숫자 범위지정을 위한 과정 ( ex, <이전 1 2 3 4 5 6 7 8 9 10 다음>) */
        
        // 현재 선택한 페이지
        int page = repo_first_index / 20 + 1;
        model.addAttribute("select_page", page);
        
        // 처음 페이지의 숫자
        int first_page = 1;
        if(page % 10 == 0) {
        	first_page = page - 9;		
        }
        else {
        	first_page = page - (page % 10) + 1;		
        }
        model.addAttribute("first_page", first_page);
        
        // 마지막 페이지의 숫자
        int last_page = 10;
        if(page % 10 == 0) {
        	last_page = page;
        }
        else if((page / 10 + 1) * 10 > page_count) {		// 마지막 페이지 그룹일 경우 실행 되는 조건문
        	last_page = page_count;
        }
        else {
        	last_page = ((page / 10) + 1) * 10;
        }
        model.addAttribute("last_page", last_page);
        
        // 총 페이지의 숫자
        model.addAttribute("page_count", page_count);
        
        
        System.out.println("메인페이지 페이지수 (선택,처음,마지막,총개수) >>>> " + page + first_page + last_page + page_count);
        
        
        // 처음 웹 사이트를 실행시켰을때는 페이지 디폴트값인 첫번째 페이지를 보여주도록 유도(board_first_num의 값을 선언과 동시에 1로 초기화)하고
        // 이후에는 사용자가 고르는 페이지가 보여지도록 함.       
        List<boardDTO> board_list = b_service.REPOlimitBoard(repo_first_index);
        model.addAttribute("BoardList", board_list);
    	
        String dateResult = null;
        // 게시글의 날짜를 현재시간을 기준으로 판별하여
        // 1. 같은 날짜의 게시글 			>> 몇시:몇분
        // 2. 같은 년도의 다른 날짜의 게시글 	>> 몇월-며칠
        // 3. 다른 년도의 게시글				>> 몇년도-몇월-며칠
        // 형식으로 바꾸어 JSP로 보낸다.
        for(int i=0; i<board_list.size(); i++) {
        	dateResult = compareDate(board_list.get(i).date);
        	board_list.get(i).setdate(dateResult);
        }
        
        // 관리자 게시글 목록(전체-펼치기ver)
        List<boardDTO> admin_board_list = b_service.printAdminBoard();
        model.addAttribute("adminBoardList", admin_board_list);
 
        // 관리자 게시글 목록(5개-접기ver)
        List<boardDTO> admin_board_foldList = b_service.limitAdminBoard();
        model.addAttribute("adminFoldList", admin_board_foldList);
        
        return "user/sharing_repo";
    }
    
    
    
    // 나만의 저장소
    @RequestMapping(value = "/user/myRepo/{page}")	
    public String secretRepoView(HttpServletRequest request, Model model, @PathVariable("page") int page) throws Exception {
    	HttpSession session = request.getSession(false);
    	
    	if(session == null || !request.isRequestedSessionIdValid()) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		model.addAttribute("session_msg", false);
    		return "redirect:/LoginPage";
    	}
    	else if(session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
    		model.addAttribute("session_msg", true);
        	session.invalidate();
        	return "redirect:/LoginPage";
    	}
    	
    	secretRepo_first_index = (page-1) * 20;
    	  	
    	
    	return "redirect:/user/myRepo";
    }
    @RequestMapping(value = "/user/myRepo")	
    public String secretRepo(HttpServletRequest request, Model model) throws Exception {
    	HttpSession session = request.getSession(false);
    	
    	if(session == null || !request.isRequestedSessionIdValid()) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		model.addAttribute("session_msg", false);
    		return "redirect:/LoginPage";
    	}
    	else if(session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
    		model.addAttribute("session_msg", true);
        	session.invalidate();
        	return "redirect:/LoginPage";
    	}
    	
    	LoginDTO loginmember = (LoginDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
    	model.addAttribute("member", loginmember);
    	
    	model.addAttribute("is_SCrepo", "1");		// 비밀 저장소가 아닌 경우 0값을 보냄
    	
        int total = b_service.REPOtotalNum(1);
        int page_count=1;
        
        // 한 페이지에 20개의 글이 보이게 한다.
        // 총 게시글 수의 대입해, 필요한 총 페이지 수(page_count)를 알아 내기위한 반복문.
        while(true) {
        	if((total != 0) && (total / 21) != 0) {
        		page_count++;
        		total -= 20;
        	}
        	else {
        		break;
        	}
        }
        
        /* 하단에 페이지 숫자 범위지정을 위한 과정 ( ex, <이전 1 2 3 4 5 6 7 8 9 10 다음>) */
        
        // 현재 선택한 페이지
        int page = repo_first_index / 20 + 1;
        model.addAttribute("select_page", page);
        
        // 처음 페이지의 숫자
        int first_page = 1;
        if(page % 10 == 0) {
        	first_page = page - 9;		
        }
        else {
        	first_page = page - (page % 10) + 1;		
        }
        model.addAttribute("first_page", first_page);
        
        // 마지막 페이지의 숫자
        int last_page = 10;
        if(page % 10 == 0) {
        	last_page = page;
        }
        else if((page / 10 + 1) * 10 > page_count) {		// 마지막 페이지 그룹일 경우 실행 되는 조건문
        	last_page = page_count;
        }
        else {
        	last_page = ((page / 10) + 1) * 10;
        }
        model.addAttribute("last_page", last_page);
        
        // 총 페이지의 숫자
        model.addAttribute("page_count", page_count);
        
        
        System.out.println("메인페이지 페이지수 (선택,처음,마지막,총개수) >>>> " + page + first_page + last_page + page_count);
        
        
        // 처음 웹 사이트를 실행시켰을때는 페이지 디폴트값인 첫번째 페이지를 보여주도록 유도(board_first_num의 값을 선언과 동시에 1로 초기화)하고
        // 이후에는 사용자가 고르는 페이지가 보여지도록 함.       
        List<boardDTO> board_list = b_service.REPOSClimitBoard(repo_first_index);
        model.addAttribute("BoardList", board_list);
    	
        String dateResult = null;
        // 게시글의 날짜를 현재시간을 기준으로 판별하여
        // 1. 같은 날짜의 게시글 			>> 몇시:몇분
        // 2. 같은 년도의 다른 날짜의 게시글 	>> 몇월-며칠
        // 3. 다른 년도의 게시글				>> 몇년도-몇월-며칠
        // 형식으로 바꾸어 JSP로 보낸다.
        for(int i=0; i<board_list.size(); i++) {
        	dateResult = compareDate(board_list.get(i).date);
        	board_list.get(i).setdate(dateResult);
        }
        
        // 관리자 게시글 목록(전체-펼치기ver)
        List<boardDTO> admin_board_list = b_service.printAdminBoard();
        model.addAttribute("adminBoardList", admin_board_list);
 
        // 관리자 게시글 목록(5개-접기ver)
        List<boardDTO> admin_board_foldList = b_service.limitAdminBoard();
        model.addAttribute("adminFoldList", admin_board_foldList);
    	
    	return "user/my_repo";
    }
    
    // unlogin_post, unlogin_main_posts는 비로그인 사용자를 위한 게시글 열람을 위한 함수이다. (조회수 증가 및 각종 로그인 사용자 기능 제한됨)
    
    @SuppressWarnings("null")
 	@RequestMapping(value = "/unlogin_posts", method = RequestMethod.GET)
     public String unlogin_posts(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam("urlnum") int urlnum) throws Exception {
     	
     	String path = "redirect:/unlogin_posts";
     	
     	// 첫 게시글과 마지막 게시글의 고유 번호를 저장하는 first_num과 last_num
     	int first_num = b_service.selectMinNum();     	 	
     	int last_num = b_service.selectMaxNum();
     	     	  	
     	boardDTO pre_posts = null;
     	boardDTO next_posts = null;
     	
     	int number = urlnum;		// urlnum은 해당 게시글의 고유번호를 의미하는데, 이것을 정수형으로 바꿔주는 구문이다.   	     	
     	
     	int pre_num=1, next_num=1;		// 열람중인 해당 게시글의 이전글과 다음글의 고유번호를 찾을때 사용 할 정수형 변수 pre_num과 next_num;
     	
     	
     	// 열람중인 해당 게시글이 첫 글이 아닐 경우 실행되는 조건문
     	if(number != first_num) {		
     		// 이전글을 찾는 반복문
     		// 해당 글의 고유번호의 바로 이전 고유번호가 삭제된 글(번호)일때 실행된다.
         	while(b_service.selectBoard(number-pre_num) == null || b_service.selectBoard(number-pre_num).is_exist == 0) {	
         		if((number-pre_num) == first_num) {		// 해당 글이 첫 게시글이면 멈춤
             		break;
             	}
             	pre_num++;		// 보다 더 이전의 게시글의 고유번호를 찾기 위한 연산이다. (이전글중 삭제되지 않은 고유번호를 찾기위함) 
             	           
     		}
         	pre_posts = b_service.selectBoard(number-pre_num);		// 이전글 저장
         	    	       	
     	}
     	else if(number == first_num) {		// 처음 선택한 게시글이 첫 글 일경우 이전글에 해당 글이 나오도록 함. 
     		pre_posts = b_service.selectBoard(first_num);
     	}
     	
     	
     	
     	// 열람중인 해당 게시글이 마지막 글이 아닐 경우 실행되는 조건문
     	if(number != last_num) {
     		// 다음글을 찾는 반복문
     		// 해당 글의 고유번호의 바로 다음 고유번호가 삭제된 글(번호)일때 실행된다.
         	while(b_service.selectBoard(number+next_num) == null || b_service.selectBoard(number+next_num).is_exist == 0) {
             	if((number+next_num) == last_num) {		// 해당 글이 마지막 게시글이면 멈춤
             		break;
             	}
             	next_num++;		// 다음 게시글의 고유번호를 찾기 위한 연산이다. (다음글중 삭제되지 않은 고유번호를 찾기위함) 
             	
     		}
         	next_posts = b_service.selectBoard(number+next_num);		// 다음글 저장
         	        	
     	}
     	else if(number == last_num) {		// 처음 선택한 게시글이 마지막 글 일경우 이전글에 해당 글이 나오도록 함. 
     		next_posts = b_service.selectBoard(last_num);
     	}
     	
     	boardDTO user_show_post = b_service.selectBoard(number);
     	
     	//show_post = b_service.selectBoard(number);		// 전역변수에 해당 게시글 조회결과 저장
     	
     	user_show_post.setcontent(user_show_post.getcontent().replace("\r\n", "<br>"));
     	
     	//System.out.println("이전글 다음글 >>>>>>>>>>>>> " + pre_posts.num + "\t" + next_posts.num);
     			    	
    	// 이전글과 다음글의 정보를 jsp로 보내기
    	model.addAttribute("pre_post", pre_posts);
    	model.addAttribute("next_post", next_posts);
    	
    	model.addAttribute("SelectPost", user_show_post);		// 해당글의 정보를 jsp로 보내기
    	
    	List<commentDTO> cmt = b_service.printComment(user_show_post.num);
    	
    	for(int i=0; i<cmt.size(); i++) {	// 댓글의 모든 줄바꿈문자 <br>형식으로 변경하여 저장
    		cmt.get(i).setcontent(cmt.get(i).getcontent().replace("\r\n", "<br>")); 	// 댓글 작성 시 사용한 Enter(줄바꿈)이 적용 되도록 재저장. (구글링을 통해 해당 정보 습득)	
    	}
    
    	
    	model.addAttribute("printComment", cmt);		// 해당 고유번호를 가진 게시글의 모든 댓글의 정보를 jsp에 보내는 구문
    	
    	List<FileDTO> fileList = b_service.fileViewer(user_show_post.num);
    	
    	
     	
    	// 이미지와 동영상 파일만 가져오는 쿼리문을 통해 가져와서 JSP로 보낸다.
    	List<FileDTO> fileViewer = b_service.viewFile(user_show_post.num);
    	model.addAttribute("fileViewer", fileViewer);
    	
    	
    	model.addAttribute("fileDown", fileList);	// 해당 게시글의 파일 전체 리스트이다. (다운로드 기능 구현때 사용할 예정) 
    	
    	
    	String[] files_type = new String[fileList.size()];
    	for(int i=0; i<fileList.size(); i++) {
    		files_type[i] = fileList.get(i).type;
    	}
    	model.addAttribute("files_type", files_type);
    	
    	return "/unlogin_posts";  	
     }

    
    // 페이지내 검색   
    @ResponseBody
    @SuppressWarnings("null")
	@RequestMapping(value="/user/pageSearch.do", method = RequestMethod.POST)
    public Object pageSearch(HttpServletRequest request, 
    		@RequestParam(value="search_filter") String search_filter, 
    		@RequestParam(value="content", required=false) String content, 
    		@RequestParam(value="is_secret") int is_secret,
    		@RequestParam(value="is_repo") int is_repo) throws Exception{
    	HttpSession session = request.getSession(false);
    	
    	
    	searchVO vo = new searchVO();
    	vo.setsearch_filter(search_filter);
    	vo.setcontent(content);
    	vo.setis_secret(is_secret);
    	vo.setis_repo(is_repo);
    	
    /*
    @RequestMapping(value="/pageSearch.do", method = RequestMethod.POST)
    public Object pageSearch(searchVO vo) throws Exception{
    	//HttpServletRequest request, 
    	//HttpSession session = request.getSession(false);
    */
    
    	List<boardDTO> searchList = null;
    	Map<String, Object> result = new HashMap<String, Object>();
    	
    	System.out.println("검색 타입 >>>>>>>>>>>>>>>>>> "+ vo.search_filter);
    	System.out.println("검색 내용 >>>>>>>>>>>>>>>>>> "+ vo.content);
    	System.out.println("검색 secret >>>>>>>>>>>>>>>>>> "+ vo.is_secret);
    	System.out.println("검색 repo >>>>>>>>>>>>>>>>>> "+ vo.is_repo); 	
    	
    	
    	if(vo != null && vo.content.isEmpty() == false && vo.content.isBlank() == false) {
        	if(vo.search_filter.equals("search_title") == true) {	
        		searchList = b_service.pageTitleSearch(vo);
        	}
        	else if(vo.search_filter.equals("search_content") == true) {
        		searchList = b_service.pageContentSearch(vo);
        	}
        	else if(vo.search_filter.equals("search_tit_cot") == true) {
        		searchList = b_service.pageTitleContentSearch(vo);
        	}
        	else if(vo.search_filter.equals("search_writer") == true) {    
        		searchList = b_service.pageWriterSearch(vo);
        	}
        	else {
        		System.out.println("문자열 비교가 잘못됐음!!!!!!!!!!!!!!!!!");      		
        	}
        	
        	// 검색 결과가 존재하지 않을 경우 NO 코드를 보냄
        	if(searchList == null) {
        		result.put("check", "NO");
        	}
        	// 검색 결과가 정상적으로 도출됏을 경우 OK 코드를 보냄
        	else {
        	    String dateResult = null;
        	    // 게시글의 날짜를 현재시간을 기준으로 판별하여
        	    // 1. 같은 날짜의 게시글 			>> 몇시:몇분
        	    // 2. 같은 년도의 다른 날짜의 게시글 	>> 몇월-며칠
        	    // 3. 다른 년도의 게시글				>> 몇년도-몇월-며칠
        	    // 형식으로 바꾸어 JSP로 보낸다.
        	    for(int i=0; i<searchList.size(); i++) {
        	        dateResult = compareDate(searchList.get(i).date);
        	        searchList.get(i).setdate(dateResult);
        	    }
        		result.put("search", searchList);
            	result.put("check", "OK");
        	}
    	}
    	// 빈 내용을 검색하거나 공백만 있을 경우 ERROR 코드를 보냄
    	else {
    		result.put("check", "ERROR");
    	}
    	
    	//result.put("check", "NO");
    	return result;
    }
    
    
    @RequestMapping(value="/user/ERROR_PAGE")
    public String errorPage(HttpServletRequest request, Model model, Locale locale) throws Exception{
    	HttpSession session = request.getSession(false);
    	if(session == null || !request.isRequestedSessionIdValid()) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		model.addAttribute("session_msg", false);
    		return "redirect:/LoginPage";
    	}
    	else if(session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
    		model.addAttribute("session_msg", true);
        	session.invalidate();
        	return "redirect:/LoginPage";
    	}
    	
    	logger.info("ERROR_PAGE");
    	return "user/ERROR_PAGE";
    }
    
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Locale locale, Model model) throws Exception{
 
        logger.info("home");
        
        List<MemberVO> memberList = service.selectMember();
        
        model.addAttribute("memberList", memberList);
 
        return "home";
    }
    
 
	
}
