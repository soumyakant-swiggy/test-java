package com.freightfox.pdf.util;

import com.freightfox.pdf.exception.AppException;
import com.lowagie.text.DocumentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

@Service
@Slf4j
public class PDFGeneratorUtil {

    private final TemplateEngine templateEngine;

    @Autowired
    public PDFGeneratorUtil(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public ByteArrayInputStream generatorPDF(String templateName, Map<String, Object> templateData) {
        Context context = new Context();
        context.setVariables(templateData);

        String htmlContent = templateEngine.process(templateName, context);
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.getSharedContext().setDotsPerPixel(13);
            renderer.layout();
            renderer.createPDF(byteArrayOutputStream,false);
            renderer.finishPDF();
            byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        } catch (DocumentException e) {
            log.error("Error in rendering PDF -> DocumentException : {}", e.getMessage());
            throw new AppException("Error while generating PDF");
        }
        return byteArrayInputStream;
    }

}
