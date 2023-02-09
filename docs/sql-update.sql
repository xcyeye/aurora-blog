# 2023-2-9 xcye 取消类别名称的唯一性
alter table au_category
    drop key unique_title;

# 2023-2-9 xcye 取消标签名称的唯一性
alter table au_tag
    drop key unique_title_index;