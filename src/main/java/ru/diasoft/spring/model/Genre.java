package ru.diasoft.spring.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Genre {
    private Long id;
    private String name;
}
