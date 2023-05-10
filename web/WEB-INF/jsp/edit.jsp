<%@ page import="com.urise.webapp.model.*" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" pattern="^[a-zA-Z]+$" name="fullName" size=50 required value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции : </h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <P style="text-align: center"><b>${type.title}</b>:</P>
            <c:set var="section" value="${resume.getSection(type)}"/>
            <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>
            <c:if test="${type=='PERSONAL' || type=='OBJECTIVE'}">
                <c:set var="textSection" value="${section}"/>
                <jsp:useBean id="textSection" type="com.urise.webapp.model.TextSection"/>
                <P style="text-align: center"><textarea name="${type.name()}" cols=165 rows=3>${textSection.text}</textarea></P>
            </c:if>
            <c:if test="${type=='ACHIEVEMENT' || type=='QUALIFICATIONS'}">
                <c:set var="listSection" value="${section}"/>
                <jsp:useBean id="listSection" type="com.urise.webapp.model.ListSection"/>
                <textarea name="${type.name()}" cols=165
                          rows=3><c:forEach var="item" items="${listSection.items}" varStatus="counter">${item}<c:if test="${counter.index != (listSection.items.size()-1)}"><%="\n"%></c:if></c:forEach></textarea>
            </c:if>
            <c:if test="${type=='EXPERIENCE' || type=='EDUCATION'}" >
                <c:set var="orgSection" value="${section}"/>
                <jsp:useBean id="orgSection" type="com.urise.webapp.model.OrganizationSection"/>
                <c:forEach var="org" items="${orgSection.organizations}" varStatus="counter">
        <dl>
            <dt>Название учереждения:</dt>
            <dd><input type="text" name='${type}' size=100 value="${org.name}"></dd>
        </dl>
        <dl>
            <dt>Сайт учереждения:</dt>
            <dd><input type="text" name='${type}website' size=100 value="${org.website}"></dd>
            </dd>
        </dl>
        <br>
        <div style="margin-left: 30px">
            <c:forEach var="period" items="${org.periods}">
                <jsp:useBean id="period" type="com.urise.webapp.model.Period"/>
            <dl>
                <dt>Начальная дата:</dt>
                <dd>
                    <input type="text" name="${type}${counter.index}startDate" size=10
                           value="<%=DateUtil.format(period.getStartDate())%>" placeholder="MM/yyyy">
                </dd>
            </dl>
            <dl>
                <dt>Конечная дата:</dt>
                <dd>
                    <input type="text" name="${type}${counter.index}endDate" size=10
                           value="<%=DateUtil.format(period.getEndDate())%>" placeholder="MM/yyyy">
            </dl>
            <dl>
                <dt>Должность:</dt>
                <dd><input type="text" name='${type}${counter.index}title' size=75
                           value="${period.title}">
            </dl>
            <dl>
                <dt>Описание:</dt>
                <dd><textarea name="${type}${counter.index}description" rows=5
                              cols=75>${period.description}</textarea></dd>
            </dl>
                    </c:forEach>
                </c:forEach>
            </c:if>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>