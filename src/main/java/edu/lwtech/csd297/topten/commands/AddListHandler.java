package edu.lwtech.csd297.topten.commands;

import java.util.*;
import javax.servlet.http.*;

import edu.lwtech.csd297.topten.pojos.*;
import edu.lwtech.csd297.topten.TopTenListsServlet;

// Handle the "addList" command
public class AddListHandler implements CommandHandler<TopTenListsServlet> {

    @Override
    public String handle(HttpServletRequest request, TopTenListsServlet servlet) {
        String message = "";
        String template = "confirm.ftl";

        Map<String, Object> templateFields = new HashMap<>();
        CommandUtils.getSessionVariables(request, templateFields);
        int ownerID = (int)templateFields.get("owner");

        TopTenList newList = CommandUtils.getTopTenListFromRequest(request, ownerID);

        if (newList == null) {
            message = "Your new TopTenList was not created because one or more fields were empty.<br /><a href='?cmd=home'>Home</a>";
        } else {
            if (servlet.getListsDAO().insert(newList) > 0)
                message = "Your new TopTen List has been created successfully.<br /><a href='?cmd=home'>Home</a>";
            else
                message = "There was a problem adding your list to the database.<br /><a href='?cmd=home'>Home</a>";
        }
        templateFields.put("message", message);
        return CommandUtils.mergeTemplate(template, templateFields, servlet.getFreeMarkerConfig());
    }

}
