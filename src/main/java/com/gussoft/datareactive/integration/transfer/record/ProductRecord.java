package com.gussoft.datareactive.integration.transfer.record;

import java.math.BigDecimal;

public record ProductRecord(Long id, String name, String description, BigDecimal priceSell,
                            Integer stock, CategoryRecord category) {

}
