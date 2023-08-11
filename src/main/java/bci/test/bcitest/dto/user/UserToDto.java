package bci.test.bcitest.dto.user;

import bci.test.bcitest.dto.phone.PhoneDto;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
@Builder
public class UserToDto {
    private String uuid;
    private String token;
    private Boolean is_active;
    private Date created;
    private Date modified;
    private Date last_login;
    private ArrayList<PhoneDto> phones;
}
