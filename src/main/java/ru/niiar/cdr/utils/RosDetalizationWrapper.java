package ru.niiar.cdr.utils;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import ru.niiar.cdr.model.RosDetalization;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class RosDetalizationWrapper {
    private Iterable<RosDetalization> rosDetalizations;
    private String title;

    public RosDetalizationWrapper(Iterable<RosDetalization> rosDetalizations, String title){
        this.rosDetalizations = rosDetalizations;
        this.title = title;
    }

    public void saveToXLS(){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("ИС СиАТВ");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue(title);
        row = sheet.createRow(2);
        row.createCell(0).setCellValue("Вызывающий");
        row.createCell(1).setCellValue("Дата время");
        row.createCell(2).setCellValue("Вызываемый");
        row.createCell(3).setCellValue("Направление");
        row.createCell(4).setCellValue("Продолжительность");
        row.createCell(5).setCellValue("Стоимость без НДС");
        row.createCell(6).setCellValue("ФИО");
        row.createCell(7).setCellValue("Вн. номер");
        row.createCell(8).setCellValue("Внешн. номер");
        row.createCell(9).setCellValue("Здание");
        row.createCell(10).setCellValue("Помещение");
        row.createCell(11).setCellValue("Подразделение");
        int countRow = 3;
        for (RosDetalization ros : rosDetalizations) {
            row = sheet.createRow(countRow);
            row.createCell(0).setCellValue(ros.getNumberA());
            row.createCell(1).setCellValue(ros.getDateTime().toString());
            row.createCell(2).setCellValue(ros.getNumberB());
            row.createCell(3).setCellValue(ros.getRoute());
            row.createCell(4).setCellValue(ros.getDuration());
            row.createCell(5).setCellValue(ros.getCost());
            if (ros.getSubscriber() != null){
                row.createCell(6).setCellValue(ros.getSubscriber().getFullName());
                if (ros.getSubscriber().getInternalNum() != null)
                    row.createCell(7).setCellValue(ros.getSubscriber().getInternalNum());
                row.createCell(8).setCellValue(ros.getSubscriber().getExternalNum());
                row.createCell(9).setCellValue(ros.getSubscriber().getBuilding());
                row.createCell(10).setCellValue(ros.getSubscriber().getRoom());
                if(ros.getSubscriber().getDivision() != null)
                    row.createCell(11).setCellValue(ros.getSubscriber().getDivision().getDivisionName());
            }
            countRow++;
        }
        String filepath = "roschart.xls";
        try (FileOutputStream fos = new FileOutputStream(filepath)){
            workbook.write(fos);
        }
        catch (FileNotFoundException ex){
            ex.printStackTrace();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
