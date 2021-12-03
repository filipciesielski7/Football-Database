package com.example.application.data.entity.helper;

import java.io.Serializable;

public class ParticipatingId implements Serializable {
    private String pesel;

    private Integer matchId;

    public ParticipatingId() {
    }

    public ParticipatingId(String pesel, Integer matchId) {
        this.pesel = pesel;
        this.matchId = matchId;
    }
}
