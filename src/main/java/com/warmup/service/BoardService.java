package com.warmup.service;

import com.warmup.dao.BoardDao;
import com.warmup.dto.BoardDto;
import com.warmup.entity.BoardEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class BoardService {
    private static final Logger logger = LoggerFactory.getLogger(BoardService.class);
    private final BoardDao boardDao;

    public BoardService(@Autowired BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    public void createBoard(BoardDto boardDto) {
        this.boardDao.createBoard(boardDto);
    }

    public BoardDto readBoard(int id) {
        BoardEntity boardEntity = this.boardDao.readBoard(id);
        BoardDto boardDto = new BoardDto(
            Math.toIntExact(boardEntity.getId()),
            boardEntity.getName()
        );
        return boardDto;
    }

    public List<BoardDto> readBoardAll() {
        Iterator<BoardEntity> iterator = this.boardDao.readBoardAll();
        List<BoardDto> boardDtoList = new ArrayList<>();
        while (iterator.hasNext()) {
            BoardEntity boardEntity = iterator.next();
            boardDtoList.add(new BoardDto(
                Math.toIntExact(boardEntity.getId()),
                boardEntity.getName()
            ));
        }
        return boardDtoList;
    }

    public void updateBoard(int id, BoardDto boardDto) {
        this.boardDao.updateBoard(id, boardDto);
    }

    public void deleteBoard(int id) {
        this.boardDao.deleteBoard(id);
    }
}