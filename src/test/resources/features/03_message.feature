@Message

Feature: Message API testing 

# ===================== POST =====================

Scenario: Creating Message 
Given I have a Message
When Create a Message using existing ResourceId
Then The Message response should contain "id"
And The Message response status code should be 201

 # ===================== GET =====================
 
 Scenario: Fetching all Message
 When Fetch All Message Details
 Then The Message response should contain "id"
 And The Message response status code should be 200
 
 # ===================== GET WITH ID =====================
 
 Scenario: Get Message Details with ID
 When Fetch Message Details with ID
 Then The Message response status code should be 200
 
 # ===================== PUT =====================
 
 Scenario: Updating PUT Message
 When Update Message
 Then The Message response status code should be 200
 And The Message response should contain "id"
 
 # ===================== PATCH =====================
 
 Scenario: Updating Patch Message request
 When Update Patch Message
 Then The Message response status code should be 200
 
 # ===================== DELETE =====================
 
 Scenario: Deleting Message
 When Delete Message with ID
 Then The Message response status code should be 204


 
