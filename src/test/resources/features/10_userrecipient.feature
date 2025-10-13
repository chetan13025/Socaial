@UserRecipient

Feature: UserRecipient API testing

# ===================== POST =====================

Scenario: Creating a UserRecipient
Given I have a UserRecipient
When Create a UserRecipient using existing MessageId
Then The UserRecipient response should contain "id"
And The UserRecipient response status code should be 201

# ===================== GET =====================

 Scenario: Fetching All UserRecipient
When Fetch All UserRecipient Details
Then The UserRecipient response should contain "id"
And The UserRecipient response status code should be 200

# ===================== GET WITH ID =====================

Scenario: Get UserRecipient details with ID
When Fetch UserRecipient Details with ID
Then The UserRecipient response status code should be 200

# ===================== PUT =====================

Scenario: Updating Put UserRecipient
When Update UserRecipient
Then The UserRecipient response should contain "id"
And The UserRecipient response status code should be 200

# ===================== PATCH =====================

Scenario: Updating Patch UserRecipient
When Update Patch UserRecipient
Then The UserRecipient response status code should be 200

# ===================== DELETE =====================

Scenario: Deleting UserRecipient
When Delete UserRecipient with ID
Then The UserRecipient response status code should be 204








