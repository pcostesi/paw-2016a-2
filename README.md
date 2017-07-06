# **SCRUMLR**

<!-- TOC -->

- [**SCRUMLR**](#scrumlr)
    - [Developer Tools](#developer-tools)
        - [Javascript / Typescript Toolchain](#javascript--typescript-toolchain)
        - [Using the Toolchain](#using-the-toolchain)
    - [Developing the project](#developing-the-project)
        - [Front-End](#front-end)
        - [Back-End](#back-end)
    - [Building the project](#building-the-project)
    - [Installation](#installation)
    - [Migrating from older versions](#migrating-from-older-versions)

<!-- /TOC -->

## Developer Tools

### Javascript / Typescript Toolchain

You will need to install the latest current version of node js for your
platform. Instructions to do so are under each section below, but stick to
this section first.

Steps:

1. Install NodeJS
2. Install Typescript: `$ npm install -g typescript tslint`
3. Install Angular: `$ npm install -g @angular/cli`
4. Install Visual Studio Code (see below).
5. Install all the dependencies using `npm install`.

### Using the Toolchain
We use `angular-cli`, which is invoked as `ng`.
Follow the official guide for more information.
Here's a short intro:

* `ng g` _`element name`_: will generate an element with name _name_ for a:
  * `component`: usually things that change behavior but do not have a template associated. *This command creates the component in the same folder it is invoked.*
  * `directive`: a component with a template.
  * `service`: usually a persistent singleton that contains the bulk of the business logic.
  * `guard`: a sort of "gate" for routes. Use with auth.
  * `pipe`: a formatter.
  * `enum`: these have nothing special.
  * `module`: use with the flag `--routing` for view modules.
* `ng build`: will build the project with the default settings (usually dev). Use the `-prod` flag for a final (distributable) build.
* `ng serve`: will serve the project with the development server. Provides support for Hot Module Reloading and other goodies.
* `ng lint`: run before pushing. Use of `ng lint --fix` is forbidden.
* `ng xi18n`: extract messages from source code for internationalization.

## Developing the project

### Front-End

Use the npm scripts to develop; *do not use the assets served by the `.war` as they point to production*. First navigate to the frontend folder (`paw/webapp/src/main/webapp/scrumlr/`), and then use `npm run start` to start the server on development mode. The default locale is English, and the language setter will *not* work.

Then go to [localhost:4200](http://localhost:4200) to see your site in action. The toolchain will take
care of live reloading the browser.

When you are feeling confident, run `npm run maven`. This is the command Maven will use to package the app, so at least you should verify this works. Check for privacy accessors that might break.

### Back-End

Use Eclipse or IntelliJ to run the project using Jetty. Set the context to `/grupo2/` and leave `8080` as the application port.

## Building the project

Run `npm run-script build` to get a dist copy. *If you want to develop
just use `ng serve`, as the `.war` contains only the Production assets.* We use the Angular CLI to develop the frontend:
[follow this for more info](https://github.com/angular/angular-cli).

To build all the project, just run `mvn clean package` and Maven will generate
a `.war` file (`paw/webapp/target/webapp.war`).

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

**NOTE:** This script will choose randomly a project member as admin for the
project by finding a task with an owner. If there are no tasks with owners in
the project, it will create an account with the following credentials as admin:

```
username: projectName
password: projectCode
mail: projectCode@scrumlr.com
```
