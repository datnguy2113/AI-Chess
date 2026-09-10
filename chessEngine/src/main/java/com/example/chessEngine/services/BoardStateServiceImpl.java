package com.example.chessEngine.services;


import com.example.chessEngine.exception.BoardStateNotFoundException;
import com.example.chessEngine.repo.BoardStateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class BoardStateServiceImpl implements BoardStateService{

    private final BoardStateRepo boardStateRepo;

    @Autowired
    public BoardStateServiceImpl(BoardStateRepo boardStateRepo) {
        this.boardStateRepo = boardStateRepo;
    }

    @Override
    public String[][] getBoardState(String id) {
        String[][] state = this.boardStateRepo.getBoardStateById(id);
        if (state == null)
            throw new BoardStateNotFoundException();
        return state;
    }
}
