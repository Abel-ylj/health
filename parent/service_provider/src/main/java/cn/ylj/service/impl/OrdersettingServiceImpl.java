package cn.ylj.service.impl;

import cn.ylj.entity.Ordersetting;
import cn.ylj.mapper.OrdersettingMapper;
import cn.ylj.service.IOrdersettingService;
import com.alibaba.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author : yanglujian
 * create at:  2021/1/19  7:22 下午
 */
//为了防止tx代理后，发布应用失败
@Service(interfaceClass = IOrdersettingService.class)
public class OrdersettingServiceImpl implements IOrdersettingService {

    @Resource
    private OrdersettingMapper ordersettingMapper;

    @Override
    public void importOrderSetting(List<Ordersetting> list) {
//        List<Map> convert = convert(list);
//        ordersettingMapper.insertList(convert);

        //用mysql 唯一约束 避免对同一天进行两次预约设置。
        ordersettingMapper.insertOrdersettingList(list);
    }

    @Override
    public List<Ordersetting> getOrdersettingByMonth(Date date) {
        return ordersettingMapper.getOrdersettingByMonth(date);
    }

    @Override
    public void insertOrder(Date date, int iNum) {
        //查询当前是否已经有预约，有则覆盖
        Integer cnt = ordersettingMapper.findCntByDate(date);
        if (cnt > 0){
            //覆盖
            ordersettingMapper.updateByDate(date, iNum);
        } else {
            //插入
            Ordersetting os = new Ordersetting();
            os.setNumber(iNum);
            os.setReservations(0);
            os.setOrderdate(date);
            ordersettingMapper.insert(os);
        }
    }

}