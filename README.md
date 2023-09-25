# Task service
- This is the service application
- This service uses Postgrsql as storage
- Make sure Postgresql is running
- Add your password and other required files in your own application.properties
- ### Properties required
  - ``` {db.password} ```
  - ``` {app.port} ```
  - ``` {app.usr} ```
  - ``` {app.env} ```
  - ``` {auth.key} ``` (Base64 encoded username:password)

 - ### Table details

```
User table

   Column   |         Type          | Collation | Nullable | Default
------------+-----------------------+-----------+----------+---------
 user_id    | integer               |           | not null |
 name       | character varying(36) |           |          |
 user_alias | character varying(36) |           |          |
Indexes:
    "t_user_pkey" PRIMARY KEY, btree (user_id)
    "uq_uuid" UNIQUE CONSTRAINT, btree (user_alias)
Referenced by:
    TABLE "task" CONSTRAINT "fk_user_alias" FOREIGN KEY (user_alias) REFERENCES t_user(user_alias)
```


```
Task table
   Column    |            Type             | Collation | Nullable |                Default
-------------+-----------------------------+-----------+----------+---------------------------------------
 task_id     | integer                     |           | not null | nextval('task_task_id_seq'::regclass)
 task_name   | character varying(255)      |           |          |
 task_status | character varying(55)       |           |          |
 created     | timestamp without time zone |           | not null |
 updated     | timestamp without time zone |           |          |
 type        | character varying(26)       |           |          |
 task_uuid   | character varying(36)       |           |          |
 user_alias  | character varying(36)       |           |          |
Indexes:
    "task_pkey" PRIMARY KEY, btree (task_id)
Foreign-key constraints:
    "fk_user_alias" FOREIGN KEY (user_alias) REFERENCES t_user(user_alias)
```
