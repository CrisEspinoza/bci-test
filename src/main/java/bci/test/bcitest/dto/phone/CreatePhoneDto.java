package bci.test.bcitest.dto.phone;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePhoneDto {
    private String number;
    private String city;
    private String country;
    private String user;
}
