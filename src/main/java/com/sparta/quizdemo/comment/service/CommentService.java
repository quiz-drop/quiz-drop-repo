package com.sparta.quizdemo.comment.service;


import com.sparta.quizdemo.comment.dto.CommentRequestDto;
import com.sparta.quizdemo.comment.dto.CommentResponseDto;
import com.sparta.quizdemo.comment.entity.Comment;
import com.sparta.quizdemo.comment.repository.CommentRepository;
import com.sparta.quizdemo.common.dto.ApiResponseDto;
import com.sparta.quizdemo.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
/*읽기 전용 어노테이션(영속성 컨텍스트의 관리를 받지 않아서 성능이 좀 더 낫다.
+ 예상치 못한 엔티티의 등록, 변경, 삭제를 예방할 수 있고, 성능을 최적화할 수 있다.)
생성, 수정, 삭제 메소드에는 쓰기 권한을 위해 @Transactional 사용
*/
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional //댓글 생성
    public ResponseEntity<ApiResponseDto> createComment(CommentRequestDto commentRequestDto, User user) {
        try {
            //예외가 발생하지않으면 201 상태코드와 메시지 반환
            Comment comment = new Comment(commentRequestDto, user);
            commentRepository.save(comment);
            ApiResponseDto apiResponseDto = new ApiResponseDto("댓글을 작성했습니다.", HttpStatus.CREATED.value());
            return new ResponseEntity<>(apiResponseDto, HttpStatus.CREATED);
        } catch (Exception e) {
            //예외가 발생하면 예외처리된 원인과 상태코드 400반환
            return ResponseEntity.badRequest().body(new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    public ResponseEntity<List<CommentResponseDto>> getComments() { // 댓글 조회
        List<Comment> comments = commentRepository.findAll();
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (Comment comment : comments) {
            commentResponseDtoList.add(new CommentResponseDto(comment));
        }
        return ResponseEntity.ok(commentResponseDtoList);
    }

    @Transactional //댓글 수정
    public ResponseEntity<ApiResponseDto> updateComment(Long id, CommentRequestDto commentRequestDto, User user) {
        try {
            //예외가 발생하지않으면 200 상태코드와 메시지 반환
            Comment comment = commentRepository.findById(id)
                    .orElseThrow((() -> (new IllegalArgumentException("댓글이 존재하지 않습니다."))));
            comment.update(commentRequestDto, user);
            ApiResponseDto apiResponseDto = new ApiResponseDto("댓글을 수정했습니다.", HttpStatus.OK.value());
            return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            //예외가 발생하면 예외처리된 원인과 상태코드 400반환
            return ResponseEntity.badRequest().body(new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    public ResponseEntity<ApiResponseDto> deleteComment(Long id) { // 댓글 삭제
        try {
            //예외가 발생하지않으면 200 상태코드와 메시지 반환
            commentRepository.deleteById(id);
            ApiResponseDto apiResponseDto = new ApiResponseDto("댓글을 삭제했습니다.", HttpStatus.OK.value());
            return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            //예외가 발생하면 예외처리된 원인과 상태코드 400반환
            return ResponseEntity.badRequest().body(new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }


    @Transactional
    public ResponseEntity<ApiResponseDto> create_CommentLike(Long id, User user) { //댓글 좋아요 생성
        try {
            Comment comment = commentRepository.findById(id)
                    .orElseThrow(() -> (new IllegalArgumentException("댓글이 존재하지 않습니다.")));
            if (comment.isBool() == true) {
                throw new Exception("이미 좋아요가 있는 존재합니다.");
            } else {
                comment.setBool(true); // 사용자가 좋아요 설정: true면 좋아요 생성, false: 좋아요 삭제
                comment.setLikeCnt(comment.getLikeCnt() + 1); // 좋아요를 했으면 좋아요 수 증가
            }
            ApiResponseDto apiResponseDto = new ApiResponseDto("댓글 좋아요가 생성되었습니다.", HttpStatus.OK.value());
            return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto(e.getMessage(), HttpStatus.OK.value()));
        }
    }

    @Transactional
    public ResponseEntity<ApiResponseDto> delete_CommentLike(Long id, User user) { //댓글 좋아요 삭제
        try {
            Comment comment = commentRepository.findById(id)
                    .orElseThrow(() -> (new IllegalArgumentException("댓글이 존재하지 않습니다.")));
            if (comment.isBool() == false) {
                throw new Exception("이미 좋아요를 삭제했습니다.");
            } else {
                comment.setBool(false); // 사용자가 좋아요 설정: true면 좋아요 생성, false: 좋아요 삭제
                comment.setLikeCnt(comment.getLikeCnt() - 1); // 좋아요를 했으면 좋아요 수 감소
            }
            commentRepository.deleteById(id);
            ApiResponseDto apiResponseDto = new ApiResponseDto("댓글 좋아요가 삭제되었습니다.", HttpStatus.OK.value());
            return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto(e.getMessage(), HttpStatus.OK.value()));
        }
    }
}
