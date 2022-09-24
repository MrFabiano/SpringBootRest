package com.algafoodapi.domain.service;

import com.algafoodapi.domain.dto.filter.model.VendaDiariaFilter;
import com.algafoodapi.domain.model.VendaDiaria;

import java.util.List;

public interface VendaQueryService {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}
