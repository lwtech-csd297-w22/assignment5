package edu.lwtech.csd297.topten.commands;

import java.util.*;
import javax.servlet.http.*;

import edu.lwtech.csd297.topten.pojos.*;
import edu.lwtech.csd297.topten.TopTenListsServlet;

// Handle the "showList" command
public class ShowListHandler implements CommandHandler<TopTenListsServlet> {

    @Override
    public String handle(HttpServletRequest request, TopTenListsServlet servlet) {
        String template = "show.ftl";
        Map<String, Object> templateFields = new HashMap<>();
        CommandUtils.getSessionVariables(request, templateFields);

        int index = CommandUtils.parseInt(request.getParameter("index"));
        if (index < 0)
            index = 0;

        int nextIndex= 0;
        int prevIndex = 0;
        int listSize = servlet.getListsDAO().size();
        if (listSize > 0) {
            nextIndex = (index + 1) % listSize;
            prevIndex = index - 1;
        }
        if (prevIndex < 0)
            prevIndex = listSize - 1;

        TopTenList list = servlet.getListsDAO().retrieveByIndex(index);
        if (list == null)
            return "";

        list.incrementNumViews();
        templateFields.put("topTenList", list);
        templateFields.put("listNumber", index+1);                   // Java uses 0-based indexes.  Users want to see 1-based indexes.
        templateFields.put("prevIndex", prevIndex);
        templateFields.put("nextIndex", nextIndex);

        return CommandUtils.mergeTemplate(template, templateFields, servlet.getFreeMarkerConfig());
    }

}
