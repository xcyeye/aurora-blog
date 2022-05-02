package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.Comment;
import generator.service.CommentService;
import generator.mapper.CommentMapper;
import org.springframework.stereotype.Service;

/**
* @author aurora
* @description 针对表【au_comment】的数据库操作Service实现
* @createDate 2022-05-02 10:43:56
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

}




