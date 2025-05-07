package com.example.notes.service;

import com.example.notes.domain.Note;
import com.example.notes.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class NoteServiceTest {

  @Mock
  private NoteRepository noteRepository;

  @InjectMocks
  private NoteService noteService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testFindAll() {
    Note note1 = new Note();
    note1.setId(1L);
    note1.setContents("Note 1");

    Note note2 = new Note();
    note2.setId(2L);
    note2.setContents("Note 2");

    when(noteRepository.findAll()).thenReturn(Arrays.asList(note1, note2));

    List<Note> notes = noteService.findAll();
    assertEquals(2, notes.size());
    verify(noteRepository, times(1)).findAll();
  }

  @Test
  void testFindById() {
    Note note = new Note();
    note.setId(1L);
    note.setContents("Note 1");

    when(noteRepository.findById(1L)).thenReturn(Optional.of(note));

    Optional<Note> foundNote = noteService.findById(1L);
    assertTrue(foundNote.isPresent());
    assertEquals(note, foundNote.get());
    verify(noteRepository, times(1)).findById(1L);
  }

  @Test
  void testSave() {
    Note note = new Note();
    note.setContents("New Note");

    when(noteRepository.save(note)).thenReturn(note);

    Note savedNote = noteService.save(note);
    assertEquals(note, savedNote);
    verify(noteRepository, times(1)).save(note);
  }

  @Test
  void testDeleteById() {
    doNothing().when(noteRepository).deleteById(1L);

    noteService.deleteById(1L);
    verify(noteRepository, times(1)).deleteById(1L);
  }
}
