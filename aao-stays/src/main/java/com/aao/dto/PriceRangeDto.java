package com.aao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class PriceRangeDto {
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
}
