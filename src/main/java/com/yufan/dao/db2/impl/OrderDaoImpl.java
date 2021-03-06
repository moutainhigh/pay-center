package com.yufan.dao.db2.impl;

import com.yufan.common.IGeneralDao;
import com.yufan.common.IGeneralDao2;
import com.yufan.dao.db2.IOrderDao;
import com.yufan.pojo2.TbOrder;
import com.yufan.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 创建人: lirf
 * 创建时间:  2020/1/11 22:32
 * 功能介绍:
 */
//@Transactional(transactionManager = "transactionManagerSecond")
@Transactional
@Repository
public class OrderDaoImpl implements IOrderDao {

    @Autowired
    private IGeneralDao iGeneralDao;

    @Override
    public TbOrder loadOrderByOrderNo(String orderNo) {
        String hql = " from TbOrder where orderNo=?1 ";
        return iGeneralDao.queryUniqueByHql(hql, orderNo);
    }

    @Override
    public List<Map<String, Object>> queryOrderListMap(String orderNo) {
        String sql = " SELECT o.order_status,o.order_price,d.goods_name from store_db.tb_order o JOIN store_db.tb_order_detail d on o.order_id=d.order_id where o.order_no=? and o.order_status=? ";
        return iGeneralDao.getBySQLListMap(sql, orderNo, Constant.ORDER_STATUS_0);
    }
}
