//package social_comments;
//
//import io.qameta.allure.Allure;
//import io.qameta.allure.model.Label;
//import io.cucumber.java.Before;
//
//public class AllureHooks {
//
//    @Before
//    public void setFeatureLabel() {
//        Allure.getLifecycle().updateTestCase(tc -> {
//            // remove any feature label that equals the unwanted value
//            tc.getLabels().removeIf(l -> "feature".equals(l.getName())
//                    && "ResourceGraph API Testing".equals(l.getValue()));
//
//            // then ensure the correct feature label is present
//            tc.getLabels().removeIf(l -> "feature".equals(l.getName()));
//            tc.getLabels().add(new Label().setName("feature")
//                    .setValue("End-to-end ResourceGraph and ResourceRegistries CRUD flow"));
//        });
//    }
//}
