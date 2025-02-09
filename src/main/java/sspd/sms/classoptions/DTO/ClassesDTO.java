package sspd.sms.classoptions.DTO;

import lombok.Getter;
import lombok.Setter;
import sspd.sms.classoptions.model.Classes;

@Getter
@Setter
public class ClassesDTO {
    private static volatile ClassesDTO instance;
    private Classes classes;

    private ClassesDTO() {}

    public static ClassesDTO getInstance() {
        if (instance == null) {
            synchronized (ClassesDTO.class) {
                if (instance == null) {
                    instance = new ClassesDTO();
                }

            }
        }
        return instance;
    }


}
