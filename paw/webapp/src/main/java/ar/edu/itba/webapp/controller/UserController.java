package ar.edu.itba.webapp.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.interfaces.service.EmailService;
import ar.edu.itba.interfaces.service.TranslationService;
import ar.edu.itba.interfaces.service.UserService;
import ar.edu.itba.models.User;
import ar.edu.itba.webapp.form.EditPasswordForm;
import ar.edu.itba.webapp.form.UserForm;


@Controller
public class UserController extends BaseController {

    @Autowired
    private UserService us;

    @Autowired
    private EmailService es;

    @Autowired
    private TranslationService ts;

    final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginForm() {
        return new ModelAndView("user/login");
    }

    @RequestMapping(value = "/user/new", method = RequestMethod.GET)
    public ModelAndView getRegisterForm(@ModelAttribute("userForm") final UserForm userForm) {
        return new ModelAndView("user/newUser");
    }

    @RequestMapping(value = "/user/new", method = RequestMethod.POST)
    public ModelAndView postRegisterForm(@Valid @ModelAttribute("userForm") final UserForm userForm, final BindingResult result) {

        if (result.hasErrors()) {
            return new ModelAndView("user/newUser");
        } else {
            final User user = us.create(userForm.getUser(), userForm.getPassword(), userForm.getMail());
            sendSuccessfulRegistrationEmail(user);
            return new ModelAndView("user/login");
        }

    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public ModelAndView me() {
        return new ModelAndView("user/profile");
    }

    @RequestMapping(value = "/me/edit/password", method = RequestMethod.GET)
    public ModelAndView getEditPasswordForm(@ModelAttribute("editPasswordForm") final EditPasswordForm editPasswordForm ) {
        return new ModelAndView("user/editPassword");
    }

    @RequestMapping(value = "/me/edit/password", method = RequestMethod.POST)
    public ModelAndView editPasswordForm(@Valid @ModelAttribute("editPasswordForm") final EditPasswordForm editPasswordForm, final BindingResult result) {
        if(result.hasErrors()) {
            return new ModelAndView("user/editPassword");
        }
        else {
            us.editPassword(user(), editPasswordForm.getPassword());
        }
        return new ModelAndView("user/profile");
    }


    private static String userVerificationCode(final User user) {
        return Sha512DigestUtils.shaHex(user.username() + ":" + user.password());
    }

    @Async
    private void sendSuccessfulRegistrationEmail(final User user) {
        final String subject = ts.getMessage("user.new.email.subject", user);
        final String body  = ts.getMessage("user.new.email.body", user.username(), userVerificationCode(user));
        final boolean result = es.notifyUser(user, subject, body);
        logger.debug("Sent email to user {}, result was {}", user, result);
    }
}
