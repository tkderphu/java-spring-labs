package site.viosmash;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)  // apply on class/type level
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = VipUserValidator.class)
public @interface VipCodeRequired {
    String message() default "VIP code is required for VIP users";
    Class<?>[] groups() default {};    // standard Bean Validation boilerplate
    Class<? extends Payload>[] payload() default {}; // standard payload (for severity or metadata)
}