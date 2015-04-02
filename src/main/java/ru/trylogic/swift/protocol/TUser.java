package ru.trylogic.swift.protocol;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;
import lombok.*;
import lombok.experimental.FieldDefaults;

@ThriftStruct
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
public final class TUser {
    
    @ThriftField(value = 1, requiredness = ThriftField.Requiredness.REQUIRED)
    String firstName;

    @ThriftField(value = 2, requiredness = ThriftField.Requiredness.OPTIONAL)
    String secondName;
}
