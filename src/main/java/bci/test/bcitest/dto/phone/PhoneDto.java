package bci.test.bcitest.dto.phone;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Builder
public class PhoneDto {
    private String number;
    private String city;
    private String country;
}
