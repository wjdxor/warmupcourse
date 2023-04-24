package com.warmup.repository;

import com.warmup.entity.PostEntity;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<PostEntity, Long> {
    Iterable<Object> findAllByBoardEntityId(long boardId);
}