package ru.itis.aivar.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Title {

    private Long id;
    private String title;
    private Author author;
    private String titleImg;
    private String description;

}
