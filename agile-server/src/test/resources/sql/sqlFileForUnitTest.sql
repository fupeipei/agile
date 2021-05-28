/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 8.0.22
*********************************************************************
111
111
*/
/*!40101 SET NAMES utf8 */;
DELETE FROM agile.sprint WHERE sprint_id IN (1001,1002,1003);



insert into agile.`sprint` (`sprint_id`, `project_id`, `sprint_name`, `sprint_desc`, `status`, `state`, `start_time`, `end_time`, `sprint_days`, `finish_time`, `team_id`, `work_hours`, `version_number`, `create_uid`, `create_time`, `update_uid`, `update_time`, `sync_state`, `tenant_code`) values('1001','1','sprit name','3 day','2','E','2021-04-29 10:36:43','2021-04-29 23:59:59','1619663803375','2021-04-29 00:00:00','100108','3','v1','11','2021-04-29 10:36:43','9999','2021-04-29 10:38:57',NULL,NULL);
insert into agile.`sprint` (`sprint_id`, `project_id`, `sprint_name`, `sprint_desc`, `status`, `state`, `start_time`, `end_time`, `sprint_days`, `finish_time`, `team_id`, `work_hours`, `version_number`, `create_uid`, `create_time`, `update_uid`, `update_time`, `sync_state`, `tenant_code`) values('1002','783336509438078976','12月的第一个迭代','','3','U','2020-12-01 00:00:00','2020-12-11 23:59:59','1606752000000|1606838400000|1606924800000|1607011200000|1607270400000|1607356800000|1607443200000|1607529600000|1607616000000',NULL,'61','8','12YDDYGDD','783335106934124544','2020-12-01 15:14:14',NULL,NULL,NULL,'2');
insert into agile.`sprint` (`sprint_id`, `project_id`, `sprint_name`, `sprint_desc`, `status`, `state`, `start_time`, `end_time`, `sprint_days`, `finish_time`, `team_id`, `work_hours`, `version_number`, `create_uid`, `create_time`, `update_uid`, `update_time`, `sync_state`, `tenant_code`) values('1003','807205215180939264','迭代1','','3','U','2021-02-09 00:00:00','2021-02-28 23:59:59','1612800000000|1612886400000|1612972800000|1613059200000|1613145600000|1613232000000|1613318400000|1613404800000|1613491200000|1613577600000|1613664000000|1613750400000|1613836800000|1613923200000|1614009600000|1614096000000|1614182400000|1614268800000|1614355200000|1614441600000',NULL,'62','8','DD12','808372190482649088','2021-02-09 17:21:58',NULL,NULL,NULL,'1');
