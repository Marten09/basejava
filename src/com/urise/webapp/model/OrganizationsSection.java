package com.urise.webapp.model;

import java.util.List;

public class OrganizationsSection extends Section{
    private final List<Organizations> organizationsList;

    public OrganizationsSection(List<Organizations> organizationsList) {
        this.organizationsList = organizationsList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        OrganizationsSection that = (OrganizationsSection) o;

        return organizationsList.equals(that.organizationsList);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + organizationsList.hashCode();
        return result;
    }

    public List<Organizations> getOrganizationsList() {
        return organizationsList;
    }

    @Override
    public String toString() {
        return "ExperienceSection{" +
                "organizationsList=" + organizationsList +
                '}';
    }
}
