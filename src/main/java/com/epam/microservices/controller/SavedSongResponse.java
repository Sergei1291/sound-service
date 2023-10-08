package com.epam.microservices.controller;

public class SavedSongResponse {

    private Long id;

    public SavedSongResponse() {
    }

    public SavedSongResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
