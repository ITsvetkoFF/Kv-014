package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.service.EmployeeService;
import edu.softserve.zoo.service.UserService;
import edu.softserve.zoo.service.exception.UserException;
import edu.softserve.zoo.service.security.AuthUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Implementation of {@link UserService}
 *
 * @author Ilya Doroshenko
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Checks if confirmation password matches current user's password and if so - changes currents user password to new specified one
     *
     * @param newPassword          new password for user
     * @param confirmationPassword actual user's password for confirmation
     */
    @Override
    public void changePassword(String newPassword, String confirmationPassword) {
        AuthUserDetails currentUser = (AuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        boolean passwordConfirmed = passwordEncoder.matches(confirmationPassword, currentUser.getPassword());

        if (passwordConfirmed) {
            Employee currentEmployee = employeeService.findOne(currentUser.getId());
            currentEmployee.setPassword(passwordEncoder.encode(newPassword));
            currentEmployee.setPasswordChangeDate(LocalDateTime.now());
            employeeService.update(currentEmployee);
        } else {
            throw ApplicationException.getBuilderFor(UserException.class)
                    .forReason(UserException.Reason.SPECIFIED_PASSWORD_NOT_MATCHES_ACTUAL)
                    .withMessage("Specified password not matches current user's password")
                    .build();
        }
    }

    /**
     * @return currently authenticated user
     */
    @Override
    public Employee getCurrentUser() {
        UserDetails currentUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = currentUser.getUsername();

        return employeeService.getEmployeeByEmail(email);
    }
}
