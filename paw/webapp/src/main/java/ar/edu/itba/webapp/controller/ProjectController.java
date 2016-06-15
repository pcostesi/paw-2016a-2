package ar.edu.itba.webapp.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import ar.edu.itba.interfaces.service.BacklogService;
import ar.edu.itba.interfaces.service.IterationService;
import ar.edu.itba.interfaces.service.ProjectService;
import ar.edu.itba.interfaces.service.UserService;
import ar.edu.itba.models.BacklogItem;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;
import ar.edu.itba.webapp.form.AddMemberForm;
import ar.edu.itba.webapp.form.ProjectForm;

@Controller
@RequestMapping("/project")
public class ProjectController extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService ps;

    @Autowired
    private IterationService is;

    @Autowired
    private BacklogService bs;

    @Autowired
    private UserService us;

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView getNewResource(@ModelAttribute("projectForm") final ProjectForm projectForm) {
        final ModelAndView mav = new ModelAndView("project/newProject");
        final List<String> usernames = us.getUsernamesExcept(super.user());
        mav.addObject("usernames", usernames);
        return mav;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ModelAndView postNewResource(@Valid @ModelAttribute("projectForm") final ProjectForm projectForm, final BindingResult result) {
        final ModelAndView mav;
        if (result.hasErrors()) {
            final List<String> usernames = us.getUsernamesExcept(super.user());
            mav = new ModelAndView("project/newProject");
            mav.addObject("usernames", usernames);
        } else {
            final User me = super.user();
            final List<String> usernames = projectForm.getMembers();
            final Set<User> members = new HashSet<User>();
            if (usernames != null) {
                for (final String username: usernames) {
                    members.add(us.getByUsername(username));
                }
            }
            ps.createProject(me, members, projectForm.getName(), projectForm.getDescription(), projectForm.getCode());
            final String resourceUrl = MvcUriComponentsBuilder.fromMappingName(UriComponentsBuilder.fromPath("/"),"project.list").build();
            mav = new ModelAndView("redirect:" + resourceUrl);
        }
        return mav;
    }

    @RequestMapping(value = "/{projectCode}", method = RequestMethod.GET, name = "project.details")
    public ModelAndView getResource(@PathVariable final String projectCode) {
        final ModelAndView mav = new ModelAndView("project/iterationList");
        final Project project = ps.getProjectByCode(projectCode);
        final List<Iteration> iterations = is.getIterationsForProject(project);
        final List<BacklogItem> backlog = bs.getBacklogForProject(project);
        final boolean isAdmin = super.user().equals(project.admin());
        mav.addObject("iterations", iterations);
        mav.addObject("project", project);
        mav.addObject("backlog", backlog);
        mav.addObject("isAdmin", isAdmin);
        return mav;
    }

    @RequestMapping(value = "/{projectCode}/edit", method = RequestMethod.GET)
    public ModelAndView getModifyResource(@ModelAttribute("projectForm") final ProjectForm projectForm, @PathVariable final String projectCode) {
        final ModelAndView mav = new ModelAndView("project/editProject");
        final Project project = ps.getProjectByCode(projectCode);
        projectForm.setCode(project.code());
        projectForm.setName(project.name());
        projectForm.setDescription(project.description());
        mav.addObject("project", project);
        return mav;
    }

    @RequestMapping(value = "/{projectCode}/members", method = RequestMethod.GET, name = "project.members")
    public ModelAndView getMembersList(@ModelAttribute("addMemberForm") final AddMemberForm addMemberForm, @PathVariable final String projectCode) {
        final ModelAndView mav = new ModelAndView("project/membersList");
        final Project project = ps.getProjectByCode(projectCode);
        final List<User> members = ps.getProjectMembers(project);
        final User me = super.user();
        final List<String> usernames = us.getAvailableUsers(project);
        mav.addObject("user", me);
        mav.addObject("usernames", toJSONFormat(usernames));
        mav.addObject("project", project);
        mav.addObject("members", members);
        return mav;
    }

    private String toJSONFormat(final List<String> usernames) {
        String formattedString = "[";
        for (int i = 0; i < usernames.size(); i++) {
            formattedString = formattedString +"\""+ usernames.get(i) + "\"";
            if (i+1 < usernames.size()) {
                formattedString = formattedString +",";
            }
        }
        return formattedString + "]";
    }

    @RequestMapping(value = "/{projectCode}/members/new", method = RequestMethod.POST)
    public ModelAndView addNewMember(@Valid @ModelAttribute("addMemberForm") final AddMemberForm addMemberForm, final BindingResult result, @PathVariable final String projectCode) {
        final Project project = ps.getProjectByCode(projectCode);
        final ModelAndView mav;
        if (result.hasErrors()) {
            mav = new ModelAndView("project/membersList");
            final List<String> usernames = us.getAvailableUsers(project);
            final List<User> members = ps.getProjectMembers(project);
            mav.addObject("usernames", toJSONFormat(usernames));
            mav.addObject("members", members);
            mav.addObject("project", project);
        } else {
            final User me = super.user();
            final User user = us.getByUsername(addMemberForm.getMember());
            ps.addUserToProject(me, project, user);
            final String resourceUrl = MvcUriComponentsBuilder.fromMappingName(UriComponentsBuilder.fromPath("/"), "project.members")
                    .arg(1, projectCode).build();
            mav = new ModelAndView("redirect:" + resourceUrl);
        }
        return mav;
    }

    @RequestMapping(value = "/{projectCode}/members/{username}/delete", method = RequestMethod.POST)
    public ModelAndView deleteMember(@PathVariable final String projectCode, @PathVariable final String username) {
        final ModelAndView mav;
        final Project project = ps.getProjectByCode(projectCode);
        final User me = super.user();
        final User userToDelete = us.getByUsername(username);
        ps.deleteUserFromProject(me, project, userToDelete);
        final String resourceUrl = MvcUriComponentsBuilder.fromMappingName(UriComponentsBuilder.fromPath("/"), "project.members")
                .arg(1, projectCode).build();
        System.out.println(resourceUrl);
        mav = new ModelAndView("redirect:" + resourceUrl);
        return mav;
    }

    @RequestMapping(value = "/{projectCode}/edit", method = RequestMethod.POST)
    public ModelAndView modifyResource(@Valid @ModelAttribute("projectForm") final ProjectForm projectForm, final BindingResult result,
            @PathVariable final String projectCode) {
        final ModelAndView mav;
        final Project project = ps.getProjectByCode(projectCode);
        final User user = super.user();
        if (result.hasErrors()) {
            mav = new ModelAndView("project/editProject");
            mav.addObject("project", project);
        } else {
            ps.setCode(user, project, projectForm.getCode());
            ps.setDescription(user, project, projectForm.getDescription());
            ps.setName(user, project, projectForm.getName());
            final String resourceUrl = MvcUriComponentsBuilder.fromMappingName(UriComponentsBuilder.fromPath("/"), "project.list").build();
            mav = new ModelAndView("redirect:" + resourceUrl);
        }
        return mav;
    }

    @RequestMapping(value = "/{projectCode}/delete", method = RequestMethod.POST)
    public ModelAndView deleteResource(@PathVariable final String projectCode) {
        final Project project = ps.getProjectByCode(projectCode);
        final User user = super.user();
        ps.deleteProject(user, project);
        final String resourceUrl = MvcUriComponentsBuilder.fromMappingName(UriComponentsBuilder.fromPath("/"), "project.list").build();
        return new ModelAndView("redirect:" + resourceUrl);
    }

}
