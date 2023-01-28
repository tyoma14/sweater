insert into usr (id, username, password, active)
values (1, 'admin', '{bcrypt}$2a$10$VqOGj1ejUpujE49nUPDOH.jsj/pM9ogL0L16jCBgU.KjdMO4Z6COK', true); -- 123 pass

insert into user_role (user_id, roles)
values (1, 'USER'), (1, 'ADMIN');
