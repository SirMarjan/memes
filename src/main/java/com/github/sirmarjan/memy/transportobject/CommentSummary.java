package com.github.sirmarjan.memy.transportobject;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentSummary {
    private long commentId;
    private String username;
    private String commentText;
    private long createTimestamp;
}
