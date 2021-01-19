package cn.ylj.controller;

import cn.ylj.constant.MessageConstant;
import cn.ylj.model.Result;
import cn.ylj.service.IOrdersettingService;
import cn.ylj.utils.POIUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author : yanglujian
 * create at:  2021/1/19  7:15 下午
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private IOrdersettingService ordersettingService;

    @RequestMapping("/upload")
    public Result uploadExcel(@RequestParam("excelFile") MultipartFile excelFile){
        //解析成数据传到后面的服务
        try {
            List<String[]> list = POIUtils.readExcel(excelFile);
            ordersettingService.importOrderSetting(list);
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }
}