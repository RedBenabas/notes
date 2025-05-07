package com.example.notes.controller;

import com.example.notes.domain.Note;
import com.example.notes.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class NoteControllerTest {

    @Mock
    private NoteService noteService;

    @InjectMocks
    private NoteController noteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllNotes() {
        Note note1 = new Note();
        note1.setId(1L);
        note1.setContents("Note 1");

        Note note2 = new Note();
        note2.setId(2L);
        note2.setContents("Note 2");

        when(noteService.findAll()).thenReturn(Arrays.asList(note1, note2));

        List<Note> notes = noteController.getAllNotes();
        assertEquals(2, notes.size());
        verify(noteService, times(1)).findAll();
    }

    @Test
    void testGetNoteById() {
        Note note = new Note();
        note.setId(1L);
        note.setContents("Note 1");

        when(noteService.findById(1L)).thenReturn(Optional.of(note));

        ResponseEntity<Note> response = noteController.getNoteById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(note, response.getBody());
        verify(noteService, times(1)).findById(1L);
    }

    @Test
    void testGetNoteById_NotFound() {
        when(noteService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Note> response = noteController.getNoteById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(noteService, times(1)).findById(1L);
    }

    @Test
    void testCreateNote() {
        Note note = new Note();
        note.setContents("New Note");

        when(noteService.save(note)).thenReturn(note);

        Note createdNote = noteController.createNote(note);
        assertEquals(note, createdNote);
        verify(noteService, times(1)).save(note);
    }

    @Test
    void testUpdateNote() {
        Note existingNote = new Note();
        existingNote.setId(1L);
        existingNote.setContents("Existing Note");

        Note updatedNote = new Note();
        updatedNote.setContents("Updated Note");

        when(noteService.findById(1L)).thenReturn(Optional.of(existingNote));
        when(noteService.save(existingNote)).thenReturn(existingNote);

        ResponseEntity<Note> response = noteController.updateNote(1L, updatedNote);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Note", response.getBody().getContents());
        verify(noteService, times(1)).findById(1L);
        verify(noteService, times(1)).save(existingNote);
    }

    @Test
    void testUpdateNote_NotFound() {
        Note updatedNote = new Note();
        updatedNote.setContents("Updated Note");

        when(noteService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Note> response = noteController.updateNote(1L, updatedNote);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(noteService, times(1)).findById(1L);
    }

    @Test
    void testDeleteNoteById() {
        when(noteService.findById(1L)).thenReturn(Optional.of(new Note()));

        ResponseEntity<Void> response = noteController.deleteNoteById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(noteService, times(1)).findById(1L);
        verify(noteService, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNoteById_NotFound() {
        when(noteService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = noteController.deleteNoteById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(noteService, times(1)).findById(1L);
    }
}
