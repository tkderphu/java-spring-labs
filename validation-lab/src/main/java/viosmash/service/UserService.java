package viosmash.service;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import viosmash.dto.UserAddDto;


@Service
@Validated
public class UserService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    public void addUser(@Valid UserAddDto userAddDto) {
        logger.info("[addUser][userAddDto: {}]", userAddDto);
    }
}
