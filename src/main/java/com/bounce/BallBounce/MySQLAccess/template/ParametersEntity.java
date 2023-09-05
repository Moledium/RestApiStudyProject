package com.bounce.BallBounce.MySQLAccess.template;

import jakarta.persistence.*;

@Entity
@Table(name = "params", schema = "ballparameters")
public class ParametersEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    @Column(name = "futureId")
    private Long futureId;
    @Column(name = "velocity1")
    private Double velocity1;
    @Column(name = "velocity2")
    private Double velocity2;
    @Column(name = "mass1")
    private Double mass1;
    @Column(name = "mass2")
    private Double mass2;
    @Column(name = "finalvelocity")
    private Double finalVelocity;

    public ParametersEntity(Long id, Long futureId, Double velocity1, Double velocity2, Double mass1, Double mass2, Double finalVelocity) {
        this.id = id;
        this.futureId = futureId;
        this.velocity1 = velocity1;
        this.velocity2 = velocity2;
        this.mass1 = mass1;
        this.mass2 = mass2;
        this.finalVelocity = finalVelocity;
    }

    public ParametersEntity(Double velocity1, Double velocity2, Double mass1, Double mass2, Double finalVelocity) {
        this.velocity1 = velocity1;
        this.velocity2 = velocity2;
        this.mass1 = mass1;
        this.mass2 = mass2;
        this.finalVelocity = finalVelocity;
    }

    public ParametersEntity() {

    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Double getVelocity1() {
        return velocity1;
    }
    public void setVelocity1(Double velocity1) {
        this.velocity1 = velocity1;
    }
    public Double getVelocity2() {
        return velocity2;
    }
    public void setVelocity2(Double velocity2) {
        this.velocity2 = velocity2;
    }

    public Double getMass1() {
        return mass1;
    }

    public void setMass1(Double mass1) {
        this.mass1 = mass1;
    }

    public Double getMass2() {
        return mass2;
    }

    public void setMass2(Double mass2) {
        this.mass2 = mass2;
    }

    public Double getFinalVelocity() {
        return finalVelocity;
    }

    public void setFinalVelocity(Double finalVelocity) {
        this.finalVelocity = finalVelocity;
    }

    public Long getFutureId() {
        return futureId;
    }

    public void setFutureId(Long future_id) {
        this.futureId = future_id;
    }
}
