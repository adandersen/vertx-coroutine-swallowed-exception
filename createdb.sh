# if any of these parameters change, also must change them in mainVert.kt
HOST=localhost
USER=postgres
DBNAME=exampledb
PORT=5432

read -sp 'pg password:' PGPASS
CONNECTIONSTR="postgresql://${USER}:${PGPASS}@${HOST}:${PORT}"

psql ${CONNECTIONSTR} -c "create database ${DBNAME};"
psql ${CONNECTIONSTR}/"${DBNAME}" -c 'create table test(id serial);'

# connect after to manually drop database
psql ${CONNECTIONSTR}

