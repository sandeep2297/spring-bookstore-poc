package com.poc.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private String title;

    private Integer publishYear;

    private Integer price;

    private String genre;

    private List<Integer> authorIdList;

}
