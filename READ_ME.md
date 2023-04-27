# Tested application with below queries:

* CREATE TABLE poc(house_no varchar(255) NOT NULL,city varchar(255) NOT NULL) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

* show tables;

* select * from tbl;

* update tbl set user_name='don' where password='xyz'

* select * from tbl order by password

* delete from tbl where password='xyz'

* drop table poc

### Note: All queries should be passed in base64:

show tables  ->  c2hvdyB0YWJsZXM=

select * from tbl ->  c2VsZWN0ICogZnJvbSB0Ymw=

update tbl set user_name='don' where password='xyz' -> dXBkYXRlIHRibCBzZXQgdXNlcl9uYW1lPSdkb24nIHdoZXJlIHBhc3N3b3JkPSd4eXon

select * from tbl order by password ->
c2VsZWN0ICogZnJvbSB0Ymwgb3JkZXIgYnkgcGFzc3dvcmQ=

delete from tbl where password='xyz' -> ZGVsZXRlIGZyb20gdGJsIHdoZXJlIHBhc3N3b3JkPSd4eXon

drop table poc -> ZHJvcCB0YWJsZSBwb2M=

## Request-1:
{
"datasourceUrl":"jdbc:mysql://localhost:3306/auth_db",
"query":"c2hvdyB0YWJsZXM="
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
"query":"ZGVzYyB0Ymw="
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