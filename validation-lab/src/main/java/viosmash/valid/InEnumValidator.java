package viosmash.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class InEnumValidator implements ConstraintValidator<InEnum, Integer> {

    private List<Integer> values = new ArrayList<>();

    @Override
    public void initialize(InEnum constraintAnnotation) {
        IntArrayValuable[] enumConstants = constraintAnnotation.clazz().getEnumConstants();


    }

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
