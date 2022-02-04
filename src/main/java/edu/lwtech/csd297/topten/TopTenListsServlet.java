package edu.lwtech.csd297.topten;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import org.apache.logging.log4j.*;
import freemarker.template.*;

import edu.lwtech.csd297.topten.daos.*;
import edu.lwtech.csd297.topten.pojos.*;
import edu.lwtech.csd297.topten.commands.*;

@WebServlet(name = "topten", urlPatterns = {"/*"}, loadOnStartup = 0)
public class TopTenListsServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(TopTenListsServlet.class);

    private static Configuration freeMarkerConfig = null;
    private static final Map<String, CommandHandler<TopTenListsServlet>> supportedCommands = new HashMap<>();

    private DAO<Member> membersDAO = null;
    private DAO<TopTenList> listsDAO = null;

    @Override
    public void init(ServletConfig config) throws ServletException {

        logger.warn("");
        logger.warn("========================================================");
        logger.warn("   topten-lists init() started");
        logger.warn("      http://localhost:8080/topten");
        logger.warn("========================================================");
        logger.warn("");

        String resourcesDir = config.getServletContext().getRealPath("/WEB-INF/classes");
        logger.info("resourcesDir = {}", resourcesDir);

        logger.info("Initializing FreeMarker...");
        String templateDir = config.getServletContext().getRealPath("/WEB-INF/classes/templates");
        logger.debug("templatesDir = {}", templateDir);
        freeMarkerConfig = new Configuration(Configuration.VERSION_2_3_0);
        try {
            freeMarkerConfig.setDirectoryForTemplateLoading(new File(templateDir));
        } catch (IOException e) {
            throw new UnavailableException("Template directory not found");
        }
        logger.info("Successfully initialized FreeMarker.");

        supportedCommands.put("home", new HomeHandler());
        supportedCommands.put("like", new LikeHandler());
        supportedCommands.put("login", new LoginHandler());
        supportedCommands.put("logout", new LogoutHandler());
        supportedCommands.put("addList", new AddListHandler());
        supportedCommands.put("register", new RegisterHandler());
        supportedCommands.put("showList", new ShowListHandler());
        supportedCommands.put("showLogin", new ShowLoginHandler());
        supportedCommands.put("showAddList", new ShowAddListHandler());
        supportedCommands.put("showRegistration", new ShowRegistrationHandler());

        logger.info("Initializing the DAOs...");
        membersDAO = new MemberMemoryDAO();
        listsDAO = new TopTenListMemoryDAO();
        //membersDAO = new MemberSqlDAO();
        //listsDAO = new TopTenListSqlDAO();

        // String initParams = "jdbc:mariadb://localhost:3306/topten";
        String initParams = "jdbc:mariadb://topten-lists-mysql-db.cv18zcsjzteu.us-west-2.rds.amazonaws.com:3306/topten";

        initParams += "?useSSL=false&allowPublicKeyRetrieval=true";
        initParams += "&user=topten&password=topten-rox";             // In the real word, credentials should be stored in AWS Secrets Manager

        if (!membersDAO.initialize(initParams))
            throw new UnavailableException("Unable to initialize the MembersDAO.");
        if (!listsDAO.initialize(initParams))
            throw new UnavailableException("Unable to initialize the ListsDAO.");
        logger.info("Successfully initialized the DAOs!");

        logger.warn("");
        logger.warn("Servlet initialization complete!");
        logger.warn("");
    }

    @Override
    public void destroy() {
        listsDAO.terminate();
        membersDAO.terminate();
        logger.warn("-----------------------------------------");
        logger.warn("  topten-lists destroy() completed!");
        logger.warn("-----------------------------------------");
        logger.warn(" ");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        long startTime = System.currentTimeMillis();
        String logInfo = getLogInfo(request);

        try {
            // Get the cmd parameter from the URI (default to 'home')
            String cmd = getCommand(request, "home");

            // Handle "health" commands here so they don't get logged
            if (cmd.equals("health")) {
                response.sendError(HttpServletResponse.SC_OK, "OK");
                return;
            }

            logger.debug("IN - {}", logInfo);

            // Find the CommandHandler for cmd
            CommandHandler<TopTenListsServlet> command = supportedCommands.get(cmd);
            if (command == null) {
                logger.info("Unrecognized command received. cmd: {}", cmd);
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            // Handle the command request
            String output = command.handle(request, this);
            if (output == null || output.isBlank()) {
                logger.info("Null/Empty response returned when command was handled. cmd: {}", cmd);
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            // Send the command's output to the user (using try-with-resources)
            try (ServletOutputStream out = response.getOutputStream(); ) {
                out.println(output);
            }
        } catch (IOException e) {
            logger.debug("Unexpected I/O exception: ", e);
        } catch (RuntimeException e) {
            logger.error("Unexpected runtime exception: ", e);
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unexpected server error.");
            } catch (IOException ex) {
                logger.error("Unable to send 500 response code.", ex);
            }
        }
        long time = System.currentTimeMillis() - startTime;
        logger.info("OUT- {} {}ms", logInfo, time);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "topten-lists Servlet";
    }

    public Configuration getFreeMarkerConfig() {
        return freeMarkerConfig;
    }

    public DAO<TopTenList> getListsDAO() {
        return listsDAO;
    }

    public DAO<Member> getMembersDAO() {
        return membersDAO;
    }

    // =================================================================

    private static String getLogInfo(HttpServletRequest request) {
        StringBuilder logInfo = new StringBuilder();
        logInfo.append(request.getRemoteAddr());
        logInfo.append(" ");
        logInfo.append(request.getMethod());
        logInfo.append(" ");
        logInfo.append(request.getRequestURI());
        logInfo.append(getSanitizedQueryString(request));
        return logInfo.toString();
    }

    private static String getCommand(HttpServletRequest request, String defaultCmd) {
        String cmd = request.getParameter("cmd");
        if (cmd == null)
            cmd = defaultCmd;
        return cmd;
    }

    private static String getSanitizedQueryString(HttpServletRequest request) {
        String queryString = request.getQueryString();
        if (queryString == null)
            return "";

        try { 
            queryString = URLDecoder.decode(queryString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);                         // Should never happen
        }
        queryString = "?" + sanitizedString(queryString);
        return queryString;
    }

    private static String sanitizedString(String s) {
        return s.replaceAll("[\n|\t]", "_");
    }

}
