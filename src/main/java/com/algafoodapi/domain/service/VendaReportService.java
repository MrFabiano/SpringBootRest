package com.algafoodapi.domain.service;

import com.algafoodapi.domain.dto.filter.model.VendaDiariaFilter;

public interface VendaReportService {

    byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}
