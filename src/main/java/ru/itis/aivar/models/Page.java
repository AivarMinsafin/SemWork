package ru.itis.aivar.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Page {

    private Long id;
    private int pageNumber;
    private String imagePath;
    private Chapter chapter;

}
