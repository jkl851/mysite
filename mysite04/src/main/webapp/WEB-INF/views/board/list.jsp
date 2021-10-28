<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="a" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:set var='count' value='${fn:length(list) }'/>
					<c:forEach items='${list }' var='vo' varStatus='status'>				
						<tr>
							<td>${vo.no }</td>
							<td style="text-align:left; padding-left:${8*vo.depth }px;">
								<c:if test="${vo.depth ne 0}">
									<img src="${pageContext.request.contextPath }/assets/images/reply.png" width="10" height="10">
								</c:if>
							<c:choose>
								<c:when test='${vo.status == "1" }'>
									<a href="${pageContext.request.contextPath }/board/view/${vo.no }">${vo.title }</a>
									<td>${vo.user_name }</td>
									<td>${vo.hit }</td> 
									<td>${vo.reg_date }</td>
								</c:when>
								<c:otherwise>
									<td>${vo.no }</td>
									<td><a href="">이미 삭제된 글입니다.</a></td>
									<td></td>
									<td></td> 
									<td></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test='${vo.user_no == authUser.no }'>
									<td><a href="${pageContext.request.contextPath }/board/delete/${vo.no }?gN=${vo.group_no}">
									<img src="${pageContext.request.contextPath }/assets/images/recycle.png" width="20" height="20">
									</a></td>
								</c:when>
								<c:otherwise>
									<td></td>
								</c:otherwise>
							</c:choose>
						</tr>
					</c:forEach>
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
					<c:choose>
						<c:when test='${nvo.quotient != 0}'>
							<li><a href="${pageContext.request.contextPath }/board?quotientL=${nvo.quotient-1}">◀</a></li>
						</c:when>
						<c:otherwise>
							<li>◀</li>
						</c:otherwise>
					</c:choose>
					<c:forEach items='${pageList }' var='pageNum' varStatus='status'>	
					<c:choose>
						<c:when test='${pageNum == nvo.pageNum}'>
							<li class="selected"><a href="${pageContext.request.contextPath }/board?pageNum=${pageNum}">${pageNum}</a></li>
						</c:when>
						<c:when test='${pageNum != nvo.pageNum and pageNum <= nvo.maxPageNum}'>
							<li><a href="${pageContext.request.contextPath }/board?pageNum=${pageNum}">${pageNum}</a></li>
						</c:when>
						<c:otherwise>
							<li>${pageNum}</li>
						</c:otherwise>
					</c:choose>
					</c:forEach>
					<c:choose>
						<c:when test='${nvo.maxQuotient > nvo.quotient }'>
							<li><a href="${pageContext.request.contextPath }/board?quotient=${nvo.quotient+1}">▶</a></li>
						</c:when>
						<c:otherwise>
							<li>▶</li>
						</c:otherwise>
					</c:choose>
					</ul>
				</div>					
				<!-- pager 추가 -->
				
				<div class="bottom">
					<a href="${pageContext.request.contextPath }/board/write" id="new-book">글쓰기</a>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>