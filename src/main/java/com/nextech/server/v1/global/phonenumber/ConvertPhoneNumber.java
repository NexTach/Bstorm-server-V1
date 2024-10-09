package com.nextech.server.v1.global.phonenumber;

import com.nextech.server.v1.global.exception.PhoneNumberNullException;
import org.springframework.stereotype.Component;

@Component
public class ConvertPhoneNumber {
    public String convertPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            throw new PhoneNumberNullException("Phone number is null");
        }
        if (phoneNumber.startsWith("010")) {
            return phoneNumber.replaceFirst("010", "+8210");
        }
        return phoneNumber;
    }
}