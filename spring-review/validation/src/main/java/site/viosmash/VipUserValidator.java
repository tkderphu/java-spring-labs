package site.viosmash;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class VipUserValidator implements ConstraintValidator<VipCodeRequired, UserDTO> {
    @Override
    public boolean isValid(UserDTO user, ConstraintValidatorContext context) {
        if (user == null) {
            return true; // handle null object (no validation needed here, or could consider null invalid)
        }
        if (user.getUserType() == UserType.VIP) {
            // If user is VIP, vipCode must not be null or blank
            String code = user.getVipCode();
            if (code == null || code.trim().isEmpty()) {
                return false; // not valid if VIP has no code
            }
        }
        // If userType is not VIP (e.g. REGULAR), we don't impose any rule on vipCode (it can be null or anything).
        return true;
    }
}