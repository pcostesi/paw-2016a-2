package ar.edu.itba.models;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "experience", uniqueConstraints = @UniqueConstraint(columnNames = {"project_id", "username"}))
public final class Experience implements Serializable {

    @Id
    @XmlElement
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Id
    @XmlElement
    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user;

    @XmlElement
    @Column(nullable = false)
    private int experience;

    private Experience() {
        // Just for Hibernate
    }

    private Experience(final Project project, final User user, final int experience) {
        this.project = project;
        this.user = user;
        this.experience = experience;
    }

    public static Experience.Builder builder() {
        return new Experience.Builder();
    }

    private Project project() {
        return project;
    }

    private User user() {
        return user;
    }

    private int experience() {
        return experience;
    }

    public boolean equals(final Object another) {
        if (this == another) {
            return true;
        }
        return another instanceof Experience
                && equalTo((Experience) another);
    }

    private boolean equalTo(final Experience another) {
        return experience == another.experience
                && user.equals(another.user)
                && project.equals(another.project);
    }

    public int hashCode() {
        int h = 31;
        h = h * 17 + project.hashCode();
        h = h * 17 + user.hashCode();
        h = h * 17 + experience;
        return h;
    }

    public String toString() {
        return "Experience{"
                + "project=" + project
                + ", user=" + user
                + ", experience=" + experience
                + "}";
    }

    public static final class Builder {
        private static final long INIT_BIT_PROJECT = 0x1L;
        private static final long INIT_BIT_USER = 0x2L;
        private static final long INIT_BIT_EXPERIENCE = 0x4L;
        private long initBits = 0x7;

        private Project project;
        private User user;
        private int experience;

        private Builder() {
        }

        public final Builder from(final Experience instance) {
            Objects.requireNonNull(instance, "instance");
            project(instance.project());
            user(instance.user());
            experience(instance.experience());
            return this;
        }

        public final Builder project(final Project project) {
            this.project = Objects.requireNonNull(project, "project");
            initBits &= ~INIT_BIT_PROJECT;
            return this;
        }

        public final Builder user(final User user) {
            this.user = Objects.requireNonNull(user, "user");
            initBits &= ~INIT_BIT_USER;
            return this;
        }

        public final Builder experience(final int experience) {
            this.experience = experience;
            initBits &= ~INIT_BIT_EXPERIENCE;
            return this;
        }

        public Experience build() {
            if (initBits != 0) {
                throw new IllegalStateException(formatRequiredAttributesMessage());
            }
            return new Experience(project, user, experience);
        }

        private String formatRequiredAttributesMessage() {
            final List<String> attributes = new ArrayList<String>();
            if ((initBits & INIT_BIT_PROJECT) != 0) {
                attributes.add("project");
            }
            if ((initBits & INIT_BIT_USER) != 0) {
                attributes.add("user");
            }
            if ((initBits & INIT_BIT_EXPERIENCE) != 0) {
                attributes.add("experience");
            }
            return "Cannot build Experience, some of required attributes are not set " + attributes;
        }
    }
}