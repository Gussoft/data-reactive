package com.gussoft.datareactive.core.repository;

import com.gussoft.datareactive.core.models.Player;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PlayerRepository extends R2dbcRepository<Player, Long> {

//  @Query("SELECT s FROM Player s WHERE s.name LIKE %$1% LIMIT $2 OFFSET $3")
  @Query("SELECT a.id, a.name, a.nick, a.name || ' ' || a.nick as full_name "
  + "FROM player a WHERE a.name LIKE CONCAT('%',:name ,'%') or a.nick LIKE CONCAT('%',:name ,'%') "
  + "ORDER BY a.id asc LIMIT :pageSize OFFSET :offSet")
  Flux<Player> findByNames(String name, int pageSize, long offSet);

  @Query("SELECT count(a.id) FROM Player a WHERE "
    + "a.name like '%' || :q || '%' or a.nick like '%' || :q || '%'")
  Mono<Integer> findCountByQ(String q);


  @Query("UPDATE player SET name=$1, nick=$2 WHERE id=$3")
  Mono<Player> updatePlayer(String name, String nick, Long id);
}
