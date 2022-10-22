package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Role;
import models.User;
import services.RoleService;
import services.UserService;

/**
 *
 * @author meeye
 */
public class UserServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        UserService us = new UserService();
        RoleService rs = new RoleService();
        
        try {
            
            List<User> users = us.getAll();
            List<Role> roles = rs.getAll();
            
            request.setAttribute("users", users);
            request.setAttribute("roles", roles);
            
            String action = request.getParameter("action");
            
            String message = null;
            User user = null;
            
            switch (action) {
                case "edit":
                    message = "edit";
                    request.setAttribute("message", message);
                    String userEmail = request.getParameter("userEmail");

                    user = us.get(userEmail);
                    
                    users = us.getAll();
                    roles = rs.getAll();

                    request.setAttribute("user", user);
                    request.setAttribute("users", users);
                    request.setAttribute("roles", roles);

                    getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
            
                    break;

            }


        } catch (NullPointerException e)    {
            
            getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
            
        } catch (Exception ex) {
            
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "error");
        }
        
        
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        UserService us = new UserService();
        RoleService rs = new RoleService();
        
        HttpSession session = request.getSession();
            
        List<User> users = null;
        List<Role> roles = null;
        String action = request.getParameter("action");

        String message = null;
        User user = null;
        Role role = null;
        
        String email = null;
        String firstname = null;
        String lastname = null;
        String password = null;
        String roleIDString = null;
        int roleID = 0;
        
        try {
            
            switch (action) {
                case "add":
                    message = "add";
                    request.setAttribute("message", message);
                    email = request.getParameter("email");
                    firstname = request.getParameter("firstname");
                    lastname = request.getParameter("lastname");
                    password = request.getParameter("password");
                    roleIDString = request.getParameter("role");
                    roleID = Integer.parseInt(roleIDString);
                    role = rs.getRole(roleID);
                    
                    user = new User(email, firstname, lastname, password, role);
                    us.addUser(user);
                    
                    users = us.getAll();
                    roles = rs.getAll();

                    request.setAttribute("users", users);
                    request.setAttribute("roles", roles);

                    message = null;
                    request.setAttribute("message", message);
                    getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
            
                    break;
                case "update":

                    email = request.getParameter("email");
                    firstname = request.getParameter("firstname");
                    lastname = request.getParameter("lastname");
                    password = request.getParameter("password");
                    roleIDString = request.getParameter("role");
                    roleID = Integer.parseInt(roleIDString);
                    role = rs.getRole(roleID);
                    
                    user = new User(email, firstname, lastname, password, role);
                    us.updateUser(user);
                    
                    users = us.getAll();
                    roles = rs.getAll();

                    request.setAttribute("users", users);
                    request.setAttribute("roles", roles);
                    
                    getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
                    break;
            }
            
            users = us.getAll();
            roles = rs.getAll();
            
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }

    
}
