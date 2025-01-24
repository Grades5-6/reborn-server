package com.reborn.server.domain.community.application;

import com.reborn.server.domain.community.dao.CommunityPostRepository;
import com.reborn.server.domain.community.dao.PostCategoryTagRepository;
import com.reborn.server.domain.community.dao.PostInterestTagRepository;
import com.reborn.server.domain.community.domain.*;
import com.reborn.server.domain.community.dto.request.CommunityPostRequest;
import com.reborn.server.domain.community.dto.request.CommunityPostUpdateRequest;
import com.reborn.server.domain.community.dto.response.CommunityPostResponse;
import com.reborn.server.domain.user.dao.UserRepository;
import com.reborn.server.domain.user.domain.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CommunityService {
    private final CommunityPostRepository communityPostRepository;
    private final PostInterestTagRepository postInterestTagRepository;
    private final PostCategoryTagRepository postCategoryTagRepository;
    private final UserRepository userRepository;

    public CommunityService(CommunityPostRepository communityPostRepository,
                            PostInterestTagRepository postInterestTagRepository,
                            PostCategoryTagRepository postCategoryTagRepository,
                            UserRepository userRepository) {
        this.communityPostRepository = communityPostRepository;
        this.postInterestTagRepository = postInterestTagRepository;
        this.postCategoryTagRepository = postCategoryTagRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public List<CommunityPost> getAllPosts() {
        return communityPostRepository.findAll();
    }

    @Transactional
    public CommunityPostResponse createPosts(CommunityPostRequest communityPostRequest) {
        // 사용자 ID로 사용자 조회
        User author = userRepository.findById(communityPostRequest.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("User Not Found"));
        // 게시글 생성
        CommunityPost post = CommunityPost.from(communityPostRequest, author);

        CommunityPost newPost = communityPostRepository.save(post);

        // InterestTag와 CategoryTag 처리
        if (communityPostRequest.getInterestTags() != null) {
            for (InterestTag interestTag : communityPostRequest.getInterestTags()) {
                PostInterestTag postInterestTag = PostInterestTag.of(newPost, interestTag);
                postInterestTagRepository.save(postInterestTag);
            }
        }

        if (communityPostRequest.getCategoryTags() != null) {
            for (CategoryTag categoryTag : communityPostRequest.getCategoryTags()) {
                PostCategoryTag postCategoryTag = PostCategoryTag.of(newPost, categoryTag);
                postCategoryTagRepository.save(postCategoryTag);
            }
        }
        return getPosts(newPost.getId());
    }

    @Transactional
    public CommunityPostResponse getPosts(Long postId) {
        CommunityPost post = communityPostRepository.findById(postId)
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

        return CommunityPostResponse.from(author, post, interestTagNameList, categoryTagNameList);
    }

    @Transactional
    public CommunityPostResponse updatePosts(Long postId, CommunityPostUpdateRequest communityPostUpdateRequest) {

        if (communityPostUpdateRequest == null) {
            throw new IllegalArgumentException("CommunityPostUpdateRequest cannot be null");
        }

        CommunityPost post = communityPostRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post Not Found"));

        post.updatePost(communityPostUpdateRequest.getTitle(),
                communityPostUpdateRequest.getContent(),
                communityPostUpdateRequest.getRegion(),
                communityPostUpdateRequest.getPostImage()
        );

        CommunityPost updatedPost = communityPostRepository.save(post);

        // 기존에 저장된 태그 리스트 가져오기
        List<InterestTag> existingInterestTags = postInterestTagRepository.findInterestTagsByPostId(postId);
        List<CategoryTag> existingCategoryTags = postCategoryTagRepository.findCategoryTagsByPostId(postId);

        // Update InterestTag
        List<InterestTag> newInterestTags = communityPostUpdateRequest.getInterestTags();

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
        List<CategoryTag> newCategoryTags = communityPostUpdateRequest.getCategoryTags();

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

    }

}
