package social_comments;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import DataCreate.DataCreate;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import reports.ExtentCucumberListener;
import utils.ConfigReader;

public class Tenant {
	private Response response;
	private String requestBody;
	public static int TenantId;
	private static String baseUrl = ConfigReader.getProperty("baseUrl");
	private static String tenantId = ConfigReader.getProperty("tenantId");
	private static String author = ConfigReader.getProperty("author");
	private static String Tenant_endpoint = ConfigReader.getProperty("tenant_endpoint");

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

	private void captureDbSnapshot(int TenantId, String action) {
		ExtentTest current = ExtentCucumberListener.getCurrentScenario();
		if (current == null)
			return;

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://10.10.2.45:3306", "root", "dhi123");
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM `social_comments_model_dhin`.`Tenant` LIMIT 1000")) {

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

				if (currentId == TenantId) {
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

	@Given("I have a Tenant")
	public void i_have_a_tenant() {
		requestBody = DataCreate.generateTenantJson();
//		System.out.println("Resource JSON:\n" + requestBody);
	}

	@When("Create a Tenant")
	public void create_a_tenant() {

		requestBody = DataCreate.generateTenantJson();
		System.out.println(requestBody);

		response = given().header("Content-Type", "application/json").header("X-TENANT-ID", tenantId)
				.header("Author", author).body(requestBody).when().post(baseUrl + Tenant_endpoint).then().extract()
				.response();

		// log response to console
		System.out.println("POST Tenant Status: " + response.getStatusCode());
		System.out.println("POST Tenant Body: " + response.getBody().asString());
		TenantId = response.jsonPath().getInt("id");
		System.out.println("Stored TenantID: " + TenantId);
		// log to extent
		logToExtent("Create a Tenant", requestBody, response);
		captureDbSnapshot(TenantId, "Create a Tenant");
	}

	@When("Fetch All Tenant Details")
	public void Fetch_All_tenant_Details() {
		response = given().header("Content-Type", "application/json").header("X-TENANT-ID", tenantId)
				.header("Author", author).when().get(baseUrl + Tenant_endpoint).then().extract().response();

		System.out.println("Response Body:\n" + response.getBody().asString());
		System.out.println("Status Code: " + response.getStatusCode());

		logToExtent("Fetch All Tenant Response", requestBody, response);
//          AllureLogger.logResponse("Fetch All Resources Response", response);
	}

	@When("Fetch Tenant Details with ID")
	public void Fetch_tenant_Details_with_ID() {
		response = given().header("Content-Type", "application/json").header("X-TENANT-ID", tenantId)
				.header("Author", author).pathParam("id", TenantId).when().get(baseUrl + Tenant_endpoint + "/{id}")
				.then().extract().response();

		System.out.println("Response Body:\n" + response.getBody().asString());
		System.out.println("Status Code: " + response.getStatusCode());
//          AllureLogger.logResponse("Create Resource Response", response);
		logToExtent("Fetch Tenant by ID", null, response);
	}

	@When("Update Tenant")
	public void Update_tenant() {
//		if (MessageId == 0)
//			throw new IllegalStateException("No ResourceID available. Run POST first.");

		String newJson = DataCreate.generateTenantJson();

		response = given().header("Content-Type", "application/json").header("X-TENANT-ID", tenantId)
				.header("Author", author).pathParam("id", TenantId).body(newJson).when()
				.put(baseUrl + Tenant_endpoint + "/{id}").then().extract().response();

		System.out.println("PUT Response Body:\n" + response.getBody().asString());
		System.out.println("Status Code: " + response.getStatusCode());
//        AllureLogger.logResponse("Create Resource Response", response);
		logToExtent("Update Tenant (PUT)", newJson, response);
		captureDbSnapshot(TenantId, "Update Tenant (PUT)");

	}

	@When("Update Patch Tenant")
	public void Update_Patch_Tenant() {
//		if (MessageId == 0)
//			throw new IllegalStateException("No BookId available. Run POST first.");

		String newJson = DataCreate.generateTenantJson();
//        String title = newJson.split("\"title\"\\s*:\\s*\"")[1].split("\"")[0];
//        String patchPayload = "{ \"title\": \"" + title + "\" }";

		response = given().header("Content-Type", "application/json").header("X-TENANT-ID", tenantId)
				.header("Author", author).pathParam("id", TenantId).body(newJson).when()
				.patch(baseUrl + Tenant_endpoint + "/{id}").then().extract().response();

		System.out.println("PATCH Request Body:\n" + newJson);
		System.out.println("PATCH Response Body:\n" + response.getBody().asString());
		System.out.println("Status Code: " + response.getStatusCode());
//        AllureLogger.logResponse("Create Resource Response", response);
		logToExtent("Update Patch Tenant (PATCH)", newJson, response);
		captureDbSnapshot(TenantId, "Update Tenant (PATCH)");

	}

	@When("Delete Tenant with ID")
	public void delete_tenant_with_ID() {
		response = given().header("Content-Type", "application/json").header("X-TENANT-ID", tenantId)
				.header("Author", author).pathParam("id", TenantId).when().delete(baseUrl + Tenant_endpoint + "/{id}")
				.then().extract().response();

		System.out.println("Delete Response Body:\n" + response.getBody().asString());
		System.out.println("Delete Status Code: " + response.getStatusCode());

		logToExtent("Delete Tenant", null, response);
		captureDbSnapshot(TenantId, "Delete Tenant");

	}

	@Then("The Tenant response status code should be {int}")
	public void the_userrecipient_response_status_code_should_be(Integer statusCode) {
		if (response == null) {
			throw new AssertionError("No response available to assert against. Make sure the POST ran.");
		}
		assertEquals(statusCode.intValue(), response.getStatusCode());
	}

	@Then("The Tenant response should contain {string}")
	public void the_userrecipient_response_should_contain(String key) {
		if (response == null) {
			throw new AssertionError("No response available to assert against. Make sure the POST ran.");
		}
		assertTrue("Response does not contain key: " + key, response.getBody().asString().contains(key));
	}

}
