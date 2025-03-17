package viosmash;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/test")
@RestController
public class Controller {
    @GetMapping("/{message}")
    @PreAuthorize("@ss.hasRole('ADMIN')")
    public String message(@PathVariable("message") String message) {
        AuthLoginResp res = (AuthLoginResp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(res);
        return message;
    }
}
