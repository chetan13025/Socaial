@Negative
Feature: Resource Graph testing 
Scenario: Creating resource with ID
Given I have a ResourceID with ID
When Create New ResourceID with ID
Then The Resource response status code should be 201
And The Resource response should contain "id" 
