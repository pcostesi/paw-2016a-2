# **SCRUMLR**

## Installation
Running this command will run the tests and create the war file:
```
mvn clean install
```
## Migrating from older versions
Migration script in found on:
```
/paw/persistence/migration.sql
```

**NOTE:** This script will choose randomly a project member as admin for the project by finding a task with an owner. If there are no tasks with owners in the project, it will create an account with the following credentials as admin:
```
username: projectName
password: projectCode
mail: projectCode@scrumlr.com
```