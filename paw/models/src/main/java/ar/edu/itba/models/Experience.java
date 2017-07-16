package ar.edu.itba.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlElement;

@Entity
@Table(name = "experience", uniqueConstraints = @UniqueConstraint(columnNames = {"project_id", "user_id	"}))
public class Experience implements Serializable {

	@Id
	@XmlElement
	@ManyToOne
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;
	
	@Id
	@XmlElement
	@ManyToOne
	@JoinColumn(name = "username", nullable = false)
	private User user;

	@XmlElement
	@Column(nullable = false)
	private int experience;

	private Experience() {
		// Just for Hibernate
	}
	
	private Experience(Project project, User user, int experience) {
		this.project = project;
		this.user = user;
		this.experience = experience;
	}

	public Project project() {
		return project;
	}

	public User user() {
		return user;
	}

	public int experience() {
		return experience;
	}

	
	public boolean equals(Object another) {
		if (this == another) return true;
		return another instanceof Experience 
				&& equalTo((Experience) another);
	}

	private boolean equalTo(Experience another) {
		return experience == another.experience
				&& user.equals(another.user)
				&& project.equals(another.project);
	}

	public int hashCode() {
		int h = 31;
		h = h * 17 + project.hashCode();
		h = h * 17 + user.hashCode();
		h = h * 17 + experience;
		return h;
	}

	public String toString() {
		return "Experience{"
				+ "project=" + project
				+ ", user=" + user
				+ ", experience=" + experience
				+ "}";
	}

	public static Experience.Builder builder() {
		return new Experience.Builder();
	}

	public static final class Builder {
		private static final long INIT_BIT_PROJECT = 0x1L;
		private static final long INIT_BIT_USER = 0x2L;
		private static final long INIT_BIT_EXPERIENCE = 0x4L;
		private long initBits = 0x7;

		private Project project;
		private User user;
		private int experience;

		private Builder() {
		}

		public final Builder from(Experience instance) {
			Objects.requireNonNull(instance, "instance");
			project(instance.project());
			user(instance.user());
			experience(instance.experience());
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
		
		public final Builder experience(int experience) {
			this.experience = experience;
			initBits &= ~INIT_BIT_EXPERIENCE;
			return this;
		}
		
		public Experience build() {
			if (initBits != 0) {
				throw new IllegalStateException(formatRequiredAttributesMessage());
			}
			return new Experience(project, user, experience);
		}

		private String formatRequiredAttributesMessage() {
			List<String> attributes = new ArrayList<String>();
			if ((initBits & INIT_BIT_PROJECT) != 0) attributes.add("project");
			if ((initBits & INIT_BIT_USER) != 0) attributes.add("user");
			if ((initBits & INIT_BIT_EXPERIENCE) != 0) attributes.add("experience");
			return "Cannot build Experience, some of required attributes are not set " + attributes;
		}
	}
}