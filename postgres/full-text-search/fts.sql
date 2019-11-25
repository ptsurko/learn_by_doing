CREATE SCHEMA pavel;

CREATE TABLE pavel.author(
   id SERIAL PRIMARY KEY,
   name TEXT NOT NULL
);

CREATE TABLE pavel.post(
   id SERIAL PRIMARY KEY,
   title TEXT NOT NULL,
   content TEXT NOT NULL,
   author_id INT NOT NULL references pavel.author(id) 
);

CREATE TABLE pavel.tag(
   id SERIAL PRIMARY KEY,
   name TEXT NOT NULL 
);

CREATE TABLE pavel.posts_tags(
   post_id INT NOT NULL references pavel.post(id),
   tag_id INT NOT NULL references pavel.tag(id)
 );

INSERT INTO pavel.author (id, name) 
VALUES (1, 'Pete Graham'), 
       (2, 'Rachid Belaid'), 
       (3, 'Robert Berry');

INSERT INTO pavel.tag (id, name) 
VALUES (1, 'scifi'), 
       (2, 'politics'), 
       (3, 'science');

INSERT INTO pavel.post (id, title, content, author_id) 
VALUES (1, 'Endangered species', 
        'Pandas are an endangered species', 1 ), 
       (2, 'Freedom of Speech', 
        'Freedom of speech is a necessary right in our species', 2), 
       (3, 'Star Wars vs Star Trek', 
        'Few words from a big fan', 3);


INSERT INTO pavel.posts_tags (post_id, tag_id) 
VALUES (1, 3), 
       (2, 2), 
       (3, 1);
       
SELECT to_tsvector(pavel.post.title) || 
       to_tsvector(pavel.post.content) ||
       to_tsvector(pavel.author.name) ||
       to_tsvector(coalesce((string_agg(pavel.tag.name, ' ')), '')) as document
FROM pavel.post
JOIN pavel.author ON pavel.author.id = pavel.post.author_id
JOIN pavel.posts_tags ON pavel.posts_tags.post_id = pavel.posts_tags.tag_id
JOIN pavel.tag ON pavel.tag.id = pavel.posts_tags.tag_id
GROUP BY pavel.post.id, pavel.author.id;


SELECT to_tsvector('Try not to become a man of success, but rather try to become a man of value');


select to_tsvector('If you can dream it, you can do it') @@ 'dream';
select to_tsvector('It''s kind of fun to do the impossible') @@ to_tsquery('impossible');



select pid, ptitle
from (
  SELECT pavel.post.id as pid,
        pavel.post.title as ptitle,
        pavel.post.title || ' ' || 
        pavel.post.content || ' ' ||
        pavel.author.name || ' ' ||
        coalesce((string_agg(pavel.tag.name, ' ')), '') as document
  FROM pavel.post
  JOIN pavel.author ON pavel.author.id = pavel.post.author_id
  JOIN pavel.posts_tags ON pavel.posts_tags.post_id = pavel.posts_tags.tag_id
  JOIN pavel.tag ON pavel.tag.id = pavel.posts_tags.tag_id
  GROUP BY pavel.post.id, pavel.author.id
) psearch
where psearch.document @@ to_tsquery('Endangered & Species');


select 
    pid, 
    ptitle,
    ts_rank(psearch.document, to_tsquery('Endangered | Species')) as rank
from (
  SELECT pavel.post.id as pid,
        pavel.post.title as ptitle,
        setweight(to_tsvector(pavel.post.title), 'A') || 
        setweight(to_tsvector(pavel.post.content), 'B') ||
        setweight(to_tsvector(pavel.author.name), 'C') ||
        setweight(to_tsvector(coalesce((string_agg(pavel.tag.name, ' ')), '')), 'B') as document
  FROM pavel.post
  JOIN pavel.author ON pavel.author.id = pavel.post.author_id
  JOIN pavel.posts_tags ON pavel.posts_tags.post_id = pavel.posts_tags.tag_id
  JOIN pavel.tag ON pavel.tag.id = pavel.posts_tags.tag_id
  GROUP BY pavel.post.id, pavel.author.id
) psearch
where psearch.document @@ to_tsquery('Endangered | Species')
order by rank desc;


SELECT ts_rank(to_tsvector('This is an example of document'), 
               to_tsquery('example | document')) as relevancy;
               
               
CREATE MATERIALIZED VIEW search_index
AS (
  SELECT pavel.post.id as pid,
      pavel.post.title as ptitle,
      setweight(to_tsvector(pavel.post.title), 'A') || 
      setweight(to_tsvector(pavel.post.content), 'B') ||
      setweight(to_tsvector(pavel.author.name), 'C') ||
      setweight(to_tsvector(coalesce((string_agg(pavel.tag.name, ' ')), '')), 'B') as document
  FROM pavel.post
  JOIN pavel.author ON pavel.author.id = pavel.post.author_id
  JOIN pavel.posts_tags ON pavel.posts_tags.post_id = pavel.posts_tags.tag_id
  JOIN pavel.tag ON pavel.tag.id = pavel.posts_tags.tag_id
  GROUP BY pavel.post.id, pavel.author.id
)
WITH DATA;

CREATE INDEX idx_fts_search ON search_index USING gin(document);

select * from search_index;

select pid as 
    post_id, 
    ptitle,
    ts_rank(document, to_tsquery('Endangered | Species')) as rank
from search_index
where document @@ to_tsquery('Endangered | Species')
order by rank desc;