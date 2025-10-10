@ResourceRegistries

Feature: ResourceRegistries API Testing

# ===================== POST =====================

  Scenario: Creating ResourceRegistries 
   Given I have a ResourceRegistries
    When Create a ResourceRegistry using existing ResourceId
    Then The ResourceRegistries response should contain "id"
    And  The ResourceRegistries response status code should be 201
    
     # ===================== GET =====================
     
     Scenario: Fetching all ResourceRegistries
     When Fetch All ResourceRegistriesID Details
     Then The ResourceRegistries response status code should be 200
     And  The ResourceRegistries response should contain "id"
     
     # ===================== GET WITH ID =====================
     
     Scenario: Get ResourceRegistriesID Details with ID
     When Fetch ResourceRegistriesID Details with ID
     Then The ResourceRegistries response status code should be 200

 # ===================== PUT =====================
 Scenario: Updating PUT ResourceRegistriry Details with ID
 When Update ResourceRegistryID
 Then The ResourceRegistries response status code should be 200

# ===================== PATCH =====================
Scenario: Updating PAtch ResourceRegistries request
When Update Patch ResourceRegistriesID
Then The ResourceRegistries response status code should be 200

# ===================== DELETE =====================
Scenario: Deleting ResourceRegistries Details
When Delete ResourceRegistriesID with ID
Then The ResourceRegistries response status code should be 204

