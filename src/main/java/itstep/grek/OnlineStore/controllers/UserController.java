package itstep.grek.OnlineStore.controllers;

import itstep.grek.OnlineStore.Models.Role;
import itstep.grek.OnlineStore.Models.User;
import itstep.grek.OnlineStore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String userList(Model model) {
        List< User > users = new ArrayList<>();
        users = userRepository.findAll();
        model.addAttribute("users", users);
        return "userList";
    }

    @GetMapping("{user}/edit")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam Map< String, String > form,
            @RequestParam("id") User user ) {
        user.setUsername(username);
        user.setPassword(password);
        user.setActive(true);
        Set< String > roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
        return "redirect:/user";
    }

    @RequestMapping(value = "{id}/remove", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
        return "redirect:/categoryList";
    }
}
