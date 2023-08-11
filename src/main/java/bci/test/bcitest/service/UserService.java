package bci.test.bcitest.service;

import bci.test.bcitest.dto.phone.CreatePhoneDto;
import bci.test.bcitest.dto.user.CreateUserDto;
import bci.test.bcitest.dto.user.UpdateUserDto;
import bci.test.bcitest.mapper.UserMapper;
import bci.test.bcitest.model.Phone;
import bci.test.bcitest.model.User;
import bci.test.bcitest.repository.UserRepository;
import bci.test.bcitest.utils.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhoneService phoneService;

    public ResponseEntity<?> login(CreateUserDto createUserDto) {
        try{
            User user = userRepository.findByEmail(createUserDto.getEmail());
            this.validateUser(user);
            if (!user.getIs_active()){
                return Utils.exceptionCatch("Usuario Eliminado",401);
            }else if(user.getPassword().equals(createUserDto.getPassword())){
                return Utils.responseTry(user.getToken());
            } else{
                return Utils.exceptionCatch("Clave incorrecta",401);
            }
        }catch (Exception e){
            return Utils.exceptionCatch(e.getMessage(),400);
        }
    }

    public ResponseEntity<?> get(String email) {
        try{
            User user = userRepository.findByEmail(email);
            this.validateUser(user);
            return Utils.responseTry(UserMapper.toUserToReturnDto(user));
        }catch (Exception e){
            return Utils.exceptionCatch(e.getMessage(),400);
        }
    }

    public ResponseEntity<?> create(CreateUserDto createUserDto) {
        try{
            User user = userRepository.findByEmail(createUserDto.getEmail());
            if (user != null)
                return Utils.exceptionCatch("El correo ya existe dentro de nuestros registros.",409);
            else {
                user = new User();
                BeanUtils.copyProperties(createUserDto, user);
                Date date = new Date();
                user.setCreated(date);
                user.setLast_login(date);
                user.setModified(date);
                user.setUuid(UUID.randomUUID().toString());
                user.setToken(Utils.generateJWTToken(user.getUuid(), user.getEmail()));
                user = userRepository.save(user);

                User saveUser = user;
                List<Phone> phoneList = new ArrayList<>();

                createUserDto.getPhones().forEach((phoneDto -> {
                    try {
                        CreatePhoneDto createPhoneDto = CreatePhoneDto.builder().build();
                        BeanUtils.copyProperties(phoneDto, createPhoneDto);
                        Phone phone = phoneService.create(createPhoneDto, saveUser.getToken());
                        phoneList.add(phone);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }));

                saveUser.setPhones(phoneList);
                return Utils.responseTry(UserMapper.toUserToReturnDto(saveUser));
            }
        }catch (Exception e){
            return Utils.exceptionCatch(e.getMessage(),400);
        }
    }

    public ResponseEntity<?> update(String email, UpdateUserDto updateUserDto)  {
        try{
            User user = userRepository.findByEmail(email);
            this.validateUser(user);

            // El correo se deja como no modificable al ser único
            if (updateUserDto.getName() != null){
                user.setName(updateUserDto.getName());
            }if (updateUserDto.getPassword() != null){
                user.setPassword(updateUserDto.getPassword());
            }
            user.setModified(new Date());
            userRepository.save(user);
            return Utils.responseTry(UserMapper.toUserToReturnDto(user));

        }catch (Exception e){
            return Utils.exceptionCatch(e.getMessage(),400);
        }
    }

    public ResponseEntity<?> delete(String email) {
        try{
            User user = userRepository.findByEmail(email);
            this.validateUser(user);
            user.setIs_active(Boolean.FALSE);
            userRepository.save(user);
            return Utils.responseTry("Usuario Eliminado");
        }catch (Exception e){
            return Utils.exceptionCatch(e.getMessage(),400);
        }
    }

    private void validateUser(User user) {
        if (user == null)
            throw new RuntimeException("No existe usuario para el email ingresado");
    }
}
