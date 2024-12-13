package viosmash.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import viosmash.dto.UserAddDto;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/get")
    public String get(@RequestParam("id") @Min(value = 1L, message = "Id phai lon hon 0") Integer id) {
        logger.info("[get][id: {}]", id);
        return "get user success";
    }

    @GetMapping("/add")
    public void add(@Valid UserAddDto addDTO) {
        logger.info("[add][addDTO: {}]", addDTO);
    }
//
//    @PostMapping("/update_gender")
//    public void updateGender(@Valid UserUpdateGenderDTO updateGenderDTO) {
//        logger.info("[updateGender][updateGenderDTO: {}]", updateGenderDTO);
//    }

//    @PostMapping("/update_status_true")
//    public void updateStatusTrue(@Validated(UserUpdateStatusDTO.Group01.class) UserUpdateStatusDTO updateStatusDTO) {
//        logger.info("[updateStatusTrue][updateStatusDTO: {}]", updateStatusDTO);
//    }

//    @PostMapping("/update_status_false")
//    public void updateStatusFalse(@Validated(UserUpdateStatusDTO.Group02.class) UserUpdateStatusDTO updateStatusDTO) {
//        logger.info("[updateStatusFalse][updateStatusDTO: {}]", updateStatusDTO);
//    }

//    @PostMapping("/update")
//    public void update(@Valid UserUpdateDTO updateDTO) {
//        logger.info("[update][updateDTO: {}]", updateDTO);
//    }

}
