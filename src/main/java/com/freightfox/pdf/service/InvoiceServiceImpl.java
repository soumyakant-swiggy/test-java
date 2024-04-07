package com.freightfox.pdf.service;

import com.freightfox.pdf.model.Invoice;
import com.freightfox.pdf.util.PDFGeneratorUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
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


    /**
     * If there is not exist pdf for the given data, then it will generate new pdf, else it will return exist pdf
     * @param invoice
     * @return ByteArrayInputStream
     */
    @Override
    public ByteArrayInputStream fetchInvoicePDF(Invoice invoice) {
        ByteArrayInputStream existPDF = checkForExistingPDF(invoice);
        if (ObjectUtils.isNotEmpty(existPDF)) {
            log.info("Already PDF exist for this invoice");
            return existPDF;
        }

        log.info("Not exist PDF fouNd, Starting PDF generator.");
        Map<String, Object> templateData = new HashMap<>();
        templateData.put("invoice", invoice);
        ByteArrayInputStream pdf = pdfGeneratorUtil.generatorPDF("invoice_pdf_template", templateData);
        asyncSaveInvoicePdf(pdf, invoice);
        return pdf;
    }

    /**
     * This method will search for exitPDF
     * We can Integrate this method by two ways :
     * 1. We can read the entire pdf if it is same or not
     * 2. Save the entire data to database with file location where it is located
          and pdf can be saved in local(but this is not the right place) or cloud bucket
          , check the data by 2-3 fields which might be unique and retrieve the file
          if file is not there we will generate a new pdf.
     * @param invoice
     * @return ByteArrayInputStream
     */
    private ByteArrayInputStream checkForExistingPDF(Invoice invoice) {
        return null;
    }

    /**
     * This method will save the generated pdf
     * We can call this method as async in different class
     * @param pdf
     * @param invoice
     */
    private void asyncSaveInvoicePdf(ByteArrayInputStream pdf, Invoice invoice) {
    }


}
