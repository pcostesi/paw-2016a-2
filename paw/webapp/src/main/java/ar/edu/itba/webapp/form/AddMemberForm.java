package ar.edu.itba.webapp.form;

import ar.edu.itba.webapp.form.constraint.UserCanJoinProject;

import javax.validation.constraints.Size;

@UserCanJoinProject(markedField = "member")
public class AddMemberForm {

    @Size(min = 1, max = 100)
    private String member;

    private String projectCode;

    public String getMember() {
        return member;
    }

    public void setMember(final String member) {
        this.member = member;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(final String projectCode) {
        this.projectCode = projectCode;
    }
}
