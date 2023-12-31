package imt.framework.back.imtframeworkback.domain.usecases.users;

import imt.framework.back.imtframeworkback.core.errors.UserAlreadyExistException;
import imt.framework.back.imtframeworkback.core.utils.Constants;
import imt.framework.back.imtframeworkback.core.utils.UseCase;
import imt.framework.back.imtframeworkback.data.services.RoleService;
import imt.framework.back.imtframeworkback.data.services.UserService;
import imt.framework.back.imtframeworkback.domain.models.Role;
import imt.framework.back.imtframeworkback.domain.models.User;
import imt.framework.back.imtframeworkback.domain.requests.CreateUserReq;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CreateUserUseCase implements UseCase<CreateUserReq, Void> {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Void command(CreateUserReq request) {
        Optional<User> existing = userService.findByMail(request.getMail());
        if (existing.isPresent()) {
            throw new UserAlreadyExistException(request.getMail());
        }

        User user = User.fromReq(request, 200.0);
        String password = passwordEncoder.encode(user.getPassword());
        Role role = roleService.findByRole(Constants.USER_ROLE).get();

        user = user.toBuilder().password(password).authorities(Set.of(role)).build();

        userService.saveWithoutReturn(user);
        return null;
    }
}
