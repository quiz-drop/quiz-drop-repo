//package com.sparta.quizdemo.rank.controller;
//
//import com.sparta.quizdemo.rank.dto.SearchRankResponseDto;
//import com.sparta.quizdemo.rank.service.SearchRankService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//// 주문 횟수에 의해서 랭킹 기능 구현하기
//@RestController
//@RequiredArgsConstructor
//public class SearchRankController {
//    private final SearchRankService searchRankService;
//    @GetMapping("/api/search/rank")
//    public ResponseEntity<List<SearchRankResponseDto>> searchRankList(){
//
//        return searchRankService.getSearchRank();
//    }
//}
