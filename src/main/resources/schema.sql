drop table if exists USER_APP_KEY;

create table USER_APP_KEY(
	id integer auto_increment primary key,
	app_id VARCHAR(255) not NULL, 
	public_key VARCHAR(MAX), 
	private_key VARCHAR(MAX)
);
