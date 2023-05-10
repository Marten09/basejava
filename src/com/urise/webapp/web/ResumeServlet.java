package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.DateUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "add":
                r = Resume.NEW_RESUME;
                break;
            case "view":
                r = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    AbstractSection section = r.getSection(type);
                    if (section != null) {
                        r.setSection(type, section);
                    }
                }
                break;
            case "edit":
                r = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    AbstractSection section = r.getSection(type);
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                section = TextSection.EMPTY;
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (section == null) {
                                section = ListSection.EMPTY;
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            OrganizationSection organizationSection = (OrganizationSection) section;
                            List<Organization> emptyOrg = new ArrayList<>();
                            emptyOrg.add(Organization.EMPTY);
                            if (organizationSection != null) {
                                for (Organization organization : organizationSection.getOrganizations()) {
                                    List<Period> emptyPeriod = new ArrayList<>();
                                    emptyPeriod.add(Period.EMPTY);
                                    emptyPeriod.addAll(organization.getPeriods());
                                    emptyOrg.add(new Organization(organization.getName(), organization.getWebsite(), emptyPeriod));
                                }
                            }
                            section = new OrganizationSection(emptyOrg);
                            break;
//                            if (section == null) {
//                                section = new OrganizationSection(List.of(Organization.EMPTY));
//                            }
//                            break;
                    }
                    r.setSection(type, section);
                }
                break;
            default:
                throw new IllegalStateException("Action" + action + "is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        final boolean isCreate = (uuid == null || uuid.length() == 0);
        Resume r;
        if (isCreate) {
            r = new Resume(fullName);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value == null || value.trim().length() == 0) {
                r.getContact().remove(type);
            } else {
                r.setContact(type, value);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            if (value == null || value.trim().length() == 0) {
                r.getSection().remove(type);
            } else {
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        r.setSection(type, new TextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> stringList = Arrays.stream(value
                                        .replaceAll("\r", "\n")
                                        .split("\\n"))
                                .map(String::trim)
                                .filter(s -> !s.isBlank())
                                .toList();
                        r.setSection(type, new ListSection(stringList));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        List<Organization> orgs = new ArrayList<>();
                        String[] names = request.getParameterValues(type.name());
                        String[] website = request.getParameterValues(type.name() + "website");
                        for (int i = 0; i < names.length; i++) {
                            String name = names[i];
                            List<Period> periods = new ArrayList<>();
                            if (name != null && name.trim().length() != 0) {
                                String title = request.getParameter(type.name() + "title" + i);
                                String description= request.getParameter(type.name() + "description" + i);
                                LocalDate startDates = LocalDate.parse(request.getParameter(type.name() + "startDate" + i), DateTimeFormatter.ofPattern("yyyy.MM.dd"));
                                LocalDate endDates = LocalDate.parse(request.getParameter(type.name() + "endDate" + i), DateTimeFormatter.ofPattern("yyyy.MM.dd"));
                                periods.add(new Period(title,description,startDates, endDates));
                                orgs.add(new Organization(name, website[i], periods));
                            }
                        }
                        r.setSection(type, new OrganizationSection(orgs));
                        break;
                }
            }
        }
        if (isCreate) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume");
    }
}