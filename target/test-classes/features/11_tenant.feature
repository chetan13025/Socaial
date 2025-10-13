@Tenant

Feature:

# ===================== POST =====================

Scenario: Creating a Tenant
Given I have a Tenant
When Create a Tenant
Then The Tenant response should contain "id"
And The Tenant response status code should be 201

# ===================== GET =====================

 Scenario: Fetching All Tenant
When Fetch All Tenant Details
Then The Tenant response should contain "id"
And The Tenant response status code should be 200

# ===================== GET WITH ID =====================

Scenario: Get Tenant details with ID
When Fetch Tenant Details with ID
Then The Tenant response status code should be 200

# ===================== PUT =====================

Scenario: Updating Put Tenant
When Update Tenant
Then The Tenant response should contain "id"
And The Tenant response status code should be 200

# ===================== PATCH =====================

Scenario: Updating Patch Tenant
When Update Patch Tenant
Then The Tenant response status code should be 200

# ===================== DELETE =====================

Scenario: Deleting Tenant
When Delete Tenant with ID
Then The Tenant response status code should be 204




