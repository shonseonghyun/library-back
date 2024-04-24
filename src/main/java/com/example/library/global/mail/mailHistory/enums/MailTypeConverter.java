package com.example.library.global.mail.mailHistory.enums;

import com.example.library.global.mail.enums.MailType;
import jakarta.persistence.AttributeConverter;

public class MailTypeConverter  implements AttributeConverter<MailType,String > {
    @Override
    public String convertToDatabaseColumn(MailType attribute) {
        return attribute.getType();
    }

    @Override
    public MailType convertToEntityAttribute(String dbData) {
        return MailType.getMailType(dbData);
    }
}
