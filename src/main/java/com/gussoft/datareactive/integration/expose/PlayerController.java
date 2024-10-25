package com.gussoft.datareactive.integration.expose;

import com.gussoft.datareactive.core.business.PlayerService;
import com.gussoft.datareactive.core.exception.ApiException;
import com.gussoft.datareactive.integration.transfer.record.PlayerRecord;
import com.gussoft.datareactive.integration.transfer.response.MessageResponse;
import java.net.URI;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class PlayerController {

  private final PlayerService service;

  @GetMapping("/players")
  public Flux<PlayerRecord> listAll() {
    return service.getAll();
  }

  @GetMapping("/players/pages")
  public Mono<ResponseEntity<Page<PlayerRecord>>> findAllToPages(
    @RequestParam(name = "name", defaultValue = "", required = false) String name,
    @RequestParam(name = "page", defaultValue = "0", required = false) int page,
    @RequestParam(name = "size", defaultValue = "5", required = false) int size,
    @RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy,
    @RequestParam(name = "sortDirection", defaultValue = "asc", required = false) String sortDirection
  ) throws ApiException {
    String[] sortArray = sortBy.contains(",")
      ? Arrays.stream(sortBy.split(",")).map(String::trim).toArray(String[]::new)
      : new String[] { sortBy.trim() };

    Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortArray);
    Pageable pageable = PageRequest.of(page, size, sort);

    return service.getAllToPage(pageable, name)
      .flatMap(play -> Mono.just(ResponseEntity.ok(play)));
  }
  @GetMapping("/players/{id}")
  public Mono<ResponseEntity<PlayerRecord>> getPlayerById(@PathVariable Long id) {
    return service.getById(id)
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }


  @PostMapping("/players")
  public Mono<ResponseEntity<PlayerRecord>> create(@RequestBody PlayerRecord player) {
    return service.create(player)
      .map(play -> ResponseEntity
      .created(URI.create("/api/v1/players".concat("/" + play.id()))).body(play));
  }

  @PutMapping("/players/{id}")
  public Mono<ResponseEntity<MessageResponse<PlayerRecord>>> update(
    @RequestBody PlayerRecord player, @PathVariable Long id) throws ApiException {
    return service.update(player, id)
      .flatMap(play -> {
        MessageResponse<PlayerRecord> message = MessageResponse.<PlayerRecord>builder()
          .message("Registro Actualizado")
          .content(play)
          .build();
        return Mono.just(message);
      })
      .map(ResponseEntity::ok);
    //.flatMap(msg -> Mono.just(new ResponseEntity<>(msg,HttpStatus.OK)));
  }

  @DeleteMapping("/players/{id}")
  public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
    return service.delete(id).map(ResponseEntity::ok);
  }
}
