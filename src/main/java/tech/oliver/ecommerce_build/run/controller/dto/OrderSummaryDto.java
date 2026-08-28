package tech.oliver.ecommerce_build.run.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderSummaryDto(Long orderId,
                              LocalDateTime orderDate,
                              UUID userId,
                              BigDecimal total) {
}
