# Task service
- This is the service application
- This service uses Postgrsql as storage
- Make sure Postgresql is running
- Add your password and other required files in your own application.properties
- ### Properties required
  - ``` {db.password} ```
  - ``` {app.port} ```

 - ### Datbase schema

```                                          
   Column    |            Type             | Collation | Nullable |                Default                
-------------+-----------------------------+-----------+----------+---------------------------------------
 task_id     | integer                     |           | not null | nextval('task_task_id_seq'::regclass)
 task_name   | character varying(255)      |           |          | 
 task_desc   | character varying(55)       |           |          | 
 created     | timestamp without time zone |           | not null | 
 updated     | timestamp without time zone |           |          | 
 type        | character varying(26)       |           |          | 
 task_uuid   | character varying(36)       |           |          | 
```
