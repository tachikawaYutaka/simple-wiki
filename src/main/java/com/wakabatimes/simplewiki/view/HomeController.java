package com.wakabatimes.simplewiki.view;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Controller
@Slf4j
public class HomeController {
    @GetMapping("/")
    public String home(){
        return "home";
    }

    @ResponseBody
    @GetMapping(path="/puml_image", produces = MediaType.IMAGE_PNG_VALUE)
    public HttpEntity<byte[]> puml_image(@RequestParam("url")  String url){
        String decode  = "";
        try {
            decode = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        SourceStringReader reader = new SourceStringReader(decode.replace(';','\n'));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            reader.generateImage(bos, new FileFormatOption(FileFormat.PNG, false));
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        byte[] b = bos.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(b.length);
        return new HttpEntity<byte[]>(b, headers);
    }
}
