package com.example.jpa.extra.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PharmacySearch {

    private String sido;
    private String gugun;

    public String getSerchSido() {
        return sido != null ? sido : "";
    }

    public String getSerachgugun() {
        return gugun != null ? gugun : "";
    }
}
