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
@Table(name = "task", uniqueConstraints = @UniqueConstraint(columnNames = {"story_id", "title"}))
public final class Task {

    @XmlElement
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_task_id_seq")
    @SequenceGenerator(sequenceName = "task_task_id_seq", name = "task_task_id_seq", allocationSize = 1)
    @Column(name = "task_id", nullable = false, unique = true)
    private int taskId;

    @XmlElement
    @Column(length = 100, nullable = false)
    private String title;

    @XmlElement
    @Column(length = 500)
    private String description;

    @XmlElement
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Status status;

    @XmlElement
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Priority priority;

    @XmlElement
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Score score;

    @XmlElement
    @ManyToOne
    @JoinColumn(name = "owner")
    private User owner;

    @XmlElement
    @ManyToOne
    @JoinColumn(name = "story_id", nullable = false)
    private Story story;

    private Task() {
        // Just for Hibernate
    }

    private Task(final int taskId, final String title, final String description, final Status status, final Score score, final Priority priority,
                 final User owner, final Story story) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.score = score;
        this.priority = priority;
        this.owner = owner;
        this.story = story;
    }

    public static Task.Builder builder() {
        return new Task.Builder();
    }

    public int taskId() {
        return taskId;
    }

    public String title() {
        return title;
    }

    public Optional<String> description() {
        return Optional.ofNullable(description);
    }

    public Status status() {
        return status;
    }

    public Score score() {
        return score;
    }

    public Priority priority() {
        return priority;
    }

    public Optional<User> owner() {
        return Optional.ofNullable(owner);
    }

    public Story story() {
        return story;
    }

    public boolean equals(final Object another) {
        if (this == another) {
            return true;
        }
        return another instanceof Task
                && equalTo((Task) another);
    }

    private boolean equalTo(final Task another) {
        return taskId == another.taskId
                && title.equals(another.title)
                && Objects.equals(description, another.description)
                && status.equals(another.status)
                && score.equals(another.score)
                && priority.equals(another.priority)
                && Objects.equals(owner, another.owner)
                && story == another.story;
    }

    public int hashCode() {
        int h = 31;
        h = h * 17 + taskId;
        h = h * 17 + title.hashCode();
        h = h * 17 + Objects.hashCode(description);
        h = h * 17 + status.hashCode();
        h = h * 17 + score.hashCode();
        h = h * 17 + priority.hashCode();
        h = h * 17 + Objects.hashCode(owner);
        h = h * 17 + story.hashCode();
        return h;
    }

    public String toString() {
        return "Task{"
                + "taskId=" + taskId
                + ", title=" + title
                + ", description=" + description
                + ", status=" + status
                + ", score=" + score
                + ", priority=" + priority
                + ", owner=" + owner
                + ", story=" + story
                + "}";
    }

    public static final class Builder {
        private static final long INIT_BIT_TITLE = 0x1L;
        private static final long INIT_BIT_STATUS = 0x2L;
        private static final long INIT_BIT_SCORE = 0x4L;
        private static final long INIT_BIT_PRIORITY = 0x8L;
        private static final long INIT_BIT_STORY = 0x10L;
        private long initBits = 0x1f;

        private int taskId;
        private String title;
        private String description;
        private Status status;
        private Score score;
        private Priority priority;
        private User owner;
        private Story story;

        private Builder() {
        }

        public final Builder from(final Task instance) {
            Objects.requireNonNull(instance, "instance");
            taskId(instance.taskId());
            title(instance.title());
            final Optional<String> descriptionOptional = instance.description();
            if (descriptionOptional.isPresent()) {
                description(descriptionOptional);
            }
            status(instance.status());
            score(instance.score());
            priority(instance.priority());
            final Optional<User> ownerOptional = instance.owner();
            if (ownerOptional.isPresent()) {
                owner(ownerOptional);
            }
            story(instance.story());
            return this;
        }

        public final Builder taskId(final Integer taskId) {
            this.taskId = Objects.requireNonNull(taskId, "taskId");
            return this;
        }

        public final Builder taskId(final Optional<Integer> taskId) {
            this.taskId = taskId.orElse(null);
            return this;
        }

        public final Builder title(final String title) {
            this.title = Objects.requireNonNull(title, "title");
            initBits &= ~INIT_BIT_TITLE;
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

        public final Builder status(final Status status) {
            this.status = Objects.requireNonNull(status, "status");
            initBits &= ~INIT_BIT_STATUS;
            return this;
        }

        public final Builder score(final Score score) {
            this.score = Objects.requireNonNull(score, "score");
            initBits &= ~INIT_BIT_SCORE;
            return this;
        }

        public final Builder priority(final Priority priority) {
            this.priority = Objects.requireNonNull(priority, "priority");
            initBits &= ~INIT_BIT_PRIORITY;
            return this;
        }

        public final Builder owner(final User owner) {
            this.owner = Objects.requireNonNull(owner, "owner");
            return this;
        }

        public final Builder owner(final Optional<User> owner) {
            this.owner = owner.orElse(null);
            return this;
        }

        public final Builder story(final Story story) {
            this.story = story;
            initBits &= ~INIT_BIT_STORY;
            return this;
        }

        public Task build() {
            if (initBits != 0) {
                throw new IllegalStateException(formatRequiredAttributesMessage());
            }
            return new Task(taskId, title, description, status, score, priority, owner, story);
        }

        private String formatRequiredAttributesMessage() {
            final List<String> attributes = new ArrayList<String>();
            if ((initBits & INIT_BIT_TITLE) != 0) {
                attributes.add("title");
            }
            if ((initBits & INIT_BIT_STATUS) != 0) {
                attributes.add("status");
            }
            if ((initBits & INIT_BIT_SCORE) != 0) {
                attributes.add("score");
            }
            if ((initBits & INIT_BIT_PRIORITY) != 0) {
                attributes.add("priority");
            }
            if ((initBits & INIT_BIT_STORY) != 0) {
                attributes.add("storyId");
            }
            return "Cannot build Task, some of required attributes are not set " + attributes;
        }
    }
}
