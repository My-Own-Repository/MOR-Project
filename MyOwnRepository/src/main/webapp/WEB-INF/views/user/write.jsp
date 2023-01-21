<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- 
cellpadding="0" cellspacing="0"을 쓰기위한 설정
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 5.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
-->

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100&display=swap" rel="stylesheet">
	<script src="https://kit.fontawesome.com/5309915bbd.js" crossorigin="anonymous" defer></script>
	<link rel="stylesheet" type="text/css" href="../../../resources/css/write.css">
	<link rel="icon" type="image/jpg" href="../../../resources/img/MORicon.jpg">
	
	<%
		//String userNickname = (String) session.getAttribute("userNickname");  
	%> 
	
	
	<title>나만의 저장소 - MOR</title>
	
	<script>
 		src="https://code.jquery.com/jquery-3.4.1.js"
 		integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
 		crossorigin="anonymous"></script>
 		<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

</head>
 
<body>
	<div class="empty_write_div">
	<div class="total_write_div">
	<a href="/user/userMain/1">
		<img class="main-logo" src="../../../resources/img/MOR_symbol_logo.svg" />
	</a>
	<div class="search_div">
		<input type="text" class="search_input" placeholder="검색어 입력">
		<input type="button" value="검색" class="search">
	</div>
	
	<ul class="menu">
		<li>
			<a href="#">게시판</a>
			<ul class="submenu">
				<li><a href="/user/userMain">자유게시판</a></li>
				<li><a href="/user/secret_board">비밀게시판</a></li>
			</ul>
		</li>
		<li>
			<a href="#">저장소</a>
			<ul class="submenu">
				<li><a href="/my_repo">나만의 저장소</a></li>
				<li><a href="/our_repo">공유 저장소</a></li>
			</ul>
		</li>
		<li>
			<a href="/user/mypage">${member.nickname}</a>
		</li>
	</ul>
		
		<!-- <table cellpadding="0" cellspacing="0" border="1"> -->
		
	<br><br><p>자유게시판 - 글쓰기</p><br>
	<div>
	<form id="write_form" action="/user/write" method="post" enctype="multipart/form-data">
		<table border="1" class="write_form_table">
			<tr>
 				<th>작성자</th>
 				<td style="text-align: left">&nbsp;&nbsp;${member.nickname}</td>
			</tr>
			<tr>
				<th>제목</th>
				<td><input type="text" name="title" class="input_text_title"></td>
 			</tr>
 
			<tr>
				<th>내용</th> 
				<td>
					<div contenteditable="true" id="write_content_div" class="write_content_div"></div>
					<textarea id="write_content_textarea" name="content" style=display:none></textarea>
					<input type="text" id="view_content" name="view_content" style=display:none>				
				</td>
				
			</tr>

			<tr>
				<td colspan="2"><input type="file" id="input_file" class="input_file" name="files" multiple="multiple" onchange="is_img_video(this)">
				<input type="button" value="글쓰기" class="small_write_btn" onClick="submit_btn();">
				<input type="button" value="취소" class="small_write_btn" onClick="location.href='/user/userMain/1'">
				</td>
			</tr>
		</table>	
	</form>
	</div>
	<br>
	<div>
		<table border="1" class="downFile_table">
			<tr>
				<th class="downFile_th">&nbsp;첨부파일<a href="javascript:delete_allfile()"><img src="../../../resources/img/trash.png" class="trash_img"></a></th>
			</tr>
			<tr>
				<td class="downFile_td">
					<div id="uploadFiles" class="uploadFiles">
						
					</div>
				</td>
			</tr>		
		</table>
		<br><br>
	</div>
	</div>
	</div>
	<script>
	/*
		업로드 파일 개별 삭제가 도저히 안돼서 전체 삭제로 임시 구현했다.
		
		* 개별 삭제시 구현해야 할 목록
		(	
			1. 업로드된 첨부파일 목록 개별 삭제	O
			2. 미리보기 개별 삭제 X - reader.onload이 한번에 실행되는 구조라서 미리보기용 파일마다 개별 id를 줄수가없음.
			3. input file 개별 삭제 X - 왜 안되는지, 어떻게 해야할지 감이 안잡힘. 이게 가장 막막하다...
		)
	*/
	function is_img_video(f) {
		//var is_preview = true;		// 게시판에서는 파일이 이미지나, 동영상이 아닐 경우 미리보기를 지원하지 않는다.(다운로드도 지원x) 이를 위한 boolean
									// 저장소에서는 모든 유형의 파일업로드, 다운로드 지원.

		var files_index = 0;		// 파일 인덱스 선언 (파일 삭제시 사용됨)
		//var preview_id = '';
		var k=0;
		
		var preview_name = 'preview_name';
				
		var file = f.files;
		
		var divAry = new Array();
		var divId = '';
		var divName = 'preview_files';
		for(var i=0; i<file.length; i++){
			divId = 'divID_'+i;
			divAry[i] = document.createElement('div');
			divAry[i].setAttribute("id", divId);
			divAry[i].setAttribute("class", write_content_div);
			divAry[i].setAttribute("name", divName);
		}
		
		for(var i=0; i<file.length; i++) {
			var fileList = f.files;		// 파일 받아와서 스크립트 내부 변수에 저장.
			var fileName = fileList[i].name;		// 파일 이름 추출
			
			var fileLength = fileName.length;		// 파일명 길이 추출		
			var fileDot = fileName.lastIndexOf(".");	// 파일의 확장자 추출		
			var fileType = fileName.substr(fileDot+1, fileLength).toLowerCase();	// 추출한 확장자를 소문자로 변경한다.
			
            var reader = new FileReader();
			
            
            reader.onload = function (e) {
    	
            	// 파일이 이미지일때 수행
            	if("jpg" == fileType || "jpeg" == fileType || "gif" == fileType || "png" == fileType || "bmp" == fileType){
					
					// 3) 업로드 된 파일이 이미지 혹은 영상태그일 경우 새로운 div안에 img,video태그로 넣음
					divAry[k].innerHTML = '<img src="' + e.target.result + '"><br>';
					document.querySelector("div#write_content_div").appendChild(divAry[k]);
              		k++;
            	}
            	// 파일이 동영상/오디오 일때 수행
				else if("mpg" == fileType || "mpeg" == fileType || "mp4" == fileType || "ogg" == fileType || "webm" == fileType || "avi" == fileType || "wmv" == fileType || "mov" == fileType || "rm" == fileType || "ram" == fileType || 
						"swf" == fileType || "flv" == fileType || "wav" == fileType || "mp3" == fileType){
					
					// 3) 업로드 된 파일이 이미지 혹은 영상태그일 경우 새로운 div안에 img,video태그로 넣음
					divAry[k].innerHTML = '<video src="' + e.target.result + '"><br>';
					document.querySelector("div#write_content_div").appendChild(divAry[k]);
					k++;
					               
				}

				/*	 // 자유게시판, 비밀게시판에서는 오로지 이미지, 영상 파일만 업로드 할 수 있도록 하며 다운로드 기능은 제공하지 않는다.
					 // 저장소, 비밀저장소에서는 모든 파일 업로드 가능, 다운로드 기능 제공.
				else{
					is_preview = false;		// 이미지나 영상 파일이 아닌 다른 유형의 파일이 들어오면 false값을 줌
					$('#file').val('');		// input file에서 업로드한 파일들을 지우며 초기화 시킴.
					alert("FAIL\n자유 게시판에서 지원하지 않는 유형의 파일입니다.\n자유 게시판 지원 파일유형 <이미지, 영상>");
					break; 
				}
				*/
            }
            /*	// 이미지,영상 파일이 아닌 다른 유형의 파일이 선택된 경우 미리보기, 다운로드 지원 x
            if(is_preview = true){
            	reader.readAsDataURL(f.files[i]);
                uploadFileList(f.files[i]);
            }
            */
            
            reader.readAsDataURL(f.files[i]);
           
            uploadFileList(f.files[i], files_index);
            files_index += 1;		// 파일 인덱스 추가 (파일업로드를 취소 할때 파일을 특정하기 위해 사용)
        }
		/*
		// 특정 파일 삭제 이미지를 클릭하여 삭제된 경우
		// 미리보기 삭제 및 input file에서 제외 시키는 구문.
		var keep = '';		
		for(var i=0; i<file.length; i++) {
			var preview_hidden_id = 'preview_id_' + i;	// 미리보기 삭제를 위한 변수 선언	
			
			keep = 'in_js_filenum_';		// input file 삭제를 위한 변수 선언
			content = keep + i;
			if(document.getElementById(content).style.display == "none" || !document.getElementById(content)){		// 해당 div가 삭제(none)되어서 존재하지 않으면 실행되는 조건문
				// 미리보기 삭제
				var hidden_preview = document.getElementById(preview_hidden_id);
				hidden_preview.style.display = "none";
				// ${preview_hidden_id}.remove();
			
				// input file 삭제
				file[i] = null;
			}
		}
		*/
    }
	
	function uploadFileList(file, n){
		var fileName = file.name;		// 파일 이름(+확장자)
		var fileSize = file.size;		// 파일 사이즈
		
				
		var content = '';
		content = '<div class="attached_file" id="in_js_filenum_' + n + '"><font size="2px" color="blue">&nbsp;' + fileName + '</font>';
		content += '<span style="font-size: 8px">&nbsp;&nbsp;' + fileSize + '&nbsp;kb</span>&emsp;';
		content += '<a href="javascript:deleteFile(' + n + ')"><img src="../../../resources/img/Red_X_img.png" class="delete_file_img"></a><br></div>'
		var upfile = document.getElementById('uploadFiles');		// 업로드된 파일리스트를 보여줄 div 가져오기
		upfile.innerHTML += content;
		
	}

	function deleteFile(n){		
		//alert("파일 삭제 함수는 작동함");
		var attached_divID = 'in_js_filenum_'+n;		// 첨부된 파일 목록에서 삭제할 특정 div의 아이디 선언, ex : (in_js_filenum_0) 
		
		// 첨부된 파일 목록 view에서 해당 파일을 삭제한다.
		var delete_file_div = document.getElementById(attached_divID);		
		delete_file_div.style.display = "none";
		
		
		// 미리보기 삭제
		var preview_divID = 'divID_'+n;
		var preview_hidden_div = document.getElementById(preview_divID);
		preview_hidden_div.style.display = "none";
		
		
		// input_file 삭제
		var dataTransfer = new DataTransfer();
    
    	var files = $('#input_file')[0].files;	//사용자가 입력한 파일을 변수에 할당 
    	alert(files);
    	var fileArray = Array.from(files);	//변수에 할당된 파일을 배열로 변환(FileList -> Array)
    	alert(fileArray);
    	fileArray.splice(n, 1);	//해당하는 index의 파일을 배열에서 제거
    
    	fileArray.forEach(file => { dataTransfer.items.add(file); });
    	//남은 배열을 dataTransfer로 처리(Array -> FileList)
    
    	$('#input_file')[0].files = dataTransfer.files;	//제거 처리된 FileList를 돌려줌
		
	}
	
	function delete_allfile(){
		//alert("전체삭제 함수 실행은 된다");
		if(confirm("첨부한 파일들을 전부 삭제하시겠습니까?")){
			var i = 0;
			// 첨부된 파일 목록 view에서 전체 파일을 삭제한다.
			var delete_file_div = document.getElementsByClassName("attached_file");	
			while(true){
				if(delete_file_div[i] == null){
					break;
				}
				delete_file_div[i].style.display = "none";
				i++;
			}
				
			
			// 파일 미리보기 전체 삭제
			var hidden_preview = document.getElementsByName('preview_files');
			for(var i=0; i<hidden_preview.length; i++){
				hidden_preview[i].style.display = "none";
			}
				
			
			
			// input type = "file" 전체 삭제
			var beforeFiles = document.getElementById("input_file");						
			beforeFiles.value = null;
		}
	}
	
	function submit_btn(){
		var copy = document.getElementById('write_content_div').innerText;
		var total_copy = document.getElementById('write_content_div').textContent;
		
		document.getElementById('write_content_textarea').value = copy;
		document.getElementById('view_content').value = total_copy;
		
		document.getElementById('write_form').submit();
	}
	
	</script>
	
	<c:if test="${b_msg == false}">
		<script>
			alert('ERROR\n제목 : 1~50자 이내\n내용 : 1~1000자 이내로 입력해주세요!');
		</script>
	</c:if>
	
	<c:if test="${session_msg == false}">
		<script>
			alert('ERROR\n세션이 만료되었습니다.\n다시 로그인 해주세요!!');
		</script>
	</c:if>
	
</body>
</html>