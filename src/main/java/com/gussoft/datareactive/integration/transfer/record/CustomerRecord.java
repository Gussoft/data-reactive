package com.gussoft.datareactive.integration.transfer.record;

public record CustomerRecord(
  Long id,
  String name,
  int age,
  AddressRecord address,
  Integer addressId,
  String status) {

}
