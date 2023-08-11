package bci.test.bcitest.dto.user;

import bci.test.bcitest.dto.phone.PhoneDto;
import bci.test.bcitest.utils.Utils;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.util.List;

@Data
public class CreateUserDto {
    private String name;

    @Pattern(regexp = Utils.emailRE, message = "El email Debe tener el formato {}@{}.{}")
    private String email;

    @Pattern(regexp = Utils.passwordRE, message = "la contraseña esta en un formato incorrecto, debe tener un formato de numero")
    private String password;
    private List<PhoneDto> phones;
}
