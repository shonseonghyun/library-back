package com.example.library.global.mail.domain.mail.domain.mailHistory.converter;

import com.example.library.global.mail.domain.mail.enums.MailType;
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
