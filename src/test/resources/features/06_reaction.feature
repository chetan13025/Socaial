@Reaction

Feature: Reaction API testing

# ===================== POST =====================

Scenario: Creating a Reaction
Given I have a Reaction
When Create a Reaction using existing MessageId
Then The Reaction response should contain "id"
And The Reaction response status code should be 201

# ===================== GET =====================

Scenario: Fetching All Reaction
When Fetch All Reaction Details
Then The Reaction response should contain "id"
And The Reaction response status code should be 200

# ===================== GET WITH ID =====================

Scenario: Get Reaction details with ID
When Fetch Reaction Details with ID
Then The Reaction response status code should be 200

# ===================== PUT =====================

Scenario: Updating Put Reaction
When Update Reaction
Then The Reaction response should contain "id"
And The Reaction response status code should be 200

# ===================== PATCH =====================

Scenario: Updating Patch Reaction
When Update Patch Reaction
Then The Reaction response status code should be 200

# ===================== DELETE =====================

Scenario: Deleting Reaction
When Delete Reaction with ID
Then The Reaction response status code should be 204




