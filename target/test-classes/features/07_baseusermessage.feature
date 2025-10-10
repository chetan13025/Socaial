@BaseUserMessage

Feature: BasseUserMessage API testing 

# ===================== POST =====================

Scenario: Creating BaseUserMessage
Given I have a BaseUserMessage
When Create a BaseUserMessage using existing MessageId
Then The BaseUserMessage response should contain "id"
And The BaseUserMessage response status code should be 201

# ===================== GET =====================

Scenario: Fetching All BaseUserMessage
When Fetch All BaseUserMessage Details
Then The BaseUserMessage response should contain "id"
And The BaseUserMessage response status code should be 200

# ===================== GET WITH ID =====================

Scenario: Get BaseUserMessage details with ID
When Fetch BaseUserMessage Details with ID
Then The BaseUserMessage response status code should be 200

# ===================== PUT =====================

Scenario: Updating Put BaseUserMessage
When Update BaseUserMessage
Then The BaseUserMessage response should contain "id"
And The BaseUserMessage response status code should be 200


# ===================== PATCH =====================

Scenario: Updating Patch BaseUserMessage
When Update Patch BaseUserMessage
Then The BaseUserMessage response status code should be 200

# ===================== DELETE =====================

Scenario: Deleting BaseUserMessage
When Delete BaseUserMessage with ID
Then The BaseUserMessage response status code should be 204
