package com.reborn.server.domain.post.application;

import com.reborn.server.domain.post.dao.PostRepository;
import com.reborn.server.domain.post.dao.PostCategoryTagRepository;
import com.reborn.server.domain.post.dao.PostInterestTagRepository;
import com.reborn.server.domain.post.domain.*;
import com.reborn.server.domain.post.dto.request.PostRequest;
import com.reborn.server.domain.post.dto.request.PostUpdateRequest;
import com.reborn.server.domain.post.dto.response.PostResponse;
import com.reborn.server.domain.user.dao.UserRepository;
import com.reborn.server.domain.user.domain.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostInterestTagRepository postInterestTagRepository;
    private final PostCategoryTagRepository postCategoryTagRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository,
                       PostInterestTagRepository postInterestTagRepository,
                       PostCategoryTagRepository postCategoryTagRepository,
                       UserRepository userRepository) {
        this.postRepository = postRepository;
        this.postInterestTagRepository = postInterestTagRepository;
        this.postCategoryTagRepository = postCategoryTagRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Transactional
    public PostResponse createPosts(PostRequest postRequest) {
        // 사용자 ID로 사용자 조회
        User author = userRepository.findById(postRequest.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("User Not Found"));
        // 게시글 생성
        Post post = Post.from(postRequest, author);

        Post newPost = postRepository.save(post);

        // InterestTag와 CategoryTag 처리
        if (postRequest.getInterestTags() != null) {
            for (InterestTag interestTag : postRequest.getInterestTags()) {
                PostInterestTag postInterestTag = PostInterestTag.of(newPost, interestTag);
                postInterestTagRepository.save(postInterestTag);
            }
        }

        if (postRequest.getCategoryTags() != null) {
            for (CategoryTag categoryTag : postRequest.getCategoryTags()) {
                PostCategoryTag postCategoryTag = PostCategoryTag.of(newPost, categoryTag);
                postCategoryTagRepository.save(postCategoryTag);
            }
        }
        return getPosts(newPost.getId());
    }

    @Transactional
    public PostResponse getPosts(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post Not Found"));

        User author = post.getAuthor();

        List<InterestTag> interestTagList = postInterestTagRepository.findInterestTagsByPostId(postId);
        List<String> interestTagNameList = interestTagList.stream()
                .map(InterestTag::getName)
                .toList();

        List<CategoryTag> categoryTagList = postCategoryTagRepository.findCategoryTagsByPostId(postId);
        List<String> categoryTagNameList = categoryTagList.stream()
                .map(CategoryTag::getName)
                .toList();

        int commentCounts = postRepository.countNotDeletedByPostId(postId);
        post.setCommentCounts(commentCounts);
        postRepository.save(post);

        return PostResponse.from(author, post, interestTagNameList, categoryTagNameList, commentCounts);
    }

    @Transactional
    public PostResponse updatePosts(Long postId, PostUpdateRequest postUpdateRequest) {

        if (postUpdateRequest == null) {
            throw new IllegalArgumentException("CommunityPostUpdateRequest cannot be null");
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post Not Found"));

        post.updatePost(postUpdateRequest.getTitle(),
                postUpdateRequest.getContent(),
                postUpdateRequest.getRegion(),
                postUpdateRequest.getPostImage()
        );

        Post updatedPost = postRepository.save(post);

        // 기존에 저장된 태그 리스트 가져오기
        List<InterestTag> existingInterestTags = postInterestTagRepository.findInterestTagsByPostId(postId);
        List<CategoryTag> existingCategoryTags = postCategoryTagRepository.findCategoryTagsByPostId(postId);

        // Update InterestTag
        List<InterestTag> newInterestTags = postUpdateRequest.getInterestTags();

        // 기존 태그 중에서 새로운 태그에 없는 것 삭제
        for (InterestTag existingTag : existingInterestTags) {
            if (!newInterestTags.contains(existingTag)) {

                // InterestTag와 postId로 PostInterestTag를 찾기
                Optional<PostInterestTag> postInterestTagOptional = postInterestTagRepository.findByInterestTagAndPostId(existingTag, postId);
                // PostInterestTag가 존재하는 경우 삭제
                postInterestTagOptional.ifPresent(postInterestTagRepository::delete);

            }
        }

        // 새로운 태그 중에서 기존 태그에 없는 것 추가
        for (InterestTag newTag : newInterestTags) {
            if (!existingInterestTags.contains(newTag)) {
                PostInterestTag postInterestTag = PostInterestTag.of(updatedPost, newTag);
                postInterestTagRepository.save(postInterestTag);
            }
        }

        // Update CategoryTag
        List<CategoryTag> newCategoryTags = postUpdateRequest.getCategoryTags();

        for (CategoryTag existingTag : existingCategoryTags) {
            if (!newCategoryTags.contains(existingTag)) {

                // CategoryTag와 postId로 PostCategoryTag를 찾기
                Optional<PostCategoryTag> postCategoryTagOptional = postCategoryTagRepository.findByCategoryTagAndPostId(existingTag, postId);

                // PostCategoryTag가 존재하는 경우 삭제
                postCategoryTagOptional.ifPresent(postCategoryTagRepository::delete);
            }
        }

        for (CategoryTag newTag : newCategoryTags) {
            if (!existingCategoryTags.contains(newTag)) {
                PostCategoryTag postCategoryTag = PostCategoryTag.of(updatedPost, newTag);
                postCategoryTagRepository.save(postCategoryTag);
            }
        }

        return getPosts(updatedPost.getId());
    }

    @Transactional
    public void deletePosts(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post Not Found"));

        post.deletePost();
        postRepository.save(post);
    }
}
