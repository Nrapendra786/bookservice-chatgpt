package com.techwaala.bookstore.dtos;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class UpdateBookRequest {

    private String title;
    private String author;
    private BigDecimal price;
    private String isbn;
}

