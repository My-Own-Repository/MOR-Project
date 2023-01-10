package com.co.mor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.co.dto.LoginDTO;
import com.co.dto.MemberVO;
import com.co.dto.boardDTO;
import com.co.dto.commentDTO;
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
	
	public int integration_id = -1;		// 회원가입 할 때 무결성 검사용 전역변수
	public int integration_nickname = -1;	// 회원가입 할 때 무결성 검사용 전역변수
	
	public String user_id = "";		// 세션에서 회원 정보 가져오기의 임시방편으로, 로그인한 사용자의 정보를 임시로 저장하기 위한 전역변수
	public String user_nickname = "";	// 세션에서 회원 정보 가져오기의 임시방편으로, 프로그램 이용시 자주 사용되는 사용자의 닉네임을 저장해놓는 전역변수
	
	public List<boardDTO> show_post;	// 사용자가 보는 게시글을 특정하여 해당 페이지로 이동할 때 사용 할 전역변수 (좋은 방법은 아닌거같음. 향후 개선요망)
										// 컨트롤러에서 컨트롤러로 데이터를 전송할 때의 대체제로 전역변수를 사용한것임.
	
	public int board_first_index = 1;		// 사용자가 보는 게시글을 20개씩 나누어 페이징하기 위한 쿼리문에서 사용되는 전역변수.
										// = MYSQL의 쿼리문인 limitBoard의 LIMIT의 시작 인덱스가 될 값
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
        	if((total / 20) != 0 && (total % 20) > 0) {
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
        
        
        
        
        
        // 처음 웹 사이트를 실행시켰을때는 페이지 디폴트값인 첫번째 페이지를 보여주도록 유도(board_first_num의 값을 선언과 동시에 1로 초기화)하고
        // 이후에는 사용자가 고르는 페이지가 보여지도록 함.       
        List<boardDTO> board_list = b_service.limitBoard(board_first_index);
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
  
        if(session != null) {	// 사실상 표면적으로 로그인이 되어있기만 하다면 실행 되는 조건문이다. (세션이 만료가 되어도 session이 완전히 비워지진 않기때문)
        	model.addAttribute("logout_msg", true);
        	session.invalidate();
        }
        
        return "redirect:/";
    }
    
    @RequestMapping(value = "/user/userMain/{page}", method = RequestMethod.GET)
    public String userMainPage(HttpServletRequest request, Model model, @PathVariable("page") int page) throws Exception {
    	HttpSession session = request.getSession(false);
    	System.out.println("사용자 닉네임 >> " + user_nickname);
   	
    	if(session == null || !request.isRequestedSessionIdValid()) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		model.addAttribute("session_msg", false);
    		return "redirect:/LoginPage";
    	}  	
    	if(user_nickname.isBlank() == true) {
    		model.addAttribute("session_msg", false);
    		session.invalidate();
    		return "redirect:/";
    	}
    	
    	
        // 사용자가 선택한 페이지에 따라 sql에 보낼 파라미터 값(LIMIT 시작 인덱스)이 바뀜 (1번째 페이지 일 경우 값은 1, 2번째의 경우 값은 21 .... 20씩 증가)      
        board_first_index = (page-1) * 20;
    	
    	System.out.println(LOGIN_MEMBER);
    	return "redirect:/user/userMain";
	
    }
    
    @RequestMapping(value = "/user/userMain", method = RequestMethod.GET)
    public String view_userMainPage(HttpServletRequest request, Model model) throws Exception {
    	HttpSession session = request.getSession(false);
    	
    	if(session == null || !request.isRequestedSessionIdValid()) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		model.addAttribute("session_msg", false);
    		return "redirect:/LoginPage";
    	}    	
    	if(user_nickname.isBlank() == true) {
    		model.addAttribute("session_msg", false);
    		session.invalidate();
    		return "redirect:/";
    	}
    	
        int total = b_service.totalNum();
        int page_count=1;
        
        // 한 페이지에 20개의 글이 보이게 한다.
        // 총 게시글 수의 대입해, 필요한 총 페이지 수(page_count)를 알아 내기위한 반복문.
        while(true) {
        	if((total / 20) != 0 && (total % 20) > 0) {
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
        
        
        
        
        
        // 처음 웹 사이트를 실행시켰을때는 페이지 디폴트값인 첫번째 페이지를 보여주도록 유도(board_first_num의 값을 선언과 동시에 1로 초기화)하고
        // 이후에는 사용자가 고르는 페이지가 보여지도록 함.       
        List<boardDTO> board_list = b_service.limitBoard(board_first_index);
        model.addAttribute("BoardList", board_list);
    	
    	return "user/userMain";
	
    }
    
    @RequestMapping(value = "/user/mypage", method = RequestMethod.GET)
    public String mypage(HttpServletRequest request, Model model) {
    	HttpSession session = request.getSession(false);
    	
    	if(session == null || !request.isRequestedSessionIdValid()) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		model.addAttribute("session_msg", false);
    		return "redirect:/LoginPage";
    	}
    	else if(user_nickname.isBlank() == true) {
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
    	else if(user_nickname.isBlank() == true) {
    		model.addAttribute("session_msg", true);
        	session.invalidate();
        	return "redirect:/LoginPage";
    	}
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
    	else if(user_nickname.isBlank() == true) {
    		model.addAttribute("session_msg", true);
        	session.invalidate();
        	return "redirect:/LoginPage";
    	}
    	String url = "";
    	//boardDTO saved_board = b_service.writeBoard();
    	
    	
    	if(letter.title != null && letter.content != null) {
    		if(letter.title.length() > 0 && letter.content.length() <= 1000) {
    			logger.info("글작성 성공!");
    			letter.id = user_id;			// 로그인할때 저장해놓은 사용자의 id와 nickname을 글 작성자의 개인정보로 사용 (글 작성시 사용자가 자신의 id와 nickname을 따로 작성하지 않기때문에 프로그램에서 자체적으로 저장해줌)
    			letter.nickname = user_nickname;
    			
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
    	if(session == null || !request.isRequestedSessionIdValid()) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		model.addAttribute("session_msg", false);
    		return "redirect:/LoginPage";
    	}
    	else if(user_nickname.isBlank() == true) {
    		model.addAttribute("session_msg", true);
        	session.invalidate();
        	return "redirect:/LoginPage";
    	}
    	boardDTO before_post = show_post.get(0);		// 현재 보고있는 게시글을 수정하기 전 게시글의 정보로 저장하기 위한 변수
    	
    	before_post.setcontent(before_post.getcontent().replace("<br>", "\r\n")); 	// 게시글 수정 시 사용한 Enter(줄바꿈)이 적용 되도록 재저장. (구글링을 통해 해당 정보 습득)
    	
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
    	
    	if(session == null || !request.isRequestedSessionIdValid()) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		model.addAttribute("session_msg", false);
    		return "redirect:/LoginPage";
    	}
    	else if(user_nickname.isBlank() == true) {
    		model.addAttribute("session_msg", true);
        	session.invalidate();
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
    	if(session == null || !request.isRequestedSessionIdValid()) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		model.addAttribute("session_msg", false);
    		return "redirect:/LoginPage";
    	}
    	else if(user_nickname.isBlank() == true) {
    		model.addAttribute("session_msg", true);
        	session.invalidate();
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
         	while(b_service.selectBoard(number-pre_num).isEmpty() == true || b_service.selectBoard(number-pre_num).get(0).is_exist == 0) {
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
         	while(b_service.selectBoard(number+next_num).isEmpty() == true || b_service.selectBoard(number+next_num).get(0).is_exist == 0 ) {
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

    	
    	show_post = b_service.selectBoard(number);		// 전역변수에 해당 게시글 조회결과 저장
    	
    	show_post.get(0).setcontent(show_post.get(0).getcontent().replace("\r\n", "<br>")); 	// 게시글 작성 시 사용한 Enter(줄바꿈)이 적용 되도록 재저장. (구글링을 통해 해당 정보 습득)
    	
    	if(show_post == null || b_service.selectBoard(number).get(0).is_exist == 0) {				// 열람을 위해 클릭한 다른 유저의 게시글이 이미 삭제되었을 경우 실행되는 조건문 
    		model.addAttribute("msg", false);
    		path = "redirect:/user/userMain";
    	}
    	else {
    		b_service.addView(number);			// 조회수 증가
    	}
    	
    	
    	
		return path;
    	
    }
    
    
    @SuppressWarnings("null")
	@RequestMapping(value = "/user/posts")
    public String main_posts(HttpServletRequest request, Model model) throws Exception {		// 연산(실행)이 끝난 결과값을 다시 jsp로 보내주는 역할 
    	HttpSession session = request.getSession(false);
    	if(session == null || !request.isRequestedSessionIdValid()) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		model.addAttribute("session_msg", false);
    		return "redirect:/LoginPage";
    	}
    	else if(user_nickname.isBlank() == true) {
    		model.addAttribute("session_msg", true);
        	session.invalidate();
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
    	
    	for(int i=0; i<cmt.size(); i++) {	// 댓글의 모든 줄바꿈문자 <br>형식으로 변경하여 저장
    		cmt.get(i).setcontent(cmt.get(i).getcontent().replace("\r\n", "<br>")); 	// 댓글 작성 시 사용한 Enter(줄바꿈)이 적용 되도록 재저장. (구글링을 통해 해당 정보 습득)	
    	}
  	
    	model.addAttribute("printComment", cmt);		// 해당 고유번호를 가진 게시글의 모든 댓글의 정보를 jsp에 보내는 구문
    	   	
    	List<FileDTO> fileList = b_service.fileViewer(show_post.get(0).num);
    	
    	
 	
    	// 이미지와 동영상 파일만 가져오는 쿼리문을 통해 가져와서 JSP로 보낸다.
    	List<FileDTO> fileViewer = b_service.viewFile(show_post.get(0).num);
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
    	else if(user_nickname.isBlank() == true) {
        	session.invalidate();
        	return "redirect:/LoginPage";
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
    	String urlnum = Integer.toString(show_post.get(0).num);
    	return "redirect:/user/posts/"+urlnum;
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
    	else if(user_nickname.isBlank() == true) {
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
    	
    	return "redirect:/user/posts/"+cmt.b_num;
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
    	else if(user_nickname.isBlank() == true) {
        	session.invalidate();
        	return "redirect:/LoginPage";
    	}
    	
    	int board_num = Integer.parseInt(b_num);
    	int comment_num = Integer.parseInt(c_num);
    	
    	b_service.deleteComment(comment_num);		// 게시글의 댓글 삭제
    	b_service.downComment(board_num);		// 게시글의 댓글 수 1개 줄이기
    	
    	return "redirect:/user/posts/"+board_num;
    }
    
    // 게시글의 첨부 파일들을 사용자들에게 보여줌.
    
    @RequestMapping(value = "/loadfiles.do/{files_num}" , method = RequestMethod.GET)
    public void displayFiles(Model model, HttpServletRequest request, HttpServletResponse response, @PathVariable("files_num") int files_num) throws Exception{
    	System.out.println("일단 실행은 됐고 files_number 값은? >>>> " + files_num);
    	
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
    
    // 게시글 삭제
    @RequestMapping(value = "/user/delete_board")
    public String delete_board(HttpServletRequest request, @RequestParam("num") int num) throws Exception {
    	HttpSession session = request.getSession(false);
    	if(session == null || !request.isRequestedSessionIdValid()) {							// 세션이 만료된 상태로 페이지 이동을 시도할경우 로그인 페이지로 이동하게 된다.
    		return "redirect:/LoginPage";
    	}
    	else if(user_nickname.isBlank() == true) {
        	session.invalidate();
        	return "redirect:/LoginPage";
    	}
    	
    	String path = "";
    	  	
    	if(b_service.selectBoard(num) != null || b_service.selectBoard(num).get(0).is_exist == 0) {
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
         	while(b_service.selectBoard(number-pre_num).isEmpty() == true || b_service.selectBoard(number-pre_num).get(0).is_exist == 0) {	
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
         	while(b_service.selectBoard(number+next_num).isEmpty() == true || b_service.selectBoard(number+next_num).get(0).is_exist == 0) {
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
     	
     	show_post = b_service.selectBoard(number);		// 전역변수에 해당 게시글 조회결과 저장
     	
     	show_post.get(0).setcontent(show_post.get(0).getcontent().replace("\r\n", "<br>"));
     	
     	
     	
 		return path;
     	
     }
    
    @RequestMapping(value = "/unlogin_posts")
    public String unlogin_main_posts(HttpServletRequest request, Model model) throws Exception {
    	
    	
    	model.addAttribute("SelectPost", show_post);		// 해당글의 정보를 jsp로 보내기		
    	
    	// 이전글과 다음글의 정보를 jsp로 보내기
    	model.addAttribute("pre_post", pre_posts);
    	model.addAttribute("next_post", next_posts);
    	
    	List<commentDTO> cmt = b_service.printComment(show_post.get(0).num);
    	
    	for(int i=0; i<cmt.size(); i++) {	// 댓글의 모든 줄바꿈문자 <br>형식으로 변경하여 저장
    		cmt.get(i).setcontent(cmt.get(i).getcontent().replace("\r\n", "<br>")); 	// 댓글 작성 시 사용한 Enter(줄바꿈)이 적용 되도록 재저장. (구글링을 통해 해당 정보 습득)	
    	}
    
    	
    	model.addAttribute("printComment", cmt);		// 해당 고유번호를 가진 게시글의 모든 댓글의 정보를 jsp에 보내는 구문
    	
    	List<FileDTO> fileList = b_service.fileViewer(show_post.get(0).num);
    	
    	
     	
    	// 이미지와 동영상 파일만 가져오는 쿼리문을 통해 가져와서 JSP로 보낸다.
    	List<FileDTO> fileViewer = b_service.viewFile(show_post.get(0).num);
    	model.addAttribute("fileViewer", fileViewer);
    	
    	
    	model.addAttribute("fileDown", fileList);	// 해당 게시글의 파일 전체 리스트이다. (다운로드 기능 구현때 사용할 예정) 
    	
    	
    	String[] files_type = new String[fileList.size()];
    	for(int i=0; i<fileList.size(); i++) {
    		files_type[i] = fileList.get(i).type;
    	}
    	model.addAttribute("files_type", files_type);
    	
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
