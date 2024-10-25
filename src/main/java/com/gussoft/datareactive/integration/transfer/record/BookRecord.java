package com.gussoft.datareactive.integration.transfer.record;

import java.time.LocalDate;

public record BookRecord(Long bookId, String title, LocalDate publicationDate, Boolean onlineAvailability) {

}
