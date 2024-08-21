package org.example.ordermanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.Arrays;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishResponseDto {

    @JsonProperty("dishDtos")
    List<DishDto> dishDtos;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DishDto {

        private Long id;
        private Double price;
        private int dishAvailabilityStatus;
        private String itemName;
        private Long itemId;
        private Long restaurantId;
        private int restaurantStatus;
        private int maxItemProcessingCap;
        private int currItemProcessingCap;
        private Double restaurantRating;
        private int orderedQuantity;

    }

}
