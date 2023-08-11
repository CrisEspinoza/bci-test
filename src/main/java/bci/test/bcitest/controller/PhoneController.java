package bci.test.bcitest.controller;

import bci.test.bcitest.dto.phone.CreatePhoneDto;
import bci.test.bcitest.dto.phone.PhoneDto;
import bci.test.bcitest.dto.phone.UpdatePhoneDto;
import bci.test.bcitest.mapper.PhoneMapper;
import bci.test.bcitest.service.PhoneService;
import bci.test.bcitest.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/phone")
public class PhoneController {

    @Autowired
    private PhoneService phoneService;

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreatePhoneDto phoneDto) {
        try{
            return Utils.responseTry(PhoneMapper.toPhoneDto(phoneService.create(phoneDto,phoneDto.getUser())));
        } catch (Exception e){
            return Utils.exceptionCatch(e.getMessage(),400);
        }
    }

    @GetMapping("/{number}")
    public ResponseEntity<?> get(@PathVariable String number){
        try{
            return Utils.responseTry(PhoneMapper.toPhoneDto(phoneService.get(number)));
        } catch (Exception e){
            return Utils.exceptionCatch(e.getMessage(),400);
        }
    }

    @PutMapping("/{number}")
    public ResponseEntity<?> update(@PathVariable String number, @RequestBody UpdatePhoneDto phoneDto){
        try{
            return Utils.responseTry(PhoneMapper.toPhoneDto(phoneService.update(number, phoneDto)));
        } catch (Exception e){
            return Utils.exceptionCatch(e.getMessage(),400);
        }
    }

    @DeleteMapping("/{number}")
    public ResponseEntity<?> delete(@PathVariable String number){
        try{
            return Utils.responseTry(PhoneMapper.toPhoneDto(phoneService.delete(number)));
        } catch (Exception e){
            return Utils.exceptionCatch(e.getMessage(),400);
        }
    }
}
