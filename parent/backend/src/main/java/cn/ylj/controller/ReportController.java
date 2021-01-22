package cn.ylj.controller;

import cn.ylj.constant.MessageConstant;
import cn.ylj.model.Result;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : yanglujian
 * create at:  2021/1/22  6:57 下午
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    //获取报表数据
    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData(){
        return null;
    }

    //导出报表数据
    @RequestMapping("/exportData")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response) {
        try {
//            Map<String,Object> result = reportService.getBusinessReportData();
            //模拟数据
            //取出返回结果数据，准备将报表数据写入到Excel文件中
            String reportDate = "2021-01-22";
            Integer todayNewMember = 10;
            Integer totalMember = 999;
            Integer thisWeekNewMember = 23;
            Integer thisMonthNewMember = 123;
            Integer todayOrderNumber = 1;
            Integer thisWeekOrderNumber = 9;
            Integer thisMonthOrderNumber = 19;
            Integer todayVisitsNumber = 3;
            Integer thisWeekVisitsNumber = 20;
            Integer thisMonthVisitsNumber = 45;

            List<Map> hotSetmeal = new ArrayList<>();
            HashMap<String, Object> m1 = new HashMap<>();
            m1.put("name", "阳光宝贝套餐1");
            m1.put("setmeal_count", 99);
            m1.put("proportion", 12.3);

            HashMap<String, Object> m2 = new HashMap<>();
            m2.put("name", "夕阳无限好套餐2");
            m2.put("setmeal_count", 88);
            m2.put("proportion", 3.21);
            hotSetmeal.add(m1);
            hotSetmeal.add(m2);

            //获取tempalte文件夹在服务器中的绝对路径，拼接模板文件的绝对路径
            String filePath = request.getSession().getServletContext().getRealPath("template") + File.separator + "report_template.xlsx";
            //基于提供的Excel模板文件在内存中创建一个Excel表格对象
            XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream(new File(filePath)));
            //读取第一个工作表
            XSSFSheet sheet = excel.getSheetAt(0);

            XSSFRow row = sheet.getRow(2);
            row.getCell(5).setCellValue(reportDate);//日期

            row = sheet.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);//新增会员数（本日）
            row.getCell(7).setCellValue(totalMember);//总会员数

            row = sheet.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员数
            row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员数

            row = sheet.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
            row.getCell(7).setCellValue(todayVisitsNumber);//今日到诊数

            row = sheet.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
            row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周到诊数

            row = sheet.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
            row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月到诊数

            int rowNum = 12;
            for (Map map : hotSetmeal) {//热门套餐
                String name = (String) map.get("name");
                Long setmeal_count = (Long) map.get("setmeal_count");
                BigDecimal proportion = (BigDecimal) map.get("proportion");
                row = sheet.getRow(rowNum++);
                row.getCell(4).setCellValue(name);//套餐名称
                row.getCell(5).setCellValue(setmeal_count);//预约数量
                row.getCell(6).setCellValue(proportion.doubleValue());//占比
            }

            //使用输出流进行表格下载,基于浏览器作为客户端下载
            OutputStream out = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");//代表的是Excel文件类型
            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");//指定以附件形式进行下载
            excel.write(out);

            out.flush();
            out.close();
            excel.close();
            return null;
        } catch (Exception e) {
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }
}