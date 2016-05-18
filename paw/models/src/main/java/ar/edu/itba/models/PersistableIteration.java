package ar.edu.itba.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "iteration", uniqueConstraints = @UniqueConstraint(columnNames = {"project_id", "number"}))
public class PersistableIteration implements Iteration{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "iteration_iteration_id_seq")
	@SequenceGenerator(sequenceName = "iteration_iteration_id_seq", name = "iteration_iteration_id_seq", allocationSize = 1)
	@Column(name = "iteration_id", nullable = false, unique = true)
	private int iterationId;

	@Column(nullable = false)
	private int number;

	@Column(name = "date_start", nullable = false)
	private LocalDate startDate;

	@Column(name = "date_end", nullable = false)
	private LocalDate endDate;

	@ManyToOne(targetEntity = PersistableProject.class)
	@JoinColumn(name = "project_id", nullable = false)
	private int projectId;

	@OneToMany(mappedBy = "iterationId", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PersistableStory> iterationStories;

	private PersistableIteration() {
		// Just for Hibernate
	}
	
	private PersistableIteration(int iterationId, int number, LocalDate startDate, LocalDate endDate, int projectId) {
		this.iterationId = iterationId;
		this.number = number;
		this.startDate = startDate;
		this.endDate = endDate;
		this.projectId = projectId;
	}

	public int iterationId() {
		return iterationId;
	}

	public int number() {
		return number;
	}

	public LocalDate startDate() {
		return startDate;
	}

	public LocalDate endDate() {
		return endDate;
	}

	public int projectId() {
		return projectId;
	}

	public List<? extends Story>getStories() {
		return iterationStories;
	}
	
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	public boolean equals(Object another) {
		if (this == another) return true;
		return another instanceof Iteration
				&& equalTo((PersistableIteration) another);
	}

	private boolean equalTo(PersistableIteration another) {
		return iterationId == another.iterationId
				&& number == another.number
				&& startDate.equals(another.startDate)
				&& endDate.equals(another.endDate)
				&& projectId == another.projectId;
	}

	public int hashCode() {
		int h = 31;
		h = h * 17 + iterationId;
		h = h * 17 + number;
		h = h * 17 + startDate.hashCode();
		h = h * 17 + endDate.hashCode();
		h = h * 17 + projectId;
		return h;
	}

	public String toString() {
		return "Iteration{"
				+ "iterationId=" + iterationId
				+ ", number=" + number
				+ ", startDate=" + startDate
				+ ", endDate=" + endDate
				+ ", projectId=" + projectId
				+ "}";
	}

	public static PersistableIteration.Builder builder() {
		return new PersistableIteration.Builder();
	}

	public static final class Builder {
		private static final long INIT_BIT_NUMBER = 0x1L;
		private static final long INIT_BIT_START_DATE = 0x2L;
		private static final long INIT_BIT_END_DATE = 0x4L;
		private static final long INIT_BIT_PROJECT_ID = 0x8L;
		private long initBits = 0xf;

		private int iterationId;
		private int number;
		private LocalDate startDate;
		private LocalDate endDate;
		private int projectId;

		private Builder() {
		}

		public final Builder from(PersistableIteration instance) {
			Objects.requireNonNull(instance, "instance");
			iterationId(instance.iterationId());
			number(instance.number());
			startDate(instance.startDate());
			endDate(instance.endDate());
			projectId(instance.projectId());
			return this;
		}

		public final Builder iterationId(Integer iterationId) {
			this.iterationId = Objects.requireNonNull(iterationId, "iterationId");
			return this;
		}
		
		public final Builder iterationId(Optional<Integer> iterationId) {
			this.iterationId = iterationId.orElse(null);
			return this;
		}

		public final Builder number(int number) {
			this.number = number;
			initBits &= ~INIT_BIT_NUMBER;
			return this;
		}

		public final Builder startDate(LocalDate startDate) {
			this.startDate = Objects.requireNonNull(startDate, "startDate");
			initBits &= ~INIT_BIT_START_DATE;
			return this;
		}

		public final Builder endDate(LocalDate endDate) {
			this.endDate = Objects.requireNonNull(endDate, "endDate");
			initBits &= ~INIT_BIT_END_DATE;
			return this;
		}

		public final Builder projectId(int projectId) {
			this.projectId = projectId;
			initBits &= ~INIT_BIT_PROJECT_ID;
			return this;
		}

		public PersistableIteration build() {
			if (initBits != 0) {
				throw new IllegalStateException(formatRequiredAttributesMessage());
			}
			return new PersistableIteration(iterationId, number, startDate, endDate, projectId);
		}

		private String formatRequiredAttributesMessage() {
			List<String> attributes = new ArrayList<String>();
			if ((initBits & INIT_BIT_NUMBER) != 0) attributes.add("number");
			if ((initBits & INIT_BIT_START_DATE) != 0) attributes.add("startDate");
			if ((initBits & INIT_BIT_END_DATE) != 0) attributes.add("endDate");
			if ((initBits & INIT_BIT_PROJECT_ID) != 0) attributes.add("projectId");
			return "Cannot build Iteration, some of required attributes are not set " + attributes;
		}
	}
}