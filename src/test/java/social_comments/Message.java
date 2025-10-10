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

public class Message {
	private Response response;
    private String requestBody;
    public static int resourceId;
    public static int MessageId;
    private static String baseUrl = ConfigReader.getProperty("baseUrl");
    private static String tenantId = ConfigReader.getProperty("tenantId");
    private static String author = ConfigReader.getProperty("author");
    private static String Message_endpoint = ConfigReader.getProperty("message_endpoint");

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
    private void captureDbSnapshot(int MessageId, String action) {
		ExtentTest current = ExtentCucumberListener.getCurrentScenario();
		if (current == null)
			return;

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://10.10.2.45:3306/social_comments_model_dhin",
				"root", "dhi123");
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt
						.executeQuery("SELECT * FROM `social_comments_model_dhin`.`Message` LIMIT 1000")) {

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

				if (currentId == MessageId) {
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
    @Given("I have a Message")
	public void i_have_a_message() {
		requestBody = DataCreate.generateMessageJson(resourceId);
//		System.out.println("Resource JSON:\n" + requestBody);
	}
    @When("Create a Message using existing ResourceId")
    public void create_a_message_using_existing_ResourceId() {
        // obtain resource id from ResourceGraph (assumes ResourceGraph.getResourceId() returns int)
        try {
            resourceId = ResourceGraph.getResourceId();
        } catch (NoSuchMethodError | Exception e) {
            throw new IllegalStateException(
                    "Unable to get ResourceId from ResourceGraph. Ensure ResourceGraph has public static int getResourceId()", e);
        }

        if (resourceId == 0) {
            throw new IllegalStateException("No ResourceId found. Ensure ResourceGraph POST ran successfully and set the id.");
        }

        requestBody = DataCreate.generateMessageJson(resourceId);
        System.out.println(requestBody);

        response = given().header("Content-Type", "application/json")
                .header("X-TENANT-ID", tenantId)
                .header("Author", author)
                .body(requestBody)
                .when()
                .post(baseUrl + Message_endpoint)
                .then()
                .extract()
                .response();

        // log response to console
        System.out.println("POST Message Status: " + response.getStatusCode());
        System.out.println("POST Message Body: " + response.getBody().asString());
        MessageId = response.jsonPath().getInt("id");
		System.out.println("Stored MessageID: " + MessageId);
        // log to extent
        logToExtent("Create a Message", requestBody, response);
        captureDbSnapshot(MessageId, "Create a Message");
    }
    public static int getMessageId() {
		return MessageId; // or ResourceId if int
	}

	public static void setResourceId(int id) {
		MessageId = id;
	}
    @When("Fetch All Message Details")
	public void Fetch_All_message_Details() {
		response = given().header("Content-Type", "application/json").header("X-TENANT-ID", tenantId)
				.header("Author", author).when().get(baseUrl + Message_endpoint).then().extract().response();

		System.out.println("Response Body:\n" + response.getBody().asString());
		System.out.println("Status Code: " + response.getStatusCode());

		logToExtent("Fetch All Message Response", requestBody, response);
//        AllureLogger.logResponse("Fetch All Resources Response", response);
	}
    
    @When("Fetch Message Details with ID")
	public void Fetch_message_Details_with_ID() {
		response = given().header("Content-Type", "application/json").header("X-TENANT-ID", tenantId)
				.header("Author", author).pathParam("id", MessageId).when()
				.get(baseUrl + Message_endpoint + "/{id}").then().extract().response();

		System.out.println("Response Body:\n" + response.getBody().asString());
		System.out.println("Status Code: " + response.getStatusCode());
//        AllureLogger.logResponse("Create Resource Response", response);
		logToExtent("Fetch Message by ID", null, response);
	}
    @When("Update Message")
	public void Update_message() {
//		if (MessageId == 0)
//			throw new IllegalStateException("No ResourceID available. Run POST first.");

		String newJson = DataCreate.generateMessageJson(resourceId);
		String updatePayload = newJson.substring(0, newJson.length() - 1)
				+ ", \"attachments\": []," +
			        " \"baseUserMessages\": []," +
			        " \"customElements\": []," +
			        " \"customTags\": []," +
			        " \"customTemplates\": []," +
			        " \"userRecipients\": [] }";

		response = given().header("Content-Type", "application/json").header("X-TENANT-ID", tenantId)
				.header("Author", author).pathParam("id", MessageId).body(updatePayload).when()
				.put(baseUrl + Message_endpoint + "/{id}").then().extract().response();

		System.out.println("PUT Response Body:\n" + response.getBody().asString());
		System.out.println("Status Code: " + response.getStatusCode());
//        AllureLogger.logResponse("Create Resource Response", response);
		logToExtent("Update Message (PUT)", updatePayload, response);
        captureDbSnapshot(MessageId, "Update Message (PUT)");

	}
    @When("Update Patch Message")
	public void Update_Patch_Message() {
//		if (MessageId == 0)
//			throw new IllegalStateException("No BookId available. Run POST first.");

		String newJson = DataCreate.generateMessageJson(resourceId);
//        String title = newJson.split("\"title\"\\s*:\\s*\"")[1].split("\"")[0];
//        String patchPayload = "{ \"title\": \"" + title + "\" }";

		response = given().header("Content-Type", "application/json").header("X-TENANT-ID", tenantId)
				.header("Author", author).pathParam("id", MessageId).body(newJson).when()
				.patch(baseUrl + Message_endpoint + "/{id}").then().extract().response();

		System.out.println("PATCH Request Body:\n" + newJson);
		System.out.println("PATCH Response Body:\n" + response.getBody().asString());
		System.out.println("Status Code: " + response.getStatusCode());
//        AllureLogger.logResponse("Create Resource Response", response);
		logToExtent("Update Patch Message (PATCH)", newJson, response);
        captureDbSnapshot(MessageId, "Update Message (PATCH)");

	}
    @When("Delete Message with ID")
  	public void delete_Message_with_ID() {
  		response = given().header("Content-Type", "application/json").header("X-TENANT-ID", tenantId)
  				.header("Author", author).pathParam("id", MessageId).when()
  				.delete(baseUrl + Message_endpoint + "/{id}").then().extract().response();

  		System.out.println("Delete Response Body:\n" + response.getBody().asString());
  		System.out.println("Delete Status Code: " + response.getStatusCode());

  		logToExtent("Delete Message", null, response);
          captureDbSnapshot(MessageId, "Delete Message");

  	}
    
    
    @Then("The Message response status code should be {int}")
    public void the_message_response_status_code_should_be(Integer statusCode) {
        if (response == null) {
            throw new AssertionError("No response available to assert against. Make sure the POST ran.");
        }
        assertEquals(statusCode.intValue(), response.getStatusCode());
    }

    @Then("The Message response should contain {string}")
    public void the_message_response_should_contain(String key) {
        if (response == null) {
            throw new AssertionError("No response available to assert against. Make sure the POST ran.");
        }
        assertTrue("Response does not contain key: " + key, response.getBody().asString().contains(key));
    }

}
