package com.gussoft.datareactive.integration.expose;

import com.gussoft.datareactive.core.business.CategoryService;
import com.gussoft.datareactive.integration.transfer.record.CategoryRecord;
import java.net.URI;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class CategoryController {

  private final CategoryService service;

  @GetMapping("/category")
  public Flux<CategoryRecord> listAll() {
    return service.getAll();
  }

  @GetMapping("/category/{id}")
  public Mono<ResponseEntity<CategoryRecord>> getItemById(@PathVariable Long id) {
    return service.findById(id)
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping("/category")
  public Mono<ResponseEntity<CategoryRecord>> create(@RequestBody CategoryRecord request) {
    return service.save(request)
      .map(cate -> ResponseEntity
        .created(URI.create("/api/v1/category".concat("/" + cate.id()))).body(cate));
  }
}
