package org.example.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(schema = "public", name = "client")
public class Client {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "surname")
    private String surname;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "birthday")
    private LocalDate birthday;

    public String toString() {
        String surname = this.getSurname() == null ? "-" : this.getSurname();
        String firstName = this.getFirstName() == null ? "-" : this.getFirstName();
        String patronymic = this.getPatronymic() == null ? "-" : this.getPatronymic();

        return String.format("%s %s %s", surname, firstName, patronymic);
    }
}
