package com.atech.pma.reports;

import com.atech.pma.entity.mysql.CardHolder;
import com.atech.pma.mappers.CardHolderMapper;
import com.atech.pma.model.CardHolderDTO;
import com.atech.pma.repository.mysql.CardHolderRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.atech.pma.constants.AppStaticData.*;

/**
 * @author raed abu Sa'da
 * on 07/04/2023
 */
@Slf4j
@Service
public class ReportsServiceImpl implements ReportsService{

    private final CardHolderRepository cardHolderRepository;
    private final CardHolderMapper cardHolderMapper;

    public ReportsServiceImpl(CardHolderRepository cardHolderRepository,
                              CardHolderMapper cardHolderMapper) {

        this.cardHolderRepository = cardHolderRepository;
        this.cardHolderMapper = cardHolderMapper;
    }

    @Override
    public void generateAllCardHoldersReport(String fileFormat, HttpServletResponse response) {

        List<CardHolder> cardHolderList = cardHolderRepository.findAll();

        List<CardHolderDTO> cardHolderDTOList = cardHolderMapper.toDto(cardHolderList);

        InputStream reportFile = getClass().getResourceAsStream(AllCardHoldersReportName);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(cardHolderDTOList);

        Map<String, Object> params = new HashMap<>();
        params.put("listData", dataSource);

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(reportFile);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());

            if (fileFormat.equals(PdfFileFormat)){

                exportPdfReport(response, jasperPrint);
            }

            if (fileFormat.equals(ExcelFileFormat)) {

                exportExcelReport(response, jasperPrint);
            }
        } catch (JRException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void generateSingleCardHolderReport(int badgeId, String fileFormat, HttpServletResponse response) {

        Optional<CardHolder> optionalCardHolder = cardHolderRepository.findCardHolderByBadgeId(badgeId);

        if (optionalCardHolder.isEmpty()){
            try {

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        CardHolder cardHolder = optionalCardHolder.get();

        CardHolderDTO cardHolderDTO = cardHolderMapper.toDto(cardHolder);

        InputStream filePath = getClass().getResourceAsStream(SingleCardHolderReportName);

        Map<String, Object> params = loadSingleCardHolderParams(cardHolderDTO);

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(filePath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());

            if (fileFormat.equals(PdfFileFormat)){

                exportPdfReport(response, jasperPrint);
            }

            if (fileFormat.equals(ExcelFileFormat)) {

                exportExcelReport(response, jasperPrint);
            }

        } catch (JRException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void generateReportByDrivingLicense(LocalDate startDate, LocalDate endDate, String fileFormat, HttpServletResponse response){

        Optional<List<CardHolder>> optionalCardHolderList = cardHolderRepository.findAllByDrivingLicenseExpiryDateBetween(startDate, endDate);

        if (optionalCardHolderList.isEmpty()){
            //TODO
        }

        List<CardHolder> cardHolderList = optionalCardHolderList.get();

        List<CardHolderDTO> cardHolderDTOList = cardHolderMapper.toDto(cardHolderList);

        InputStream filePath = getClass().getResourceAsStream(CardHoldersByDrivingLicenseReportName);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(cardHolderDTOList);
        Map<String, Object> params = new HashMap<>();
        params.put("listData", dataSource);
        params.put("startDate", startDate.toString());
        params.put("endDate", endDate.toString());

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(filePath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());

            if (fileFormat.equals(PdfFileFormat)){

                exportPdfReport(response, jasperPrint);
            }

            if (fileFormat.equals(ExcelFileFormat)) {

                exportExcelReport(response, jasperPrint);
            }
        } catch (JRException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void generateReportByCarInsurance(LocalDate startDate, LocalDate endDate, String fileFormat, HttpServletResponse response) {

        Optional<List<CardHolder>> optionalCardHolderList = cardHolderRepository.findCardHoldersFilteredByInsuranceDate(startDate, endDate);

        if (optionalCardHolderList.isEmpty()){
            //TODO
        }

        List<CardHolder> cardHolderList = optionalCardHolderList.get();

        List<CardHolderDTO> cardHolderDTOList = cardHolderMapper.toDto(cardHolderList);
        InputStream filePath = getClass().getResourceAsStream(CardHoldersByInsuranceReportName);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(cardHolderDTOList);
        Map<String, Object> params = new HashMap<>();
        params.put("listData", dataSource);
        params.put("startDate", startDate.toString());
        params.put("endDate", endDate.toString());

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(filePath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());

            if (fileFormat.equals(PdfFileFormat)){

                exportPdfReport(response, jasperPrint);
            }

            if (fileFormat.equals(ExcelFileFormat)) {

                exportExcelReport(response, jasperPrint);
            }
        } catch (JRException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void exportExcelReport(HttpServletResponse response, JasperPrint jasperPrint) throws IOException, JRException {
        JRXlsxExporter exporter = new JRXlsxExporter();
        SimpleXlsxReportConfiguration xlsxReportConfiguration = new SimpleXlsxReportConfiguration();
        xlsxReportConfiguration.setSheetNames(new String[]{"sheet1"});

        exporter.setConfiguration(xlsxReportConfiguration);
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));

        response.setHeader("Content-Disposition", "attachment;filename=jasperReport.xlsx");
        response.setContentType("application/octet-stream");
        exporter.exportReport();
    }

    private static void exportPdfReport(HttpServletResponse response, JasperPrint jasperPrint) throws IOException, JRException {
        response.setContentType("application/x-download");
        response.setHeader("Content-Disposition", "attachment; filename=\"all-employees-report-" + LocalDate.now() + ".pdf\"");
        OutputStream outputStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
    }

    private Map<String, Object> loadSingleCardHolderParams(CardHolderDTO cardHolder) {

        Map<String, Object> params = new HashMap<>();

        params.put("firstName", cardHolder.getFirstName());
        params.put("lastName", cardHolder.getLastName());
        params.put("badgeId", cardHolder.getBadgeId());
        params.put("drivingLicenseExpiryDate", cardHolder.getDrivingLicenseExpiryDate());
        params.put("color", cardHolder.getCardHolderCarInfo().getColor());
        params.put("carBrand", cardHolder.getCardHolderCarInfo().getCarBrand());
        params.put("carModel", cardHolder.getCardHolderCarInfo().getCarModel());
        params.put("plateNumber", cardHolder.getCardHolderCarInfo().getPlateNumber());
        params.put("productionYear", cardHolder.getCardHolderCarInfo().getProductionYear());
        params.put("insuranceExpiryDate", cardHolder.getCardHolderCarInfo().getInsuranceExpiryDate());
        params.put("registrationExpiryDate", cardHolder.getCardHolderCarInfo().getRegistrationExpiryDate());

        return params;
    }
}
