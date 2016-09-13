package nju.citicup.WebController;

import nju.citicup.common.enumarate.OptionType;
import nju.citicup.common.enumarate.SafeType;
import nju.citicup.common.vo.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

/**
 * Created by nians on 2016/9/13.
 */
@Controller
public class CapitalController {

    @RequestMapping("/api/capital")
    @ResponseBody
    public ObjectDataWrapper getCapital(@RequestParam("futures_id") String futures_id) {
        ObjectDataWrapper wrapper = new ObjectDataWrapper();
        wrapper.setMsg("");
        wrapper.setCondition("success");

        CapitalListVO list = new CapitalListVO(10, 8, SafeType.safe, Arrays.asList(new CapitalVO("1111", OptionType.Eu, "2016-01-01", 1, 10, 20, 100)
                , new CapitalVO("1112", OptionType.Eu, "2016-01-02", 1, 21, 22, 101)));

        wrapper.setData(list);
        return wrapper;
    }

    @RequestMapping("/api/selectOptions")
    @ResponseBody
    public ObjectDataWrapper selectOptions(@RequestParam("option_list") String[] option_list) {
        ObjectDataWrapper wrapper = new ObjectDataWrapper();
        wrapper.setMsg("");
        wrapper.setCondition("success");

        double[] x_data = {5, 4, 3, 2, 1};
        double[] y_data = {1, 2, 3, 4, 5};

        GraphVO graphVO = new GraphVO(x_data, y_data);
        wrapper.setData(graphVO);

        return wrapper;
    }

    @RequestMapping("/api/predictResult")
    @ResponseBody
    public ObjectDataWrapper getPredictResult(@RequestParam("lower_gamma") int lower_gamma) {
        ObjectDataWrapper wrapper = new ObjectDataWrapper();
        wrapper.setMsg("");
        wrapper.setCondition("success");

        PredictResultVO vo = new PredictResultVO("011701", "玉米1701", 250, 50, 500, 66, 33, 500, 233, SafeType.safe);

        wrapper.setData(vo);

        return wrapper;
    }

    @RequestMapping("/api/adjustBin")
    @ResponseBody
    public ObjectDataWrapper getAdjustBin(@RequestParam("/api/"))
}
