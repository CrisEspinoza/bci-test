package bci.test.bcitest.repository;

import bci.test.bcitest.model.Phone;
import bci.test.bcitest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository("phoneRepository")
public interface PhoneRepository extends JpaRepository<Phone, String> {
    Phone findByNumber(String number);
}
