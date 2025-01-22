package com.reborn.server.domain.community.application;

import com.reborn.server.domain.community.dao.CommunityPostRepository;
import com.reborn.server.domain.community.dao.PostCategoryTagRepository;
import com.reborn.server.domain.community.dao.PostInterestTagRepository;
import com.reborn.server.domain.community.domain.*;
import com.reborn.server.domain.community.dto.request.CommunityPostRequest;
import com.reborn.server.domain.community.dto.response.CommunityPostResponse;
import com.reborn.server.domain.user.dao.UserRepository;
import com.reborn.server.domain.user.domain.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public void createPosts(CommunityPostRequest communityPostRequest) {
        // 사용자 ID로 사용자 조회
        User author = userRepository.findById(communityPostRequest.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("User Not Found"));
        // 게시글 생성
        CommunityPost post = CommunityPost.from(communityPostRequest, author);

        communityPostRepository.save(post);

        // InterestTag와 CategoryTag 처리
        if (communityPostRequest.getInterestTags() != null) {
            for (InterestTag interestTag : communityPostRequest.getInterestTags()) {
                PostInterestTag postInterestTag = PostInterestTag.of(post, interestTag);
                postInterestTagRepository.save(postInterestTag);
            }
        }

        if (communityPostRequest.getCategoryTag() != null) {
            for (CategoryTag categoryTag : communityPostRequest.getCategoryTag()) {
                PostCategoryTag postCategoryTag = PostCategoryTag.of(post, categoryTag);
                postCategoryTagRepository.save(postCategoryTag);
            }
        }
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

        return CommunityPostResponse.builder()
                .authorId(author.getId())
                .authorNickName(author.getNickName())
                .authorProfileImg(author.getProfileImg())
                .authorInterestTag(author.getInterestedField())
                .employmentStatus(author.getEmploymentStatus())
                .rebornTemperature(author.getRebornTemperature())
                .title(post.getTitle())
                .region(post.getRegion())
                .postImage(post.getPostImage())
                .likesCount(post.getLikesCount())
                .commentsCount(post.getCommentsCount())
                .createdAt(post.getCreatedAt())
                .interestTags(interestTagNameList)
                .categoryTag(categoryTagNameList)
                .build();
    }

    @Transactional
    public void deletePosts(Long postId) {

    }
}
