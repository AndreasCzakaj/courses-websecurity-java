package com.websecurity.app01;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
public class MfaSecret {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    private String secretKey;
    private boolean enabled;
    private Instant createdAt;

    @ElementCollection
    private List<String> recoveryCodes;

}