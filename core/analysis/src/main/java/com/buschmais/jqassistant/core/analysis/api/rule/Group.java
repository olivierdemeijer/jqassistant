package com.buschmais.jqassistant.core.analysis.api.rule;

import java.util.HashSet;
import java.util.Set;

/**
 * Defines a group.
 */
public class Group implements Rule {

    /**
     * The id of the group.
     */
    private String id;

    /**
     * The optional description.
     */
    private String description;

    /**
     * The set of concepts contained in the group.
     */
    private Set<Concept> concepts = new HashSet<>();

    /**
     * The set of constraints contained in the group.
     */
    private Set<Constraint> constraints = new HashSet<Constraint>();

    /**
     * The set of groups contained in the group.
     */
    private Set<Group> groups = new HashSet<Group>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Concept> getConcepts() {
        return concepts;
    }

    public void setConcepts(Set<Concept> concepts) {
        this.concepts = concepts;
    }

    public Set<Constraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(Set<Constraint> constraints) {
        this.constraints = constraints;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Group that = (Group) o;
        if (!id.equals(that.id))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Group{" + "id='" + id + '\'' + ", concepts=" + concepts + ", constraints=" + constraints + ", groups=" + groups + '}';
    }
}
