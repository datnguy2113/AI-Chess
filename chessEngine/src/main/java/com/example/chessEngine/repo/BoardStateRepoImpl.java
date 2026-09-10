//package com.example.chessEngine.repo;
//
//
//import org.springframework.stereotype.Repository;
//
//import java.util.Map;
//import java.util.UUID;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Repository
//public class BoardStateRepoImpl implements BoardStateRepo{
//
//    private final Map<String, String[][]> stateMap = new ConcurrentHashMap<>();
//
//    @Override
//    public String[][] getBoardStateById(String id) {
//        if (stateMap.containsKey(id)) {
//            return stateMap.get(id);
//        }
//        return null;
//    }
//
//    @Override
//    public void saveBoardState(String id, String[][] state) {
//        stateMap.put(id, state);
//    }
//}
