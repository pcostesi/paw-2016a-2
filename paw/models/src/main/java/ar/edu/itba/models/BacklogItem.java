package ar.edu.itba.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "backlog", uniqueConstraints = @UniqueConstraint(columnNames = {"project_id", "title"}))
public class BacklogItem{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "backlog_item_id_seq")
	@SequenceGenerator(sequenceName = "backlog_item_id_seq", name = "backlog_item_id_seq", allocationSize = 1)
	@Column(name = "item_id", nullable = false)	
	private int backlogItemId;

	@Column(length = 100, nullable = false)
	private String title;

	@Column(length = 500, nullable = true)
	private String description;

	@ManyToOne
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;

	private BacklogItem() {
		// Just for Hibernate
	}

	private BacklogItem(int backlogItemId, String title, String description, Project project) {
		this.backlogItemId = backlogItemId;
		this.title = title;
		this.description = description;
		this.project = project;
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
	
	public boolean equals(Object another) {
		if (this == another) {
			return true;
		}
		return another instanceof BacklogItem && equalTo((BacklogItem) another);
	}

	private boolean equalTo(BacklogItem another) {
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
		StringBuilder builder = new StringBuilder("BacklogItem{");
		builder.append("backlogItemId=").append(backlogItemId);
		builder.append(", ");
		builder.append("title=").append(title);
		if (description != null) {
			builder.append(", ");
			builder.append("description=").append(description);
		}
		return builder.append("}").toString();
	}

	public static BacklogItem.Builder builder() {
		return new BacklogItem.Builder();
	}

	public static final class Builder {
		private static final long INIT_BIT_TITLE = 0x1L;
		private static final long INIT_BIT_PROJECT= 0x2L;
		private long initBits = 0x3;

		private int backlogItemId;
		private String title;
		private String description;
		private Project project;

		private Builder() {
		}

		public final Builder from(BacklogItem instance) {
			Objects.requireNonNull(instance, "instance");
			backlogItemId(instance.backlogItemId());
			title(instance.title());
			Optional<String> descriptionOptional = instance.description();
			if (descriptionOptional.isPresent()) {
				description(descriptionOptional);
			}
			project(instance.project());
			return this;
		}

		public final Builder backlogItemId(Integer backlogItemId) {
			this.backlogItemId = Objects.requireNonNull(backlogItemId, "backlogItemId");
			return this;
		}
		
		public final Builder backlogItemId(Optional<Integer> backlogItemId) {
			this.backlogItemId = backlogItemId.orElse(null);
			return this;
		}

		public final Builder title(String title) {
			this.title = Objects.requireNonNull(title, "title");
			initBits &= ~INIT_BIT_TITLE;
			return this;
		}
		
		public final Builder project(Project project) {
			this.project = project;
			initBits &= ~INIT_BIT_PROJECT;
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

		public BacklogItem build() {
			if (initBits != 0) {
				throw new IllegalStateException(formatRequiredAttributesMessage());
			}
			return new BacklogItem(backlogItemId, title, description, project);
		}

		private String formatRequiredAttributesMessage() {
			List<String> attributes = new ArrayList<String>();
			if ((initBits & INIT_BIT_TITLE) != 0) attributes.add("title");
			if ((initBits & INIT_BIT_PROJECT) != 0) attributes.add("project");
			return "Cannot build BacklogItem, some of required attributes are not set " + attributes;
		}
	}
}