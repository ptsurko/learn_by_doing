drop materialized view if exists searches;

create materialized view searches as
	SELECT 
		ROW_NUMBER() OVER (ORDER BY A.entity_id) AS id,
		A.entity_id as entity_id,
		A.content as content,
		A.type as type
	FROM 
	(
		select 
			actor_id as entity_id,
			setweight(to_tsvector(concat(first_name, ' ', last_name)), 'C') as content,
			'actor' as type
		from actor
		union
		select
			film_id as entity_id,
			setweight(to_tsvector(title), 'A') || setweight(to_tsvector(title), 'B') as content,
			'film' as type
		from film
	) as A;

CREATE UNIQUE INDEX ON searches (id);
create index idx_content on searches using gin(content);

SELECT 
	id, 
	type,
	ts_rank(content, to_tsquery('john')) as rank
FROM searches
WHERE content @@ to_tsquery('john')
ORDER BY rank DESC;

create or replace function insert_actor_into_searches() returns trigger as
$BODY$
begin
	REFRESH MATERIALIZED VIEW concurrently searches;
	return new;
end;
$BODY$
language plpgsql;

drop trigger searches_insert on actor;

create trigger searches_insert
after insert on actor
for each row execute procedure insert_actor_into_searches();

insert into actor values (10002, 'John', 'Doe');

select * from searches where entity_id = 10002;