package com.freightfox.pdf.service;

import com.freightfox.pdf.model.Invoice;
import com.freightfox.pdf.util.PDFGeneratorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {

    private final PDFGeneratorUtil pdfGeneratorUtil;

    @Autowired
    public InvoiceServiceImpl(PDFGeneratorUtil pdfGeneratorUtil) {
        this.pdfGeneratorUtil = pdfGeneratorUtil;
    }


    @Override
    public ByteArrayInputStream fetchInvoicePDF(Invoice invoice) {
        log.info("Starting PDF generator:");
        Map<String, Object> templateData = new HashMap<>();
        templateData.put("invoice", invoice);
        return pdfGeneratorUtil.generatorPDF("invoice_pdf_template", templateData);
    }


}
