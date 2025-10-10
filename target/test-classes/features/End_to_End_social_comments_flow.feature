Feature: End-to-end ResourceGraph and ResourceRegistries CRUD flow

  # 1) POST ResourceGraph
  @Resource_Graph
  Scenario: Create ResourceID
    Given I have a ResourceID
    When Create New ResourceID
    Then The Resource response status code should be 201
    And The Resource response should contain "id"

  # 2) POST ResourceRegistries (should run after ResourceGraph POST and use stored id)
  @Resource_Registries
  Scenario: Creating ResourceRegistries
    Given I have a ResourceRegistries
    When Create a ResourceRegistry using existing ResourceId
    Then The ResourceRegistries response should contain "id"
    And The ResourceRegistries response status code should be 201
# 3) POST Message (should run after ResourceGraph POST and use stored id)

  @Message
  Scenario: Creating Message
    Given I have a Message
    When Create a Message using existing ResourceId
    Then The Message response should contain "id"
    And The Message response status code should be 201
# 3) POST Attchment (should run after Message POST and use stored id)

  @Attachment
  Scenario: Creating a Attachment
    Given I have a Attachment
    When Create a Attachment using existing MessageId
    Then The Attachment response should contain "id"
    And The Attachment response status code should be 201
# 3) POST CustomElement (should run after Message POST and use stored id)

  @CustomElement
  Scenario: Creating CustomElement
    Given I have a CustomElement
    When Create a CustomElement using existing MessageId
    Then The CustomElement response should contain "id"
    And The CustomElement response status code should be 201
# 3) POST Reaction (should run after Message POST and use stored id)

  @Reaction
  Scenario: Creating a Reaction
    Given I have a Reaction
    When Create a Reaction using existing MessageId
    Then The Reaction response should contain "id"
    And The Reaction response status code should be 201
# 3) POST BaseUserMessage (should run after Message POST and use stored id)

  @BaseUserMessage
  Scenario: Creating BaseUserMessage
    Given I have a BaseUserMessage
    When Create a BaseUserMessage using existing MessageId
    Then The BaseUserMessage response should contain "id"
    And The BaseUserMessage response status code should be 201

  # 3) GET all ResourceGraphs
  @Resource_Graph
  Scenario: Fetching all Resources
    When Fetch All ResourceID Details
    Then The Resource response status code should be 200
    And The Resource response should contain "id"

  # 4) GET ResourceGraph by id
  @Resource_Graph
  Scenario: Get ResourceID Details with ID
    When Fetch resourceID Details with ID
    Then The Resource response status code should be 200
# 5) PUT ResourceGraph update

  @Resource_Graph
  Scenario: Updating PUT ResourceID Details with ID
    When Update ResourceID
    Then The Resource response status code should be 200

  # 6) PATCH ResourceGraph update
  @Resource_Graph
  Scenario: Updating Patch ResourceID Request
    When Update Patch ResourceID
    Then The Resource response status code should be 200

  # 7) GET all ResourceRegistries
  @Resource_Registries
  Scenario: Fetching all ResourceRegistries
    When Fetch All ResourceRegistriesID Details
    Then The ResourceRegistries response status code should be 200
    And The ResourceRegistries response should contain "id"

  # 8) GET ResourceRegistries by id
  # (uses registry id created earlier — ensure your ResourceRegistriesSteps stores it or adapt)
  @Resource_Registries
  Scenario: Get ResourceRegistriesID Details with ID
    When Fetch ResourceRegistriesID Details with ID
    Then The ResourceRegistries response status code should be 200

  # 9) PUT ResourceRegistries update
  @Resource_Registries
  Scenario: Updating PUT ResourceRegistriry Details with ID
    When Update ResourceRegistryID
    Then The ResourceRegistries response status code should be 200

  # 10) PATCH ResourceRegistries update
  @Resource_Registries
  Scenario: Updating Patch ResourceRegistries request
    When Update Patch ResourceRegistriesID
    Then The ResourceRegistries response status code should be 200

  @Message
  Scenario: Fetching all Message
    When Fetch All Message Details
    Then The Message response should contain "id"
    And The Message response status code should be 200

  @Message
  Scenario: Get Message Details with ID
    When Fetch Message Details with ID
    Then The Message response status code should be 200

  @Message
  Scenario: Updating PUT Message
    When Update Message
    Then The Message response status code should be 200
    And The Message response should contain "id"

  @Message
  Scenario: Updating Patch Message request
    When Update Patch Message
    Then The Message response status code should be 200

  @Attachment
  Scenario: Fetching all Attchments
    When Fetch All Attachment Details
    Then The Attachment response should contain "id"
    And The Attachment response status code should be 200

  @Attachment
  Scenario: Get Attachment details with ID
    When Fetch Attachment Details with ID
    Then The Attachment response status code should be 200

  @Attachment
  Scenario: Updating Put Attachment
    When Update Attachment
    Then The Attachment response status code should be 200
    And The Attachment response should contain "id"

  @Attachment
  Scenario: Updating Patch Attachment
    When Update Patch Attachment
    Then The Attachment response status code should be 200

  @CustomElement
  Scenario: Fetching all CustomElement
    When Fetch All CustomElement Details
    Then The CustomElement response should contain "id"
    And The CustomElement response status code should be 200

  @CustomElement
  Scenario: Get CustomElement details with ID
    When Fetch CustomElement Details with ID
    And The CustomElement response status code should be 200

  @CustomElement
  Scenario: Updating Put CustomElement
    When Update CustomElement
    And The CustomElement response status code should be 200
    And The CustomElement response should contain "id"

  @CustomElement
  Scenario: Updating Patch CustomElement
    When Update Patch CustomElement
    And The CustomElement response status code should be 200

  @Reaction
  Scenario: Fetching All Reaction
    When Fetch All Reaction Details
    Then The Reaction response should contain "id"
    And The Reaction response status code should be 200

  @Reaction
  Scenario: Get Reaction details with ID
    When Fetch Reaction Details with ID
    Then The Reaction response status code should be 200

  @Reaction
  Scenario: Updating Put Reaction
    When Update Reaction
    Then The Reaction response should contain "id"
    And The Reaction response status code should be 200

  @Reaction
  Scenario: Updating Patch Reaction
    When Update Patch Reaction
    Then The Reaction response status code should be 200

  @Reaction
  Scenario: Deleting Reaction
    When Delete Reaction with ID
    Then The Reaction response status code should be 204

  @BaseUserMessage
  Scenario: Fetching All BaseUserMessage
    When Fetch All BaseUserMessage Details
    Then The BaseUserMessage response should contain "id"
    And The BaseUserMessage response status code should be 200

  @BaseUserMessage
  Scenario: Get BaseUserMessage details with ID
    When Fetch BaseUserMessage Details with ID
    Then The BaseUserMessage response status code should be 200

  @BaseUserMessage
  Scenario: Updating Put BaseUserMessage
    When Update BaseUserMessage
    Then The BaseUserMessage response should contain "id"
    And The BaseUserMessage response status code should be 200

  @BaseUserMessage
  Scenario: Updating Patch BaseUserMessage
    When Update Patch BaseUserMessage
    Then The BaseUserMessage response status code should be 200

  @BaseUserMessage
  Scenario: Deleting BaseUserMessage
    When Delete BaseUserMessage with ID
    Then The BaseUserMessage response status code should be 204

  @Message
  Scenario: Deleting Message
    When Delete Message with ID
    Then The Message response status code should be 204

  @Attachment
  Scenario: Deleting Attachment
    When Delete Attachment with ID
    Then The Attachment response status code should be 204

  @CustomElement
  Scenario: Deleting CustomElement
    When Delete CustomElement with ID
    And The CustomElement response status code should be 204

  @Reaction
  Scenario: Deleting Reaction
    When Delete Reaction with ID
    Then The Reaction response status code should be 204

  @BaseUserMessage
  Scenario: Deleting BaseUserMessage
    When Delete BaseUserMessage with ID
    Then The BaseUserMessage response status code should be 204

  # 11) DELETE ResourceRegistries by id
  @Resource_Registries
  Scenario: Deleting ResourceRegistries Details
    When Delete ResourceRegistriesID with ID
    Then The ResourceRegistries response status code should be 204

  # 12) DELETE ResourceGraph by id
  @Resource_Graph
  Scenario: Deleting ResourceID Detail
    When Delete ResourceID with ID
    Then The Resource response status code should be 204
