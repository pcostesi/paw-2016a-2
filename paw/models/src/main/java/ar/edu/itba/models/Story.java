package ar.edu.itba.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Formula;

@Entity
@Table(name = "story", uniqueConstraints = @UniqueConstraint(columnNames = {"iteration_id", "title"}))
public class Story {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "story_story_id_seq")
    @SequenceGenerator(sequenceName = "story_story_id_seq", name = "story_story_id_seq", allocationSize = 1)
    @Column(name = "story_id", nullable = false, unique = true)
	private int storyId;
	
	@Column(length = 100, nullable = false)
	private String title;
	
	@Formula("SELECT min(task.status) FROM Task task WHERE task.storyId = storyId")
	private Status status;
	
	@Formula("SELECT sum(task.score) FROM Task task WHERE task.storyId = storyId")
	private int totalScore;
	
	@ManyToOne
	@Column(name = "iteration_id", nullable = false)
	private int iterationId;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Task> storyTasks;
	
	private Story() {
		// Just for Hibernate
	}
	
	private Story(int storyId, String title, Status status, int totalScore, int iterationId) {
		this.storyId = storyId;
		this.title = title;
		this.status = status;
		this.totalScore = totalScore;
		this.iterationId = iterationId;
	}

	public int storyId() {
		return storyId;
	}

	public String title() {
		return title;
	}

	public Status status() {
		return status;
	}

	public int totalScore() {
		return totalScore;
	}

	public int iterationId() {
		return iterationId;
	}

	public boolean equals(Object another) {
		if (this == another) return true;
		return another instanceof Story
				&& equalTo((Story) another);
	}

	private boolean equalTo(Story another) {
		return storyId == another.storyId
				&& title.equals(another.title)
				&& status.equals(another.status())
				&& totalScore == another.totalScore
				&& iterationId == another.iterationId;
	}

	public int hashCode() {
		int h = 31;
		h = h * 17 + storyId;
		h = h * 17 + title.hashCode();
		h = h * 17 + status.hashCode();
		h = h * 17 + totalScore;
		h = h * 17 + iterationId;
		return h;
	}

	public String toString() {
		return "Story{"
				+ "storyId=" + storyId
				+ ", title=" + title
				+ ", status=" + status
				+ ", totalScore=" + totalScore
				+ ", iterationId=" + iterationId
				+ "}";
	}

	public static Story.Builder builder() {
		return new Story.Builder();
	}

	public static final class Builder {
		private static final long INIT_BIT_TITLE = 0x1L;
		private static final long INIT_BIT_STATUS = 0x2L;
		private static final long INIT_BIT_TOTAL_SCORE = 0x4L;
		private static final long INIT_BIT_ITERATION_ID = 0x8L;
		private long initBits = 0xf;

		private int storyId;
		private String title;
		private Status status;
		private int totalScore;
		private int iterationId;

		private Builder() {
		}

		public final Builder from(Story instance) {
			Objects.requireNonNull(instance, "instance");
			storyId(instance.storyId());
			title(instance.title());
			status(instance.status());
			totalScore(instance.totalScore());
			iterationId(instance.iterationId());
			return this;
		}

		public final Builder storyId(Integer storyId) {
			this.storyId = Objects.requireNonNull(storyId, "storyId");
			return this;
		}
		
		public final Builder storyId(Optional<Integer> storyId) {
			this.storyId = storyId.orElse(null);
			return this;
		}

		public final Builder title(String title) {
			this.title = Objects.requireNonNull(title, "title");
			initBits &= ~INIT_BIT_TITLE;
			return this;
		}

		public final Builder status(Status status) {
			this.status = Objects.requireNonNull(status, "status");
			initBits &= ~INIT_BIT_STATUS;
			return this;
		}

		public final Builder totalScore(int totalScore) {
			this.totalScore = totalScore;
			initBits &= ~INIT_BIT_TOTAL_SCORE;
			return this;
		}

		public final Builder iterationId(int iterationId) {
			this.iterationId = iterationId;
			initBits &= ~INIT_BIT_ITERATION_ID;
			return this;
		}

		public Story build() {
			if (initBits != 0) {
				throw new IllegalStateException(formatRequiredAttributesMessage());
			}
			return new Story(storyId, title, status, totalScore, iterationId);
		}

		private String formatRequiredAttributesMessage() {
			List<String> attributes = new ArrayList<String>();
			if ((initBits & INIT_BIT_TITLE) != 0) attributes.add("title");
			if ((initBits & INIT_BIT_STATUS) != 0) attributes.add("status");
			if ((initBits & INIT_BIT_TOTAL_SCORE) != 0) attributes.add("totalScore");
			if ((initBits & INIT_BIT_ITERATION_ID) != 0) attributes.add("iterationId");
			return "Cannot build Story, some of required attributes are not set " + attributes;
		}
	}
}
