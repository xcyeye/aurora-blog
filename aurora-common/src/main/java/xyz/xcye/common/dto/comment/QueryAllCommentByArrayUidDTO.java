package xyz.xcye.common.dto.comment;

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
