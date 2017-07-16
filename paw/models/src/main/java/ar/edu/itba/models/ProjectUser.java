package ar.edu.itba.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "project_user")
public class ProjectUser implements Serializable{

	@Id
	@ManyToOne
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;
	
	@Id
	@ManyToOne
	@XmlElement
	@JoinColumn(name = "username", nullable = false)
	private User user;
	
	public ProjectUser() {
		// Just for Hibernate
	}
	
	public ProjectUser(Project project, User user) {
		this.project = project;
		this.user = user;
	}
	
	public Project project() {
		return project;
	}
	
	public User user() {
		return user;
	}
	
	public boolean equals(Object another) {
		if (this == another) return true;
		return another instanceof Task
				&& equalTo((ProjectUser) another);
	}

	private boolean equalTo(ProjectUser another) {
		return project.equals(another.project)
				&& user.equals(another.user);
	}

	public int hashCode() {
		int h = 31;
		h = h * 17 + project.hashCode();
		h = h * 17 + user.hashCode();
		return h;
	}

	public String toString() {
		return "ProjectUser{"
				+ "project=" + project
				+ ", user=" + user
				+ "}";
	}
	
	public static ProjectUser.Builder builder() {
		return new ProjectUser.Builder();
	}

	public static final class Builder {
		private static final long INIT_BIT_PROJECT = 0x1L;
		private static final long INIT_BIT_USER = 0x2L;
		private long initBits = 0x3;

		private Project project;
		private User user;

		private Builder() {
		}

		public final Builder from(ProjectUser instance) {
			Objects.requireNonNull(instance, "instance");
			project(instance.project());
			user(instance.user());
			return this;
		}

		public final Builder project(Project project) {
			this.project = Objects.requireNonNull(project, "project");
			initBits &= ~INIT_BIT_PROJECT;
			return this;
		}

		public final Builder user(User user) {
			this.user = Objects.requireNonNull(user, "user");
			initBits &= ~INIT_BIT_USER;
			return this;
		}

		public ProjectUser build() {
			if (initBits != 0) {
				throw new IllegalStateException(formatRequiredAttributesMessage());
			}
			return new ProjectUser(project, user);
		}

		private String formatRequiredAttributesMessage() {
			List<String> attributes = new ArrayList<String>();
			if ((initBits & INIT_BIT_PROJECT) != 0) attributes.add("project");
			if ((initBits & INIT_BIT_USER) != 0) attributes.add("user");
			return "Cannot build ProjectUser, some of required attributes are not set " + attributes;
		}
	}
}
