package com.urise.webapp.model;

import java.io.Serial;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {
    @Serial
    private static final long serialVersionUID = 1L;
    private List<Organization> organization;
    public OrganizationSection(){

    }

    public OrganizationSection(List<Organization> organizations) {
        this.organization = organizations;
    }

    public List<Organization> getOrganizations() {
        return organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return organization.equals(that.organization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organization);
    }

    @Override
    public String toString() {
        return "OrganizationSection{" +
                "organizations=" + organization +
                '}';
    }
}
