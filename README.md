# vertx-coroutine-swallowed-exception

This example needs an existing database/table. 

To help make that easier there is a *createdb.sh* script that will try to connect by default to your **localhost**
postgres db with user **postgres** on port **5432**. 

The script will ask for the password for that user and use psql to create a new database called **exampledb** with a
test table. If any of those bold need to change, you can do so in the script. You will just need to also change them in the mainVert.kt file.
