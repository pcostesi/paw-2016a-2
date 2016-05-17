package ar.edu.itba.models;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "backlog", uniqueConstraints = @UniqueConstraint(columnNames = {"project_id", "title"}))
public class HibernateBacklogItem extends BacklogItem{

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
	@Column(name = "project_id", nullable = false)
	private int projectId;
	
	public int backlogItemId() {
		return backlogItemId;
	}

	public String title() {
		return title;
	}

	public Optional<String> description() {
		return Optional.ofNullable(description);
	}

}