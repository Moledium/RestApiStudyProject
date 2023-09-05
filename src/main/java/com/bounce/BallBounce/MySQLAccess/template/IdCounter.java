package com.bounce.BallBounce.MySQLAccess.template;

import jakarta.persistence.*;

@Entity
@Table(name = "id_count")
public class IdCounter {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "futureId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
