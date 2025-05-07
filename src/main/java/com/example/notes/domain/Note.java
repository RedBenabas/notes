package com.example.notes.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Optional; // Import for Optional

@Entity
public class Note {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String contents;
  private Optional<String> category; // New optional field for Category

  // Default constructor
  public Note() {
    this.category = Optional.empty(); // Initialize category as empty
  }

  // Constructor with contents and category
  public Note(String contents, String category) {
    this.contents = contents;
    this.category = Optional.ofNullable(category); // Initialize category with value or empty
  }

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getContents() {
    return contents;
  }

  public void setContents(String contents) {
    this.contents = contents;
  }

  public Optional<String> getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = Optional.ofNullable(category);
  }
}
