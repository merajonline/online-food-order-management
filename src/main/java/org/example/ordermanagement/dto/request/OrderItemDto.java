package org.example.ordermanagement.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {

    @JsonProperty("itemId")
    @NotNull(message = "item id must not be null")
    private Long itemId;

    @JsonProperty("quantity")
    @NotNull(message = "item quantity must have value greater than 0")
    @Min(value = 1, message = "item quantity must be greater than 0")
    private Integer quantity;

}
