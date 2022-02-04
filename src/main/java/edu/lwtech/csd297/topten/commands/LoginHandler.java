package edu.lwtech.csd297.topten.commands;

import java.util.*;
import javax.servlet.http.*;

import edu.lwtech.csd297.topten.pojos.*;
import edu.lwtech.csd297.topten.TopTenListsServlet;

// Handle the "login" command
public class LoginHandler implements CommandHandler<TopTenListsServlet> {

    @Override
    public String handle(HttpServletRequest request, TopTenListsServlet servlet) {

        boolean loggedIn = false;
        String message = "";
        String template = "confirm.ftl";
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Map<String, Object> templateFields = new HashMap<>();
        CommandUtils.getSessionVariables(request, templateFields);

        List<Member> matchingMembers = servlet.getMembersDAO().search(username);
        if (matchingMembers == null || matchingMembers.isEmpty()) {
            message = "We do not have a member with that username on file. Please try again.<br /><a href='?cmd=login'>Log In</a>";
            templateFields.put("message", message);
            templateFields.put("loggedIn", false);
            return CommandUtils.mergeTemplate(template, templateFields, servlet.getFreeMarkerConfig());
        }

        Member member = matchingMembers.get(0);
        if (member.getPassword().equals(password)) {
            int ownerID = member.getRecID();
            loggedIn = true;
            HttpSession session = request.getSession(true);         // true == Create a new session for this user
            session.setAttribute("owner", ownerID);
            message = "You have been successfully logged in to your account.<br /><a href='?cmd=showList'>Show Lists</a>";
        } else {
            message = "Your password did not match what we have on file. Please try again.<br /><a href='?cmd=login'>Log In</a>";
        }
        templateFields.put("loggedIn", loggedIn);
        templateFields.put("message", message);
        return CommandUtils.mergeTemplate(template, templateFields, servlet.getFreeMarkerConfig());
    }

}
