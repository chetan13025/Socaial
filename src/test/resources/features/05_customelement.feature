@CustomElement

Feature: CustomElement API testing

# ===================== POST =====================

Scenario: Creating CustomElement
Given I have a CustomElement
When Create a CustomElement using existing MessageId
Then The CustomElement response should contain "id"
And The CustomElement response status code should be 201

# ===================== GET =====================
Scenario: Fetching all CustomElement
When Fetch All CustomElement Details
Then The CustomElement response should contain "id"
And The CustomElement response status code should be 200

# ===================== GET WITH ID =====================

Scenario: Get CustomElement details with ID
When Fetch CustomElement Details with ID
And The CustomElement response status code should be 200

# ===================== PUT =====================

Scenario: Updating Put CustomElement
When Update CustomElement
And The CustomElement response status code should be 200
And The CustomElement response should contain "id"

# ===================== PATCH =====================

Scenario: Updating Patch CustomElement
When Update Patch CustomElement
And The CustomElement response status code should be 200

# ===================== DELETE =====================

Scenario: Deleting CustomElement
When Delete CustomElement with ID
And The CustomElement response status code should be 204
