package edu.softserve.zoo.service.security;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.NotFoundException;
import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.model.Role;
import edu.softserve.zoo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Serves for loading {@link Employee} authentication info for security mechanism.
 *
 * @author Ilya Doroshenko
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final String ROLE_PREFIX = "ROLE_";

    @Autowired
    private EmployeeService employeeService;

    /**
     * Creates {@link AuthUserDetails} from the loaded by specified username {@link Employee}
     *
     * @param username the email of an employee
     * @return populated {@link AuthUserDetails} for authentication mechanism.
     */
    @Override
    public AuthUserDetails loadUserByUsername(String username) {
        try {
            Employee employee = employeeService.getEmployeeByEmail(username);
            Set<GrantedAuthority> authorities = mapRolesToGrantedAuthorities(employee.getRoles());
            return new AuthUserDetails(employee.getId(), employee.getEmail(), employee.getPassword(),
                    employee.isEnabled(), employee.getToken(), employee.getPasswordChangeDate(), authorities);
        }
        catch (NotFoundException ex) {
            throw new UsernameNotFoundException(ex.getReason().getMessage());
        }

    }

    /**
     * Maps employee's {@link Role} to security-specific {@link GrantedAuthority}
     *
     * @param roles employee's account roles
     * @return user's authorities in a system
     */
    private Set<GrantedAuthority> mapRolesToGrantedAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role.getType().name()))
                .collect(Collectors.toSet());
    }
}
