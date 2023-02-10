# 2023-2-9 xcye 取消类别名称的唯一性
alter table au_category
    drop key unique_title;

# 2023-2-9 xcye 取消标签名称的唯一性
alter table au_tag
    drop key unique_title_index;

# 2023-2-9 xcye
alter table au_login_info
    modify login_ip varchar(128) not null comment '登录ip地址';