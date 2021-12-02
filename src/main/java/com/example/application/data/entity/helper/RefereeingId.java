package com.example.application.data.entity.helper;

import java.io.Serializable;

public class RefereeingId implements Serializable {
    private String pesel;

    private Integer matchId;

    public RefereeingId() {
    }

    public RefereeingId(String pesel, Integer matchId) {
        this.pesel = pesel;
        this.matchId = matchId;
    }
}
