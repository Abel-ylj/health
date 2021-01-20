package cn.ylj.controller;

import cn.ylj.constant.MessageConstant;
import cn.ylj.entity.Ordersetting;
import cn.ylj.model.Result;
import cn.ylj.service.IOrdersettingService;
import cn.ylj.utils.POIUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : yanglujian
 * create at:  2021/1/19  7:15 下午
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private IOrdersettingService ordersettingService;

    @RequestMapping("/setnum")
    public Result setnum(@RequestBody Map map){
        try {
            String sdate = (String)map.get("date");
            String num = (String)map.get("num");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(sdate);
            int iNum = Integer.parseInt(num);
            ordersettingService.insertOrder(date, iNum);
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/getOrdersettingByMonth")
    public Result getOrdersettingByMonth(@RequestParam("date") Date date){
        try {
            List<Ordersetting> list = ordersettingService.getOrdersettingByMonth(date);
            return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS, list);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/upload")
    public Result uploadExcel(@RequestParam("excelFile") MultipartFile excelFile){
        //解析成数据传到后面的服务
        try {
            List<String[]> list = POIUtils.readExcel(excelFile);
            List<Ordersetting> convert = convert(list);
            if (convert != null){
                ordersettingService.importOrderSetting(convert);
            } else {
                throw new Exception();
            }
//            ordersettingService.importOrderSetting(list);
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    private List<Ordersetting> convert(List<String[]> list){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        if (list != null && list.size() > 0){
            return list.stream().map(o->{
                Ordersetting os = new Ordersetting();
                try {
                    os.setOrderdate(sdf.parse(o[0]));
                    os.setNumber(Integer.parseInt(o[1]));
                    os.setReservations(0);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return os;
            }).collect(Collectors.toList());
        }
        return null;
    }
}