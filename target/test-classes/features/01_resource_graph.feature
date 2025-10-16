@ResourceGraph1

Feature: ResourceGraph API Testing

   # ===================== POST =====================

  Scenario: Create ResourceGraph
    Given I have a ResourceID
    When Create New ResourceID
    Then The Resource response status code should be 201
    And The Resource response should contain "id"
    
   # ===================== GET =====================
  
   Scenario: Fetching all Resources
    When Fetch All ResourceID Details
    Then The Resource response status code should be 200
    And The Resource response should contain "id"
     
  # ===================== GET WITH ID =====================
    
  Scenario: Get ResourceID Details with ID 
  When Fetch resourceID Details with ID
  Then The Resource response status code should be 200
  
   # ===================== PUT =====================
    
  Scenario: Updating PUT ResourceID Details with ID 
  When Update ResourceID
  Then The Resource response status code should be 200
  
  # ===================== PATCH =====================
	
  Scenario: Updating Patch ResourceID Request 
	When Update Patch ResourceID
	Then The Resource response status code should be 200
	
		# ===================== DELETE =====================
	
  Scenario: Deleting ResourceID Details
  	When Delete ResourceID with ID
  	Then The Resource response status code should be 204

  