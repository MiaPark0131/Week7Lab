package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import models.Role;

/**
 *
 * @author meeye
 */
public class RoleDB {
    
    public List<Role> getAll() throws Exception {
        
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            List<Role> roles = em.createNamedQuery("Role.findAll", Role.class).getResultList();
            return roles;
        } finally {
            em.close();
        }
    }
    
    
    public Role get(int role_id) throws Exception {
        
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            
            Role role = em.find(Role.class, role_id);
            return role;
            
        } finally {
            
            em.close();
        }
    }
}
