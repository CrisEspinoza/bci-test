package bci.test.bcitest.mapper;

import bci.test.bcitest.dto.user.UserToDto;
import bci.test.bcitest.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static UserToDto toUserToReturnDto(User user) {
        UserToDto userToReturnDto = UserToDto.builder().build();
        BeanUtils.copyProperties(user, userToReturnDto);
        return userToReturnDto;
    }
}
