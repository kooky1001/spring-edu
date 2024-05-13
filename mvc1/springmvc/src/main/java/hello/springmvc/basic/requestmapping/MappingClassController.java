package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mapping/users")
public class MappingClassController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping
    public String users() {
        log.info("GET /mapping/users");
        return "Get users";
    }

    @PostMapping
    public String addUser() {
        log.info("POST /mapping/users");
        return "post user";
    }

    @GetMapping("/{userId}")
    public String findUser(@PathVariable String userId) {
        log.info("GET /mapping/users/{userId}");
        return "get userId=" + userId;
    }

    @PatchMapping("/{userId}")
    public String updateUser(@PathVariable String userId) {
        log.info("PATCH /mapping/users/{userId}");
        return "update userId=" + userId;
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId) {
        log.info("DELETE /mapping/users/{userId}");
        return "delete userId=" + userId;
    }
}
