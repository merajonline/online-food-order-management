package org.example.ordermanagement.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
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
public class OrderUpdateDto {

    @JsonProperty("id")
    @NotNull(message = "order id must not be null")
    private Long id;

    @JsonProperty("status")
    @NotNull(message = "status can not be null")
    private Integer status;

}
