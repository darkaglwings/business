/*==============================================================*/
/* ddl for frame authorization                                  */
/*==============================================================*/
# SQL Manager 2007 for MySQL 4.5.0.7
# ---------------------------------------
# Host     : 192.168.1.11
# Port     : 3306
# Database : frame


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

SET FOREIGN_KEY_CHECKS=0;

DROP DATABASE IF EXISTS `frame`;

CREATE DATABASE `frame`
    CHARACTER SET 'utf8'
    COLLATE 'utf8_general_ci';

USE `frame`;

#
# Structure for the `sys_department` table : 
#

DROP TABLE IF EXISTS `sys_department`;

CREATE TABLE `sys_department` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '编号',
  `parentid` bigint(20) default NULL COMMENT '父级部门编号',
  `rank` bigint(20) default '0' COMMENT '部分层级',
  `fullname` varchar(1000) default NULL COMMENT '部门全称',
  `title` varchar(400) default NULL COMMENT '部门简称',
  `flag` int(1) default '0' COMMENT '使用标志：0 使用；1 未使用',
  `remark` varchar(1000) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';

#
# Structure for the `sys_departmentmenu` table : 
#

DROP TABLE IF EXISTS `sys_departmentmenu`;

CREATE TABLE `sys_departmentmenu` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '编号',
  `department` bigint(20) default NULL COMMENT '部门编号',
  `menu` bigint(20) default NULL COMMENT '菜单编号',
  `flag` int(1) default '0' COMMENT '使用标志：0 使用；1 未使用',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门菜单关联表';

#
# Structure for the `sys_dictionary` table : 
#

DROP TABLE IF EXISTS `sys_dictionary`;

CREATE TABLE `sys_dictionary` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '编号',
  `parentid` bigint(20) default NULL COMMENT '父级字典',
  `sort` varchar(50) default NULL COMMENT '字典类型',
  `code` varchar(20) default NULL COMMENT '字典代码',
  `meaning` varchar(100) default NULL COMMENT '字典值',
  `display` int(5) default '0' COMMENT '字典排序',
  `flag` int(1) default '0' COMMENT '使用标志：0 使用；1 未使用',
  `remark` varchar(2000) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典表';

#
# Structure for the `sys_menu` table : 
#

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '编号',
  `parentid` bigint(20) default NULL COMMENT '父级菜单编号',
  `system` varchar(100) default NULL COMMENT '所属系统',
  `title` varchar(100) default NULL COMMENT '模块名称',
  `rank` bigint(20) default NULL COMMENT '菜单等级',
  `uri` varchar(200) default NULL COMMENT '地址',
  `display` bigint(20) default '0' COMMENT '菜单显示顺序',
  `flag` int(1) default '0' COMMENT '使用标志：0 使用；1 未使用',
  `remark` varchar(250) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统菜单表';

#
# Structure for the `sys_parameter` table : 
#

DROP TABLE IF EXISTS `sys_parameter`;

CREATE TABLE `sys_parameter` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '编号',
  `title` varchar(200) default NULL COMMENT '参数名',
  `code` varchar(200) default NULL COMMENT '参数值',
  `flag` int(1) default '0' COMMENT '使用标志：0 使用；1 未使用',
  `remark` varchar(200) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统参数表';

#
# Structure for the `sys_post` table : 
#

DROP TABLE IF EXISTS `sys_post`;

CREATE TABLE `sys_post` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '编号',
  `parentid` bigint(20) default NULL COMMENT '父级职务编号',
  `rank` bigint(20) default '0' COMMENT '职务层级',
  `fullname` varchar(1000) default NULL COMMENT '职务全称',
  `title` varchar(400) default NULL COMMENT '职务简称',
  `flag` int(1) default '0' COMMENT '使用标志：0 使用；1 未使用',
  `remark` varchar(1000) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='职务表';

#
# Structure for the `sys_postmenu` table : 
#

DROP TABLE IF EXISTS `sys_postmenu`;

CREATE TABLE `sys_postmenu` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '编号',
  `post` bigint(20) default NULL COMMENT '职务编号',
  `menu` bigint(20) default NULL COMMENT '菜单编号',
  `flag` int(1) default '0' COMMENT '使用标志：0 使用；1 未使用',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='职务菜单关联表';

#
# Structure for the `sys_role` table : 
#

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '编号',
  `code` varchar(250) default NULL COMMENT '角色编码',
  `title` varchar(100) default NULL COMMENT '角色名称',
  `rank` bigint(20) default '0' COMMENT '角色等级',
  `flag` int(1) default '0' COMMENT '使用标志：0 使用；1 未使用',
  `remark` varchar(250) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色表';

#
# Structure for the `sys_rolemenu` table : 
#

DROP TABLE IF EXISTS `sys_rolemenu`;

CREATE TABLE `sys_rolemenu` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '编号',
  `role` bigint(20) default NULL COMMENT '角色编号',
  `menu` bigint(20) default NULL COMMENT '菜单编号',
  `flag` int(1) default '0' COMMENT '使用标志：0 使用；1 未使用',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单关联表';

#
# Structure for the `sys_user` table : 
#

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '编号',
  `username` varchar(255) default NULL COMMENT '用户名',
  `password` varchar(50) default NULL COMMENT '密码',
  `title` varchar(255) default NULL COMMENT '姓名',
  `sex` varchar(10) default NULL COMMENT '性别',
  `department` varchar(40) default NULL COMMENT '部门',
  `post` varchar(20) default NULL COMMENT '职务',
  `telephone` varchar(20) default NULL COMMENT '联系电话',
  `address` varchar(200) default NULL COMMENT '联系地址',
  `cellphone` varchar(20) default NULL COMMENT '移动电话',
  `email` varchar(50) default NULL COMMENT '电子邮件',
  `flag` int(1) default '0' COMMENT '使用标志：0 使用；1 未使用',
  `online` int(1) default '1' COMMENT '在线标志：0 不在线；1 在线',
  `remark` varchar(1000) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户表';

#
# Structure for the `sys_userdepartment` table : 
#

DROP TABLE IF EXISTS `sys_userdepartment`;

CREATE TABLE `sys_userdepartment` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '编号',
  `user` bigint(20) default NULL COMMENT '用户',
  `department` bigint(20) default NULL COMMENT '部门',
  `flag` int(1) default '0' COMMENT '使用标志：0 使用；1 未使用',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户部门关联表';

#
# Structure for the `sys_usermenu` table : 
#

DROP TABLE IF EXISTS `sys_usermenu`;

CREATE TABLE `sys_usermenu` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '编号',
  `user` bigint(20) default NULL COMMENT '用户',
  `menu` bigint(20) default NULL COMMENT '菜单',
  `flag` int(1) default '0' COMMENT '使用标志：0 使用；1 未使用',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户菜单关联表';

#
# Structure for the `sys_userpost` table : 
#

DROP TABLE IF EXISTS `sys_userpost`;

CREATE TABLE `sys_userpost` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '编号',
  `user` bigint(20) default NULL COMMENT '用户',
  `post` bigint(20) default NULL COMMENT '职务',
  `flag` int(1) default '0' COMMENT '使用标志：0 使用；1 未使用',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户职务关联表';

#
# Structure for the `sys_userrole` table : 
#

DROP TABLE IF EXISTS `sys_userrole`;

CREATE TABLE `sys_userrole` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '编号',
  `user` bigint(20) default NULL COMMENT '用户',
  `role` bigint(20) default NULL COMMENT '角色',
  `flag` int(1) default '0' COMMENT '使用标志：0 使用；1 未使用',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关联表';

#
# Data for the `sys_department` table  (LIMIT 0,500)
#

INSERT INTO `sys_department` (`id`, `parentid`, `rank`, `fullname`, `title`, `flag`, `remark`) VALUES 
  (1,NULL,0,'系统测试','系统测试',0,NULL);
COMMIT;

#
# Data for the `sys_departmentmenu` table  (LIMIT 0,500)
#

INSERT INTO `sys_departmentmenu` (`id`, `department`, `menu`, `flag`) VALUES 
  (1,1,1,0),
  (2,1,2,0),
  (3,1,5,0);
COMMIT;

#
# Data for the `sys_dictionary` table  (LIMIT 0,500)
#

INSERT INTO `sys_dictionary` (`id`, `parentid`, `sort`, `code`, `meaning`, `display`, `flag`,`remark`) VALUES 
  (1,NULL,'FLAG','0','启用',0,0,NULL),
  (2,NULL,'FLAG','1','弃用',1,0,NULL);
COMMIT;

#
# Data for the `sys_menu` table  (LIMIT 0,500)
#

INSERT INTO `sys_menu` (`id`, `parentid`, `system`, `title`, `rank`, `uri`, `display`, `flag`, `remark`) VALUES 
  (1,NULL,'framework','系统框架',0,NULL,0,0,NULL),
  (2,1,'framework','系统设置',1,NULL,1,0,NULL),
  (3,2,'framework','字典管理',2,'/system/dictionary/list.jspx',2,0,NULL),
  (4,2,'framework','参数管理',2,'/system/parameter/list.jspx',3,0,NULL),
  (5,2,'framework','部门管理',2,'/system/department/list.jspx',4,0,NULL),
  (6,2,'framework','职务管理',2,'/system/post/list.jspx',5,0,NULL),
  (7,2,'framework','角色管理',2,'/system/role/list.jspx',6,0,NULL),
  (8,2,'framework','菜单管理',2,'/system/menu/list.jspx',7,0,NULL),
  (9,2,'framework','人员管理',2,'/system/user/list.jspx',8,0,NULL),
  (10,2,'framework','修改密码',2,'/system/access/password.jspx',9,0,NULL),
  (11,2,'framework','部门菜单管理',2,'/system/departmentmenu/list.jspx',10,0,NULL),
  (12,2,'framework','职务菜单管理',2,'/system/postmenu/list.jspx',11,0,NULL),
  (13,2,'framework','角色菜单管理',2,'/system/rolemenu/list.jspx',12,0,NULL),
  (14,2,'framework','人员菜单管理',2,'/system/usermenu/list.jspx',13,0,NULL),
  (15,2,'framework','人员部门管理',2,'/system/userdepartment/list.jspx',14,0,NULL),
  (16,2,'framework','人员职务管理',2,'/system/userpost/list.jspx',15,0,NULL),
  (17,2,'framework','人员角色管理',2,'/system/userrole/list.jspx',16,0,NULL),
  (18,2,'framework','软件授权',2,'/system/authorization/list.jspx',17,0,NULL);
COMMIT;

#
# Data for the `sys_parameter` table  (LIMIT 0,500)
#

INSERT INTO `sys_parameter` (`id`, `title`, `code`, `flag`, `remark`) VALUES 
  (1, 'system.database', 'mysql', NULL, NULL),
  (2, 'system.name', 'framework', NULL, NULL),
  (3, 'system.os', 'windows', NULL, NULL),
  (4, 'system.server', 'tomcat', NULL, NULL),
  (5, 'system.state', 'debug', NULL, NULL),
  (6, 'system.struct', 'page', 0, 'frame or page'),
  (7, 'page.size', '15', NULL, NULL),
  (8, 'page.dispread', '5', NULL, NULL),
  (9, 'login.password.allownull', 'true', NULL, NULL),
  (10, 'department.delete', 'all', NULL, 'logical, physical or all'),
  (11, 'dictionary.delete', 'all', NULL, 'logical, physical or all'),
  (12, 'menu.delete', 'all', NULL, 'logical, physical or all'),
  (13, 'parameter.delete', 'all', NULL, 'logical, physical or all'),
  (14, 'post.delete', 'all', NULL, 'logical, physical or all'),
  (15, 'role.delete', 'all', NULL, 'logical, physical or all'),
  (16, 'user.delete', 'all', NULL, 'logical, physical or all'),
  (17, 'related.type.department', 'single', NULL, 'single or multi'),
  (18, 'related.type.post', 'single', NULL, 'single or multi'),
  (19, 'menu.related', 'all', NULL, 'none, department, post, role, user, all'),
  (20, 'user.related', 'all', NULL, 'none, department, post, role, user, all');
COMMIT;

#
# Data for the `sys_post` table  (LIMIT 0,500)
#

INSERT INTO `sys_post` (`id`, `parentid`, `rank`, `fullname`, `title`, `flag`, `remark`) VALUES 
  (1,NULL,1,'开发部/软件设计师','软件设计师',0,NULL);
COMMIT;

#
# Data for the `sys_postmenu` table  (LIMIT 0,500)
#

INSERT INTO `sys_postmenu` (`id`, `post`, `menu`, `flag`) VALUES 
  (1,1,1,0),
  (2,1,2,0),
  (3,1,6,0);
COMMIT;

#
# Data for the `sys_role` table  (LIMIT 0,500)
#

INSERT INTO `sys_role` (`id`, `code`, `title`, `rank`, `flag`, `remark`) VALUES 
  (1,'developer','系统开发人员',1,0,'系统开发人员');
COMMIT;

#
# Data for the `sys_rolemenu` table  (LIMIT 0,500)
#

INSERT INTO `sys_rolemenu` (`id`, `role`, `menu`, `flag`) VALUES 
  (1,1,1,0),
  (2,1,2,0),
  (3,1,3,0),
  (4,1,4,0),
  (5,1,7,0),
  (6,1,8,0),
  (7,1,9,0),
  (8,1,10,0);
COMMIT;

#
# Data for the `sys_user` table  (LIMIT 0,500)
#

INSERT INTO `sys_user` (`id`, `username`, `password`, `title`, `sex`, `department`, `post`, `telephone`, `address`, `cellphone`, `email`, `flag`, `online`, `remark`) VALUES 
  (1,'administrator',NULL,'系统开发人员','男','1','1',NULL,NULL,NULL,NULL,0,0,NULL);
COMMIT;

#
# Data for the `sys_userdepartment` table  (LIMIT 0,500)
#

INSERT INTO `sys_userdepartment` (`id`, `user`, `department`, `flag`) VALUES 
  (1,1,1,0);
COMMIT;

#
# Data for the `sys_usermenu` table  (LIMIT 0,500)
#

INSERT INTO `sys_usermenu` (`id`, `user`, `menu`, `flag`) VALUES 
  (1,1,1,0),
  (2,1,2,0),
  (3,1,10,0),
  (4,1,11,0),
  (5,1,12,0),
  (6,1,13,0),
  (7,1,14,0),
  (8,1,15,0),
  (9,1,16,0),
  (10,1,17,0),
  (11,1,18,0);
COMMIT;

#
# Data for the `sys_userpost` table  (LIMIT 0,500)
#

INSERT INTO `sys_userpost` (`id`, `user`, `post`, `flag`) VALUES 
  (1,1,1,0);
COMMIT;

#
# Data for the `sys_userrole` table  (LIMIT 0,500)
#

INSERT INTO `sys_userrole` (`id`, `user`, `role`, `flag`) VALUES 
  (1,1,1,0);
COMMIT;



/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;