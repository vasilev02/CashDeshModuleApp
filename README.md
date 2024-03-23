# Cash Desh Module

Spring application cash operations module in an internal information system.

## Project summary <img align="left" alt="linkedin" width="30px" src="https://i.pinimg.com/originals/1b/37/a3/1b37a31607ae30bf0fd3cf73f6009447.png" />

Cashiers should be able to deposit and withdraw money in BGN and EUR. After 2 withdrawals and 2 deposits, the cashier should be able to check
his balance and denominations. The cashier starts the day with set amount in BGN and EUR in the cash desk. All
cash operations will be executed in the same working day. The user name of the cashier within the system is
MARTINA.
### Starting Amounts:
1000 BGN, denomination: 50x10, 10x50
2000 EUR, denomination: 100x10, 20x50
### Cash Operations:
Withdrawal 1: 100 BGN, denomination: 5x10 BGN, 1x50 BGN
Withdrawal 2: 500 EUR, denomination:10x50 EUR
Deposit 1: 600 BGN, denomination: 10x10 BGN, 10x50 BGN

## Usage

## Public part

The application's public part represents endpoint to see register a new user with predifined amounts for BGN and EUR and denominations with qunatities of it: `http://localhost:8080/api/v1/create-cashier`.

## Private part

The application's private part represents endpoints to deposit money: `http://localhost:8080/api/v1/cash-operation/deposit`, withdraw money: `http://localhost:8080/api/v1/cash-operation/withdraw`
and see the user's details about his/her transactions(total amount of BGN and EUR, left denominations and quantites of it): `http://localhost:8080/api/v1/cash-balance/check`.

## Error handling

The error handling is handled by numerous checks by ApiException class which addto it a date, status and a message which we send it back to the controller. For the creation of user we check if we have such one with the same name. For the deposit we check: right currency
BGN or EUR, amount at least 5(our module works only with banknotes, not coins), amount to be equal to the given banknotes, valid banknotes, valid quantities etc.
For the withdraw the same as the deposit method but with a few more things like: checking if we have such amount to give already in our module, if we have such banknotes and enough quantity of it and etc.

## Languages and tools which I used to creat the application

[<img align="left" alt="linkedin" width="35px" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQwsq-7f5BWyog4cdeT1sQaYLVzhJ0o37Up8TjHvVU08WUgfyyMMRMHTVwJ5XReSjyhZa0&usqp=CAU" />][java]
[<img align="left" alt="linkedin" width="50px" src="https://logowik.com/content/uploads/images/731_java.jpg" />][java]
[<img align="left" alt="linkedin" width="50px" src="https://download.logo.wine/logo/PostgreSQL/PostgreSQL-Logo.wine.png" />][java]
[<img align="left" alt="linkedin" width="50px" src="https://cdn.freebiesupply.com/logos/thumbs/2x/git-logo.png" />][git]
[<img align="left" alt="linkedin" width="35px" src="https://upload.wikimedia.org/wikipedia/commons/thumb/9/91/Octicons-mark-github.svg/2048px-Octicons-mark-github.svg.png" />][github]

<br/>

## Connect with me

[<img align="left" alt="linkedin" width="30px" src="https://cdn.icon-icons.com/icons2/2429/PNG/512/linkedin_logo_icon_147268.png" />][linkedin]

[linkedin]: https://www.linkedin.com/in/valentin-vasilev-849a8b1a6/
[java]: https://www.java.com/en/
[git]: https://git-scm.com/
[github]: https://github.com/
