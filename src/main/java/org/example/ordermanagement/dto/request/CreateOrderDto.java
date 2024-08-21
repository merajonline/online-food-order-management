package org.example.ordermanagement.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDto {

    @JsonProperty("customerAddressId")
    @NotNull(message = "customer address must not be null")
    private Long customerAddressId;

    @NotEmpty(message = "item list can not be empty")
    private List<@Valid OrderItemDto> itemList;

}
