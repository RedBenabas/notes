package com.example.notes.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.notes.domain.Note;
import com.example.notes.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class NoteControllerE2ETest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private NoteRepository noteRepository;

  @BeforeEach
  void setUp() {
    noteRepository.deleteAll();
  }

  @Test
  void testCreateNote() throws Exception {
    Note note = new Note();
    note.setContents("New Note");

    mockMvc.perform(post("/notes")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"contents\":\"New Note\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.contents", is("New Note")));
  }

  @Test
  void testGetAllNotes() throws Exception {
    Note note1 = new Note();
    note1.setContents("Note 1");
    noteRepository.save(note1);

    Note note2 = new Note();
    note2.setContents("Note 2");
    noteRepository.save(note2);

    mockMvc.perform(get("/notes"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].contents", is("Note 1")))
        .andExpect(jsonPath("$[1].contents", is("Note 2")));
  }

  @Test
  void testGetNoteById() throws Exception {
    Note note = new Note();
    note.setContents("Note 1");
    note = noteRepository.save(note);

    mockMvc.perform(get("/notes/{id}", note.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.contents", is("Note 1")));
  }

  @Test
  void testUpdateNote() throws Exception {
    Note note = new Note();
    note.setContents("Old Note");
    note = noteRepository.save(note);

    mockMvc.perform(put("/notes/{id}", note.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"contents\":\"Updated Note\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.contents", is("Updated Note")));
  }

  @Test
  void testDeleteNoteById() throws Exception {
    Note note = new Note();
    note.setContents("Note to be deleted");
    note = noteRepository.save(note);

    mockMvc.perform(delete("/notes/{id}", note.getId()))
        .andExpect(status().isOk());

    mockMvc.perform(get("/notes/{id}", note.getId()))
        .andExpect(status().isNotFound());
  }
}
