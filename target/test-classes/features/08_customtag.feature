@CustomTag

Feature: CustomTag API testing 

# ===================== POST =====================

Scenario: Creating a CustomTag
Given I have a CustomTag
When Create a CustomTag using existing MessageId
Then The CustomTag response should contain "id"
And The CustomElement response status code should be 201

# ===================== GET =====================

 Scenario: Fetching All CustomTag
When Fetch All CustomTag Details
Then The CustomTag response should contain "id"
And The CustomTag response status code should be 200

# ===================== GET WITH ID =====================

Scenario: Get CustomTag details with ID
When Fetch CustomTag Details with ID
Then The CustomTag response status code should be 200

# ===================== PUT =====================

Scenario: Updating Put CustomTag
When Update CustomTag
Then The CustomTag response should contain "id"
And The CustomTag response status code should be 200

# ===================== PATCH =====================

Scenario: Updating Patch CustomTag
When Update Patch CustomTag
Then The CustomTag response status code should be 200

# ===================== DELETE =====================

Scenario: Deleting CustomTag
When Delete CustomTag with ID
Then The CustomTag response status code should be 204
