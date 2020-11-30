create table `resource_description` (
    `id` int(11) not null auto_increment,
    `origin_filename` varchar(255) not null default '' comment '原始文件名',
    `filename` varchar(255) not null default '' comment '文件名',
    `object_name` varchar(255) not null default '' comment 'oss对象名',
    `file_type` tinyint(2) not null default -1 comment '对象类型',
    `ctime` datetime not null default current_timestamp comment '创建时间',
    `mtime` datetime not null default current_timestamp on update current_timestamp comment '修改时间',
    `isvalid` tinyint(2) not null default 1 comment '是否有效 0-无效 1-有效',
    primary key `id`
) auto_increment = 1 engine innodb comment 'oss资源描述表';