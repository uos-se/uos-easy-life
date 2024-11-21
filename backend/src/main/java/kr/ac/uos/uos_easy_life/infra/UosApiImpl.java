package kr.ac.uos.uos_easy_life.infra;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import kr.ac.uos.uos_easy_life.core.interfaces.UosApi;
import kr.ac.uos.uos_easy_life.core.model.UosSession;
import kr.ac.uos.uos_easy_life.core.model.UserBasicInfo;
import kr.ac.uos.uos_easy_life.core.model.UserAcademicStatus;

@Component
public class UosApiImpl implements UosApi {

    private static String wiseRequest(String path, String body, UosSession session)
            throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://wise.uos.ac.kr" + path))
                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .header("Cookie", "UOSSESSION=" + session.getWiseSession())
                .POST(HttpRequest.BodyPublishers.ofString(body)).build();
        return client.send(request, BodyHandlers.ofString()).body();
    }

    @Override
    public UserBasicInfo getUserInfo(UosSession session) {
        String path = "/Main/onLoad.do";
        String body = "default.locale=CCMN101.KOR";

        try {
            String response = wiseRequest(path, body, session);

            // 응답 파싱
            JSONObject obj = new JSONObject(response);
            JSONObject userInfo = obj.getJSONObject("dmUserInfo");
            String name = userInfo.getString("USER_NM");
            String studentId = userInfo.getString("USER_ID");

            return new UserBasicInfo(name, studentId);
        } catch (IOException | InterruptedException | JSONException e) {
            return null;
        }
    }

    @Override
    public List<String> getUserCourseCodes(UosSession session, String studentId) {
        String path = "/SCH/SusrMasterInq/list.do";
        String body = "_AUTH_MENU_KEY=SusrMasterInq_2&_AUTH_PGM_ID=SusrMasterInq&__PRVC_PSBLTY_YN=N&_AUTH_TASK_AUTHRT_ID=CCMN_SVC&default.locale=CCMN101.KOR&%40d1%23strStdntNo="
                + studentId + "&%40d%23=%40d1%23&%40d1%23=dmReqKey&%40d1%23tp=dm";

        try {
            String response = wiseRequest(path, body, session);

            // 응답 파싱
            JSONObject obj = new JSONObject(response);
            JSONArray courseList = obj.getJSONArray("dsTlsnList");
            List<String> courseCodes = new ArrayList<>();
            for (int i = 0; i < courseList.length(); i++) {
                JSONObject course = courseList.getJSONObject(i);
                String code = course.getString("SBJC_NO");
                courseCodes.add(code);
            }

            return courseCodes;
        } catch (IOException | InterruptedException | JSONException e) {
            return null;
        }
    }

    @Override
    public UserAcademicStatus getUserAcademicStatus(UosSession session, String studentId) {
        // TODO: Implement this method
        // Mock data
        int totalCompletedCredit = 120;
        int majorCompletedCredit = 60;
        int majorEssentialCompletedCredit = 24;
        int liberalCompletedCredit = 30;
        int liberalEssentialCompletedCredit = 14;
        int engineeringCompletedCredit = 6;
        int generalCompletedCredit = 30;
        double totalGradePointAverage = 3.5;

        return new UserAcademicStatus(totalCompletedCredit, majorCompletedCredit, majorEssentialCompletedCredit, liberalCompletedCredit, 
        liberalEssentialCompletedCredit, engineeringCompletedCredit, generalCompletedCredit, totalGradePointAverage);
    }

    @Override
    public boolean isLanguageCertificationCompleted(UosSession session, String studentId) {
        // TODO: Implement this method
        // Mock data
        return true;
    }

    @Override
    public boolean isVolunteerCompleted(UosSession session, String studentId) {
        // TODO: Implement this method  
        // Mock data
        return true;
    }
}
