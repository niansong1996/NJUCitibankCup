package nju.citicup.data.impl;

import nju.citicup.common.entity.BasicFutureInfo;
import nju.citicup.common.entity.BasicOptionInfo;
import nju.citicup.common.jsonobj.OptionExtraInfo;
import nju.citicup.data.dao.FutureDao;
import nju.citicup.data.dao.OptionDao;
import nju.citicup.data.pyalgo.PyAlgoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lenovo on 2016/9/10.
 */
@Component
public class OptionConfigDao {

    @Autowired
    OptionDao optionDao;

    @Autowired
    FutureDao futureDao;

    @Autowired
    PyAlgoClient pyAlgoClient;

    /**
     * 用户在确定成交价格之后将期权录入数据库中
     * @param basicOptionInfo 期权基础信息(行权价格和成交日期)
     * @param target 期权标的
     */
    @Transactional
    public void insertOneOption(BasicOptionInfo basicOptionInfo, String target){

        OptionExtraInfo optionExtraInfo = pyAlgoClient.getOptionInfo(basicOptionInfo, target);
        basicOptionInfo.setPrice(optionExtraInfo.getPrice());

        BasicFutureInfo basicFutureInfo = futureDao.findOne(target);
        basicFutureInfo.addOptionInfo(basicOptionInfo);
    }

    /**
     * 供用户和期权公司进行价格商讨
     * @param basicOptionInfo 期权基础信息(行权价格和成交日期)
     * @param target 期权标的
     * @return 期权价格
     */
    public double getOptionPrice(BasicOptionInfo basicOptionInfo, String target){
        OptionExtraInfo optionExtraInfo = pyAlgoClient.getOptionInfo(basicOptionInfo, target);
        return optionExtraInfo.getPrice();
    }

    @Transactional
    public List<BasicOptionInfo> getOptionInfoListByTarget(String target){
        BasicFutureInfo basicFutureInfo = futureDao.findOne(target);
        List<BasicOptionInfo> basicOptionInfoList = basicFutureInfo.getOptionInfos();

        for(BasicOptionInfo basicOptionInfo: basicOptionInfoList){
            OptionExtraInfo optionExtraInfo = pyAlgoClient.getOptionInfo(basicOptionInfo, target);
            basicOptionInfo.setGamma(optionExtraInfo.getGamma());
            basicOptionInfo.setDelta(optionExtraInfo.getDelta());
        }

        System.out.println("Target: "+target+"  OptionList's Siza: "+basicOptionInfoList.size());
        return basicOptionInfoList;
    }

    /**
     * 根据具体的ID找出期权
     * @param id
     * @return
     */
    @Transactional
    public BasicOptionInfo getOptionInfoById(int id){
        BasicOptionInfo basicOptionInfo = optionDao.findOne(id);
        BasicFutureInfo basicFutureInfo = basicOptionInfo.getBasicFutureInfo();
        String target = basicFutureInfo.getTarget();

        OptionExtraInfo optionExtraInfo = pyAlgoClient.getOptionInfo(basicOptionInfo, target);
        basicOptionInfo.setDelta(optionExtraInfo.getDelta());
        basicOptionInfo.setGamma(optionExtraInfo.getGamma());

        return basicOptionInfo;
    }

    /**
     * 用于界面上选择一定的期权之后进行操作
     * @param idList
     * @return
     */
    @Transactional
    public List<BasicOptionInfo> getOptionInfoListByIdList(List<Integer> idList){
        List<BasicOptionInfo> basicOptionInfoList = (List<BasicOptionInfo>) optionDao.findAll(idList);
        BasicFutureInfo basicFutureInfo = basicOptionInfoList.get(0).getBasicFutureInfo();
        String target = basicFutureInfo.getTarget();
        for(BasicOptionInfo basicOptionInfo: basicOptionInfoList){
            OptionExtraInfo optionExtraInfo = pyAlgoClient.getOptionInfo(basicOptionInfo, target);
            basicOptionInfo.setDelta(optionExtraInfo.getDelta());
            basicOptionInfo.setGamma(optionExtraInfo.getGamma());
        }
        return basicOptionInfoList;
    }

}