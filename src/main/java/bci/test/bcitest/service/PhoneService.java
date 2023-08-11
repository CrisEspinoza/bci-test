package bci.test.bcitest.service;

import bci.test.bcitest.dto.phone.CreatePhoneDto;
import bci.test.bcitest.dto.phone.PhoneDto;
import bci.test.bcitest.dto.phone.UpdatePhoneDto;
import bci.test.bcitest.model.Phone;
import bci.test.bcitest.model.User;
import bci.test.bcitest.repository.PhoneRepository;
import bci.test.bcitest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class PhoneService {

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private UserRepository userRepository;

    public Phone create(CreatePhoneDto phoneDto, String email) throws Exception {
        try{
            Phone phone = phoneRepository.findByNumber(phoneDto.getNumber());
            User user = userRepository.findByEmail(email);
            if (phone != null)
                throw new RuntimeException("Error: El numero ya esta registrado.");
            else {
                phone = new Phone();
                phone.setCity(phoneDto.getCity());
                phone.setCountry(phoneDto.getCountry());
                phone.setNumber(phoneDto.getNumber());
                phone.setUuid(UUID.randomUUID().toString());
                phone.setUser(user);
                return phoneRepository.save(phone);
            }
        }catch (Exception e){
            throw new Exception("Error:" , e);
        }
    }

    public Phone get(String number) throws Exception {
        try{
            Phone phone = phoneRepository.findByNumber(number);
            this.validatePhone(phone);
            return phone;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public Phone update(String number, UpdatePhoneDto updatePhoneDto) throws Exception {
        try{
            Phone phone = phoneRepository.findByNumber(number);
            this.validatePhone(phone);
            if (updatePhoneDto.getCity() != null){
                phone.setCity(updatePhoneDto.getCity());
            } if (updatePhoneDto.getCountry() != null){
                phone.setCountry(updatePhoneDto.getCountry());
            }
            return phoneRepository.save(phone);
        }catch (Exception e){
            throw new Exception("Error:" , e);
        }
    }

    public Phone delete(String number) throws Exception {
        try{
            Phone phone = phoneRepository.findByNumber(number);
            this.validatePhone(phone);
            phone.setIs_active(Boolean.FALSE);
            return phoneRepository.save(phone);
        }catch (Exception e){
            throw new Exception("Error:" , e);
        }
    }

    private void validatePhone(Phone phone){
        if (phone == null)
            throw new RuntimeException("Error: El numero no existe.");
    }
}
