package bci.test.bcitest.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "phone")
public class Phone {
    @Id
    private String uuid;

    @Column(length = 20, unique = true)
    private String number;

    @Column(length = 20)
    private String city;

    @Column(length = 20)
    private String country;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean is_active = true;

    @ManyToOne
    @JoinColumn
    private User user;
}
