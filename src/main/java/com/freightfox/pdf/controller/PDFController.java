package com.freightfox.pdf.controller;

import com.freightfox.pdf.exception.ErrorFieldsMessage;
import com.freightfox.pdf.model.Invoice;
import com.freightfox.pdf.service.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springdoc.api.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;


@RestController
@RequestMapping("/api")
@Slf4j
public class PDFController {

    private final InvoiceService invoiceService;


    @Autowired
    public PDFController(final InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Operation(summary = "Generates PDF")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "Success")
            , @ApiResponse(responseCode = "400", description = "Bad data", content = @Content(schema = @Schema(implementation = ErrorFieldsMessage.class)))
            , @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    }
    )
    @PostMapping(value = "v1/invoice/pdf/generate", produces = MediaType.APPLICATION_PDF_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void generatePurchaseOrderPreviewPDFHandler(@Valid @RequestBody Invoice request, HttpServletResponse response) throws IOException {
        ByteArrayInputStream byteArrayInputStream = invoiceService.fetchInvoicePDF(request);
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + "Invoice_doc" + ".pdf");
        IOUtils.copy(byteArrayInputStream, response.getOutputStream());
    }


}
