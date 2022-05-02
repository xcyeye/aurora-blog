package xyz.xcye.comment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.xcye.comment.service.CommentService;

/**
 * @author qsyyke
 */

@SpringBootTest
public class MainTest {

    @Autowired
    private CommentService service;

    @Test
    public void test() {
        /*for (int i = 0; i < 5; i++) {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setContent("内容" + i);
            commentDTO.setCommentIp("sdf" + i);
            commentDTO.setReplyCommentUid(34876345L);
            commentDTO.setAvatar("aa.jpb" + i);
            commentDTO.setEmail("2291308094@qq.com" + i);
            commentDTO.setOperationSystemInfo("mac");
            commentDTO.setPath("https://baidu.com");
            commentDTO.setUsername("chuchen");
            commentDTO.setSite("https://xcye.xyz");
            ModifyResult modifyResult = service.insertComment(commentDTO);
            System.out.println(modifyResult);
        }*/

        /*for (int i = 0; i < 2; i++) {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setContent("回复的内容内容");
            commentDTO.setCommentIp("sdf");
            commentDTO.setReplyCommentUid(1511741509339758592L);
            commentDTO.setAvatar("aa.jpb");
            commentDTO.setEmail("2291308094@qq.com");
            commentDTO.setOperationSystemInfo("mac");
            commentDTO.setPath("https://baidu.com");
            commentDTO.setUsername("chuchen");
            commentDTO.setSite("https://xcye.xyz");
            ModifyResult modifyResult = service.insertComment(commentDTO);
            System.out.println(modifyResult);
        }*/

        //CommentVO commentVO = service.queryAllComment(new Long[]{1511739958395191296L, 1511739958936256512L},12L,2);

        //ModifyResult modifyResult = service.setCommentDeleteStatus(1511741920838729728L);
        //System.out.println(modifyResult);
        //service.deleteComment(1511741920838729728L);

        /*CommentDTO commentDTO = new CommentDTO();
        commentDTO.setUid(1511741920037617664L);
        commentDTO.setContent("修改的内容内容");
        commentDTO.setCommentIp("127.0.0.1");
        //commentDTO.setReplyCommentUid(1511741509339758592L);
        //commentDTO.setAvatar("aa.jpb");
        commentDTO.setEmail("2604400276@qq.com");
        commentDTO.setOperationSystemInfo("windows");
        commentDTO.setPath("https://baidu.com");
        commentDTO.setUsername("chuchen");
        commentDTO.setSite("https://xcye.xyz");*/
        //ModifyResult modifyResult = service.updateComment(commentDTO);
        //System.out.println(modifyResult);
    }
}
