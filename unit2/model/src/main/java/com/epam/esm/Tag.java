package com.epam.esm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    private long id;
    private String name;

    public Tag(String name) {
        this.name = name;
    }

}
