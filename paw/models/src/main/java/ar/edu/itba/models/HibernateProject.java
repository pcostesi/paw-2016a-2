package ar.edu.itba.models;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "project")
public class HibernateProject extends Project {
	
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
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<HibernateIteration> projectIterations;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<HibernateBacklogItem> projectBacklogItems;

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

}
