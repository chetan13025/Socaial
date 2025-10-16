package DataCreate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class DataCreate {
	static String createdDate;
	static String updatedDate;

	private static final Random RANDOM = new Random();
//	private static final String[] PREFIXES = { "USR", "EMP", "CUST", "RES" };
//	private static final String[] SUFFIXES_WORDS = { "Alpha", "Beta", "Gamma", "Delta", "Omega" };
	// ResourceGraph
	private static final String[] FIRST_NAMES = { "Meera", "Arjun", "Leena", "Rohan", "Priya", "Vikram", "Karan",
			"Rahul", "Kevin","Chetan","Santosh","Sada","Vinod","Prajwal", "Adarsh","Tushar","Bhanupraksh","Naveen","Prashanth","Dilip","Abhishek","Akshay","Manjunath","Amruth","Aditi","Nagaraj" };

	private static final String[] LAST_NAMES = { "Nair", "Kapoor", "Thomas", "Das", "Menon", "Sharma", "Iyer",
			"Singh","Patil","Kumar","Biradar","Mirji","Nag","Dodda" };

	public static String generateAuthor() {
		return FIRST_NAMES[RANDOM.nextInt(FIRST_NAMES.length)] + " " + LAST_NAMES[RANDOM.nextInt(LAST_NAMES.length)];
	}

	public static String generateResourceId() {
//		String prefix = PREFIXES[RANDOM.nextInt(PREFIXES.length)];
		String name = generateAuthor(); // meaningful name
//		String suffix = SUFFIXES_WORDS[RANDOM.nextInt(SUFFIXES_WORDS.length)];
		return name;
	}

	public static String generateResourceJson() {
		return "{\n" + "  \"resourceId\": \"" + generateResourceId() + "\"\n" + "}";
	}
	
	public static String generateResourceNJson(String ResourceName) {
		 int id = new Random().nextInt(100000);
		return "{\n" +
		          
		"  \"resourceId\": \""+ResourceName  +"\"\n" + "}";
	}

// ResourceRegistries
	public static String generateResourceName() {
		String first = FIRST_NAMES[RANDOM.nextInt(FIRST_NAMES.length)];
		String last = LAST_NAMES[RANDOM.nextInt(LAST_NAMES.length)];
		return first + " " + last;
	}

	public static String generateCreatedDate() {
		return LocalDate.now().toString();
	}

	public static String generateResourceRegistriesJson(int resourceId) {
		createdDate = generateCreatedDate();
		String resourceName = generateResourceName();

		return "{\n" + "  \"resourceGraph\": {\n" + "    \"id\": " + resourceId + "\n" + "  },\n"
				+ "  \"createdDate\": \""+ "\",\n" + "  \"resourceName\": \"" + resourceName + "\",\n"
				+ "  \"label\": \"" + resourceName + "\",\n" + "  \"resourceValue\": \"" + resourceName + "\"\n" + "}";
	}

	// Message
	private static final String[] PRIVACY_OPTIONS = { "PUBLIC", "USER", "ROLE", "ONLY_ME" };
	private static final String[] MESSAGE_TYPES = { "ACTION_TICKLER", "DEFAULT", "DOODLE_TICKLER", "EDIT_TICKLER",
			"REPLY_TICKLER", "SOURCE_CODE", "TOODLE_LABELS", "TOODLE_NOFIES", "TOODLE_THEMES" };
	private static final String[] DECORATOR_LAYERS = { "INFO", "WARNING", "SUCCESS", "ERROR" };
	private static final String[] FLAG_STATUS = { "LOW", "MEDIUM", "HIGH" };
	private static final String[] ROLE_NAMES = { "HR Executive", "System Admin", "Finance Manager", "Operations Lead",
			"Team Coordinator", "QA Manager", "QA Engineer" };
	private static final String[] CONTENT_MESSAGES = {
			"Quarterly performance appraisal results are available on the HR dashboard.",
			"System maintenance will occur tonight between 2:00 AM and 3:00 AM.",
			"The finance department has updated the reimbursement policy for Q4.",
			"New security guidelines have been issued — please review them on the company portal.",
			"A reminder: team feedback sessions will begin next Monday.",
			"Project alpha milestones have been successfully completed this week.",
			"New login security features are now active across all systems.",
			"Employee engagement survey closes this Friday — don’t forget to participate." };

	public static String getRandomElement(String[] arr) {
		return arr[RANDOM.nextInt(arr.length)];
	}

	public static boolean randomBoolean() {
		return RANDOM.nextBoolean();
	}

	public static String randomDate() {
		// Create random date near today (within ±15 days)
		int offset = RANDOM.nextInt(30) - 15;
		LocalDate date = LocalDate.now().plusDays(offset);
		return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
	}

	public static String generateRandomUserId() {
		return "USR-" + (10000 + RANDOM.nextInt(90000));
	}

	public static String generateCaseId() {
		return "CASE-" + (2000 + RANDOM.nextInt(8000)) + "-Q" + (1 + RANDOM.nextInt(4));
	}

	public static String generateExternalId() {
		return "EXT-" + (1000 + RANDOM.nextInt(9999));
	}

	private static int generateRandomId() {
		return 1000 + RANDOM.nextInt(200000);
	}

	public static String generateMessageJson(int resourceId) {
		createdDate = randomDate();
		updatedDate = randomDate();

		String messagePrivacy = getRandomElement(PRIVACY_OPTIONS);
		String messageType = getRandomElement(MESSAGE_TYPES);
		String decoratorLayer = getRandomElement(DECORATOR_LAYERS);
		String flag = getRandomElement(FLAG_STATUS);
		String role = getRandomElement(ROLE_NAMES);
		String content = getRandomElement(CONTENT_MESSAGES);
		String userId = generateRandomUserId();
		String caseId = generateCaseId();
		String externalId = generateExternalId();
		int rootMessageId = generateRandomId();
		int parentId = generateRandomId();

		return "{\n" + "  \"createdDate\": \"" + createdDate + "\",\n" + "  \"messagePrivacy\": \"" + messagePrivacy
				+ "\",\n" + "  \"messageType\": \"" + messageType + "\",\n" + "  \"userId\": \"" + userId + "\",\n"
				+ "  \"hasCustomTemplate\": " + randomBoolean() + ",\n" + "  \"decoratorLayer\": \"" + decoratorLayer
				+ "\",\n" + "  \"hasUserRecipient\": " + randomBoolean() + ",\n" + "  \"hasChildren\": "
				+ randomBoolean() + ",\n" + "  \"isEdited\": " + randomBoolean() + ",\n" + "  \"content\": \"" + content + "\",\n" + "  \"hasAttachment\": "
				+ randomBoolean() + ",\n" + "  \"isRemoved\": " + randomBoolean() + ",\n" + "  \"caseId\": \"" + caseId
				+ "\",\n" + "  \"rootMessageId\":" + "\"\""+  ",\n" + "  \"parentId\": " + parentId + ",\n"
				+ "  \"externalIdentifiers\": \"" + externalId + "\",\n" + "  \"resourceGraph\": {\n" + "    \"id\": \""
				+ resourceId + "\"\n" + "  },\n" + "  \"flagStatus\": \"" + flag + "\",\n" + "  \"roleName\": \"" + role
				+ "\",\n" + "  \"hasCustomElement\": " + randomBoolean() + ",\n" + "  \"hasReaction\": "
				+ randomBoolean() + ",\n" + "  \"hasCustomTag\": " + randomBoolean() + "\n" + "}";
	}

	public static String generateAttachmentJson(int messageId) {
		// Random numbers for IDs
		int documentId = RANDOM.nextInt(100) + 1; // 1–100
		int versionId = 700000 + RANDOM.nextInt(99999); // 700000–799999
//     int messageId = RANDOM.nextInt(50) + 1; // 1–50
		createdDate = randomDate();
		// Random file paths
		String[] fileNames = { "report.pdf", "invoice.png", "document.docx", "image.jpg", "notes.txt", "diagram.svg" };
		String randomFile = fileNames[RANDOM.nextInt(fileNames.length)];
		String filePath = "C:\\\\Users\\\\cpatil\\\\Downloads\\\\" + randomFile;
		String json = "{\n" + "  \"documentId\": " + documentId + ",\n" + "  \"versionId\": " + versionId + ",\n"
				 + "  \"attachments\": [\n" + "    {\n"
				+ "      \"value\": \"" + filePath + "\"\n" + "    }\n" + "  ],\n" + "  \"message\": {\n"
				+ "    \"id\": " + messageId + "\n" + "  }\n" + "}";

		return json;
	}

	public static String generateCustomElementJson(int messageId) {
		// Generate random fields
		String[] names = { "invoice_doc", "payment_doc", "credit_note", "order_summary", "tax_report" };
		String name = names[RANDOM.nextInt(names.length)];
		createdDate = randomDate();
		String[] types = { "SUGGESTION", "DOCUMENTS_INFO", "CONFIRMATION", "THUMBNAIL_INFO", "PERSONALITY" };
		String type = types[RANDOM.nextInt(types.length)];
		int invoiceNum = 2000 + RANDOM.nextInt(999);
		String payload = "Invoice#" + invoiceNum + "-PDF";
		String json = "{\n" + "  \"name\": \"" + name + "\",\n" + "  \"type\": \"" + type + "\",\n"
				  + "  \"payload\": \"" + payload + "\",\n"
				+ "  \"message\": {\n" + "    \"id\": \"" + messageId + "\"\n" + "  }\n" + "}";

		return json;
	}

	public static String generateReactionJson(int messageId) {
		// Random reaction types
		String[] reactionTypes = { "LIKE", "LOVE", "CLAP", "APPROVE", "DISLIKE" };
		String reactionType = reactionTypes[RANDOM.nextInt(reactionTypes.length)];
		createdDate = randomDate();
		// Random role names (emojis or actions)
		String[] roleNames = { "MODERATOR", "USER", "ADMIN", "SYSTEM" };
		String roleName = roleNames[RANDOM.nextInt(roleNames.length)];

		// Random user IDs
		int userNum = 1000 + RANDOM.nextInt(9000);
		String userId = "user_" + userNum;
		String json = "{\n" + "  \"createdDate\": \"" + createdDate + "\",\n" + "  \"reactionType\": \"" + reactionType
				+ "\",\n" + "  \"roleName\": \"" + roleName + "\",\n" + "  \"userId\": \"" + userId + "\",\n"
				 + "  \"message\": {\n" + "    \"id\": " + messageId
				+ "\n" + "  }\n" + "}";

		return json;
	}

	public static String generateBaseUserMessageJson(int messageId) {
		// Random role names
		String[] roles = { "Software Developer", "QA Engineer", "Product Manager", "UX Designer", "QA Manager",
				"Software Test Engineer" };
		String roleName = roles[RANDOM.nextInt(roles.length)];
		createdDate = randomDate();
		updatedDate = randomDate();
		// Random user IDs
		int userIdNum = 10000 + RANDOM.nextInt(90000);
		String userId = String.valueOf(userIdNum);
		String json = "{\n"  + "  \"roleName\": \"" + roleName
				+ "\",\n"  + "  \"message\": {\n" + "    \"id\": \""
				+ messageId + "\"\n" + "  },\n" + "  \"userId\": \"" + userId + "\"\n" + "}";

		return json;
	}

	public static String generateCustomTagJson(int messageId) {
		// Random content messages
		String[] contents = { "Comment added on post", "Great job on the project!", "Please review the latest update",
				"Nice work!", "Follow up required" };
		String content = contents[RANDOM.nextInt(contents.length)];

		// Random type
		String[] types = { "ACTION", "HASH", "REFERENCE", "USER" };
		String type = types[RANDOM.nextInt(types.length)];

		// Random payload
		String[] payloads = { "Great job on the project!", "Needs revision", "Approved", "Please check",
				"Urgent attention" };
		String payload = payloads[RANDOM.nextInt(payloads.length)];
		createdDate = randomDate();
		// Random referenceId
		int refNum = 100 + RANDOM.nextInt(900);
		String referenceId = "COMM-REF-" + refNum;
		String json = "{\n" + "  \"content\": \"" + content + "\",\n" + "  \"type\": \"" + type + "\",\n"
				 + "  \"payload\": \"" + payload + "\",\n"
				+ "  \"message\": {\n" + "    \"id\": \"" + messageId + "\"\n" + "  },\n" + "  \"referenceId\": \""
				+ referenceId + "\"\n" + "}";

		return json;
	}

	public static String generateCustomTemplateJson(int messageId) {
		String[] largeValues = { "Employee Engagement Survey", "Customer Feedback Form", "Annual Performance Review",
				"Project Retrospective", "Employee Satisfaction Poll" };

		String[] smallValues = { "Survey", "Feedback", "Report", "Form", "Checklist" };

		String[] mediumValues = { "Analysis of employee feedback regarding workplace culture",
				"Summary of customer experience across all regions", "Evaluation of project delivery and teamwork",
				"Annual summary of key performance indicators", "Detailed analysis of satisfaction trends" };
		createdDate = randomDate();
		updatedDate = randomDate();
		String[] names = { "Rahul", "Sneha", "Amit", "Priya", "Vikram", "Neha" };
		String[] templateTypes = { "MACRO", "MICRO", "NANO" };
		String largeValue = largeValues[RANDOM.nextInt(largeValues.length)];
		String smallValue = smallValues[RANDOM.nextInt(smallValues.length)];
		String mediumValue = mediumValues[RANDOM.nextInt(mediumValues.length)];
		String name = names[RANDOM.nextInt(names.length)];
		String templateType = templateTypes[RANDOM.nextInt(templateTypes.length)];
		boolean isCurrent = RANDOM.nextBoolean();
		int position = RANDOM.nextInt(10) + 1;
		String json = "{\n" + "  \"largeValue\": \"" + largeValue + "\",\n" + "  \"templateType\": \"" + templateType
				+ "\",\n" + "  \"isCurrent\": " + isCurrent + ",\n" + "  \"smallValue\": \"" + smallValue + "\",\n"
				+  "  \"name\": \"" + name + "\",\n"
				+ "  \"position\": " + position + ",\n" 
				+ "  \"message\": {\n" + "    \"id\": \"" + messageId + "\"\n" + "  },\n" + "  \"mediumValue\": \""
				+ mediumValue + "\"\n" + "}";

		return json;
	}
	public static String generateUserRecipientJson(int messageId) {
        // Random read states
        String[] readStates = {"READ", "UNREAD"};
        String readState = readStates[RANDOM.nextInt(readStates.length)];

        // Random role names
        String[] roleNames = {"Admin", "User", "Moderator", "Reviewer"};
        String roleName = roleNames[RANDOM.nextInt(roleNames.length)];

        // Random user ID
        int userNum = 1000 + RANDOM.nextInt(9000);
        String userId = "user_" + userNum;

        // Construct JSON
        String json = "{\n" +
                "  \"readState\": \"" + readState + "\",\n" +
                "  \"roleName\": \"" + roleName + "\",\n" +
                "  \"userId\": \"" + userId + "\",\n" +
                "  \"message\": {\n" +
                "    \"id\": " + messageId + "\n" +
                "  }\n" +
                "}";

        return json;
    }

	public static String generateTenantJson() {
        // Random name options
        String[] names = {"Rahul", "Sneha", "Amit", "Priya", "Karan", "Neha","Chetan","Adarsh","Prashath","Rohit","Naveen","Thushar","Dilip","Santosh","Prajwal","Vinod"};
        String name = names[RANDOM.nextInt(names.length)];

        createdDate = randomDate();

        // Construct JSON
        String json = "{\n" +
                "  \"name\": \"" + name + "\"\n" +
                "}";

        return json;
    }
	
	public static String generateUserCustomTemplateJson(int customtemplateId) {
        // Random payload text
        String[] payloads = {"Pinned update", "Important notice", "System message", "Urgent alert", "Reminder"};
        String payload = payloads[RANDOM.nextInt(payloads.length)];

        // Random userId
        String userId = "user_" + (1000 + RANDOM.nextInt(9000));
        String type = "PINNED";

        // Random date
        createdDate = randomDate();

        // Construct JSON
        String json = "{\n" +
                "  \"payload\": \"" + payload + "\",\n" +
                "  \"type\": \"" + type + "\",\n" +
                "  \"userId\": \"" + userId + "\",\n" +
                "  \"customTemplate\": {\n" +
                "    \"id\": " + customtemplateId + "\n" +
                "  }\n" +
                "}";

        return json;
    }

	public static void main(String[] args) {

//		System.out.println("\nResource JSON:");
//		System.out.println(generateResourceJson());
		System.out.println(generateMessageJson(1));
//		System.out.println(generateAttachmentJson(3));
//		System.out.println(generateCustomElementJson(2));
//		System.out.println(generateReactionJson(1));
//		System.out.println(generateBaseUserMessageJson(1));
//		System.out.println(generateCustomTagJson(1));
//		System.out.println(generateCustomTemplateJson(2));
//		System.out.println(generateUserRecipientJson(1));
//		System.out.println(generateTenantJson());
//		System.out.println(generateUserCustomTemplateJson(1));
//		System.out.println(generateResourceNJson());
	}
}
