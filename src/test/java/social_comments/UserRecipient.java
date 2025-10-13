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

public class UserRecipient {
	private Response response;
	private String requestBody;
	public static int UserRecipientId;
	public static int MessageId;
	private static String baseUrl = ConfigReader.getProperty("baseUrl");
	private static String tenantId = ConfigReader.getProperty("tenantId");
	private static String author = ConfigReader.getProperty("author");
	private static String UserRecipient_endpoint = ConfigReader.getProperty("userrecipients_endpoint");
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

	private void captureDbSnapshot(int UserRecipientId, String action) {
		ExtentTest current = ExtentCucumberListener.getCurrentScenario();
		if (current == null)
			return;

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://10.10.2.45:3306",
				"root", "dhi123");
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt
						.executeQuery("SELECT * FROM `social_comments_model_dhin`.`UserRecipient` LIMIT 1000")) {

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

				if (currentId == UserRecipientId) {
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
	@Given("I have a UserRecipient")
	public void i_have_a_userrecipient() {
		requestBody = DataCreate.generateUserRecipientJson(MessageId);
//		System.out.println("Resource JSON:\n" + requestBody);
	}
	@When("Create a UserRecipient using existing MessageId")
	public void create_a_userrecipient_using_existing_MessageId() {
		// obtain resource id from ResourceGraph (assumes ResourceGraph.getResourceId()
		// returns int)
		try {
			MessageId = Message.getMessageId();
		} catch (NoSuchMethodError | Exception e) {
			throw new IllegalStateException(
					"Unable to get MessageId from Message. Ensure Message has public static int getMessageId()", e);
		}

		if (MessageId == 0) {
			throw new IllegalStateException("No MessageId found. Ensure Message POST ran successfully and set the id.");
		}

		requestBody = DataCreate.generateUserRecipientJson(MessageId);
		System.out.println(requestBody);

		response = given().header("Content-Type", "application/json").header("X-TENANT-ID", tenantId)
				.header("Author", author).body(requestBody).when().post(baseUrl + UserRecipient_endpoint).then().extract()
				.response();

		// log response to console
		System.out.println("POST UserRecipient Status: " + response.getStatusCode());
		System.out.println("POST UserRecipient Body: " + response.getBody().asString());
		UserRecipientId = response.jsonPath().getInt("id");
		System.out.println("Stored UserRecipientID: " + UserRecipientId);
		// log to extent
		logToExtent("Create a UserRecipient", requestBody, response);
		captureDbSnapshot(UserRecipientId, "Create a UserRecipient");
	}
	
	@When("Fetch All UserRecipient Details")
	public void Fetch_All_userrecipirnt_Details() {
		response = given().header("Content-Type", "application/json").header("X-TENANT-ID", tenantId)
				.header("Author", author).when().get(baseUrl + UserRecipient_endpoint).then().extract().response();

		System.out.println("Response Body:\n" + response.getBody().asString());
		System.out.println("Status Code: " + response.getStatusCode());

		logToExtent("Fetch All UserRecipient Response", requestBody, response);
//          AllureLogger.logResponse("Fetch All Resources Response", response);
	}

	@When("Fetch UserRecipient Details with ID")
	public void Fetch_userrecipient_Details_with_ID() {
		response = given().header("Content-Type", "application/json").header("X-TENANT-ID", tenantId)
				.header("Author", author).pathParam("id", UserRecipientId).when().get(baseUrl + UserRecipient_endpoint + "/{id}")
				.then().extract().response();

		System.out.println("Response Body:\n" + response.getBody().asString());
		System.out.println("Status Code: " + response.getStatusCode());
//          AllureLogger.logResponse("Create Resource Response", response);
		logToExtent("Fetch UserRecipient by ID", null, response);
	}

	@When("Update UserRecipient")
	public void Update_userrecipient() {
//		if (MessageId == 0)
//			throw new IllegalStateException("No ResourceID available. Run POST first.");

		String newJson = DataCreate.generateUserRecipientJson(MessageId);

		response = given().header("Content-Type", "application/json").header("X-TENANT-ID", tenantId)
				.header("Author", author).pathParam("id", UserRecipientId).body(newJson).when()
				.put(baseUrl + UserRecipient_endpoint + "/{id}").then().extract().response();

		System.out.println("PUT Response Body:\n" + response.getBody().asString());
		System.out.println("Status Code: " + response.getStatusCode());
//        AllureLogger.logResponse("Create Resource Response", response);
		logToExtent("Update UserRecipient (PUT)", newJson, response);
		captureDbSnapshot(UserRecipientId, "Update UserRecipient (PUT)");

	}

	@When("Update Patch UserRecipient")
	public void Update_Patch_UserRecipient() {
//		if (MessageId == 0)
//			throw new IllegalStateException("No BookId available. Run POST first.");

		String newJson = DataCreate.generateUserRecipientJson(MessageId);
//        String title = newJson.split("\"title\"\\s*:\\s*\"")[1].split("\"")[0];
//        String patchPayload = "{ \"title\": \"" + title + "\" }";

		response = given().header("Content-Type", "application/json").header("X-TENANT-ID", tenantId)
				.header("Author", author).pathParam("id", UserRecipientId).body(newJson).when()
				.patch(baseUrl + UserRecipient_endpoint + "/{id}").then().extract().response();

		System.out.println("PATCH Request Body:\n" + newJson);
		System.out.println("PATCH Response Body:\n" + response.getBody().asString());
		System.out.println("Status Code: " + response.getStatusCode());
//        AllureLogger.logResponse("Create Resource Response", response);
		logToExtent("Update Patch UserRecipient (PATCH)", newJson, response);
		captureDbSnapshot(UserRecipientId, "Update UserRecipient (PATCH)");

	}

	@When("Delete UserRecipient with ID")
	public void delete_userrecipient_with_ID() {
		response = given().header("Content-Type", "application/json").header("X-TENANT-ID", tenantId)
				.header("Author", author).pathParam("id", UserRecipientId).when()
				.delete(baseUrl + UserRecipient_endpoint + "/{id}").then().extract().response();

		System.out.println("Delete Response Body:\n" + response.getBody().asString());
		System.out.println("Delete Status Code: " + response.getStatusCode());

		logToExtent("Delete UserRecipient", null, response);
		captureDbSnapshot(UserRecipientId, "Delete UserRecipient");

	}
	
	@Then("The UserRecipient response status code should be {int}")
	public void the_userrecipient_response_status_code_should_be(Integer statusCode) {
		if (response == null) {
			throw new AssertionError("No response available to assert against. Make sure the POST ran.");
		}
		assertEquals(statusCode.intValue(), response.getStatusCode());
	}

	@Then("The UserRecipient response should contain {string}")
	public void the_userrecipient_response_should_contain(String key) {
		if (response == null) {
			throw new AssertionError("No response available to assert against. Make sure the POST ran.");
		}
		assertTrue("Response does not contain key: " + key, response.getBody().asString().contains(key));
	}

}
