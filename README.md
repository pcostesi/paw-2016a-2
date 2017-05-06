# **SCRUMLR**

## Developer Tools

### Javascript / Typescript Toolchain

You will need to install the latest current version of node js for your
platform. Instructions to do so are under each section below, but stick to
this section first.

Steps:
1. Install NodeJS
2. Install Typescript: `$ npm install -g typescript tslint`
3. Install Angular: `$ npm install -g @angular/cli`
4. Install Visual Studio Code (see below).

## Building the project

### Server Side App

### Client Side App



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
