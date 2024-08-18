package com.example.adapter.rest.dto;

import com.example.domain.entity.Quote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuoteDTO {
    private Quote quote;
    private AuthorDTO authorDTO;
}




