package com.example.demo.controller;

import com.example.demo.domain.Camisa;
import com.example.demo.domain.User;
import com.example.demo.service.Service;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class Controller {



    @GetMapping("/")
    @SneakyThrows
    public void exportToExcel(HttpServletResponse response){

        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<User> listUsers = new ArrayList<>();

        listUsers.add(User.builder().email("Teste Email")
                .enabled(true)
                .id(1)
                .fullName("Igor")
                .password("Seha")
                .build());

        Service service = new Service(listUsers);

        service.export(response);


    }

    @GetMapping("/base64")
    public ResponseEntity<String> exportToBase64(){


        return ResponseEntity.ok("");
    }

}
