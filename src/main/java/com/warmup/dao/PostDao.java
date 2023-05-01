package com.warmup.dao;

import com.warmup.dto.PostDto;
import com.warmup.entity.PostEntity;
import com.warmup.repository.BoardRepository;
import com.warmup.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Iterator;
import java.util.Optional;


@Repository
public class PostDao {
    private static final Logger logger = LoggerFactory.getLogger(PostDao.class);
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;

    public PostDao(
        @Autowired PostRepository postRepository, BoardRepository boardRepository) {
        this.postRepository = postRepository;
        this.boardRepository = boardRepository;
    }

    public void createPost(PostDto dto) {
        PostEntity postEntity = new PostEntity();
        if (boardRepository.findById((long) dto.getBoardId()).isEmpty()) {
            postEntity.setBoardEntity(null);
        } else {
            postEntity.setBoardEntity(boardRepository.findById((long) dto.getBoardId()).get());
        }

        postEntity.setTitle(dto.getTitle());
        postEntity.setContent(dto.getContent());
        postEntity.setWriter(dto.getWriter());

        this.postRepository.save(postEntity);
    }

    public PostEntity readPost(int id) {
        Optional<PostEntity> postEntity = this.postRepository.findById((long) id);
        if (postEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return postEntity.get();
    }

    public Iterator<PostEntity> readPostAll() {
        return this.postRepository.findAll().iterator();
    }

    public Iterator<Object> readPostByBoardId(int boardId) {
        return this.postRepository.findAllByBoardEntityId((long) boardId).iterator();
    }

    public void updatePost(int id, PostDto dto) {
        Optional<PostEntity> targetEntity = this.postRepository.findById((long) id);
        if (targetEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        PostEntity postEntity = targetEntity.get();
        postEntity.setTitle(
            dto.getTitle() == null ? postEntity.getTitle() : dto.getTitle());
        postEntity.setContent(
            dto.getContent() == null ? postEntity.getContent() : dto.getContent());
        postEntity.setWriter(
            dto.getWriter() == null ? postEntity.getWriter() : dto.getWriter());
        this.postRepository.save(postEntity);
    }

    public void deletePost(int id) {
        Optional<PostEntity> targetEntity = this.postRepository.findById((long) id);
        if (targetEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        this.postRepository.delete(targetEntity.get());
    }
}