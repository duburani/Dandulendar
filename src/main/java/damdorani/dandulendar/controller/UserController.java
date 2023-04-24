package damdorani.dandulendar.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import damdorani.dandulendar.dto.CalendarForm;
import damdorani.dandulendar.dto.UserForm;
import damdorani.dandulendar.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;


    /**
     * 회원 가입 화면
     * @param code
     * @return
     */
    @GetMapping("/regist")
    public String getCode(@RequestParam String code, Model model) {

        String accessToken = "";
        String refreshToken = "";
        String requestURL = "https://kauth.kakao.com/oauth/token";

        log.info("코드 : " + code);

        // TODO 회원일 때, 아닐 때 구분 필요
/*
        if (User.isEmpty()) {
            userDto.setEmail(socialDto.getEmail());
            userDto.setName(socialDto.getName());
            userDto.setImageUrl(socialDto.getImageUrl());
            userDto.setPassword(bCryptPasswordEncoder.encode(UUID.randomUUID().toString()));
            UserEntity userEntity = DtoToEntity.userDtoToEntity(userDto);
            // &#xD68C;&#xC6D0;&#xAC00;&#xC785;
            userRepository.save(userEntity);
        }*/

        try {
            URL url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // POST 요청에서 필요한 파라미터를 OutputStream을 통해 전송
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            String sb = "grant_type=authorization_code" +
                    "&client_id=da61026106bee083468e91e7f4f57ca6" + // REST_API_KEY
                    "&redirect_uri=http://localhost:8089/regist" + // REDIRECT_URI
                    "&code=" + code;
            bufferedWriter.write(sb);
            bufferedWriter.flush();

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            // 요청을 통해 얻은 데이터를 InputStreamReader을 통해 읽어 오기
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            System.out.println("response body : " + result);

            JsonObject object = JsonParser.parseString(result.toString()).getAsJsonObject();

            accessToken = object.getAsJsonObject().get("access_token").getAsString();
            refreshToken = object.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("accessToken : " + accessToken);
            System.out.println("refreshToken : " + refreshToken);

            // 사용자정보 가져오기
            // 요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
            HashMap<String, Object> userInfo = new HashMap<>();
            String reqURL = "https://kapi.kakao.com/v2/user/me";
            try {
                URL url2 = new URL(reqURL);
                HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
                conn2.setRequestMethod("POST");

                //    요청에 필요한 Header에 포함될 내용
                conn2.setRequestProperty("Authorization", "Bearer " + accessToken);

                int responseCode2 = conn2.getResponseCode();
                System.out.println("responseCode : " + responseCode);

                BufferedReader br = new BufferedReader(new InputStreamReader(conn2.getInputStream()));

                String line2 = "";
                String result2 = "";

                while ((line2 = br.readLine()) != null) {
                    result2 += line2;
                }
                System.out.println("response body : " + result2);

                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(result2);

                JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
                JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

                String nickname = properties.getAsJsonObject().get("nickname").getAsString();
                String email = kakao_account.getAsJsonObject().get("email").getAsString();

                userInfo.put("user_name", nickname);
                userInfo.put("email", email);
                userInfo.put("user_id", email);
                userInfo.put("provider", "kakao");
//                UserRepository.saveUserInfo(userInfo);

                model.addAttribute("userInfo", userInfo);

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "join";
    }

    @PostMapping("/regist")
    public String regist(@Valid UserForm userForm, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "user/join";
        }
        userService.saveUser(userForm);
        return "redirect:/";
    }


}
