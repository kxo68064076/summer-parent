package com.capricorn.mail.controller;

import com.capricorn.common.utils.SendEmailUtil;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("mail")
public class MailController {

    @PostMapping("sendMail")
    public HashMap<String, Object> sendEmailMessage(@RequestBody Map<String, Object> map) {
        String from = map.get("from").toString();
        String address = map.get("address").toString();
        String subject = map.get("subject").toString();
        String content = map.get("content").toString();
        int i = SendEmailUtil.sendHtmlMail(from, address, subject, content);
        HashMap<String, Object> result = new HashMap<>();
        if (i==0){
            result.put("code",0);
            result.put("message","success");
        }else {
            result.put("code",1);
            result.put("message","fail");
        }
        return result;

    }

//    {
//        "from":"68064076@qq.com",
//            "address":"gaoqiao0405@126.com",
//            "subject":"这是一封测试邮箱",
//            "content":"尊敬的何先生"
//    }
}
