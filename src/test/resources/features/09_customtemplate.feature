@CustomTemplate

Feature: CustomTemplate API testing 

# ===================== POST =====================

Scenario: Creating a CustomTemplate
Given I have a CustomTemplate
When Create a CustomTemplate using existing MessageId
Then The CustomTemplate response should contain "id"
And The CustomTemplate response status code should be 201

# ===================== GET =====================

 Scenario: Fetching All CustomTemplate
When Fetch All CustomTemplate Details
Then The CustomTemplate response should contain "id"
And The CustomTemplate response status code should be 200

# ===================== GET WITH ID =====================

Scenario: Get CustomTemplate details with ID
When Fetch CustomTemplate Details with ID
Then The CustomTemplate response status code should be 200

# ===================== PUT =====================

Scenario: Updating Put CustomTemplate
When Update CustomTemplate
Then The CustomTemplate response should contain "id"
And The CustomTemplate response status code should be 200

# ===================== PATCH =====================

Scenario: Updating Patch CustomTemplate
When Update Patch CustomTemplate
Then The CustomTemplate response status code should be 200

# ===================== DELETE =====================

Scenario: Deleting CustomTemplate
When Delete CustomTemplate with ID
Then The CustomTemplate response status code should be 204



