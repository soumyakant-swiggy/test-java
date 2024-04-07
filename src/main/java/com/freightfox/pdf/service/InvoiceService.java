package com.freightfox.pdf.service;

import com.freightfox.pdf.model.Invoice;

import java.io.ByteArrayInputStream;

public interface InvoiceService {

    ByteArrayInputStream fetchInvoicePDF(Invoice invoice);

}
