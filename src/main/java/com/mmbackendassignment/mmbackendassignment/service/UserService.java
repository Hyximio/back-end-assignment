package com.mmbackendassignment.mmbackendassignment.service;

import com.mmbackendassignment.mmbackendassignment.dto.AuthDto;
import com.mmbackendassignment.mmbackendassignment.dto.PageDto;
import com.mmbackendassignment.mmbackendassignment.dto.UserOutputDto;
import com.mmbackendassignment.mmbackendassignment.exception.RecordNotFoundException;
import com.mmbackendassignment.mmbackendassignment.model.Role;
import com.mmbackendassignment.mmbackendassignment.model.User;
import com.mmbackendassignment.mmbackendassignment.repository.ProfileRepository;
import com.mmbackendassignment.mmbackendassignment.repository.RoleRepository;
import com.mmbackendassignment.mmbackendassignment.repository.UserRepository;
import com.mmbackendassignment.mmbackendassignment.util.Check;
import com.mmbackendassignment.mmbackendassignment.util.JwtHandler;
import com.mmbackendassignment.mmbackendassignment.util.PagableUtil;
import com.mmbackendassignment.mmbackendassignment.util.ServiceUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repo;
    private final RoleRepository roleRepo;
    private final ProfileRepository profileRepo;
    private final PasswordEncoder encoder;

    UserService(UserRepository repo, RoleRepository roleRepo, ProfileRepository profileRepo, PasswordEncoder encoder) {
        this.repo = repo;
        this.roleRepo = roleRepo;
        this.profileRepo = profileRepo;
        this.encoder = encoder;
    }

    public String createUser(AuthDto dto) {

        /* Check if username is already exist */
        Optional<User> existence = repo.findById(dto.username);

        /* If existence is empty the username is not in use and safe to add */
        if (existence.isEmpty()) {
            User user = new User(dto.username, encoder.encode(dto.password));

            repo.save(user);
            return "New user created";
        } else {
            return "Username is already in use";
        }
    }

    public UserOutputDto getUser(String username) {
        User user = (User) ServiceUtil.getRepoObjectById(repo, username, "username");

        if (!JwtHandler.isAdmin()) JwtHandler.abortIfEntityIsNotFromSameUser(user);
        return userToDto(user);
    }

    public PageDto getUsers(int page, int size, String sort) {

        sort = PagableUtil.getSortName(sort, UserOutputDto.class);

        /* sort by roles converted to username because of the String array input */
        PageRequest pr = sort.equals("username") || sort.equals("roles") ?
                PageRequest.of(page, size, Sort.by("username")) :
                PageRequest.of(page, size, Sort.by(sort).and(Sort.by("username")));

        Page<User> pageContent = repo.findAll(pr);

        List<User> contentList = pageContent.getContent();
        ArrayList<UserOutputDto> contentDto = new ArrayList<>();

        for (User u : contentList) {
            contentDto.add(this.userToDto(u));
        }

        return PagableUtil.createPageDto(contentDto, pageContent);
    }

    public String addRole(String username, String role) {
        User user = (User) ServiceUtil.getRepoObjectById(repo, username, "username");

        if (doesRoleExist(role)) {
            user.addRole(role);
            repo.save(user);
        }
        return "Done";
    }

    public String removeRole(String username, String role) {
        User user = (User) ServiceUtil.getRepoObjectById(repo, username, "username");

        user.removeRole(role);
        repo.save(user);
        return "Done";
    }

    public String deleteUser(String username) {
        User user = (User) ServiceUtil.getRepoObjectById(repo, username, "username");

        Check.hasDependency(user.getProfile(), "profile");
        repo.deleteById(username);

        return "Deleted";
    }

    public String setEnabled(String username, boolean enabled) {
        User user = (User) ServiceUtil.getRepoObjectById(repo, username, "username");

        user.setEnabled(enabled);
        repo.save(user);

        return ("User '" + username + "' enabled set to '" + enabled + "'");
    }

    public String setPassword(AuthDto dto) {
        User user = (User) ServiceUtil.getRepoObjectById(repo, dto.username, "username");

        JwtHandler.abortIfEntityIsNotFromSameUser(user);

        user.setPassword( encoder.encode( dto.password ));
        repo.save(user);

        return "Done";
    }

    private UserOutputDto userToDto(User user) {

        UserOutputDto dto = new UserOutputDto(
                user.getUsername(),
                user.isEnabled()
        );

        /* Convert collection to String[] */
        String[] roles = new String[user.getRoles().size()];
        List<Role> roleList = new ArrayList<>(user.getRoles());
        for (int r = 0; r < user.getRoles().size(); r++) {
            roles[r] = roleList.get(r).getRole();
        }
        dto.roles = roles;

        return dto;
    }

    private boolean doesRoleExist(String role) {

        List<Role> roles = roleRepo.findAll();

        for (Role r : roles) {
            if (r.getRole().equals(role.toUpperCase())) {
                return true;
            }
        }

        throw new RecordNotFoundException("role", role);
    }

//    private String getSortName( String sort ){
//        boolean sortExist = false;
//        for (Field f : UserOutputDto.class.getDeclaredFields() ){
//            if (f.getName().equalsIgnoreCase( sort )){
//                sort = f.getName();
//                sortExist = true;
//                break;
//            }
//        }
//
//        if (!sortExist){
//            throw new SortNotSupportedException( sort );
//        }
//        return sort;
//    }
}
