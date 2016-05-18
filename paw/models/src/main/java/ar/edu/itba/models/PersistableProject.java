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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "project")
public class PersistableProject implements Project{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_project_id_seq")
    @SequenceGenerator(sequenceName = "project_project_id_seq", name = "project_project_id_seq", allocationSize = 1)
    @Column(name = "project_id", nullable = false, unique = true)
	private int projectId;
	
	@Column(length = 100, nullable = false, unique = true)
	private String name;
	
	@Column(length = 10, nullable = false, unique = true)
	private String code;
	
	@Column(length = 500, nullable = false)
	private String description;
	
	@Column(nullable = false)
	private LocalDate startDate;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Iteration> projectIterations;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BacklogItem> projectBacklogItems;

	private PersistableProject() {
		// Just for Hibernate
	}
	
	private PersistableProject(int projectId, String name, String code, String description, LocalDate startDate) {
		this.projectId = projectId;
		this.name = name;
		this.code = code;
		this.description = description;
		this.startDate = startDate;
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

	public LocalDate startDate() {
		return startDate;
	}
	
	public List<Iteration> geIterations() {
		return projectIterations;
	}
	
	public List<BacklogItem> getBacklogItems() {
		return projectBacklogItems;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public boolean equals(Object another) {
		if (this == another) return true;
		return another instanceof Project
				&& equalTo((PersistableProject) another);
	}
	
	private boolean equalTo(PersistableProject another) {
		return projectId == another.projectId
				&& name.equals(another.name)
				&& code.equals(another.code)
				&& description.equals(another.description)
				&& startDate.equals(another.startDate);
	}

	public int hashCode() {
		int h = 31;
		h = h * 17 + projectId;
		h = h * 17 + name.hashCode();
		h = h * 17 + code.hashCode();
		h = h * 17 + description.hashCode();
		h = h * 17 + startDate.hashCode();
		return h;
	}

	public String toString() {
		return "Project{"
				+ "projectId=" + projectId
				+ ", name=" + name
				+ ", code=" + code
				+ ", description=" + description
				+ ", startDate=" + startDate
				+ "}";
	}

	public static PersistableProject.Builder builder() {
		return new PersistableProject.Builder();
	}

	public static final class Builder {
		private static final long INIT_BIT_NAME = 0x1L;
		private static final long INIT_BIT_CODE = 0x2L;
		private static final long INIT_BIT_DESCRIPTION = 0x4L;
		private static final long INIT_BIT_START_DATE = 0x8L;
		private long initBits = 0xf;

		private int projectId;
		private String name;
		private String code;
		private String description;
		private LocalDate startDate;

		private Builder() {
		}

		public final Builder from(PersistableProject instance) {
			Objects.requireNonNull(instance, "instance");
			projectId(instance.projectId());
			name(instance.name());
			code(instance.code());
			description(instance.description());
			startDate(instance.startDate());
			return this;
		}

		public final Builder projectId(Integer projectId) {
			this.projectId = Objects.requireNonNull(projectId, "projectId");
			return this;
		}
		
		public final Builder projectId(Optional<Integer> projectId) {
			this.projectId = projectId.orElse(null);
			return this;
		}

		public final Builder name(String name) {
			this.name = Objects.requireNonNull(name, "name");
			initBits &= ~INIT_BIT_NAME;
			return this;
		}

		public final Builder code(String code) {
			this.code = Objects.requireNonNull(code, "code");
			initBits &= ~INIT_BIT_CODE;
			return this;
		}

		public final Builder description(String description) {
			this.description = Objects.requireNonNull(description, "description");
			initBits &= ~INIT_BIT_DESCRIPTION;
			return this;
		}

		public final Builder startDate(LocalDate startDate) {
			this.startDate = Objects.requireNonNull(startDate, "startDate");
			initBits &= ~INIT_BIT_START_DATE;
			return this;
		}

		public PersistableProject build() {
			if (initBits != 0) {
				throw new IllegalStateException(formatRequiredAttributesMessage());
			}
			return new PersistableProject(projectId, name, code, description, startDate);
		}

		private String formatRequiredAttributesMessage() {
			List<String> attributes = new ArrayList<String>();
			if ((initBits & INIT_BIT_NAME) != 0) attributes.add("name");
			if ((initBits & INIT_BIT_CODE) != 0) attributes.add("code");
			if ((initBits & INIT_BIT_DESCRIPTION) != 0) attributes.add("description");
			if ((initBits & INIT_BIT_START_DATE) != 0) attributes.add("startDate");
			return "Cannot build Project, some of required attributes are not set " + attributes;
		}
	}
}
