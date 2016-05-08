package ar.edu.itba.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import ar.edu.itba.webapp.form.constraint.*;

@ProjectNameFree(markedField="name")
@ProjectCodeFree(markedField="code")
public class ProjectForm {
	
	@Size(min=1, max=100)
	private String name;

	@Size(min=1, max=10)
	@Pattern(regexp="[A-Za-z0-9]+", message="Code must be alphanumeric, and can't have any kind of whitespace")
	private String code;

	@Size(min=1, max=500)
	private String description;	
	
	private String oldName;
	private String oldCode;
	
	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public String getOldCode() {
		return oldCode;
	}

	public void setOldCode(String oldCode) {
		this.oldCode = oldCode;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
