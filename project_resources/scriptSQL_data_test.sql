/* Imports données tests table ROLE */
use pay_my_buddy;

INSERT INTO role (role_id,role_name) VALUES(1,'user');
INSERT INTO role (role_id,role_name) VALUES(2,'admin');
commit;

/* Imports données tests table USER */
use pay_my_buddy;

INSERT INTO user (user_id, user_email, user_password, user_first_name,user_last_name, user_balance, user_bank_account, user_role_id) VALUES(1,'hayley.smith@email.com','$2a$10$9l9ppA.6vGefZnrXf8csRuncfWwr998mzOScjd0F16Ic1BW34urwe','Hayley','Smith',500,'1111',1);
INSERT INTO user (user_id, user_email, user_password, user_first_name,user_last_name, user_balance, user_bank_account, user_role_id) VALUES(2,'clara.dupont@mail.fr','$2a$10$9tIygrmW.Ag7SD/W554yS.zxzvDFU4C7xbh1Gvz8SvDL9G7isu7GO','Clara','Dupont',0,'2222',1);
INSERT INTO user (user_id, user_email, user_password, user_first_name,user_last_name, user_balance, user_bank_account, user_role_id) VALUES(3,'john.smith@unmail.com','$2a$10$dkzlYftYpmnKbsrVdvP4POmGuDXOZqH/UmbeaeOgd68sgNLD4IjDe','John','Smith',1250,'3333',1);
INSERT INTO user (user_id, user_email, user_password, user_first_name,user_last_name, user_balance, user_bank_account, user_role_id) VALUES(4,'arthur.duval@email.fr','$2a$10$2U6wU.nf14IENpJD5dBWp.V4oZxfzSGtUN7/7w8LYZmG068./Rpqy','Arthur','Duval',999.88,'4444',1);
INSERT INTO user (user_id, user_email, user_password, user_first_name,user_last_name, user_balance, user_bank_account, user_role_id) VALUES(5,'ernest.durant@email.fr','$2a$10$0LKj5FCpuhwvQvQZuf.SL.kOY.vPnJ4M4m1GUdx/9tmmPCEETxr2S','Ernest','Durant',7500,'5555',1);
INSERT INTO user (user_id, user_email, user_password, user_first_name,user_last_name, user_balance, user_bank_account, user_role_id) VALUES(6,'jean.bon@mail.com','$2a$10$L6pRmip2fwe3rx8Uj/uNkOtw2e7MfRqm3MIPcz/Try.FHlaLxDK5K','Jean','Bon',125,'6666',1);
INSERT INTO user (user_id, user_email, user_password, user_first_name,user_last_name, user_balance, user_bank_account, user_role_id) VALUES(7,'lefort.martine@mail.de','$2a$10$PTTL7mIF8LiyKGQLPb7GnOv7BAMCFTuTwMY/aGHx8ILJ91ce3/T66','Martine','Lefort',100,'7777',1);
commit;

/* Imports données tests table CONNECTION */
use pay_my_buddy;

INSERT INTO connection (con_user_user_id,con_connection_user_id,con_connection_name) VALUES(2,4,'Arthur Duval');
INSERT INTO connection (con_user_user_id,con_connection_user_id,con_connection_name) VALUES(2,5,'M. Durant');
INSERT INTO connection (con_user_user_id,con_connection_user_id,con_connection_name) VALUES(3,2,'Maman');
INSERT INTO connection (con_user_user_id,con_connection_user_id,con_connection_name) VALUES(3,5,'Ernest Durant');
INSERT INTO connection (con_user_user_id,con_connection_user_id,con_connection_name) VALUES(3,6,'Jeannot');
INSERT INTO connection (con_user_user_id,con_connection_user_id,con_connection_name) VALUES(4,3,'Johnny');
INSERT INTO connection (con_user_user_id,con_connection_user_id,con_connection_name) VALUES(4,6,'Jean Bon');
INSERT INTO connection (con_user_user_id,con_connection_user_id,con_connection_name) VALUES(5,1,'Hayley');
INSERT INTO connection (con_user_user_id,con_connection_user_id,con_connection_name) VALUES(5,2,'Clara');
INSERT INTO connection (con_user_user_id,con_connection_user_id,con_connection_name) VALUES(5,3,'Smith');
INSERT INTO connection (con_user_user_id,con_connection_user_id,con_connection_name) VALUES(6,3,'John Smith');
INSERT INTO connection (con_user_user_id,con_connection_user_id,con_connection_name) VALUES(6,4,'Arthur');
commit;

/* Imports données tests table FEE */
use pay_my_buddy;

INSERT INTO fee (fee_id,fee_rate,fee_end_date) VALUES(1,0.25,'2018-08-31');
INSERT INTO fee (fee_id,fee_rate,fee_start_date,fee_end_date) VALUES(2,0.4,'2018-09-01','2019-12-31');
INSERT INTO fee (fee_id,fee_rate,fee_start_date) VALUES(3,0.5,'2020-01-01');
commit;

/* Imports données tests table TRANSACTION */
use pay_my_buddy;

INSERT INTO transaction (tra_id, tra_sender_user_id, tra_recipient_user_id, tra_amount, tra_date,tra_description, tra_fee_rate_fee_id, tra_billed) VALUES(1,5,1,10,'2017-01-25 15:36:20','Restaurant bill share',1,1);
INSERT INTO transaction (tra_id, tra_sender_user_id, tra_recipient_user_id, tra_amount, tra_date,tra_description, tra_fee_rate_fee_id, tra_billed) VALUES(2,5,2,25,'2017-03-05 10:32:52','Trip money',1,0);
INSERT INTO transaction (tra_id, tra_sender_user_id, tra_recipient_user_id, tra_amount, tra_date,tra_description, tra_fee_rate_fee_id, tra_billed) VALUES(3,5,3,8,'2018-09-30 16:45:27','Movie ticket',2,0);
INSERT INTO transaction (tra_id, tra_sender_user_id, tra_recipient_user_id, tra_amount, tra_date,tra_description, tra_fee_rate_fee_id, tra_billed) VALUES(4,2,5,15,'2021-07-18 21:12:00','Gasoline',3,0);
commit;