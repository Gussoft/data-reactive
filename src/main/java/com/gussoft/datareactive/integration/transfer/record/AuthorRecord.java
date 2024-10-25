package com.gussoft.datareactive.integration.transfer.record;

import java.time.LocalDate;

public record AuthorRecord(Long authorId, String firstName, String lastName, LocalDate birthdate) {

}
