package ar.edu.itba.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "task", uniqueConstraints = @UniqueConstraint(columnNames = {"story_id", "title"}))
public class PersistableTask implements Task{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_task_id_seq")
    @SequenceGenerator(sequenceName = "task_task_id_seq", name = "task_task_id_seq", allocationSize = 1)
    @Column(name = "task_id", nullable = false, unique = true)
	private int taskId;
	
	@Column(length = 100, nullable = false)
	private String title;
	
	@Column(length = 500, nullable = true)
	private String description;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	private Status status;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	private Priority priority;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	private Score score;
	
	@ManyToOne
	@Column(nullable = true)
	private String owner;
	
	@ManyToOne
	@Column(name = "story_id", nullable = false)
	private int storyId;

	private PersistableTask() {
		// Just for Hibernate
	}
	
	private PersistableTask(int taskId, String title, String description, Status status, Score score, Priority priority,
			String owner, int storyId) {
		this.taskId = taskId;
		this.title = title;
		this.description = description;
		this.status = status;
		this.score = score;
		this.priority = priority;
		this.owner = owner;
		this.storyId = storyId;
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

	public Optional<String> owner() {
		return Optional.ofNullable(owner);
	}
	
	public int storyId() {
		return storyId;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setOwner(Optional<String> owner) {
		this.owner = owner.orElse(null);
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(Optional<String> description) {
		this.description = description.orElse(null);
	}
	
	public boolean equals(Object another) {
		if (this == another) return true;
		return another instanceof Task
				&& equalTo((PersistableTask) another);
	}

	private boolean equalTo(PersistableTask another) {
		return taskId == another.taskId
				&& title.equals(another.title)
				&& Objects.equals(description, another.description)
				&& status.equals(another.status)
				&& score.equals(another.score)
				&& priority.equals(another.priority)
				&& Objects.equals(owner, another.owner)
				&& storyId == another.storyId;
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
		h = h * 17 + storyId;
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
				+ ", storyId=" + storyId
				+ "}";
	}

	public static PersistableTask.Builder builder() {
		return new PersistableTask.Builder();
	}

	public static final class Builder {
		private static final long INIT_BIT_TITLE = 0x1L;
		private static final long INIT_BIT_STATUS = 0x2L;
		private static final long INIT_BIT_SCORE = 0x4L;
		private static final long INIT_BIT_PRIORITY = 0x8L;
		private static final long INIT_BIT_STORY_ID = 0x10L;
		private long initBits = 0x1f;

		private int taskId;
		private String title;
		private String description;
		private Status status;
		private Score score;
		private Priority priority;
		private String owner;
		private int storyId;

		private Builder() {
		}

		public final Builder from(PersistableTask instance) {
			Objects.requireNonNull(instance, "instance");
			taskId(instance.taskId());
			title(instance.title());
			Optional<String> descriptionOptional = instance.description();
			if (descriptionOptional.isPresent()) {
				description(descriptionOptional);
			}
			status(instance.status());
			score(instance.score());
			priority(instance.priority());
			Optional<String> ownerOptional = instance.owner();
			if (ownerOptional.isPresent()) {
				owner(ownerOptional);
			}
			storyId(instance.storyId());
			return this;
		}

		public final Builder taskId(Integer taskId) {
			this.taskId = Objects.requireNonNull(taskId, "taskId");
			return this;
		}
		
		public final Builder taskId(Optional<Integer> taskId) {
			this.taskId = taskId.orElse(null);
			return this;
		}

		public final Builder title(String title) {
			this.title = Objects.requireNonNull(title, "title");
			initBits &= ~INIT_BIT_TITLE;
			return this;
		}

		public final Builder description(String description) {
			this.description = Objects.requireNonNull(description, "description");
			return this;
		}

		public final Builder description(Optional<String> description) {
			this.description = description.orElse(null);
			return this;
		}

		public final Builder status(Status status) {
			this.status = Objects.requireNonNull(status, "status");
			initBits &= ~INIT_BIT_STATUS;
			return this;
		}

		public final Builder score(Score score) {
			this.score = Objects.requireNonNull(score, "score");
			initBits &= ~INIT_BIT_SCORE;
			return this;
		}

		public final Builder priority(Priority priority) {
			this.priority = Objects.requireNonNull(priority, "priority");
			initBits &= ~INIT_BIT_PRIORITY;
			return this;
		}
		
		public final Builder owner(String owner) {
			this.owner = Objects.requireNonNull(owner, "owner");
			return this;
		}
		
		public final Builder owner(Optional<String> owner) {
			this.owner = owner.orElse(null);
			return this;
		}
		
		public final Builder storyId(int storyId) {
			this.storyId = storyId;
			initBits &= ~INIT_BIT_STORY_ID;
			return this;
		}

		public PersistableTask build() {
			if (initBits != 0) {
				throw new IllegalStateException(formatRequiredAttributesMessage());
			}
			return new PersistableTask(taskId, title, description, status, score, priority, owner, storyId);
		}

		private String formatRequiredAttributesMessage() {
			List<String> attributes = new ArrayList<String>();
			if ((initBits & INIT_BIT_TITLE) != 0) attributes.add("title");
			if ((initBits & INIT_BIT_STATUS) != 0) attributes.add("status");
			if ((initBits & INIT_BIT_SCORE) != 0) attributes.add("score");
			if ((initBits & INIT_BIT_PRIORITY) != 0) attributes.add("priority");
			if ((initBits & INIT_BIT_STORY_ID) != 0) attributes.add("storyId");
			return "Cannot build Task, some of required attributes are not set " + attributes;
		}
	}
}
