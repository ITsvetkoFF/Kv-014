package edu.softserve.zoo.service.security;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.model.Role;
import edu.softserve.zoo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Serves for loading {@link Employee} authentification info for security mechanism.
 *
 * @author Ilya Doroshenko
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private EmployeeService employeeService;

    /**
     * Creates {@link AuthUserDetails} from the loaded by specified username {@link Employee}
     *
     * @param username the email of an employee
     * @return populated {@link UserDetails} for authentification mechanism.
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        Employee employee = employeeService.getEmployeeByEmail(username);
        Set<GrantedAuthority> authorities = mapRolesToGrantedAuthorities(employee.getRoles());
        return new AuthUserDetails(employee.getEmail(), employee.getPassword(), employee.isEnabled(), authorities);
    }

    /**
     * Maps employee's {@link Role} to security-specific {@link GrantedAuthority}
     *
     * @param roles employee's account roles
     * @return user's authorities in a system
     */
    private Set<GrantedAuthority> mapRolesToGrantedAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getType().name()))
                .collect(Collectors.toSet());
    }
}
