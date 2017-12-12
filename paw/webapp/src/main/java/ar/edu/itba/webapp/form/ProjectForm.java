package ar.edu.itba.webapp.form;

import ar.edu.itba.webapp.form.constraint.ProjectCodeFree;
import ar.edu.itba.webapp.form.constraint.ProjectNameFree;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@ProjectNameFree(markedField = "name")
@ProjectCodeFree(markedField = "code")
public class ProjectForm {

    @Size(min = 1, max = 100)
    private String name;

    @Size(min = 1, max = 10)
    @Pattern(regexp = "[A-Za-z0-9]+", message = "Code must be alphanumeric, and can't have any kind of whitespace")
    private String code;

    @Size(min = 1, max = 500)
    private String description;

    private List<String> members;

    private String oldName;
    private String oldCode;

    public String getOldName() {
        return oldName;
    }

    public void setOldName(final String oldName) {
        this.oldName = oldName;
    }

    public String getOldCode() {
        return oldCode;
    }

    public void setOldCode(final String oldCode) {
        this.oldCode = oldCode;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(final List<String> members) {
        this.members = members;
    }

}
