package ar.edu.itba.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@XmlRootElement
@Entity
@Table(name = "iteration", uniqueConstraints = @UniqueConstraint(columnNames = {"project_id", "number"}))
public final class Iteration {

    @XmlElement
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "iteration_iteration_id_seq")
    @SequenceGenerator(sequenceName = "iteration_iteration_id_seq", name = "iteration_iteration_id_seq", allocationSize = 1)
    @Column(name = "iteration_id", nullable = false, unique = true)
    private int iterationId;

    @XmlElement
    @Column(nullable = false)
    private int number;

    @XmlElement
    @Column(name = "date_start", nullable = false)
    private LocalDate startDate;

    @XmlElement
    @Column(name = "date_end", nullable = false)
    private LocalDate endDate;

    @XmlElement
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @OneToMany(mappedBy = "iteration", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Story> iterationStories;

    private Iteration() {
        // Just for Hibernate
    }

    private Iteration(final int iterationId, final int number, final LocalDate startDate, final LocalDate endDate, final Project project) {
        this.iterationId = iterationId;
        this.number = number;
        this.startDate = startDate;
        this.endDate = endDate;
        this.project = project;
    }

    public static Iteration.Builder builder() {
        return new Iteration.Builder();
    }

    public int iterationId() {
        return iterationId;
    }

    public int number() {
        return number;
    }

    public String formattedStartDate(final String format) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return startDate.format(formatter);
    }

    public String formattedEndDate(final String format) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return endDate.format(formatter);
    }

    public LocalDate startDate() {
        return startDate;
    }

    public LocalDate endDate() {
        return endDate;
    }

    public Project project() {
        return project;
    }

    public Status status() {
        final LocalDate now = LocalDate.now();
        if (now.compareTo(startDate) < 0) {
            return Status.NOT_STARTED;
        } else if (now.compareTo(endDate) <= 0) {
            return Status.STARTED;
        } else {
            return Status.COMPLETED;
        }
    }

    public List<Story> getStories() {
        return iterationStories;
    }

    public boolean equals(final Object another) {
        if (this == another) {
            return true;
        }
        return another instanceof Iteration
                && equalTo((Iteration) another);
    }

    private boolean equalTo(final Iteration another) {
        return iterationId == another.iterationId
                && number == another.number
                && startDate.equals(another.startDate)
                && endDate.equals(another.endDate)
                && project.equals(another.project);
    }

    public int hashCode() {
        int h = 31;
        h = h * 17 + iterationId;
        h = h * 17 + number;
        h = h * 17 + startDate.hashCode();
        h = h * 17 + endDate.hashCode();
        h = h * 17 + project.hashCode();
        return h;
    }

    public String toString() {
        return "Iteration{"
                + "iterationId=" + iterationId
                + ", number=" + number
                + ", startDate=" + startDate
                + ", endDate=" + endDate
                + ", project=" + project
                + "}";
    }

    public static final class Builder {
        private static final long INIT_BIT_NUMBER = 0x1L;
        private static final long INIT_BIT_START_DATE = 0x2L;
        private static final long INIT_BIT_END_DATE = 0x4L;
        private static final long INIT_BIT_PROJECT = 0x8L;
        private long initBits = 0xf;

        private int iterationId;
        private int number;
        private LocalDate startDate;
        private LocalDate endDate;
        private Project project;

        private Builder() {
        }

        public final Builder from(final Iteration instance) {
            Objects.requireNonNull(instance, "instance");
            iterationId(instance.iterationId());
            number(instance.number());
            startDate(instance.startDate());
            endDate(instance.endDate());
            project(instance.project());
            return this;
        }

        public final Builder iterationId(final Integer iterationId) {
            this.iterationId = Objects.requireNonNull(iterationId, "iterationId");
            return this;
        }

        public final Builder iterationId(final Optional<Integer> iterationId) {
            this.iterationId = iterationId.orElse(null);
            return this;
        }

        public final Builder number(final int number) {
            this.number = number;
            initBits &= ~INIT_BIT_NUMBER;
            return this;
        }

        public final Builder startDate(final LocalDate startDate) {
            this.startDate = Objects.requireNonNull(startDate, "startDate");
            initBits &= ~INIT_BIT_START_DATE;
            return this;
        }

        public final Builder endDate(final LocalDate endDate) {
            this.endDate = Objects.requireNonNull(endDate, "endDate");
            initBits &= ~INIT_BIT_END_DATE;
            return this;
        }

        public final Builder project(final Project project) {
            this.project = project;
            initBits &= ~INIT_BIT_PROJECT;
            return this;
        }

        public Iteration build() {
            if (initBits != 0) {
                throw new IllegalStateException(formatRequiredAttributesMessage());
            }
            return new Iteration(iterationId, number, startDate, endDate, project);
        }

        private String formatRequiredAttributesMessage() {
            final List<String> attributes = new ArrayList<String>();
            if ((initBits & INIT_BIT_NUMBER) != 0) {
                attributes.add("number");
            }
            if ((initBits & INIT_BIT_START_DATE) != 0) {
                attributes.add("startDate");
            }
            if ((initBits & INIT_BIT_END_DATE) != 0) {
                attributes.add("endDate");
            }
            if ((initBits & INIT_BIT_PROJECT) != 0) {
                attributes.add("project");
            }
            return "Cannot build Iteration, some of required attributes are not set " + attributes;
        }
    }
}