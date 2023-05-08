package com.hateoas.entity;

public enum TransferMode {
    ACH("Automated Clearing House"), WIRE("Wire Transfer"), ELECTRONIC("Electronic Transfer");

    public final String label;

    private TransferMode(String label) {
        this.label = label;
    }
}
