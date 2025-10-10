@Attachment

Feature: Attachment API testing 

# ===================== POST =====================

Scenario: Creating a Attachment
Given I have a Attachment
When Create a Attachment using existing MessageId
Then The Attachment response should contain "id"
And The Attachment response status code should be 201

# ===================== GET =====================

Scenario: Fetching all Attchments
When Fetch All Attachment Details
Then The Attachment response should contain "id"
And The Attachment response status code should be 200

# ===================== GET WITH ID =====================

Scenario: Get Attachment details with ID
When Fetch Attachment Details with ID
Then The Attachment response status code should be 200

# ===================== PUT =====================

Scenario: Updating Put Attachment
When Update Attachment
Then The Attachment response status code should be 200
And The Attachment response should contain "id"

# ===================== PATCH =====================

Scenario: Updating Patch Attachment
When Update Patch Attachment
Then The Attachment response status code should be 200

# ===================== DELETE =====================

Scenario: Deleting Attachment
When Delete Attachment with ID
Then The Attachment response status code should be 204







