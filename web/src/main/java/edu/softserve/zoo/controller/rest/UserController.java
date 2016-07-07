package edu.softserve.zoo.controller.rest;

import edu.softserve.zoo.Error;
import edu.softserve.zoo.annotation.DocsClassDescription;
import edu.softserve.zoo.annotation.DocsTest;
import edu.softserve.zoo.converter.ModelConverter;
import edu.softserve.zoo.dto.ChangePasswordDto;
import edu.softserve.zoo.dto.EmployeeDto;
import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.NotFoundException;
import edu.softserve.zoo.service.UserService;
import edu.softserve.zoo.service.exception.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static edu.softserve.zoo.controller.rest.Routes.USER;
import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

/**
 * Controller for operations with currently authenticated user.
 *
 * @author Ilya Doroshenko
 */
@RestController
@RequestMapping(USER)
@DocsClassDescription("User resource")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelConverter converter;

    @Autowired
    private MessageSource messageSource;

    /**
     * Handles the request for info about currently authenticated user
     *
     * @return current user
     */
    @DocsTest()
    @RequestMapping(method = RequestMethod.GET)
    public EmployeeDto getUser() {
        return converter.convertToDto(userService.getCurrentUser());
    }

    @DocsTest()
    @RequestMapping(method = RequestMethod.POST, path = "/change-pass")
    public ResponseEntity changePassword(@RequestBody ChangePasswordDto dto) {
        userService.changePassword(dto.getNewPassword(), dto.getConfirmationPassword());
        return ResponseEntity.ok().build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public Error handleApplicationException(ApplicationException exception) {
        String message = messageSource.getMessage(exception.getReason().getMessage(), null,
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), getLocale());
        return new Error(message);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public Error handleNotFoundExceptionException(UserException exception) {
        String message = messageSource.getMessage(exception.getReason().getMessage(), null,
                HttpStatus.NOT_FOUND.getReasonPhrase(), getLocale());
        return new Error(message);
    }
}