package com.intugratic.dto;

import lombok.Data;

import java.util.Map;

@Data
public class OrderLevelRowDto {

    private String orderId;

    // dynamic key–value storage for all columns
    private Map<String, Object> data;
}