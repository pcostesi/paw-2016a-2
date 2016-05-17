package ar.edu.itba.models;

import java.time.LocalDate;
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

@Entity
@Table(name = "iteration", uniqueConstraints = @UniqueConstraint(columnNames = {"project_id", "number"}))
public class HibernateIteration extends Iteration {

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
	
	@ManyToOne
	@Column(name = "project_id", nullable = false)
	private int projectId;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<HibernateStory> iterationStories;
	
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

}