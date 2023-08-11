package bci.test.bcitest.model;

import bci.test.bcitest.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Data()
@Entity(name = "_user")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String uuid;

    @Column(nullable = false)
    @Lob()
    private String token;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = Utils.emailRE, message = "El email Debe tener el formato {}@{}.{}")
    private String email;

    @Pattern(regexp = Utils.passwordRE, message = "la contraseña esta en un formato incorrecto, debe tener un formato de numero")
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean is_active = true;

    @Column()
    private Date created;

    @Column()
    private Date modified;

    @Column()
    private Date last_login;

    @OneToMany(mappedBy = "user")
    private List<Phone> phones;
}
