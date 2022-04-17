package xyz.xcye.comment.interceptor;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.seata.core.context.RootContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author qsyyke
 */

@Component
public class CommentRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {

        String xid = RootContext.getXID();
        if (StringUtils.hasLength(xid)) {
            template.header("transactionalXid",xid);
        }
    }
}
