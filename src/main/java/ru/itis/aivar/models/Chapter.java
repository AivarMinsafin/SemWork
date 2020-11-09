package ru.itis.aivar.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Chapter {

    private Long id;
    private String chapterTitle;
    private int number;
    private Title title;

}
