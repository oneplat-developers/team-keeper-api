# Team Keeper API Server

This project has multiple modules to apply MSA.

```shell
./gradlew clean build
```

## Preparation to run on local

### If you want to run h2 database on TCP mode

```shell
touch ~/test.mv.db
```

H2 database doesn't create a database automatically on TCP mode due to security reasons since 1.4.198,
so you should run the command to create a database.

#### References

- https://github.com/h2database/h2database/issues/2900
- https://h2database.com/html/tutorial.html#creating_new_databases
