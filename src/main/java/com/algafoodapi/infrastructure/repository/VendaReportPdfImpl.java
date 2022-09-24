package com.algafoodapi.infrastructure.repository;

import com.algafoodapi.domain.dto.filter.model.VendaDiariaFilter;
import com.algafoodapi.domain.exception.ReportException;
import com.algafoodapi.domain.service.VendaQueryService;
import com.algafoodapi.domain.service.VendaReportService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;

@Service
public class VendaReportPdfImpl implements VendaReportService {

    @Autowired
    private VendaQueryService vendaQueryService;

    @Override
    public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
        try {
        var  inputStream = this.getClass().getResourceAsStream(
                "/relatorios/vendas-diarias.jasper");
        var parametros = new HashMap<String, Object>();
        parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));

        var vendasDiarias = vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
        var dataSource = new JRBeanCollectionDataSource(vendasDiarias);
        var jasperPrint = JasperFillManager.fillReport(inputStream, parametros, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new ReportException("Não foi possivel emitir o relatorio", e);
        }
    }
}
