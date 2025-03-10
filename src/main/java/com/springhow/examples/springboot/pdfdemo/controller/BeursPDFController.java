package com.springhow.examples.springboot.pdfdemo.controller;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.springhow.examples.springboot.pdfdemo.BeursOrderHelper;
import com.springhow.examples.springboot.pdfdemo.pojo.BeursOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/beursorders")
public class BeursPDFController {


    @Autowired
    ServletContext servletContext;

    private final TemplateEngine templateEngine;

    public BeursPDFController(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @RequestMapping(path = "/")
    public String getOrderPage(Model model) {
        log.debug("getOrder");
        BeursOrder order = BeursOrderHelper.getOrder();
        model.addAttribute("orderEntry", order);
        return "beursOrder";
    }

    @RequestMapping(path = "/pdf")
    public ResponseEntity<?> getPDF(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.debug("getPdf");
        /* Do Business Logic*/

        BeursOrder order = BeursOrderHelper.getOrder();

        /* Create HTML using Thymeleaf template Engine */

        WebContext context = new WebContext(request, response, servletContext);
        context.setVariable("orderEntry", order);
        String orderHtml = templateEngine.process("beursOrder", context);

        /* Setup Source and target I/O streams */

        ByteArrayOutputStream target = new ByteArrayOutputStream();
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri("http://localhost:8080");
        /* Call convert method */
        HtmlConverter.convertToPdf(orderHtml, target, converterProperties);

        /* extract output as bytes */
        byte[] bytes = target.toByteArray();


        /* Send the response as downloadable PDF */

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=beursorder.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);

    }

}
