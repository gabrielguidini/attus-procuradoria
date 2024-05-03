## Attus Back-End Java Developer Vacancy

### Author:
- [@gabrielguidini](https://github.com/gabrielguidini)

### - Description / Descrição:
- This project consists in a application which needs to be able to manage client and their address

```mermaid
classDiagram
    class Client{
        - id : Integer
        - birthDate : Date
        - clientUuid : UUID
        - name : String
        - surname : String
    }
    class Address{
        - addressId : Integer
        - city : String
        - clientAddressEnum : ClientAddressEnum
        - houseNumber : String
        - streetName : String
        - uf : String
        - zipCode : String
    }
    class ClientAddressEnum{
        MAIN_ADRESS : Enum
        SECONDARY_ADDRESS : Enum
    }

    Client -- Address
    ClientAddressEnum --> Address
```
