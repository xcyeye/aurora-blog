package xyz.xcye.comment.vo;

import lombok.Data;
import xyz.xcye.comment.dto.CommentDTO;

import java.util.List;

/**
 * 展示给前端的commentVO
 * @author qsyyke
 */


@Data
public class CommentVO {
    /** 此评论对应的文章的uid，或者是友情链接等的uid **/
    private Long articleUid;

    /** 此评论对应的页面的类型 **/
    private Integer pageType;

    /** 此页面的所有父评论的个数，也就是一级评论数 **/
    private Integer parentNodeNum;

    /** 展示所有的评论 **/
    private List<CommentDTO> commentList;
}
