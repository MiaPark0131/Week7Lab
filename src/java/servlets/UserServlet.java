package servlets;

import exceptions.InvalidInputException;
import java.io.IOException;
import java.sql.SQLException;
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
        
        String action = request.getParameter("action");
        
        String message = null;
        User user = null;
        String userEmail = null;
        List<User> users = null;
        List<Role> roles = null;
        
        try {
            
            users = us.getAll();
            roles = rs.getAll();
            
            request.setAttribute("users", users);
            request.setAttribute("roles", roles);

            switch (action) {
                case "edit":
                    message = "edit";
                    request.setAttribute("message", message);
                    
                    userEmail = request.getParameter("userEmail");
                    user = us.get(userEmail);
                    request.setAttribute("user", user);

                    break;
                case "delete":
                    userEmail = request.getParameter("userEmail");
                    us.deleteUser(userEmail);
                    break;
            }

        } catch (NullPointerException e)    {
            
        } catch (Exception ex) {
            
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "error");
            
        } finally   {
            
            try {    
                users = us.getAll();
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.setAttribute("users", users);
            
            String isEmpty = ((users.isEmpty()) ? "true" : "false");
            request.setAttribute("isEmpty", isEmpty);
            
            getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
        }

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        UserService us = new UserService();
        RoleService rs = new RoleService();

        List<User> users = null;
        List<Role> roles = null;
        String action = request.getParameter("action");

        String message = null;
        String errorMessage = null;
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

                    message = null;
                    request.setAttribute("message", message);
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
                    break;
                case "cancel":
                    break;
            }
            
        } catch (SQLException ex)   {
            
            errorMessage = "User already exists.";
            request.setAttribute("errorMessage", errorMessage);
            message = null;
            request.setAttribute("message", message);
            
        } catch (InvalidInputException e)   {
            
            errorMessage = "All fields are required.";
            request.setAttribute("errorMessage", errorMessage);
            message = null;
            request.setAttribute("message", message);
            
        } catch (Exception ex) {
            
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            
        }  finally  {
            
            try {
                users = us.getAll();
                roles = rs.getAll();
                
                request.setAttribute("users", users);
                request.setAttribute("roles", roles);
                
                getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        
    }

    
}
