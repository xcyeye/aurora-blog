# 2023-2-9 xcye 取消类别名称的唯一性
alter table au_category
    drop key unique_title;

# 2023-2-9 xcye 取消标签名称的唯一性
alter table au_tag
    drop key unique_title_index;

# 2023-2-9 xcye
alter table au_login_info
    modify login_ip varchar(128) not null comment '登录ip地址';

# 2023-2-10 xcye
alter table au_comment
    modify comment_ip varchar(128) null comment '评论者的ip地址';

# 2023-2-10 xcye
alter table au_link
    modify category_name varchar(255) null comment '此条友情链接属于哪个分类';

# 2023-2-14 xcye 邮件发送记录增加userUid
alter table au_email_log
    add user_uid bigint not null after uid;

