package social_comments;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import DataCreate.DataCreate;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.restassured.response.Response;
import reports.ExtentCucumberListener;

import utils.ConfigReader;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class ResourceGraph {
	public static int ResourceId;
	private Response response;
	private String requestBody;
	private static String baseUrl = ConfigReader.getProperty("baseUrl");
	private static String tenantId = ConfigReader.getProperty("tenantId");
	private static String author = ConfigReader.getProperty("author");
	private static String Resource_endpoint = ConfigReader.getProperty("resource_endpoint");

	private void logToExtent(String action, String reqBody, Response resp) {
		ExtentTest current = ExtentCucumberListener.getCurrentScenario();
		if (current == null)
			return;

		current.info("Action: " + action);

		if (reqBody != null) {
			current.info("Request Payload:");
			current.info(MarkupHelper.createCodeBlock(reqBody, CodeLanguage.JSON));
		}

		current.info("Response Status Code: " + (resp == null ? "null" : resp.getStatusCode()));
		if (resp != null) {
			current.info("Response Body:");
			current.info(MarkupHelper.createCodeBlock(resp.getBody().asString(), CodeLanguage.JSON));
		}
	}

	private void captureDbSnapshot(int ResourceId, String action) {
		ExtentTest current = ExtentCucumberListener.getCurrentScenario();
		if (current == null)
			return;

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://10.10.2.45:3306",
				"root", "dhi123");
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt
						.executeQuery("SELECT * FROM `social_comments_model_dhin`.`ResourceGraph` LIMIT 1000")) {

			ResultSetMetaData meta = rs.getMetaData();
			int colCount = meta.getColumnCount();

			StringBuilder html = new StringBuilder("<table border='1' style='border-collapse:collapse;'>");

			// Table header
			html.append("<tr style='background-color:#f2f2f2;'>");
			for (int i = 1; i <= colCount; i++) {
				html.append("<th>").append(meta.getColumnName(i)).append("</th>");
			}
			html.append("</tr>");

			// Table rows
			while (rs.next()) {
				int currentId;
				try {
					currentId = rs.getInt("id"); // ✅ use Id if exists
				} catch (Exception e) {
					currentId = rs.getInt("id"); // fallback
				}

				if (currentId == ResourceId) {
					html.append("<tr style='background-color:#90EE90; font-weight:bold;'>"); // highlight
				} else {
					html.append("<tr>");
				}

				for (int i = 1; i <= colCount; i++) {
					html.append("<td>").append(rs.getString(i)).append("</td>");
				}
				html.append("</tr>");
			}

			html.append("</table>");

			// ✅ Attach HTML table into Extent report
			current.info("Database Snapshot after: " + action);
			current.info(html.toString());

		} catch (Exception e) {
			current.warning("⚠️ Failed to capture DB snapshot: " + e.getMessage());
		}
	}

	@Given("I have a ResourceID")
	public void i_have_a_resourceid() {
		requestBody = DataCreate.generateResourceJson();
		System.out.println("Resource JSON:\n" + requestBody);
	}

	@When("Create New ResourceID")
	public void Create_new_resourceid() {
		response = given().header("Content-Type", "application/json").header("X-TENANT-ID", tenantId)
				.header("Author", author).body(requestBody).when().post(baseUrl + Resource_endpoint).then().extract()
				.response();

		System.out.println("Response Body:\n" + response.getBody().asString());
		System.out.println("Status Code: " + response.getStatusCode());
		ResourceId = response.jsonPath().getInt("id");
		System.out.println("Stored ResourceID: " + ResourceId);
		logToExtent("Create Resource Response", requestBody, response);
		captureDbSnapshot(ResourceId, "Create New ResourceID");
	}

	public static int getResourceId() {
		return ResourceId; // or ResourceId if int
	}

	public static void setResourceId(int id) {
		ResourceId = id;
	}

	@When("Fetch All ResourceID Details")
	public void Fetch_All_resourceid_Details() {
		response = given().header("Content-Type", "application/json").header("X-TENANT-ID", tenantId)
				.header("Author", author).when().get(baseUrl + Resource_endpoint).then().extract().response();

		System.out.println("Response Body:\n" + response.getBody().asString());
		System.out.println("Status Code: " + response.getStatusCode());

		logToExtent("Fetch All Resources Response", requestBody, response);
//        AllureLogger.logResponse("Fetch All Resources Response", response);
	}

	@When("Fetch resourceID Details with ID")
	public void Fetch_resourceID_Details_with_ID() {
		response = given().header("Content-Type", "application/json").header("X-TENANT-ID", tenantId)
				.header("Author", author).pathParam("id", ResourceId).when().get(baseUrl + Resource_endpoint + "/{id}")
				.then().extract().response();

		System.out.println("Response Body:\n" + response.getBody().asString());
		System.out.println("Status Code: " + response.getStatusCode());
//        AllureLogger.logResponse("Create Resource Response", response);
		logToExtent("Fetch Resource by ID", null, response);
	}

	@When("Update ResourceID")
	public void Update_ResourceID() {
		if (ResourceId == 0)
			throw new IllegalStateException("No ResourceID available. Run POST first.");

		String newJson = DataCreate.generateResourceJson();
		 int registryId = ResourceRegistries.ResourceRegistryId;
		String updatePayload = newJson.substring(0, newJson.length() - 1) + ", \"id\": " + ResourceId
				+ ", \"resourceRegistries\": [ ] }";
		String updatePayload1 = newJson.substring(0, newJson.length() - 1)
	            + ", \"resourceRegistries\": [ { \"id\": " + registryId + " } ] }";
		
		

		response = given().header("Content-Type", "application/json").header("X-TENANT-ID", tenantId)
				.header("Author", author).pathParam("id", ResourceId).body(updatePayload1).when()
				.put(baseUrl + Resource_endpoint + "/{id}").then().extract().response();

		System.out.println("PUT Response Body:\n" + response.getBody().asString());
		System.out.println("Status Code: " + response.getStatusCode());
//        AllureLogger.logResponse("Create Resource Response", response);
		logToExtent("Update ResourceID (PUT)", updatePayload1, response);
        captureDbSnapshot(ResourceId, "Update ResourceID (PUT)");

	}

	@When("Update Patch ResourceID")
	public void Update_Patch_ResourceID() {
		if (ResourceId == 0)
			throw new IllegalStateException("No BookId available. Run POST first.");

		String newJson = DataCreate.generateResourceJson();
//        String title = newJson.split("\"title\"\\s*:\\s*\"")[1].split("\"")[0];
//        String patchPayload = "{ \"title\": \"" + title + "\" }";

		response = given().header("Content-Type", "application/json").header("X-TENANT-ID", tenantId)
				.header("Author", author).pathParam("id", ResourceId).body(newJson).when()
				.patch(baseUrl + Resource_endpoint + "/{id}").then().extract().response();

		System.out.println("PATCH Request Body:\n" + newJson);
		System.out.println("PATCH Response Body:\n" + response.getBody().asString());
		System.out.println("Status Code: " + response.getStatusCode());
//        AllureLogger.logResponse("Create Resource Response", response);
		logToExtent("Update ResourceID (PATCH)", newJson, response);
        captureDbSnapshot(ResourceId, "Update ResourceID (PATCH)");

	}

	@When("Delete ResourceID with ID")
	public void delete_ResourceID_with_ID() {
		response = given().header("Content-Type", "application/json").header("X-TENANT-ID", tenantId)
				.header("Author", author).pathParam("id", ResourceId).when()
				.delete(baseUrl + Resource_endpoint + "/{id}").then().extract().response();

		System.out.println("Delete Response Body:\n" + response.getBody().asString());
		System.out.println("Delete Status Code: " + response.getStatusCode());

		logToExtent("Delete ResourceID", null, response);
        captureDbSnapshot(ResourceId, "Delete ResourceID");

	}

	@Then("The Resource response status code should be {int}")
	public void the_resource_response_status_code_should_be(Integer statusCode) {
		assertEquals(statusCode.intValue(), response.getStatusCode());

	}

	@Then("The Resource response should contain {string}")
	public void the_resource_response_should_contain(String key) {
		assertTrue("Response does not contain key: " + key, response.getBody().asString().contains(key));

	}
}
