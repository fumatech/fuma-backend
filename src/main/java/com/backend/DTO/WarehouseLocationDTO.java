package com.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseLocationDTO {
    private Long productId;
    private Long productVariationId;
    private String productName;
    private String productVariationName;
    private String productSku;
    private Long rackId;
    private String rackCode;
    private Long binId;
    private String binCode;
    private Long quantity;
}
