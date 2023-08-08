package damdorani.dandulendar.controller;

import com.google.gson.Gson;
import damdorani.dandulendar.dto.EmailPostDto;
import damdorani.dandulendar.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/mail")
    @ResponseBody
    public String sendMail(@RequestBody EmailPostDto emailPostDto){
        emailService.sendSimpleMessage(emailPostDto);
        return new Gson().toJson("success");
    }
}
