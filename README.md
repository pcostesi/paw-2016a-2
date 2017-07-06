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

## Building the project

Run `npm run-script build` to get a dist copy. If you want to develop
just use `ng serve`. We use the Angular CLI to develop the frontend:
(this)[https://github.com/angular/angular-cli]

To build all the project, just run `mvn clean package` and Maven will generate
a `.war` file (`paw/webapp/target/webapp.war`).

## Running the project

Use `ng serve` in `paw/webapp/src/main/webapp/scrumlr/`. Then go to
http://localhost:4200 to see your site in action. The developer tools will take
care of the hot module reloading and code upgrade in the browser.


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
