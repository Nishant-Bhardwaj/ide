# Tested application with below queries:

* CREATE TABLE poc(house_no varchar(255) NOT NULL,city varchar(255) NOT NULL) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

* show tables;

* select * from tbl;

* update tbl set user_name='don' where password='xyz'

* select * from tbl order by password

* delete from tbl where password='xyz'

* drop table poc

## Request-1:
{
"datasourceUrl":"jdbc:mysql://localhost:3306/auth_db",
"query":"show tables"
}

## Response-1:

[
{
"Tables_in_auth_db": "poc"
},
{
"Tables_in_auth_db": "tbl"
}
]

## Request-2: 
{
"datasourceUrl":"jdbc:mysql://localhost:3306/auth_db",
"query":"desc tbl"
}

## Response-2:
[
{
"Field": "password",
"Type": "varchar(255)",
"Null": "NO",
"Key": "",
"Default": null,
"Extra": ""
},
{
"Field": "user_name",
"Type": "varchar(255)",
"Null": "NO",
"Key": "",
"Default": null,
"Extra": ""
}
]