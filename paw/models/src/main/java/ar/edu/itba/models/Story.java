package ar.edu.itba.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

import ImmutableStory.Builder;

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
		private static final long INIT_BIT_STORY_ID = 0x1L;
		private static final long INIT_BIT_TITLE = 0x2L;
		private static final long INIT_BIT_STATUS = 0x4L;
		private static final long INIT_BIT_TOTAL_SCORE = 0x8L;
		private static final long INIT_BIT_ITERATION_ID = 0x10L;
		private long initBits = 0x7;

		private int storyId;
		private String title;
		private int iteration;

		private Builder() {
		}

		/**
		 * Fill a builder with attribute values from the provided {@code Story} instance.
		 * Regular attribute values will be replaced with those from the given instance.
		 * Absent optional values will not replace present values.
		 * @param instance The instance from which to copy values
		 * @return {@code this} builder for use in a chained invocation
		 */
		public final Builder from(Story instance) {
			Objects.requireNonNull(instance, "instance");
			storyId(instance.storyId());
			title(instance.title());
			iteration(instance.iterationId());
			return this;
		}

		/**
		 * Initializes the value for the {@link Story#storyId() storyId} attribute.
		 * @param storyId The value for storyId 
		 * @return {@code this} builder for use in a chained invocation
		 */
		public final Builder storyId(int storyId) {
			this.storyId = storyId;
			initBits &= ~INIT_BIT_STORY_ID;
			return this;
		}

		/**
		 * Initializes the value for the {@link Story#title() title} attribute.
		 * @param title The value for title 
		 * @return {@code this} builder for use in a chained invocation
		 */
		public final Builder title(String title) {
			this.title = Objects.requireNonNull(title, "title");
			initBits &= ~INIT_BIT_TITLE;
			return this;
		}

		/**
		 * Initializes the value for the {@link Story#iteration() iteration} attribute.
		 * @param iteration The value for iteration 
		 * @return {@code this} builder for use in a chained invocation
		 */
		public final Builder iteration(int iteration) {
			this.iteration = iteration;
			initBits &= ~INIT_BIT_ITERATION;
			return this;
		}

		/**
		 * Builds a new {@link ImmutableStory ImmutableStory}.
		 * @return An immutable instance of Story
		 * @throws java.lang.IllegalStateException if any required attributes are missing
		 */
		public ImmutableStory build() {
			if (initBits != 0) {
				throw new IllegalStateException(formatRequiredAttributesMessage());
			}
			return new ImmutableStory(storyId, title, iteration);
		}

		private String formatRequiredAttributesMessage() {
			List<String> attributes = new ArrayList<String>();
			if ((initBits & INIT_BIT_STORY_ID) != 0) attributes.add("storyId");
			if ((initBits & INIT_BIT_TITLE) != 0) attributes.add("title");
			if ((initBits & INIT_BIT_ITERATION) != 0) attributes.add("iteration");
			return "Cannot build Story, some of required attributes are not set " + attributes;
		}
	}
}
