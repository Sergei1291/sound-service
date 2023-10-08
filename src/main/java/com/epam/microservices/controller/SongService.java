package com.epam.microservices.controller;

import com.epam.microservices.dto.SoundMetadata;
import com.epam.microservices.exception.Mp3ValidationException;
import com.epam.microservices.exception.SongNotFoundException;
import com.epam.microservices.repository.SongRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpClient;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/songs")
public class SongService {

    private final SongRepository songRepository;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public SongService(SongRepository songRepository, HttpClient httpClient) {
        this.songRepository = songRepository;
        this.httpClient = httpClient;
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public SavedSongResponse addSong(@RequestBody String body) throws Mp3ValidationException {
        try {
            final SoundMetadata soundMetadata = objectMapper.readValue(body, SoundMetadata.class);
            final SoundMetadata savedSoundMetadata = songRepository.save(soundMetadata);
            return new SavedSongResponse(savedSoundMetadata.getId());
        } catch (final JsonProcessingException e) {
            throw new Mp3ValidationException(e);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public SoundMetadata getSoundMetadata(@PathVariable final long id) throws SongNotFoundException {
        return songRepository.findById(id).orElseThrow(SongNotFoundException::new);
    }

    @RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
    public List<Long> deleteSongs(@PathVariable final String ids) {
        final List<Long> idsList = Arrays.stream(ids.split(",")).map(Long::parseLong).toList();
        songRepository.deleteAllById(idsList);
        return idsList;
    }
}
