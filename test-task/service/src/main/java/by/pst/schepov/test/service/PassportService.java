package by.pst.schepov.test.service;

import by.pst.schepov.test.core.entity.Passport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PassportService {

    Page<Passport> getAllPassports(Pageable pageable);

    Passport getPassportByPassportNumber(String number);

    void deletePassportByNumber(String number);

    Passport createPassport(Passport passport);
}
