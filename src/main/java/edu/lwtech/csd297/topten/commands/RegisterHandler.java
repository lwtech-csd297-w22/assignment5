package edu.lwtech.csd297.topten.commands;

import java.util.*;
import javax.servlet.http.*;

import edu.lwtech.csd297.topten.pojos.*;
import edu.lwtech.csd297.topten.TopTenListsServlet;

// Handle the "register" command
public class RegisterHandler implements CommandHandler<TopTenListsServlet> {

    @Override
    public String handle(HttpServletRequest request, TopTenListsServlet servlet) {
        String message = "";
        String template = "confirm.ftl";

        Map<String, Object> templateFields = new HashMap<>();
        CommandUtils.getSessionVariables(request, templateFields);
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        List<Member> registeredMembers = servlet.getMembersDAO().search(username);
        if (registeredMembers != null && !registeredMembers.isEmpty()) {
            message = "That username is already registered here. Please use a different username.<br /><a href='?cmd=login'>Log In</a>";
            templateFields.put("message", message);
            return CommandUtils.mergeTemplate(template, templateFields, servlet.getFreeMarkerConfig());
        }

        Member member = new Member(username, password);
        servlet.getMembersDAO().insert(member);

        message = "Welcome to the TopTen-Lists server! You are now a registered member. Please <a href='?cmd=login'>log in</a>.";
        templateFields.put("message", message);
        return CommandUtils.mergeTemplate(template, templateFields, servlet.getFreeMarkerConfig());
    }

}
