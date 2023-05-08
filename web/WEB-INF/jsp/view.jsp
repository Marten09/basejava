<%@ page import="com.urise.webapp.model.TextSection" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.OrganizationSection" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contact}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.ContactType , java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>
    <table>
        <c:forEach var="sectionEntry" items="${resume.section}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.AbstractSection>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>
            <c:if test="<%=section != null && section.toString().trim().length() > 0%>">
                <c:if test="${type=='PERSONAL' || type=='OBJECTIVE'}">
                    <P style="text-align: center"><b>${type.title}</b>:</P>
                    ${section}<br/>
                </c:if>
                <c:if test="${type=='ACHIEVEMENT' || type=='QUALIFICATIONS'}">
                    <P style="text-align: center"><b>${type.title}</b>:</P>
                    <ul>
                        <c:forEach var="item" items="${section.items}">
                            <li>${item}<br/></li>
                        </c:forEach>
                    </ul>
                </c:if>
                <c:if test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                    <P style="text-align: center"><b>${type.title}</b>:</P>
                    <c:forEach var="org" items="<%=((OrganizationSection) section).getOrganizations()%>">
                        <c:if test="${empty org.website}">
                            ${org.name}<br/>
                        </c:if>
                        <c:if test="${!empty org.website}">
                            <a href="${org.website}">${org.name}</a><br/>
                        </c:if>
                        <ul>
                            <c:forEach var="pos" items="${org.periods}">
                                <li>${pos.startDate} - ${pos.endDate}<br/>
                                        ${pos.title}<br/>
                                        ${pos.description}</li>
                                <br/>
                            </c:forEach>
                        </ul>
                    </c:forEach>
                </c:if>
            </c:if>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>