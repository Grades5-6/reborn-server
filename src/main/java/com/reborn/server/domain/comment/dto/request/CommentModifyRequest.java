package com.reborn.server.domain.comment.dto.request;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentModifyRequest {
    private String text;
}
