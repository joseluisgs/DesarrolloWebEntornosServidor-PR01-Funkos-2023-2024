package com.madirex.models;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class Funko {
    @Builder.Default
    private UUID cod = UUID.randomUUID();
    private String name;
    private Model model;
    private double price;
    private LocalDate releaseDate;
}
