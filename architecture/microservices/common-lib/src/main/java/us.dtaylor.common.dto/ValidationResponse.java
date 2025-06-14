package us.dtaylor.common.dto;

public record ValidationResponse(boolean valid, double totalPrice, String message) {}
