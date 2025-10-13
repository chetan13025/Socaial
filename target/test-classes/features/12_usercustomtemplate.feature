@UserCustomTemplate

Feature: UserCustomTemplate API testing

# ===================== POST =====================

Scenario: Creating a UserCustomTemplate
Given I have a UserCustomTemplate
When Create a UserCustomTemplate using existing CustomTemplateId
Then The UserCustomTemplate response should contain "id"
And The UserCustomTemplate response status code should be 201

# ===================== GET =====================

 Scenario: Fetching All UserCustomTemplate
When Fetch All UserCustomTemplate Details
Then The UserCustomTemplate response should contain "id"
And The UserCustomTemplate response status code should be 200

# ===================== GET WITH ID =====================

Scenario: Get UserCustomTemplate details with ID
When Fetch UserCustomTemplate Details with ID
Then The UserCustomTemplate response status code should be 200

# ===================== PUT =====================

Scenario: Updating Put UserCustomTemplate
When Update UserCustomTemplate
Then The UserCustomTemplate response should contain "id"
And The UserCustomTemplate response status code should be 200

# ===================== PATCH =====================

Scenario: Updating Patch UserCustomTemplate
When Update Patch UserCustomTemplate
Then The UserCustomTemplate response status code should be 204

# ===================== DELETE =====================

Scenario: Deleting UserCustomTemplate
When Delete UserCustomTemplate with ID
Then The UserCustomTemplate response status code should be 204
