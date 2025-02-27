package com.reborn.server.domain.post.application;

import com.reborn.server.domain.post.dao.PostRepository;
import com.reborn.server.domain.post.domain.*;
import com.reborn.server.domain.post.dto.request.PostRequest;
import com.reborn.server.domain.post.dto.request.PostUpdateRequest;
import com.reborn.server.domain.post.dto.response.PostResponse;
import com.reborn.server.domain.user.dao.UserRepository;
import com.reborn.server.domain.user.domain.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAllNotDeleted();

        return posts.stream()
                .map(post -> PostResponse.of(post, post.getCommentsCount())).toList();

//        return posts.stream()
//                .map(PostResponse::of)
//                .collect(Collectors.toList());
    }

    @Transactional
    public PostResponse createPosts(PostRequest postRequest) {
        // 사용자 ID로 사용자 조회
        User author = userRepository.findById(postRequest.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("User Not Found"));

        // 게시글 생성
        Post post = Post.from(postRequest, author);

        // InterestTag와 CategoryTag 처리
        List<PostInterestTag> postInterestTags = new ArrayList<>(postRequest.getInterestTags().stream()
                .map(interestTag -> PostInterestTag.of(post, interestTag))
                .toList());

        post.setPostInterestTags(postInterestTags);
        System.out.println(postInterestTags.getFirst().getInterestTag().toString());

        List<PostCategoryTag> postCategoryTags = new ArrayList<>(postRequest.getCategoryTags().stream()
                .map(categoryTag -> PostCategoryTag.of(post, categoryTag))
                .toList());

        post.setPostCategoryTags(postCategoryTags);

        Post newPost = postRepository.save(post);
        return getPosts(newPost.getId());
    }

    @Transactional
    public PostResponse getPosts(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post Not Found"));

        User author = post.getAuthor();

        List<PostInterestTag> interestTagList = post.getPostInterestTags();
        List<InterestTag> interestTagNameList = interestTagList.stream()
                .map(PostInterestTag::getInterestTag) // PostInterestTag에서 interestTag 추출
                .toList();

        List<PostCategoryTag> categoryTagList = post.getPostCategoryTags();
        List<CategoryTag> categoryTagNameList = categoryTagList.stream()
                .map(PostCategoryTag::getCategoryTag)
                .toList();

        int commentCounts = postRepository.countNotDeletedByPostId(postId);
        post.setCommentCounts(commentCounts);
        postRepository.save(post);

        return PostResponse.from(author, post, interestTagNameList, categoryTagNameList, commentCounts);
    }

    @Transactional
    public PostResponse updatePosts(Long postId, PostUpdateRequest postUpdateRequest) {

        if (postUpdateRequest == null) {
            throw new IllegalArgumentException("PostUpdateRequest cannot be null");
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post Not Found"));

        // InterestTag와 CategoryTag 처리
        List<PostInterestTag> postInterestTags = new ArrayList<>(postUpdateRequest.getInterestTags().stream()
                .map(interestTag -> PostInterestTag.of(post, interestTag))
                .toList());

        List<PostCategoryTag> postCategoryTags = new ArrayList<>(postUpdateRequest.getCategoryTags().stream()
                .map(categoryTag -> PostCategoryTag.of(post, categoryTag))
                .toList());

        post.updatePost(postUpdateRequest.getTitle(),
                postUpdateRequest.getContent(),
                postUpdateRequest.getRegion(),
                postUpdateRequest.getPostImage(),
                postInterestTags,
                postCategoryTags
        );

        Post updatedPost = postRepository.save(post);

        return getPosts(updatedPost.getId());
    }

    @Transactional
    public void deletePosts(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post Not Found"));

        post.deletePost();
        postRepository.save(post);
    }

    @Transactional
    public List<PostResponse> getPostsByHashtag(InterestTag interestTag, CategoryTag categoryTag) {
        List<Post> postList = postRepository.findPostsByInterestTagAndCategoryTag(interestTag, categoryTag);

        return postList.stream()
                .map(post -> PostResponse.of(post, post.getCommentsCount()))
                .toList();
    }

    @Transactional
    public List<PostResponse> getPostsByRegionAndHashtag(String region, InterestTag interestTag, CategoryTag categoryTag) {
        List<Post> postList = postRepository.findPostsByRegionAndInterestTagAndCategoryTag(region, interestTag, categoryTag);

        return postList.stream()
                .map(post -> PostResponse.of(post, post.getCommentsCount()))
                .toList();
    }
}
