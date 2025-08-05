package se.samuel.minimums.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import se.samuel.minimums.AppUser.Dto.RegistrationRequestDto;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, RegistrationRequestDto> {

    @Override
    public boolean isValid(RegistrationRequestDto dto, ConstraintValidatorContext context) {
        if (dto.getPassword() == null || dto.getConfirmPassword() == null) {
            return false;
        }

        boolean isValid = dto.getPassword().equals(dto.getConfirmPassword());
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Passwords do not match")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation();
        }

        return isValid;
    }
}