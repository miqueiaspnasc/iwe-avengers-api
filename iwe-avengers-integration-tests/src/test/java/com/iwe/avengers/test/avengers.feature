Feature: Perform integrated tests on the Avengers registration API

Background:
* url 'https://w3bvnhl758.execute-api.us-east-1.amazonaws.com/dev'

 * def getToken =
"""
function() {
 var TokenGenerator = Java.type('com.iwe.avengers.test.authorization.TokenGenerator');
 var sg = new TokenGenerator();
 return sg.getToken();
}
"""
* def token = call getToken

Scenario: Should return non-authenticated access

Given path 'avengers', 'any-id'
When method get
Then status 401

Scenario: Avenger not found

Given path 'avengers', 'avenger-not-found'
And header Authorization = 'Bearer ' + token
When method get
Then status 404

Scenario: Create a new Avenger

Given path 'avengers'
And header Authorization = 'Bearer ' + token
And request {name: 'Captain America', secretIdentity: 'Steve Rogers'}
When method post
Then status 201
And match response == {id: '#string', name: 'Captain America', secretIdentity: 'Steve Rogers'}

* def savedAvenger = response

Given path 'avengers', savedAvenger.id
And header Authorization = 'Bearer ' + token
When method get
Then status 200
And match $ == savedAvenger

Scenario: Create a new Avenger without the required data

Given path 'avengers'
And header Authorization = 'Bearer ' + token
And request {name: 'Captain America'}
When method post
Then status 400 

Scenario: Avenger not found on delete

Given path 'avengers', 'avenger-not-found'
And header Authorization = 'Bearer ' + token
When method delete
Then status 404

Scenario: Delete a Avenger by Id

Given path 'avengers'
And header Authorization = 'Bearer ' + token
And request {name: 'Captain America', secretIdentity: 'Steve Rogers'}
When method post
Then status 201
And match response == {id: '#string', name: 'Captain America', secretIdentity: 'Steve Rogers'}

* def savedAvenger = response

Given path 'avengers', savedAvenger.id
And header Authorization = 'Bearer ' + token
When method delete
Then status 204

Given path 'avengers', savedAvenger.id
And header Authorization = 'Bearer ' + token
And header Authorization = 'Bearer ' + token
When method get
Then status 404

Scenario: Avenger not found on update

Given path 'avengers', 'avenger-not-found' 
And header Authorization = 'Bearer ' + token
And request {name: 'Captain America', secretIdentity: 'Steve Rogers'}
When method put
Then status 404

Scenario: Update a Avenger

Given path 'avengers'
And header Authorization = 'Bearer ' + token
And request {name: 'Captain America', secretIdentity: 'Steve Rogers'}
When method post
Then status 201
And match response == {id: '#string', name: 'Captain America', secretIdentity: 'Steve Rogers'}

* def savedAvenger = response

Given path 'avengers', savedAvenger.id
And header Authorization = 'Bearer ' + token 
And request {name: 'Iron Man', secretIdentity: 'Tony Stark'}
When method put
Then status 200
And match response == {id: '#string', name: '#string', secretIdentity: '#string'}

* def updatedAvenger = response

Given path 'avengers', updatedAvenger.id
And header Authorization = 'Bearer ' + token
When method get
Then status 200
And match $.id == updatedAvenger.id
And match $.name == updatedAvenger.name
And match $.secretIdentity == updatedAvenger.secretIdentity

Scenario: Update a Avenger without the required data

Given path 'avengers', 'aaaa-bbbb-cccc-dddd'
And header Authorization = 'Bearer ' + token
And request {name: 'Captain America'}
When method put
Then status 400	

Scenario: delete all Avengers

Given path 'avengers'
When method delete
Then status 204

Scenario: Should return non-authorized access

Given path 'avengers', 'anyid' 
And header Authorization = 'Bearer ' + token + 'a'
When method get
Then status 403
