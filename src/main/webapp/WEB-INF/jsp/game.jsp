<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">

<spring:url value="/resources/css/style.css" var="mainCss" />
<link href="${mainCss}" rel="stylesheet" />

<body>
	<%--<c:url value="/resources/text.txt" var="url"/>--%>
	<%--<spring:url value="/resources/text.txt" htmlEscape="true" var="springUrl" />--%>
	<%--Spring URL: ${springUrl} at ${time}--%>
	<%--<br>--%>
	<%--JSTL URL: ${url}--%>
	<%--<br>--%>
	<%--Message: ${message}--%>

	<h1>Mancala Game</h1>

	<div class="grid">
		<div>
			<div class="pit middle">${pitStones[0]}</div>
			<img class="score" src="resources/images/leftScore.jpg" />
		</div>
		<div class="board">
			<a href="/input/1"><img src="resources/images/topBoard.jpg" /></a>
			<a href="/input/2"><img src="resources/images/topBoard.jpg" /></a>
			<a href="/input/3"><img src="resources/images/topBoard.jpg" /></a>
			<a href="/input/4"><img src="resources/images/topBoard.jpg" /></a>
			<a href="/input/5"><img src="resources/images/topBoard.jpg" /></a>
			<a href="/input/6"><img src="resources/images/topBoard.jpg" /></a>
			<a href="/input/13"><img src="resources/images/bottomBoard.jpg" /></a>
			<a href="/input/12"><img src="resources/images/bottomBoard.jpg" /></a>
			<a href="/input/11"><img src="resources/images/bottomBoard.jpg" /></a>
			<a href="/input/10"><img src="resources/images/bottomBoard.jpg" /></a>
			<a href="/input/9"><img src="resources/images/bottomBoard.jpg" /></a>
			<a href="/input/8"><img src="resources/images/bottomBoard.jpg" /></a>
		</div>
		<div>
			<div class="pit middle">${pitStones[7]}</div>
			<img class="score" src="resources/images/rightScore.jpg" />
		</div>
	</div>

	<c:forEach var="val" items="${pitStones}" begin="1" end = "6" varStatus="iterator">
		<div id="p${iterator.count}" class="pit top" varStatus="iterator">
			<c:out value="${val}" />
		</div>
	</c:forEach>
	<c:forEach var="val" items="${pitStones}" begin="1" end = "6" varStatus="iterator">
		<div id="p${iterator.count}" class="pit bottom" varStatus="iterator">
			<c:out value="${val}" />
		</div>
	</c:forEach>

	<br>
	<p>Current Player is ${currentPlayer}</p>

</body>

</html>
