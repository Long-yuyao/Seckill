package com.yyy.seckill.service;

import com.yyy.seckill.dao.GoodsDao;
import com.yyy.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {
    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> ListGoodsVo(){
        return goodsDao.GetGoodsVoList();
    }

    public GoodsVo GetGoodsVoByGoodsId(long goodsId) {return goodsDao.GetGoodsVoByGoodsId(goodsId);}

    public void reduceStock(GoodsVo goodsVo) {
        goodsDao.reduceStock(goodsVo.getId());
    }
}
