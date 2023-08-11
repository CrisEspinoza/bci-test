package bci.test.bcitest.mapper;

import bci.test.bcitest.dto.phone.PhoneDto;
import bci.test.bcitest.model.Phone;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class PhoneMapper {
    
    public static PhoneDto toPhoneDto(Phone phone) {
        PhoneDto phoneDto = PhoneDto.builder().build();
        BeanUtils.copyProperties(phone, phoneDto);
        return phoneDto;
    }
}
