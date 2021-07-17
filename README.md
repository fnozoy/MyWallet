# MyWallet
My Wallet demo application with Java, Springboot, Postgresql, React JS<br />
With JPA, Spring Security, JWT, pageable, etc


## `Use Cases`

## Login
User may exist or not.<br />
In case the user exists it will be possible to login with email and password. <br />
There will be a form for new users signup. <br />

## User signup
App will allow new users signup.<br />
It'll be required name, email and password.<br />
Email must be unique.<br />

## Debits and Credits entries
App will allow the CRUD for incomes and outcomes of the month with date, value, description and entry type<br />
For every entry it will be necessary to approve or reject <br />
Users will have a report to filter by </b>year, month, description or entry type</b>.<br />

## `buil package to deploy`
using heroku <br />
> mvnw clean package <br />
