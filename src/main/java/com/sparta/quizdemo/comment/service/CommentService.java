package com.sparta.quizdemo.comment.service;


import com.sparta.quizdemo.comment.dto.CommentRequestDto;
import com.sparta.quizdemo.comment.dto.CommentResponseDto;
import com.sparta.quizdemo.comment.entity.Comment;
import com.sparta.quizdemo.comment.repository.CommentRepository;
import com.sparta.quizdemo.common.dto.ApiResponseDto;
import com.sparta.quizdemo.common.entity.UserRoleEnum;
import com.sparta.quizdemo.product.entity.Product;
import com.sparta.quizdemo.product.repository.ProductRepository;
import com.sparta.quizdemo.user.entity.User;
import com.sparta.quizdemo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/*읽기 전용 어노테이션(영속성 컨텍스트의 관리를 받지 않아서 성능이 좀 더 낫다.
+ 예상치 못한 엔티티의 등록, 변경, 삭제를 예방할 수 있고, 성능을 최적화할 수 있다.)
생성, 수정, 삭제 메소드에는 쓰기 권한을 위해 @Transactional 사용
*/
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CommentService {
    private final ProductRepository productRepository;
    private final CommentRepository commentRepository;

    @Transactional //댓글 생성
    public ResponseEntity<ApiResponseDto> createComment(Long productNo, CommentRequestDto commentRequestDto, User user) {
        try {
            Product product = productRepository.findById(productNo).orElseThrow(() -> new NullPointerException("해당 번호의 상품이 존재하지 않습니다."));
            //예외가 발생하지않으면 201 상태코드와 메시지 반환
            Comment comment = new Comment(product, commentRequestDto, user);
            commentRepository.save(comment);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto("댓글을 등록하였습니다.", HttpStatus.CREATED.value()));
        } catch (Exception e) {
            //예외가 발생하면 예외처리된 원인과 상태코드 400반환
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto("댓글 등록에 실패하였습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    public ResponseEntity<List<CommentResponseDto>> getComments() { // 댓글 조회
        List<Comment> comments = commentRepository.findAll();
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (Comment comment : comments) {
            Product product = productRepository.findById(comment.getProduct().getId()).orElseThrow(() -> new NullPointerException("해당 번호의 상품이 존재하지 않습니다."));
            commentResponseDtoList.add(new CommentResponseDto(product, comment));
        }
        return ResponseEntity.status(HttpStatus.OK).body(commentResponseDtoList);
    }

    @Transactional
    public ResponseEntity<ApiResponseDto> delete_Comment(Long commentNo, User user) { // 댓글 삭제
        Comment comment = commentRepository.findById(commentNo)
                .orElseThrow((() -> (new NullPointerException("댓글이 존재하지 않습니다."))));
        try {
            if (comment.getUser().getId().equals(user.getId()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
                commentRepository.delete(comment);
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("댓글을 삭제하였습니다.", HttpStatus.OK.value()));
            } else {
                throw new Exception("해당 댓글에 대한 권한이 없습니다.");
            }

        } catch (Exception e) {
            //예외가 발생하면 예외처리된 원인과 상태코드 400반환
            return ResponseEntity.badRequest().body(new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}
