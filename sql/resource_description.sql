create table `resource`
(
    `id`      bigint(20) unsigned not null auto_increment,
    `title`   varchar(64)         not null default '' comment '资源标题',
    `url`     varchar(255)        not null default '' comment '资源地址',
    `origin`  varchar(128)        not null default '' comment '来源',
    `remark`  varchar(255)        not null default '' comment '备注',
    `ctime`   datetime            not null default current_timestamp comment '创建时间',
    `mtime`   datetime            not null default current_timestamp on update current_timestamp comment '修改时间',
    `isvalid` tinyint(2)          not null default 1 comment '是否有效 0-无效 1-有效',
    primary key (`id`)
) auto_increment = 1
  engine innodb comment '资源表';
create table `attachment`
(
    `id`        bigint(20) unsigned not null auto_increment,
    `url`       varchar(255)        not null default '' comment '附件地址',
    `file_name` varchar(64)         not null default '' comment '资源名称',
    `file_type` tinyint(2)          not null default -1 comment '资源类型',
    `file_size` int(11)             not null default 0 comment '资源大小',
    `oss_key`   varchar(64)         not null default '' comment 'oss key',
    `oss_path`  varchar(255)        not null default '' comment 'oss path',
    `remark`    varchar(255)        not null default '' comment '备注',
    `ctime`     datetime            not null default current_timestamp comment '创建时间',
    `mtime`     datetime            not null default current_timestamp on update current_timestamp comment '修改时间',
    `isvalid`   tinyint(2)          not null default 1 comment '是否有效 0-无效 1-有效',
    primary key (`id`)
) auto_increment = 1
  engine innodb comment 'oss资源描述表';