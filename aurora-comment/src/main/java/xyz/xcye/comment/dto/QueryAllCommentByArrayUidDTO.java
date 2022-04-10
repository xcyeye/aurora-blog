package xyz.xcye.comment.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author qsyyke
 */

@Data
public class QueryAllCommentByArrayUidDTO {

    Long[] arrayCommentUid;

    @NotNull
    Long articleUid;

    @NotNull
    Integer pageType;
}
