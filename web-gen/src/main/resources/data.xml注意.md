# 注意 #

dbunit的data*.xml不能写
<?xml version='1.0' encoding='UTF-8'?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/2002/xmlspec/dtd/2.10/xmlspec.dtd">

否则不能导入数据会报异常：org.dbunit.dataset.NoSuchTableException

alter table t_users add creater varchar(50);
alter table t_users add created datetime;
alter table t_users add modifier varchar(50);
alter table t_users add modified datetime;
alter table t_users add version int(11);
alter table t_users add deletion int(11);
alter table t_users add history int(11);
alter table t_users add memo varchar(255);