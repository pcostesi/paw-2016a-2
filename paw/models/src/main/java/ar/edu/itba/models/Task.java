package ar.edu.itba.models;

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
public class Task {
	
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

}
