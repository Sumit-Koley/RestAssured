Feature: Validating Place APIs
@AddPlace
Scenario Outline: Verify If place is being successfully added using AddPlaceAPI
  
  Given Add Place Payload with "<name>" "<language>" "<address>"
  When User Calls "AddPlaceAPI" With "GET" http request
  Then The API call got success and Status code is 200
  And "status" in response body is "OK"
  And "scope" in response body is "APP"
  And verify place_Id Created maps to "<name>" using "GetPlaceAPI"
  
Examples:
 | name  | language | address |
 |A House| Bengali  | Kolkata |
 |Flat A | Bangla   | Howrah  | 
 

 Scenario: Verify Is Delete Functionality is working 
  
  Given DeletePlace Payload
  When User Calls "DeletePlaceAPI" With "GET" http request
  Then The API call got success and Status code is 200
  And "status" in response body is "OK"