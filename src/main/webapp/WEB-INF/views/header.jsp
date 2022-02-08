<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link href="${path}/resources/css/jumbotron.css" rel="stylesheet">

    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">
    
<script type="text/javascript">
	$(function() {
		const email = '${sessionScope.email}';
		console.log(email);
		if (email == ''){ //로그인 전
			$('#loginInfo').hide();
			$('#logout').hide();
			$('#list').hide();
		}else{ //로그인 후
			$('#loginInfo').show();
			$('#btnLogin').hide();
			$('#join').hide();
			$('#form_email').hide();
			$('#form_passwd').hide();
		}
		
	});
	
	$(function() {
		//로그인 버튼을 클릭했을때
		$('#btnLogin').on('click', function() {
			var logemail = $('#email').val();
			var logpasswd = $('#passwd').val();
			if (logemail==''){
				alert('이메일을 입력해 주세요!');
				$('#email').focus();
			}else if (logpasswd==''){
				alert('비밀번호를 입력해 주세요!');
				$('#passwd').focus();
			}else{
				$('#frmLogin').attr('action', '${path}/login')
							  .attr('method', 'post')
							  .submit();
			}
			
		});
	});
	
</script>
</head>
<body>
    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="${path}/" id="home">테스트 프로젝트</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
        <!--로그인 폼 -->
          <form class="navbar-form navbar-right" id="frmLogin" >
            <div class="form-group" id ="form_email">
              <input type="text" placeholder="Email" class="form-control" name="email" id="email">
            </div>
            <div class="form-group" id = "form_passwd">
              <input type="password" placeholder="Password" class="form-control" name="passwd" id="passwd">
            </div>
            <div id="loginInfo">
				<a class="navbar-brand" href="${path}/member/modify?email=${sessionScope.email}">${sessionScope.email}</a>
			</div>
            <button type="button" id="btnLogin" class="btn btn-success">Sign in</button>      
            <a href="${path}/logout" id="logout">로그아웃</a>
          </form>
        </div><!--/.navbar-collapse -->
      </div>
    </nav>

    <!-- Main jumbotron for a primary marketing message or call to action -->	

	
	
<%-- 	<header>
		<div><h2>게시물관리</h2></div>
		<div id="minfo">
			<div id="loginInfo">
				<a href="${path}/member/modify?email=${sessionScope.email}">${sessionScope.email}</a>
			</div>
			<div><a href="${path}/loginbs" id="login">로그인</a></div>
			
			<div><a href="${path}/member/join" id="join">회원가입</a></div>
		</div>
	</header> --%>
<%-- 	<hr>
	<nav> 
		<a href="${path}/" id="home">홈</a>
		<a href="${path}/member/" id="memberlist">회원리스트</a>
		<a href="${path}/board/list" id="list">게시물리스트</a>
		<a href="${path}/board/add" id="add">게시물등록</a>
		<a href="${path}/company" id="company">오시는길</a>
	</nav>
	<hr> --%>
</body>
</html>