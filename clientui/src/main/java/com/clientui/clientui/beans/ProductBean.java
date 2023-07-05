package com.clientui.clientui.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductBean {
    private int id;
    private String titre;
    private String description;
    private String image;
    private Double prix;

    @Override
    public String toString() {
        return "ProductBean [id=" + id + ", titre=" + titre + ", description=" + description + ", image=" + image
                + ", prix=" + prix + "]";
    }
}
