package com.zhys.service;

import com.zhys.vo.InvoiceCondition;

public interface InvoiceViewService {
    InvoiceCondition getInvoiceSummary(InvoiceCondition invoiceCondition);
}
