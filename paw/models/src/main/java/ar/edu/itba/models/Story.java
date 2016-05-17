package ar.edu.itba.models;

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

}
