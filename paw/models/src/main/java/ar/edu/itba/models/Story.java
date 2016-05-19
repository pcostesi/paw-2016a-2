package ar.edu.itba.models;

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

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "story", uniqueConstraints = @UniqueConstraint(columnNames = {"iteration_id", "title"}))
public class Story{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "story_story_id_seq")
    @SequenceGenerator(sequenceName = "story_story_id_seq", name = "story_story_id_seq", allocationSize = 1)
    @Column(name = "story_id", nullable = false, unique = true)
	private int storyId;
	
	@Column(length = 100, nullable = false)
	private String title;
	
	@Formula("(select coalesce(min(task.status),0) from task where task.story_id = story_id)")
	private Status status;
	
	@Formula("(select coalesce(sum(task.score),0) from task where task.story_id = story_id)")
	private int totalScore;
	
	@ManyToOne
	@JoinColumn(name = "iteration_id", nullable = false)
	private Iteration iteration;
	
	@OneToMany(mappedBy ="story", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Task> storyTasks;
	
	private Story() {
		// Just for Hibernate
	}
	
	private Story(int storyId, String title, Iteration iteration) {
		this.storyId = storyId;
		this.title = title;
		this.iteration = iteration;
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

	public Iteration iteration() {
		return iteration;
	}

	public List<Task> tasks() {
		return storyTasks;
	}
	
	public boolean equals(Object another) {
		if (this == another) return true;
		return another instanceof Story
				&& equalTo((Story) another);
	}

	private boolean equalTo(Story another) {
		return storyId == another.storyId
				&& title.equals(another.title)
				&& status.equals(another.status)
				&& totalScore == another.totalScore
				&& iteration.equals(another.iteration);
	}

	public int hashCode() {
		int h = 31;
		h = h * 17 + storyId;
		h = h * 17 + title.hashCode();
		h = h * 17 + status.hashCode();
		h = h * 17 + totalScore;
		h = h * 17 + iteration.hashCode();
		return h;
	}

	public String toString() {
		return "Story{"
				+ "storyId=" + storyId
				+ ", title=" + title
				+ ", status=" + status
				+ ", totalScore=" + totalScore
				+ ", iteration=" + iteration
				+ "}";
	}

	public static Story.Builder builder() {
		return new Story.Builder();
	}

	public static final class Builder {
		private static final long INIT_BIT_TITLE = 0x1L;
		private static final long INIT_BIT_ITERATION = 0x2L;
		private long initBits = 0x3;

		private int storyId;
		private String title;
		private Iteration iteration;

		private Builder() {
		}

		public final Builder from(Story instance) {
			Objects.requireNonNull(instance, "instance");
			storyId(instance.storyId());
			title(instance.title());
			iteration(instance.iteration());
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

		public final Builder iteration(Iteration iteration) {
			this.iteration = iteration;
			initBits &= ~INIT_BIT_ITERATION;
			return this;
		}

		public Story build() {
			if (initBits != 0) {
				throw new IllegalStateException(formatRequiredAttributesMessage());
			}
			return new Story(storyId, title, iteration);
		}

		private String formatRequiredAttributesMessage() {
			List<String> attributes = new ArrayList<String>();
			if ((initBits & INIT_BIT_TITLE) != 0) attributes.add("title");
			if ((initBits & INIT_BIT_ITERATION) != 0) attributes.add("iteration");
			return "Cannot build Story, some of required attributes are not set " + attributes;
		}
	}
}
