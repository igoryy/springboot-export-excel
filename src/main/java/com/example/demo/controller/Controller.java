package com.example.demo.controller;

import com.example.demo.domain.Camisa;
import com.example.demo.domain.User;
import com.example.demo.service.Service;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

    @GetMapping("/base64ToExcel")
    @SneakyThrows
    public void exportToExcel2(HttpServletResponse response ){

        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);


        XSSFWorkbook wb = new XSSFWorkbook();

        //pegar o diretório do usuário e criar um arquivo com o determinado nome
        String PathTillProject = System.getProperty("user.dir");
        FileOutputStream fileOut = new FileOutputStream(PathTillProject + "/src/Export.xls");

        //criar várias abas
        XSSFSheet abaPrimaria = wb.createSheet("ABA 1");
        XSSFSheet abaSecundaria = wb.createSheet("ABA 2");

        //criar linhas (passar o nome da aba onde deseja criar)
        XSSFRow primeiraLinha = abaPrimaria.createRow(0);
        XSSFRow segundaLinha = abaPrimaria.createRow(0);

        //criar uma célula em uma linha (passar o nome da linha onde deseja criar)
        XSSFCell primeiraCelulaColuna = primeiraLinha.createCell(0);
        XSSFCell segundaCelulaColuna = primeiraLinha.createCell(1);

        ByteArrayOutputStream b = new ByteArrayOutputStream();

        wb.write(b);

        byte[] teste  = b.toByteArray();
        var base64 = Base64.encodeBase64String(teste);
        byte[] teste2 = Base64.decodeBase64(base64);




        response.getOutputStream().write(teste2);


    }

    @GetMapping("/base64ToExcelTeste")
    @SneakyThrows
    public ResponseEntity exportToExcel3( ){

//https://swagger.io/docs/specification/media-types/
        //https://swagger.io/docs/specification/describing-responses/

        XSSFWorkbook wb = new XSSFWorkbook();

        //pegar o diretório do usuário e criar um arquivo com o determinado nome
        String PathTillProject = System.getProperty("user.dir");
        FileOutputStream fileOut = new FileOutputStream(PathTillProject + "/src/Export.xls");

        //criar várias abas
        XSSFSheet abaPrimaria = wb.createSheet("ABA 1");
        XSSFSheet abaSecundaria = wb.createSheet("ABA 2");

        //criar linhas (passar o nome da aba onde deseja criar)
        XSSFRow primeiraLinha = abaPrimaria.createRow(0);
        XSSFRow segundaLinha = abaPrimaria.createRow(0);

        //criar uma célula em uma linha (passar o nome da linha onde deseja criar)
        XSSFCell primeiraCelulaColuna = primeiraLinha.createCell(0);
        XSSFCell segundaCelulaColuna = primeiraLinha.createCell(1);

        ByteArrayOutputStream b = new ByteArrayOutputStream();

        wb.write(b);

        byte[] teste  = b.toByteArray();
        var base64 = Base64.encodeBase64String(teste);
        byte[] teste2 = Base64.decodeBase64(base64);


        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("application/vnd.ms-excel"))
              //  .header(HttpHeaders.CONTENT_ENCODING,"base64")
              //  .header(HttpHeaders.TRANSFER_ENCODING, "base64")
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"my.xlsx\"")
                .body(teste2);

    }

}
