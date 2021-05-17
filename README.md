<h1> PhoneBook Spec  
  
### Running  
  
 Build the maven project, then write to command line: 
  ```java -jar .\phoneBook-0.0.1-SNAPSHOT.jar```

--------------------

<h2> JSON Objects returned by API:


### Single Person or single Contact
  ```json
{
    "id": 1,
    "name": "Gleb",
    "phoneNumber": "89116754756"
}
```

  ### Multiple Persons or multiple Contacts
   ```json
[
    {
        "id": 1,
        "name": "Gleb",
        "phoneNumber": "89116754756"
    },
    {
        "id": 2,
        "name": "Andrey",
        "phoneNumber": "89116721346"
    }
]
```

<h2> Endpoints:
  
  ### Get All Persons
  
   ```GET /api/persons```
      
  Returns a [Persons](#multiple-persons-or-multiple-contacts)  
  
  >If there are no persons, will return an empty list
  
  ### Get Person
  
   ```GET /api/persons/{id}```
      
  Returns a [Person](#single-person-or-single-contact)  
  
  >If {id} does not match, will return Status Code 500 with message: "No such user exists"
  
  ### Create Person
  
   ```POST /api/persons```
      
   Example request body:
   
   ```json
{
    "name": "Gleb",
    "phoneNumber": "89116754756"
}
```

Returns a [Person](#single-person-or-single-contact)  
  
Required fields: ```name```, ```phoneNumber``` 

> If object == null or fields are empty, will return Status Code 500 with one of these messages:   
> -"Illegal arguments"  
> -"Name cannot be empty"  
> -"Phone number cannot be empty"  

### Delete person

 ```DELETE /api/persons/{id}```

### Update person

```PUT /api/persons/{id}```
    
 Example request body:
 
 ```json
{
    "name": "Gleb",
}
```

Returns a [Person](#single-person-or-single-contact)  

Optional fields: ```name```, ```phoneNumber```

###  Find Persons By Name

 ```GET /api/persons/find```
 
Filtered by name:  

```?name=Vadim```
 
Returns a [Persons](#multiple-persons-or-multiple-contacts) 
 
 >If persons with this name does not match, will retrun an empty list

### Get Person Contacts 

 ```GET /api/persons/{personId}/contacts```
    
 Returns a [Contacts](#multiple-persons-or-multiple-contacts)  
 
 >If a person has no contacts, will return an empty list
 
### Get Contact

 ```GET /api/persons/{personId}/contacts/{contactId}```

Returns a [Contact](#single-person-or-single-contact)  

>If {contactId} does not match, will return Status Code 500 with message: "No such contact exists"

### Create Contact

 ```POST /api/persons/{personId}/contacts```

Example request body:
 
 ```json
{
    "name": "Gleb",
    "phoneNumber": "89116754756"
}
```

Returns a [Contact](#single-person-or-single-contact)  
  
Required fields: ```name```, ```phoneNumber``` 

> If object == null or fields are empty will return Status Code 500 with one of these messages:   
> -"Illegal arguments"  
> -"Name cannot be empty"  
> -"Phone number cannot be empty"  

### DeleteContact
 
 ```DELETE /api/persons/{personId}/contacts/{contactId}```
 
### Update Contact

 ```PUT /api/persons/{personId}/contacts/{contactId}```
    
 Example request body:
 
 ```json
{
    "name": "Gleb",
}
```

Returns a [Contact](#single-person-or-single-contact)  

Optional fields: ```name```, ```phoneNumber```  

### Find Contacts By Phone Number

  ```GET /api/persons/{}/find```

Filtered by phone number:  

```?phoneNumber=8912112331```
 
Returns a [Contact](#single-person-or-single-contact) 
 
 >If contacts with this phone number does not match, will retrun an empty list
