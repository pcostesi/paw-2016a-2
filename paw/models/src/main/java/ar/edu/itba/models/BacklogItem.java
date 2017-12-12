package ar.edu.itba.models;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@XmlRootElement
@Entity
@Table(name = "backlog", uniqueConstraints = @UniqueConstraint(columnNames = {"project_id", "title"}))
public final class BacklogItem {

    @XmlElement
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "backlog_item_id_seq")
    @SequenceGenerator(sequenceName = "backlog_item_id_seq", name = "backlog_item_id_seq", allocationSize = 1)
    @Column(name = "item_id", nullable = false)
    private int backlogItemId;

    @XmlElement
    @Column(length = 100, nullable = false)
    private String title;

    @XmlElement
    @Column(length = 500)
    private String description;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    private BacklogItem() {
        // Just for Hibernate
    }

    private BacklogItem(final int backlogItemId, final String title, final String description, final Project project) {
        this.backlogItemId = backlogItemId;
        this.title = title;
        this.description = description;
        this.project = project;
    }

    public static BacklogItem.Builder builder() {
        return new BacklogItem.Builder();
    }

    public int backlogItemId() {
        return backlogItemId;
    }

    public String title() {
        return title;
    }

    public Optional<String> description() {
        return Optional.ofNullable(description);
    }

    public Project project() {
        return project;
    }

    public boolean equals(final Object another) {
        if (this == another) {
            return true;
        }
        return another instanceof BacklogItem && equalTo((BacklogItem) another);
    }

    private boolean equalTo(final BacklogItem another) {
        return backlogItemId == another.backlogItemId
                && title.equals(another.title)
                && Objects.equals(description, another.description);
    }

    public int hashCode() {
        int h = 31;
        h = h * 17 + backlogItemId;
        h = h * 17 + title.hashCode();
        h = h * 17 + Objects.hashCode(description);
        return h;
    }

    public String toString() {
        final StringBuilder builder = new StringBuilder("BacklogItem{");
        builder.append("backlogItemId=").append(backlogItemId);
        builder.append(", ");
        builder.append("title=").append(title);
        if (description != null) {
            builder.append(", ");
            builder.append("description=").append(description);
        }
        return builder.append("}").toString();
    }

    public static final class Builder {
        private static final long INIT_BIT_TITLE = 0x1L;
        private static final long INIT_BIT_PROJECT = 0x2L;
        private long initBits = 0x3;

        private int backlogItemId;
        private String title;
        private String description;
        private Project project;

        private Builder() {
        }

        public final Builder from(final BacklogItem instance) {
            Objects.requireNonNull(instance, "instance");
            backlogItemId(instance.backlogItemId());
            title(instance.title());
            final Optional<String> descriptionOptional = instance.description();
            if (descriptionOptional.isPresent()) {
                description(descriptionOptional);
            }
            project(instance.project());
            return this;
        }

        public final Builder backlogItemId(final Integer backlogItemId) {
            this.backlogItemId = Objects.requireNonNull(backlogItemId, "backlogItemId");
            return this;
        }

        public final Builder backlogItemId(final Optional<Integer> backlogItemId) {
            this.backlogItemId = backlogItemId.orElse(null);
            return this;
        }

        public final Builder title(final String title) {
            this.title = Objects.requireNonNull(title, "title");
            initBits &= ~INIT_BIT_TITLE;
            return this;
        }

        public final Builder project(final Project project) {
            this.project = project;
            initBits &= ~INIT_BIT_PROJECT;
            return this;
        }

        public final Builder description(final String description) {
            this.description = Objects.requireNonNull(description, "description");
            return this;
        }

        public final Builder description(final Optional<String> description) {
            this.description = description.orElse(null);
            return this;
        }

        public BacklogItem build() {
            if (initBits != 0) {
                throw new IllegalStateException(formatRequiredAttributesMessage());
            }
            return new BacklogItem(backlogItemId, title, description, project);
        }

        private String formatRequiredAttributesMessage() {
            final List<String> attributes = new ArrayList<String>();
            if ((initBits & INIT_BIT_TITLE) != 0) {
                attributes.add("title");
            }
            if ((initBits & INIT_BIT_PROJECT) != 0) {
                attributes.add("project");
            }
            return "Cannot build BacklogItem, some of required attributes are not set " + attributes;
        }
    }
}