<%@ page import="com.urise.webapp.model.*" %>
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
                    <P style="text-align: center">Название организации:
                        <textarea name="${type}" rows=1 cols=165>${org.name}</textarea></P>
                    <P style="text-align: center">Ссылка:
                        <textarea name="${type}url" rows=1 cols=165>${org.website}</textarea></P>
                    <c:if test="${org.periods.size() <= 1}">
                        <P style="text-align: center">Должность: </P><br/>
                    </c:if>
                    <c:if test="${org.periods.size() > 1}">
                        <P style="text-align: center">Должности: </P><br/>
                    </c:if>
                    <c:forEach var="period" items="${org.periods}">
                        Занимаемая должность:
                        <input type="text" name="${type}post${counter.index}" value="${period.title}" size=50/><br/>
                        Описание:
                        <input type="text" name="${type}definition${counter.index}" value="${period.description}"
                               size=100/><br/><br/>
                        От:
                        <input type="text" name="${type}dateStart${counter.index}" value="${period.startDate}"
                               size=8 placeholder="yyyy-mm-dd"/><br/>
                        До:
                        <input type="text" name="${type}dateFinish${counter.index}" value="${period.endDate}"
                               size=8 placeholder="yyyy-mm-dd"/><br/>
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