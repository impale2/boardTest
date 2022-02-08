<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../include/includeFile.jsp" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
  
<script type="text/javascript">
	$(function(){
		$('.alist').on('click', function(e) {
			e.preventDefault(); //기본이벤트 삭제
			var curPage = $(this).attr('href');
			var findkey = $('#findkey').val();
			var findvalue = $('#findvalue').val();
			location.href = '${path}/board/list?curPage='+curPage+'&findkey='+findkey+'&findvalue='+findvalue;
			});
		});


	</script>
	</head>
	<body>
	<div class="container">
	<%-- 	${page} --%>
	
		<hr>
		<%@ include file="../header.jsp" %>
		<h2>게시물조회</h2>
		<form class="form-inline" action="${path}/board/list">
			<div class="form-group">
				<select class="form-control" name="findkey" id="findkey">
					<option value="email" <c:out value="${page.findkey=='email'?'selected':''}"/>>이메일</option>
					<option value="subject" <c:out value="${page.findkey=='subject'?'selected':''}"/>>제목</option>
					<option value="content" <c:out value="${page.findkey=='content'?'selected':''}"/>>내용</option>
					<option value="subcon" <c:out value="${page.findkey=='subcon'?'selected':''}"/>>제목+내용</option>
				</select>
				<input class="form-control mr-sm-2" placeholder="Search" name="findvalue" id="findvalue" value="${page.findvalue}">
				<button class="btn btn-success">조회</button>
			</div>
		</form>
	<%-- 	${blist} --%>
		<table class="table table-striped">
			<tr>
				<th>번호</th>
				<th>이메일</th>
				<th>제목</th>
				<th>조회수</th>
				<th>좋아요</th>
				<th>싫어요</th>
				<th>등록일자</th>
			</tr>
			<c:forEach var="board" items="${blist}">
				<tr>
					<td>${board.bnum}</td>
					<td>${board.email}</td>			
					<td><a href="${path}/board/detail?bnum=${board.bnum}">${board.subject}</a></td>			
					<td>${board.readcnt}</td>			
					<td>${board.likecnt}</td>			
					<td>${board.dislikecnt}</td>	
					<td><fmt:formatDate value="${board.regidate}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>		
				</tr>
			</c:forEach>
		</table>
		<!-- 페이징 처리 -->
		<div class="pagination">
			<c:if test="${page.startPage != 1 }">
				<a href="${page.startPage-1}" class="alist">앞으로</a>
			</c:if>
			<c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
				<c:if test="${i==page.curPage}">
					<a href="${i}" class="alist active">${i}</a>
				</c:if>
				<c:if test="${i!=page.curPage}">
					<a href="${i}" class="alist" >${i}</a>
				</c:if>
			</c:forEach>
			 <c:if test="${page.totPage>page.endPage}">
				<a href="${page.endPage+1}" class="alist">뒤로</a>
			</c:if> 

		</div>
	</div>
		
		
	</body>
</html>