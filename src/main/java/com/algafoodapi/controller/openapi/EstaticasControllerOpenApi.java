package com.algafoodapi.controller.openapi;

import com.algafoodapi.domain.dto.filter.model.VendaDiariaFilter;
import com.algafoodapi.domain.model.VendaDiaria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api(tags = "Estaticas")
public interface EstaticasControllerOpenApi {



    @ApiOperation("Consulta as vendas diarias")
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro,
                                                    @RequestParam(required = false, defaultValue = "+00:00") String timeOffset);



    @ApiOperation("Consulta as vendas diarias e emite PDF")
    public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro,
                                                            @RequestParam(required = false, defaultValue = "+00:00")
                                                                    String timeOffset);

}
