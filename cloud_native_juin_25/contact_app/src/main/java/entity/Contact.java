package entity;

import dto.ContactDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
@Data
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;

    public ContactDTO toDTO() {
        return ContactDTO.builder()
                .id(id)
                .email(email)
                .firstname(firstname)
                .lastname(lastname)
                .phone(phone)
                .build();
    }


}
