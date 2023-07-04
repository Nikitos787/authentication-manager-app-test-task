package project.authentication.manager.app.security;

import project.authentication.manager.app.exception.AuthenticationException;
import project.authentication.manager.app.model.User;

public interface AuthenticationService {

    User register(String email, String password, String firstName, String lastName);

    User login(String email, String password) throws AuthenticationException;
}
