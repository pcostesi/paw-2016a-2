package ar.edu.itba.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@XmlRootElement
@Entity
@Table(name = "project")
public final class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_project_id_seq")
    @SequenceGenerator(sequenceName = "project_project_id_seq", name = "project_project_id_seq", allocationSize = 1)
    @Column(name = "project_id", nullable = false, unique = true)
    private int projectId;

    @XmlElement
    @Column(length = 100, nullable = false, unique = true)
    private String name;

    @XmlElement
    @Column(length = 10, nullable = false, unique = true)
    private String code;

    @XmlElement
    @Column(length = 500, nullable = false)
    private String description;

    @XmlElement
    @Column(name = "date_start", nullable = false)
    private LocalDate startDate;

    @XmlElement
    @ManyToOne
    @JoinColumn(name = "admin", nullable = false)
    private User admin;

    @XmlElement(name = "members")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "project_user", joinColumns = {
            @JoinColumn(name = "project_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "username",
                    nullable = false, updatable = false)})
    private Set<User> members;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Iteration> projectIterations;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<BacklogItem> projectBacklogItems;

    private Project() {
        // Just for Hibernate
    }

    private Project(final int projectId, final String name, final String code, final String description, final User admin, final LocalDate startDate) {
        this.projectId = projectId;
        this.name = name;
        this.code = code;
        this.description = description;
        this.startDate = startDate;
        this.admin = admin;
    }

    public static Project.Builder builder() {
        return new Project.Builder();
    }

    public int projectId() {
        return projectId;
    }

    public String name() {
        return name;
    }

    public String code() {
        return code;
    }

    public String description() {
        return description;
    }

    public String formattedStartDate(final String format) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return startDate.format(formatter);
    }

    public LocalDate startDate() {
        return startDate;
    }

    public User admin() {
        return admin;
    }

    public List<Iteration> geIterations() {
        return projectIterations;
    }

    public List<BacklogItem> getBacklogItems() {
        return projectBacklogItems;
    }

    public Set<User> getMembers() {
        return members;
    }

    public boolean equals(final Object another) {
        if (this == another) {
            return true;
        }
        return another instanceof Project
                && equalTo((Project) another);
    }

    private boolean equalTo(final Project another) {
        return projectId == another.projectId
                && name.equals(another.name)
                && code.equals(another.code)
                && description.equals(another.description)
                && startDate.equals(another.startDate)
                && admin.equals(another.admin);
    }

    public int hashCode() {
        int h = 31;
        h = h * 17 + projectId;
        h = h * 17 + name.hashCode();
        h = h * 17 + code.hashCode();
        h = h * 17 + description.hashCode();
        h = h * 17 + startDate.hashCode();
        h = h * 17 + admin.hashCode();
        return h;
    }

    public String toString() {
        return "Project{"
                + "projectId=" + projectId
                + ", name=" + name
                + ", code=" + code
                + ", description=" + description
                + ", admin=" + admin
                + ", startDate=" + startDate
                + "}";
    }

    public static final class Builder {
        private static final long INIT_BIT_NAME = 0x1L;
        private static final long INIT_BIT_CODE = 0x2L;
        private static final long INIT_BIT_DESCRIPTION = 0x4L;
        private static final long INIT_BIT_START_DATE = 0x8L;
        private static final long INIT_BIT_ADMIN = 0x10L;
        private long initBits = 0x1f;

        private int projectId;
        private String name;
        private String code;
        private String description;
        private LocalDate startDate;
        private User admin;

        private Builder() {
        }

        public final Builder from(final Project instance) {
            Objects.requireNonNull(instance, "instance");
            projectId(instance.projectId());
            name(instance.name());
            code(instance.code());
            description(instance.description());
            startDate(instance.startDate());
            admin(instance.admin());
            return this;
        }

        public final Builder projectId(final Integer projectId) {
            this.projectId = Objects.requireNonNull(projectId, "projectId");
            return this;
        }

        public final Builder projectId(final Optional<Integer> projectId) {
            this.projectId = projectId.orElse(null);
            return this;
        }

        public final Builder name(final String name) {
            this.name = Objects.requireNonNull(name, "name");
            initBits &= ~INIT_BIT_NAME;
            return this;
        }

        public final Builder code(final String code) {
            this.code = Objects.requireNonNull(code, "code");
            initBits &= ~INIT_BIT_CODE;
            return this;
        }

        public final Builder description(final String description) {
            this.description = Objects.requireNonNull(description, "description");
            initBits &= ~INIT_BIT_DESCRIPTION;
            return this;
        }

        public final Builder startDate(final LocalDate startDate) {
            this.startDate = Objects.requireNonNull(startDate, "startDate");
            initBits &= ~INIT_BIT_START_DATE;
            return this;
        }

        public Builder admin(final User admin) {
            this.admin = Objects.requireNonNull(admin, "admin");
            initBits &= ~INIT_BIT_ADMIN;
            return this;
        }

        public Project build() {
            if (initBits != 0) {
                throw new IllegalStateException(formatRequiredAttributesMessage());
            }
            return new Project(projectId, name, code, description, admin, startDate);
        }

        private String formatRequiredAttributesMessage() {
            final List<String> attributes = new ArrayList<String>();
            if ((initBits & INIT_BIT_NAME) != 0) {
                attributes.add("name");
            }
            if ((initBits & INIT_BIT_CODE) != 0) {
                attributes.add("code");
            }
            if ((initBits & INIT_BIT_DESCRIPTION) != 0) {
                attributes.add("description");
            }
            if ((initBits & INIT_BIT_START_DATE) != 0) {
                attributes.add("startDate");
            }
            if ((initBits & INIT_BIT_ADMIN) != 0) {
                attributes.add("startDate");
            }
            return "Cannot build Project, some of required attributes are not set " + attributes;
        }
    }
}
