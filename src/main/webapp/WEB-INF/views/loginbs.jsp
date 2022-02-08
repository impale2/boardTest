<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.min.js"></script>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title>Signin Template for Bootstrap</title>



    <!-- Custom styles for this template -->
    <link href="resources/css/signin.css" rel="stylesheet">

<script type="text/javascript">
$(function() {
	//로그인 버튼을 클릭했을때
	$('#btnLogin').on('click', function() {
		var email = $('#email').val();
		var passwd = $('#passwd').val();
		if (email==''){
			alert('이메일을 입력해 주세요!');
			$('#email').focus();
		}else if (passwd==''){
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
<%@ include file="header.jsp" %>
    <div class="container">

      <form class="form-signin">
        <h2 class="form-signin-heading">Please sign in</h2>
        <label for="inputEmail" class="sr-only" id = "email">Email address</label>
        <input type="email" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
        <label for="inputPassword" class="sr-only" id= "passwd">Password</label>
        <input type="password" id="inputPassword" class="form-control" placeholder="Password" required>
        <div class="checkbox">
          <label>
            <input type="checkbox" value="remember-me"> Remember me
          </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="button" id="btnLogin">Sign in</button>
        <a href="${apiURL}"><img height="50" src="${path}/resources/images/btnNaver.png"/></a>
      </form>

    </div> <!-- /container -->


    
   
  </body>
</html>
