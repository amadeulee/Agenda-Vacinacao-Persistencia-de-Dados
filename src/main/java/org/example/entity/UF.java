package org.example.entity;

import java.util.ArrayList;
import java.util.List;

public enum UF {
    RO, AC, AM, RR, PA, AP, TO, MA, PI, CE, RN, PB, PE, AL, SE, BA, MG, ES, RJ, SP, PR, SC, RS, MS, MT, GO, DF;

    public static List<String> getAllValues() {
        List<String> ufs = new ArrayList<>();
        for(var uf : UF.values()) {
            ufs.add(uf.toString());
        }
        return ufs;
    }
}
